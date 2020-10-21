package eu.faircode.netguard.g2d.receivers;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.widget.Toast;

import eu.faircode.netguard.R;
import eu.faircode.netguard.g2d.listener.UpdatePinViewListenner;
import eu.faircode.netguard.g2d.localstore.LocalStore;

public class GDDeviceAdminReceiver extends DeviceAdminReceiver {

    private static UpdatePinViewListenner updatePinViewListenner;
    void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        LocalStore.setDeviceAdmin(context);
        showToast(context, context.getString(R.string.admin_receiver_status_enabled));
        updateView();

    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        //return context.getString(R.string.admin_receiver_status_disable_warning);

    showToast(context, "No Cheating");

    return super.onDisableRequested(context, intent);
    }


    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, context.getString(R.string.admin_receiver_status_disabled));
         LocalStore.removeDeviceAdmin(context);
         updateView();
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent, UserHandle userHandle ) {
        showToast(context, context.getString(R.string.admin_receiver_status_pw_changed));
    }

    private void updateView() {
        if(updatePinViewListenner != null) {updatePinViewListenner.update();}
    }
    public static void setUpdatePinViewListenner(UpdatePinViewListenner updatePinViewListenner) {

        GDDeviceAdminReceiver.updatePinViewListenner = updatePinViewListenner;
    }

}