package eu.faircode.netguard.g2d.ui;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivityLoginBinding;
import eu.faircode.netguard.g2d.ui.base.BaseActivitty;

public class LoginActivity extends BaseActivitty {

    ActivityLoginBinding binding = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }

    public void onLogin(View view) {

    }

    public void onResetPassword(View view) {
        routes.navigateToResetPassword(this);
    }

    public void onCreateAccount(View view) {
        routes.navigateToSignUp(this);
    }
}