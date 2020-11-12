package eu.faircode.netguard.g2d.localstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.google.gson.Gson;

import java.util.Iterator;

import eu.faircode.netguard.g2d.models.AppModelJson;
import eu.faircode.netguard.g2d.models.BlockAppList;
import eu.faircode.netguard.g2d.models.User;


public class LocalStore {

    private static final String MyPREFERENCES = "MyPrefs" ;
    private static final String appBlockListKey = "AppBlockList";
    private static final String TAG = LocalStore.class.getSimpleName();
    private static final String deviceAdminKey = "DEVICE_ADMIN";
    private static final String isPnSetup = "IS_PIN_SETUP";
    private static final String PIN = "PIN";
    private static final String AppRunning = "APP_RUNNING";
    private static final String vpnRunning = "VPN_RUNNING";
    private static final String authTokenKey = "AUTH_TOKEN_KEY";
    private static final String userKey = "USER_KEY";
    private static final String domainUpdateKey = "DOMAIN_UPDATE_KEY";

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

    public static void setAuthToken(Context context, String authToken) {
        Log.d(TAG, "setAuthToken: "+authToken);
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(authTokenKey, authToken);
        editor.commit();
    }


    public static String getAuthToken(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getString(authTokenKey, null);
    }

    public static void storeUser(Context context, User user) {

        Gson gson = new Gson();
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String data = gson.toJson(user, User.class);
        editor.putString(userKey, data);
        editor.commit();

    }

    public static User getUser(Context context) {

        Gson gson = new Gson();
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String data =  sharedpreferences.getString(userKey, null);
        return gson.fromJson(data, User.class);
    }

    public static void removeData(Context context) {
        removePin(context);
        setAuthToken(context, null);
    }

    public static void setDomainUpdate(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(domainUpdateKey, true);
        editor.commit();

    }

    public static boolean getDomainUpdate(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return  sharedpreferences.getBoolean(domainUpdateKey, false);
    }


}
