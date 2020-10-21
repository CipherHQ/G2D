package eu.faircode.netguard.g2d.models;

import android.graphics.drawable.Drawable;

public class AppModel {

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private String packageName;
    private String appName;
    private Drawable appImage;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppImage() {
        return appImage;
    }

    public void setAppImage(Drawable appImage) {
        this.appImage = appImage;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    private boolean isBlock = false;

    public AppModel() { }

//    public AppModel(String appName, Drawable appImage, boolean isBlock) {
//        this.appName = appName;
//        this.appImage = appImage;
//        this.isBlock = isBlock;
//    }
}
