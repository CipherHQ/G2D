package eu.faircode.netguard.g2d.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivitySettingsBinding;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.services.AccessbilityService;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    ActivitySettingsBinding binding;
    private boolean pinSwitch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        setUpToolbarWithBackButton((Toolbar) binding.appBar.findViewById(R.id.toolbar), getResources().getString(R.string.settings));
         showProgressdDialog(this, "", "");

         initUi();
    }

    public void initUi() {
        int blackListSize;

        try {
            blackListSize = LocalStore.getBLockAppList(this).getList().size();

        } catch (Exception ex){
            blackListSize = 0;
        }


        if(LocalStore.isPinActive(this) && LocalStore.isDeviceAdmin(this) && AccessbilityService.isMyServiceRunning(this, AccessbilityService.class)) {
            pinSwitch = true;
            binding.pinSwitch.setChecked(true);
        }

        binding.vpnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onVpn(view);
            }
        });

        binding.pinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                onPinProtection(compoundButton);

            }
        });

        binding.domainBlockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onDomainBlackList(view);
            }
        });



        binding.appBlockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onAppBlock(view);
            }
        });



        hideProgressDialog();
    }

    public void onVpn(View view) {
        routes.navigateToVPNMain(SettingsActivity.this);

    }
    public void onAppBlock(View view) {

        onBlockAppsClick(null);

    }

    public void onPinProtection(View view) {
        pinSwitch = !pinSwitch;
        if(pinSwitch) {
            routes.navigateToPinProtection(SettingsActivity.this);
        } else {
            LocalStore.removePin(SettingsActivity.this);
        }
        binding.pinSwitch.setChecked(pinSwitch);

    }

    public void onDomainBlackList(View view) {
        routes.navigateToAddDomainBlacklist(SettingsActivity.this);

    }
    public void onBlockAppsClick(View view) {
        routes.navigateToAppBlock(this);
    }

   public void onLogout(View view) {

        routes.routeToEnterPinActivity(this, "LOG_OUT");


   }

}