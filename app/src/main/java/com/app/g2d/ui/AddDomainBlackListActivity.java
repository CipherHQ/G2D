package com.app.g2d.ui;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.app.g2d.R;
import com.app.g2d.databinding.ActivityAddDomainBlackListBinding;
import com.app.g2d.ui.base.BaseActivitty;

public class AddDomainBlackListActivity extends BaseActivitty {

    ActivityAddDomainBlackListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_domain_black_list);
        Toolbar toolbar = binding.appBar.findViewById(R.id.toolbar);
        super.setUpToolbarWithBackButton(toolbar, getResources().getString(R.string.blacklist));
    }
}