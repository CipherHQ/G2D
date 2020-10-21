package eu.faircode.netguard.g2d.localstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.google.gson.Gson;

import java.util.Iterator;

import eu.faircode.netguard.g2d.models.AppModelJson;
import eu.faircode.netguard.g2d.models.BlockAppList;


public class LocalStore {

    private static final String MyPREFERENCES = "MyPrefs" ;
    private static final String appBlockListKey = "AppBlockList";
    private static final String TAG = LocalStore.class.getSimpleName();
    private static final String deviceAdminKey = "DEVICE_ADMIN";
    private static final String isPnSetup = "IS_PIN_SETUP";
    private static final String PIN = "PIN";
    private static final String AppRunning = "APP_RUNNING";
    private static final String vpnRunning = "VPN_RUNNING";

    public static void addAppToBlockList(Context context, AppModelJson appModel) {
        Gson gson = new Gson();

        BlockAppList blockAppList = getBLockAppList(context);

        if(blockAppList == null){
            blockAppList = new BlockAppList();
        }

        appModel.setIndex(blockAppList.getList().size());
        blockAppList.getList().add(appModel);

        String jsonString = gson.toJson(blockAppList);
        Log.d(TAG, "addAppToBlockList: "+jsonString);
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(appBlockListKey, jsonString);
        editor.commit();
    }

    public static void removeAppBlockList(Context context, AppModelJson appModel) {
        Gson gson = new Gson();

        BlockAppList blockAppList = getBLockAppList(context);
        Log.d(TAG, "removeAppBlockList: "+appModel.getAppName()  + appModel.isBlock() + appModel.getIndex());


        for (Iterator<AppModelJson> iterator = blockAppList.getList().iterator(); iterator.hasNext(); ) {
            AppModelJson value = iterator.next();

            if(value.getAppName().equalsIgnoreCase(appModel.getAppName())) {
                iterator.remove();
            }
        }

        String jsonString = gson.toJson(blockAppList);
        Log.d(TAG, "removeAppBlockList: "+jsonString);
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(appBlockListKey, jsonString);
        editor.commit();
    }


    public static void reset(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(appBlockListKey, null);
        editor.commit();

    }

    private static String getBlockAppListJson(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getString(appBlockListKey, null);
    }

    public static BlockAppList getBLockAppList(Context context) {
        Gson gson  = new Gson();

        BlockAppList blockAppList;
        String jsoData = getBlockAppListJson(context);

        //List<AppModel> students = gson.fromJson(json, type);
        blockAppList =  gson.fromJson(jsoData, BlockAppList.class);
        return  blockAppList;
    }


    public static void setDeviceAdmin(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(deviceAdminKey, true);
        editor.commit();

    }

    public static boolean isDeviceAdmin(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getBoolean(deviceAdminKey, false);
    }

    public static void removeDeviceAdmin(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(deviceAdminKey, false);
        editor.commit();

    }

    public static void setPin(Context context, String pin) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(isPnSetup, true);
        editor.putString(PIN, pin);
        editor.commit();
    }



    public static void removePin(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(isPnSetup, false);
        editor.commit();

    }

    public static boolean isPinActive(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getBoolean(isPnSetup, false);
    }

    public static String getPin(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getString(PIN, null);
    }

    public static Boolean isAppRunning(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getBoolean(AppRunning, false);
    }

    public static void setAppRunning(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(AppRunning, true);
        editor.commit();
    }



    public static void removeAppRunning(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(AppRunning, false);
        editor.commit();

    }

    public static void setVPNRunning(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(vpnRunning, true);
        editor.commit();
    }

    public static void removeVPNRunning(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(vpnRunning, false);
        editor.commit();

    }

    public static Boolean isVPNRunning(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getBoolean(vpnRunning, false);
    }
}
