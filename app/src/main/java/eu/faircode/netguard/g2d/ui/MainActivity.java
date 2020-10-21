package eu.faircode.netguard.g2d.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import eu.faircode.netguard.ActivityNetguardMain;
import eu.faircode.netguard.R;
import eu.faircode.netguard.ServiceSinkhole;
import eu.faircode.netguard.Util;
import eu.faircode.netguard.databinding.ActivityMainBinding;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.services.AccessbilityService;
import eu.faircode.netguard.g2d.ui.base.BaseActivitty;
import eu.faircode.netguard.g2d.util.Utils;

public class MainActivity extends BaseActivitty {

    String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    private int REQUEST_CODE_ENABLE_WINDOW = 1;
    private int REQUEST_CODE_VPN = 2;
    static boolean isAppRunning = false;
    Intent vpnIntent;
    private AlertDialog dialogDoze = null;
    private AlertDialog dialogVpn = null;
    private boolean running = false;
    private AlertDialog dialogFirst = null;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.setUpToolbar((Toolbar) binding.appBar.findViewById(R.id.toolbar), getResources().getString(R.string.app_name));
        isAppRunning = LocalStore.isAppRunning(this);
//      /  Utils.downloadHostFile(this);
        if (!Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            myIntent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(myIntent, REQUEST_CODE_ENABLE_WINDOW);
        }

        onTurn(null);
    }

    public void onTurn(View view) {


        if (isAppRunning) {

            if (LocalStore.isDeviceAdmin(this) && LocalStore.isPinActive(this) && AccessbilityService.isMyServiceRunning(this, AccessbilityService.class)) {

                setAppRunning();

            } else {

                Toast.makeText(this, "Please complete pin process first.", Toast.LENGTH_SHORT).show();
            }


        } else {
            ServiceSinkhole.stop("switch off", MainActivity.this, false);

            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_switch_off));
            binding.notificationTv.setText(getResources().getString(R.string.tap_to_turn_on));
            LocalStore.removeAppRunning(this);


        }
        isAppRunning = !isAppRunning;
    }

    private void setAppRunning() {
        binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_switch_on));
        binding.notificationTv.setText(getResources().getString(R.string.blocker_is_on));
        LocalStore.setAppRunning(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_support:
                routes.navigateToSupport(this);
                return true;
            case R.id.action_settings:
                routes.navigateToSettings(this);
                return true;
            case R.id.action_uninstall:
                Log.d(TAG, "onOptionsItemSelected: Uninstall");
                if (LocalStore.isPinActive(this)) {
                    routes.routeToEnterPinActivity(this);
                } else {
                    routes.unInstall(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_VPN) {
            // Handle VPN approval
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putBoolean("enabled", resultCode == RESULT_OK).commit();
            if (resultCode == RESULT_OK) {
                ServiceSinkhole.start("prepared", this);

//                Toast on = Toast.makeText(MainActivity.this, R.string.msg_on, Toast.LENGTH_LONG);
//                on.setGravity(Gravity.CENTER, 0, 0);
//                on.show();

            } else if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, R.string.msg_vpn_cancelled, Toast.LENGTH_LONG).show();

        }
    }
}