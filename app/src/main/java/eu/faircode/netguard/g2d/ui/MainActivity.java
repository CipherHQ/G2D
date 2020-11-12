package eu.faircode.netguard.g2d.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import eu.faircode.netguard.R;
import eu.faircode.netguard.ServiceSinkhole;
import eu.faircode.netguard.databinding.ActivityMainBinding;
import eu.faircode.netguard.g2d.api.API;
import eu.faircode.netguard.g2d.api.APIClient;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.models.Domain;
import eu.faircode.netguard.g2d.models.RequestResponse;
import eu.faircode.netguard.g2d.models.User;
import eu.faircode.netguard.g2d.models.UserResponse;
import eu.faircode.netguard.g2d.services.AccessbilityService;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    private int REQUEST_CODE_ENABLE_WINDOW = 1;
    private int REQUEST_CODE_VPN = 2;
    public static boolean isAppRunning = false;
    boolean wasAppRunning = false;
    Intent vpnIntent;
    private AlertDialog dialogDoze = null;
    private AlertDialog dialogVpn = null;
    private boolean running = false;
    private AlertDialog dialogFirst = null;
    private  API api = null;
    private  UserResponse userResponse;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.setUpToolbar((Toolbar) binding.appBar.findViewById(R.id.toolbar), getResources().getString(R.string.app_name));
        loadData();
        isAppRunning = LocalStore.isAppRunning(this);
//      /  Utils.downloadHostFile(this);
        if (!Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            myIntent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(myIntent, REQUEST_CODE_ENABLE_WINDOW);
        }

        onTurn(null);
    }

    private void loadData() {
        showProgressdDialog(this, "Loading data, please wait..", "");
        api = APIClient.getClient().create(API.class);

        Call<UserResponse> call = api.details("Bearer " + LocalStore.getAuthToken(this));

        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                hideProgressDialog();
                if(response.body() == null) {
                    routes.navigateToSignUp(MainActivity.this);
                    finish();
                    return;
                }
                Log.d(TAG, response.body().toString());
                userResponse = response.body();
                if(userResponse.subscription.status.equalsIgnoreCase("Active")) {
                    Log.d(TAG, "onResponse: "+userResponse.user.name);
                    LocalStore.storeUser(MainActivity.this, userResponse.user);
                    if(userResponse.user.updateDomain == 1) {
                        Log.d(TAG, "onResponse: updating domain");
                        loadDomains();
                    }
                } else {

                    Toast.makeText(MainActivity.this, "Sorry, your account is suspended!", Toast.LENGTH_SHORT).show();
                    routes.navigateToSignUp(MainActivity.this);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "Something went wrong, Try again.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });


    }

    public void onTurn(View view) {

        if (isAppRunning) {

            if (LocalStore.isDeviceAdmin(this) && LocalStore.isPinActive(this) && AccessbilityService.isMyServiceRunning(this, AccessbilityService.class)) {

                setAppRunning();
                wasAppRunning = true;

            } else {

                Toast.makeText(this, "Please complete pin process first.", Toast.LENGTH_SHORT).show();
            }


        } else {
            if(wasAppRunning)  {
                wasAppRunning = false;
                routes.routeToEnterPinActivity(this, "STOP_APP");

            }
            binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_switch_off));
            binding.notificationTv.setText(getResources().getString(R.string.tap_to_turn_on));
        }
        isAppRunning = !isAppRunning;
    }

    private void setAppRunning() {
        binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_switch_on));
        binding.notificationTv.setText(getResources().getString(R.string.blocker_is_on));
        LocalStore.setAppRunning(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_support:
                routes.navigateToSupport(this);
                return true;
            case R.id.action_settings:
                routes.navigateToSettings(this);
                return true;
            case R.id.action_uninstall:
                Log.d(TAG, "onOptionsItemSelected: Uninstall");
                if (LocalStore.isPinActive(this)) {
                    routes.routeToEnterPinActivity(this, "UNINSTALL");
                } else {
                    routes.unInstall(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadDomains() {
        Log.d(TAG, "loadDomains: ");
        showProgressdDialog(this, "Loading domains, please wait..", "");
        api = APIClient.getClient().create(API.class);

        Call<List<Domain>> call = api.getDomains();

        call.enqueue(new Callback<List<Domain>>() {

            @Override
            public void onResponse(Call<List<Domain>> call, Response<List<Domain>> response) {
                Log.d(TAG, response.body().toString());
                List<Domain> domains = response.body();
                File hosts = new File(getFilesDir(), "hosts.txt");
                if(!hosts.exists()) {
                    try {
                        hosts.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for (Domain domain : domains) {
                    try {
                        String data = "0.0.0.0 "+domain.domain+"\r\n";
                        Log.d(TAG, "onResponse: "+data);

                        FileOutputStream outputStream = new FileOutputStream(getFilesDir()+"/hosts.txt", true);
                        byte[] strToBytes = data.getBytes();
                        outputStream.write(strToBytes);

                        outputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                LocalStore.setDomainUpdate(MainActivity.this);
                setDomainUpdate();
                hideProgressDialog();

            }

            @Override
            public void onFailure(Call<List<Domain>> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "Something went wrong, Try again.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }

    private void setDomainUpdate() {
        Call<UserResponse> call = api.setDomainUpdate("Bearer " + LocalStore.getAuthToken(this));

        call.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                hideProgressDialog();
                Log.d(TAG, response.body().toString());
                userResponse = response.body();
                Log.d(TAG, "onResponse: "+userResponse.user.updateDomain);
                LocalStore.storeUser(MainActivity.this, userResponse.user);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "Something went wrong, Try again.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_VPN) {
            // Handle VPN approval
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putBoolean("enabled", resultCode == RESULT_OK).commit();
            if (resultCode == RESULT_OK) {
                ServiceSinkhole.start("prepared", this);

//                Toast on = Toast.makeText(MainActivity.this, R.string.msg_on, Toast.LENGTH_LONG);
//                on.setGravity(Gravity.CENTER, 0, 0);
//                on.show();

            } else if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, R.string.msg_vpn_cancelled, Toast.LENGTH_LONG).show();

        }
    }
}