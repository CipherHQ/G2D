package com.app.routes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.app.g2d.ui.AddDomainBlackListActivity;
import com.app.g2d.ui.CreatePinActivity;
import com.app.g2d.ui.LoginActivity;
import com.app.g2d.ui.MainActivity;
import com.app.g2d.ui.PinProtection;
import com.app.g2d.ui.ResetPasswordActivity;
import com.app.g2d.ui.SettingsActivity;
import com.app.g2d.ui.SignUpActivity;
import com.app.g2d.ui.SupportActivity;

public class Routes {
    private static Routes routes = null;
    public static Routes getInstance() {
        if(routes == null) {
            routes = new Routes();
            return  routes;
        } else {

            return routes;
        }
    }

    public void navigateToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void navigateToSignUp(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    public void navigateToResetPassword(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }

    public void navigateToMain(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    public void navigateToSettings(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public void navigateToPinProtection(Context context) {
        Intent intent = new Intent(context, PinProtection.class);
        context.startActivity(intent);
    }

    public void navigateToPinCreate(Context context) {
        Intent intent = new Intent(context, CreatePinActivity.class);
        context.startActivity(intent);
    }

    public void navigateToAddDomainBlacklist(Context context) {
        Intent intent = new Intent(context, AddDomainBlackListActivity.class);
        context.startActivity(intent);
    }

    public void navigateToSupport(Context context) {
        Intent intent = new Intent(context, SupportActivity.class);
        context.startActivity(intent);
    }
}
