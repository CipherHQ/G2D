package com.app.g2d.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.g2d.R;
import com.app.g2d.databinding.ActivityMainBinding;
import com.app.g2d.ui.base.BaseActivitty;

public class MainActivity extends BaseActivitty {

    ActivityMainBinding binding;
    static boolean isAppRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.setUpToolbar((Toolbar) binding.appBar.findViewById(R.id.toolbar), getResources().getString(R.string.app_name));

    }

    public void onTurn(View view) {

        if(isAppRunning) {
            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_switch_on));
            binding.notificationTv.setText(getResources().getString(R.string.blocker_is_on));
        } else {
            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_switch_off));
            binding.notificationTv.setText(getResources().getString(R.string.tap_to_turn_on));


        }
        isAppRunning = !isAppRunning;
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}