package com.app.g2d.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.app.g2d.R;
import com.app.g2d.databinding.ActivitySignUpBinding;
import com.app.g2d.databinding.ActivitySignUpBindingImpl;
import com.app.g2d.ui.base.BaseActivitty;

public class SignUpActivity extends BaseActivitty {

    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

    }

    public void onCreateAccount(View view) { routes.navigateToMain(this);}

    public void onLogin(View view) {
        routes.navigateToLogin(this);
    }
}