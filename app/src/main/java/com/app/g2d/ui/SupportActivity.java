
package com.app.g2d.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.app.g2d.R;
import com.app.g2d.databinding.ActivitySupportBinding;
import com.app.g2d.ui.base.BaseActivitty;

public class SupportActivity extends BaseActivitty {

    ActivitySupportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_support);
        setUpToolbarWithBackButton((Toolbar) binding.appBar.findViewById(R.id.toolbar), getResources().getString(R.string.support));
    }
}