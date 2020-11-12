
package eu.faircode.netguard.g2d.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivitySupportBinding;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;

public class SupportActivity extends BaseActivity {

    ActivitySupportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_support);
        setUpToolbarWithBackButton((Toolbar) binding.appBar.findViewById(R.id.toolbar), getResources().getString(R.string.support));
    }
}