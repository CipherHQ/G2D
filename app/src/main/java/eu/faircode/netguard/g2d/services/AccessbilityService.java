package eu.faircode.netguard.g2d.services;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import eu.faircode.netguard.R;
import eu.faircode.netguard.g2d.listener.UpdatePinViewListenner;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.models.AppModelJson;

import java.util.List;

public class AccessbilityService extends AccessibilityService {
    private String TAG = AccessbilityService.class.getSimpleName();
    private WindowManager windowManager;
    private View interceptView;
    private WindowManager.LayoutParams params;
    private TextView infoTv = null;

    public static boolean isRunning = false;
    private static final String APPNAME = "[G2D]";
    private static final String DEVICE_ADMIN_APP = "[Device admin app]";
    private static UpdatePinViewListenner updatePinViewListenner;
    private static  final String UNINSTALL_KEY = "[Uninstall]";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        if(!LocalStore.isAppRunning(this)) {return;}

        final int eventType = accessibilityEvent.getEventType();
        AccessibilityNodeInfo source = accessibilityEvent.getSource();
        if (source == null) {
            return;
        }

        Log.d(TAG, "onAccessibilityEvent: "+accessibilityEvent.getSource().getPackageName());
        Log.d(TAG, "onAccessibilityEvent: "+accessibilityEvent.getSource().getViewIdResourceName());
        String packageName = accessibilityEvent.getSource().getPackageName().toString();
        String eventText = null;
        switch(eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d(TAG, "onAccessibilityEvent: Window state changed"+accessibilityEvent.getPackageName());
//                Log.d(TAG, "onAccessibilityEvent: " + accessibilityEvent.getSource().getChild(0).getText());


                //windowManager.removeViewImmediate(interceptView);
                //checkForDeviceAdminSettings(accessibilityEvent);
                checkForBlockApps(accessibilityEvent.getSource().getPackageName().toString());
                break;

            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                Log.d(TAG, "onAccessibilityEvent: LongClicked");
                checkUninstall(accessibilityEvent);

                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.d(TAG, "onAccessibilityEvent: TYPE_VIEW_CLICKED" + accessibilityEvent.getText());
                checkForAccessbillitySettings(accessibilityEvent);
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "Focused: ";
                Log.d(TAG, "onAccessibilityEvent: Focused " + accessibilityEvent.getText());
                break;

        }



    }



    public void checkForBlockApps(String packageName) {

        List<AppModelJson> appModelList;
        try {
            appModelList = LocalStore.getBLockAppList(this).getList();
        } catch (Exception ex){
            return;
        }

        if(appModelList == null) {
            return;
        }

        for (AppModelJson appModelJson: appModelList) {

            if(packageName.equalsIgnoreCase(appModelJson.getPackageName())) {

                doIntercept("You have blacklisted this application on G2D, please remove application from blacklist to use");

            }
        }

    }


    public void checkForDeviceAdminSettings (AccessibilityEvent event) {

        String nodeText = event.getText().toString();
        Log.d(TAG, "checkForDeviceAdminSettings: "+nodeText);

        if(event.getText().toString().equalsIgnoreCase(DEVICE_ADMIN_APP)) {
            //doIntercept();
        }
    }

    public void checkForAccessbillitySettings(AccessibilityEvent event) {
        String eventText = event.getText().toString();
        Log.d(TAG, "checkForAccessbillitySettings: "+eventText.contains("G2D"));
        if(eventText.contains(  "G2D" )) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            performGlobalAction(AccessbilityService.GLOBAL_ACTION_HOME);

            doIntercept("You have enabled pin protect on G2D \nplease uninstall app in G2D Application");
        }
    }
    @Override
    public void onInterrupt() {

    }

    @Override
    public void onServiceConnected() {
        Log.d(TAG, "onServiceConnected: ");
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){

        // several lines of awesome code

        return START_STICKY;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkUninstall(AccessibilityEvent event) {
        Log.d(TAG, "checkUninstall: "+event.getText());

        if(event.getText().toString().equalsIgnoreCase(APPNAME)) {
            doIntercept("You have enabled pin protect on G2D, please uninstall app in G2D Application");
        }
//        windowManager.addView(interceptView, params);
//        String packageName = event.getSource().getPackageName().toString();
//        for ( String s:
//                event.getSource().getAvailableExtraData()) {
//            System.out.println(event.getSource().getExtras().getString(s));
//        }
//        if(packageName.equalsIgnoreCase(SystemPackages.getLaunchgerPackage())) {
//            Log.d(TAG, "checkUninstall: ");
//        }
        

    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(updatePinViewListenner != null) {updatePinViewListenner.update();}

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        interceptView = inflater.inflate(R.layout.intercept_layout, null);
        infoTv = interceptView.findViewById(R.id.skip);
        Button closeBtn = interceptView.findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowManager.removeView(interceptView);
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            }
        });


        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

                params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

    }

    private void doIntercept(String s) {

        infoTv.setText(s);

        try {
            windowManager.addView(interceptView, params);

        }catch (Exception ex) {

            windowManager.removeViewImmediate(interceptView);
            windowManager.addView(interceptView, params);
        }
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void setUpdatePinViewListenner(UpdatePinViewListenner updatePinViewListenner) {
        AccessbilityService.updatePinViewListenner = updatePinViewListenner;
    }

}
