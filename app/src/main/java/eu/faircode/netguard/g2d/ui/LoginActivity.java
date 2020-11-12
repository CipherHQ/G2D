package eu.faircode.netguard.g2d.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivityLoginBinding;
import eu.faircode.netguard.g2d.api.API;
import eu.faircode.netguard.g2d.api.APIClient;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.models.RequestResponse;
import eu.faircode.netguard.g2d.models.User;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    ActivityLoginBinding binding = null;
    API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }

    public void onLogin(View view) {
        showProgressdDialog(this, "Please wait..", "");
        if(!binding.name.getText().toString().isEmpty() && !binding.password.getText().toString().isEmpty()) {
            User user = new User(null,binding.name.getText().toString(), binding.password.getText().toString(), null);
            loginUser(user);
        } else {
            hideProgressDialog();
            Toast.makeText(this, "Please enter details.", Toast.LENGTH_SHORT).show();
        }

    }

    private void loginUser(User user) {
        api = APIClient.getClient().create(API.class);

        Call<RequestResponse> call = api.login(user);

        call.enqueue(new Callback<RequestResponse>() {

            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                hideProgressDialog();
                try{
                Log.d(TAG, response.body().toString());
                RequestResponse requestResponse = response.body();
                if(requestResponse.error == null) {
                    LocalStore.setAuthToken(LoginActivity.this, requestResponse.success.token);

                    if(requestResponse.success.token != null) {
                        routes.navigateToMain(LoginActivity.this);
                        finish();
                    }
                }
                }catch (Exception ex) {  Toast.makeText(LoginActivity.this, "Something went wrong, Try again.", Toast.LENGTH_SHORT).show(); }
            }


            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, "Something went wrong, Try again.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }

    public void onResetPassword(View view) {
        routes.navigateToResetPassword(this);
    }

    public void onCreateAccount(View view) {
        onBackPressed();
    }
}