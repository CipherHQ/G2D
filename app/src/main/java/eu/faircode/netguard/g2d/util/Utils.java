package eu.faircode.netguard.g2d.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.preference.PreferenceManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.faircode.netguard.DownloadTask;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.models.AppModel;
import eu.faircode.netguard.g2d.models.AppModelJson;
import eu.faircode.netguard.g2d.models.BlockAppList;

public class Utils {

    public static String pinEnterKey = "PIN_ENTER_KEY";
    private static boolean appsLoaded = false;
    private static List<AppModel> loadedApps = null;

    public static List<AppModel> getInstalledAppList(Context context) {

        if(!appsLoaded) {
            List<AppModel> list = new ArrayList<>();
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> apps = pm.getInstalledApplications(0);
            BlockAppList blockList = LocalStore.getBLockAppList(context);
            String TAG = Utils.class.getSimpleName();

            for (ApplicationInfo app: apps) {

                if(!isSystemPackage(app) && !isOwnApp(app)) {
                    String label = (String)pm.getApplicationLabel(app);
                    Drawable icon = pm.getApplicationIcon(app);
                    AppModel appModel = new AppModel();
                    appModel.setAppName(label);
                    appModel.setAppImage(icon);
                    appModel.setBlock(false);
                    appModel.setPackageName(app.packageName);


                    try {
                        for (AppModelJson modelJson : blockList.getList()) {
                            if(modelJson.getAppName().equalsIgnoreCase(appModel.getAppName())) {
                                appModel.setBlock(true);
                            }
                        }
                    } catch (Exception ex) {}

                    list.add(appModel);
                }

            }
            loadedApps = list;
            appsLoaded = true;
            return  list;

        } else {

            return loadedApps;
        }

    }

    private static boolean isSystemPackage(ApplicationInfo pkgInfo) {

        return (pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    private static boolean isOwnApp(ApplicationInfo packageInfo) {

        if(packageInfo.packageName.equalsIgnoreCase("eu.faircode.netguard")) {
            return true;
        }

        return false;
    }


    public static void downloadHostFile(final Activity context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        final File tmp = new File(context.getFilesDir(), "hosts.tmp");
        final File hosts = new File(context.getFilesDir(), "hosts.txt");
        System.out.println("==>Hostpath "+hosts.getPath());
         String hosts_url = "https://www.netguard.me/hosts";

        try {
            new DownloadTask(context, new URL(hosts_url), tmp, new DownloadTask.Listener() {
                @Override
                public void onCompleted() {
                    if (hosts.exists())
                        hosts.delete();
                    tmp.renameTo(hosts);

                    String last = SimpleDateFormat.getDateTimeInstance().format(new Date().getTime());
                    prefs.edit().putString("hosts_last_download", last).apply();

                   // ServiceSinkhole.reload("hosts file download",  context, false);
                }

                @Override
                public void onCancelled() {
                    if (tmp.exists())
                        tmp.delete();
                }

                @Override
                public void onException(Throwable ex) {
                    if (tmp.exists())
                        tmp.delete();


                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (MalformedURLException ex) {
                     ex.printStackTrace();
        }
    }


    public static void setupVPNFilter() {


    }


}
