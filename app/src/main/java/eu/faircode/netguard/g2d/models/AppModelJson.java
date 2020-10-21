package eu.faircode.netguard.g2d.models;


public class AppModelJson {

    private String appName;
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private boolean isBlock = false;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index = 0;

    public AppModelJson() { }

    public AppModelJson(String appName, boolean isBlock, String packageName) {
        this.appName = appName;
        this.isBlock = isBlock;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }



    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }



}
