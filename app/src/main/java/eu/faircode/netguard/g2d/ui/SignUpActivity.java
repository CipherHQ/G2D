package eu.faircode.netguard.g2d.ui;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivitySignUpBinding;
import eu.faircode.netguard.g2d.api.API;
import eu.faircode.netguard.g2d.api.APIClient;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.models.RequestResponse;
import eu.faircode.netguard.g2d.models.User;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    ActivitySignUpBinding binding;
    API api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

    }

    public void onCreateAccount(View view) {
        showProgressdDialog(this, "Creating account..","");
        if(!binding.name.getText().toString().isEmpty() && !binding.email.getText().toString().isEmpty() && !binding.password.getText().toString().isEmpty()) {
             User user = new User(binding.name.getText().toString(), binding.email.getText().toString(), binding.password.getText().toString(), binding.password.getText().toString());
          registerUser(user);

        } else {
            hideProgressDialog();
            Toast.makeText(this, "Please enter details.", Toast.LENGTH_SHORT).show();

        }
    }

    private void registerUser(User user) {

         api = APIClient.getClient().create(API.class);

        Call<RequestResponse> call = api.register(user);

        call.enqueue(new Callback<RequestResponse>() {

            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                hideProgressDialog();
                Log.d(TAG, response.body().toString());
                RequestResponse requestResponse = response.body();
                if(requestResponse.error == null) {
                    LocalStore.setAuthToken(SignUpActivity.this, requestResponse.success.token);

                    if(requestResponse.success.token != null) {routes.navigateToMain(SignUpActivity.this);}
                }

            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(SignUpActivity.this, "Something went wrong, Try again.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }

    public void onLogin(View view) {
        routes.navigateToLogin(this);
    }
}