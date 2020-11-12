package eu.faircode.netguard.g2d.routes;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.regex.Pattern;

import eu.faircode.netguard.ActivityNetguardMain;
import eu.faircode.netguard.g2d.receivers.GDDeviceAdminReceiver;
import eu.faircode.netguard.g2d.ui.AddDomainBlackListActivity;
import eu.faircode.netguard.g2d.ui.AppBlockActivity;
import eu.faircode.netguard.g2d.ui.CreatePinActivity;
import eu.faircode.netguard.g2d.ui.EnterPinActivity;
import eu.faircode.netguard.g2d.ui.LoginActivity;
import eu.faircode.netguard.g2d.ui.MainActivity;
import eu.faircode.netguard.g2d.ui.PinProtection;
import eu.faircode.netguard.g2d.ui.ResetPasswordActivity;
import eu.faircode.netguard.g2d.ui.SettingsActivity;
import eu.faircode.netguard.g2d.ui.SignUpActivity;
import eu.faircode.netguard.g2d.ui.SupportActivity;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;
import eu.faircode.netguard.g2d.util.Utils;


public class Routes {

    private static Routes routes = null;
    private static String TAG = Routes.class.getSimpleName();

    public static Routes getInstance() {
        if (routes == null) {
            routes = new Routes();
            return routes;
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void navigateToResetPassword(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }

    public void navigateToMain(BaseActivity context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public void navigateToVPNMain(Context context) {

        Intent intent = new Intent(context, ActivityNetguardMain.class);
        context.startActivity(intent);

    }

    public void navigateToSupport(Context context) {
        Intent intent = new Intent(context, SupportActivity.class);
        context.startActivity(intent);
    }

    public void navigateToAppBlock(Context context) {

        Intent intent = new Intent(context, AppBlockActivity.class);
        context.startActivity(intent);
    }

    public void routeToEnterPinActivity(Context context, String keyFor) {
        Intent intent = new Intent(context, EnterPinActivity.class);
        intent.putExtra(Utils.pinEnterKey, keyFor);
        context.startActivity(intent);
    }

    public void unInstall(Context context) {
        String packageName = context.getPackageName();
        Log.d(TAG, "unInstall: "+packageName);
        ComponentName deviceAdminComponent = new ComponentName(context, GDDeviceAdminReceiver.class);

        DevicePolicyManager mDPM = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDPM.removeActiveAdmin(deviceAdminComponent);
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:"+packageName));
        context.startActivity(intent);
    }




}
