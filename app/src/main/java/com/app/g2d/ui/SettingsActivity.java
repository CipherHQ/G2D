package com.app.g2d.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.app.g2d.R;
import com.app.g2d.databinding.ActivitySettingsBinding;
import com.app.g2d.ui.base.BaseActivitty;

public class SettingsActivity extends BaseActivitty {

    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        setUpToolbarWithBackButton((Toolbar) binding.appBar.findViewById(R.id.toolbar), getResources().getString(R.string.settings));
        binding.pinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    routes.navigateToPinProtection(SettingsActivity.this);
                }
            }
        });

        binding.domainBlockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    routes.navigateToAddDomainBlacklist(SettingsActivity.this);
                }
            }
        });

    }
}