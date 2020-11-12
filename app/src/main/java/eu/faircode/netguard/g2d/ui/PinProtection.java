package eu.faircode.netguard.g2d.ui;

import androidx.databinding.DataBindingUtil;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivityPinProtectionBinding;
import eu.faircode.netguard.g2d.listener.UpdatePinViewListenner;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.receivers.GDDeviceAdminReceiver;
import eu.faircode.netguard.g2d.services.AccessbilityService;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;


public class PinProtection extends BaseActivity implements UpdatePinViewListenner {

    ActivityPinProtectionBinding binding;
    private int REQUEST_CODE_ENABLE_ADMIN = 0;
    private int REQUEST_CODE_ENABLE_WINDOW = 1;
    private DeviceAdminReceiver deviceAdminReceiver;
    private ComponentName deviceAdminComponent;
    DevicePolicyManager mDPM;
    private String TAG = PinProtection.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pin_protection);
        showProgressdDialog(this, "","Loading..");
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        deviceAdminComponent = new ComponentName(this, DeviceAdminReceiver.class);
        AccessbilityService.setUpdatePinViewListenner(this);
        GDDeviceAdminReceiver.setUpdatePinViewListenner(this);
        CreatePinActivity.setUpdatePinViewListenner(this);
        initUI();


    }

    public void initUI() {

        if(isAdmin() || LocalStore.isDeviceAdmin(this)) {
            binding.step1Btn.setEnabled(false);
            binding.step1Btn.setBackgroundColor(getResources().getColor(R.color.dark_grey));
            binding.step1Btn.setText("Done!");
        }

        if(AccessbilityService.isMyServiceRunning(this, AccessbilityService.class)) {
            binding.step2Btn.setEnabled(false);
            binding.step2Btn.setBackgroundColor(getResources().getColor(R.color.dark_grey));
            binding.step2Btn.setText("Done!");
        }


        if(LocalStore.isPinActive(this)) {

            binding.step3Btn.setEnabled(false);
            binding.step3Btn.setBackgroundColor(getResources().getColor(R.color.dark_grey));
            binding.step3Btn.setText("Done!");
        }

        hideProgressDialog();
    }
    public boolean isAdmin() {
        return mDPM.isAdminActive(deviceAdminComponent);
    }
    public void onCreatePin(View view) {
        routes.navigateToPinCreate(this);
    }

    public void onDeviceAdmin(View view) {

        if(mDPM.isAdminActive(deviceAdminComponent) == false) {
            Log.d(TAG, "onDeviceAdmin: ");
            // Launch the activity to have the user enable our admin.
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminComponent);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    this.getString(R.string.add_admin_extra_app_text));
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings"));
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
        }

    }

    public void onAccessablity(View view) {

        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        startService(new Intent(this,AccessbilityService.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult: " + resultCode);
        if (resultCode == REQUEST_CODE_ENABLE_ADMIN) {

            update();
        } else if(resultCode == REQUEST_CODE_ENABLE_WINDOW) {

            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            startService(new Intent(this,AccessbilityService.class));

        } else {
            Toast.makeText(this, "Device administrator must be activated to lock the screen", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void update() {
        initUI();
    }

    @Override
    public void onBackPressed() {

        if(LocalStore.isPinActive(this) && LocalStore.isDeviceAdmin(this) && AccessbilityService.isMyServiceRunning(this, AccessbilityService.class))
        {
            routes.navigateToMain(this);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}