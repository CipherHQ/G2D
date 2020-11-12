package eu.faircode.netguard.g2d.ui;

import android.os.Bundle;
import android.os.Handler;

import eu.faircode.netguard.R;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;
import eu.faircode.netguard.g2d.util.Utils;

public class SplashScreen extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Utils.getInstalledAppList(this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                 if(LocalStore.getAuthToken(SplashScreen.this) == null || LocalStore.getAuthToken(SplashScreen.this).isEmpty()) {
                     routes.navigateToSignUp(SplashScreen.this);
                 } else {
                     routes.navigateToMain(SplashScreen.this);
                 }

                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}