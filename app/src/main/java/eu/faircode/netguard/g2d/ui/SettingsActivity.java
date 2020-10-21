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
import eu.faircode.netguard.g2d.ui.base.BaseActivitty;

public class SettingsActivity extends BaseActivitty {

    ActivitySettingsBinding binding;

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
            binding.pinSwitch.setChecked(true);
        }

        binding.vpnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 routes.navigateToVPNMain(SettingsActivity.this);
            }
        });

        binding.pinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    routes.navigateToPinProtection(SettingsActivity.this);
                } else {
                    LocalStore.removePin(SettingsActivity.this);
                }

            }
        });

        binding.domainBlockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routes.navigateToAddDomainBlacklist(SettingsActivity.this);

            }
        });



        binding.appBlockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBlockAppsClick(null);

            }
        });



        hideProgressDialog();
    }


    public void onBlockAppsClick(View view) {
        showProgressdDialog(this, "", "Loading your apps.");
        routes.navigateToAppBlock(this);
        hideProgressDialog();
    }
}