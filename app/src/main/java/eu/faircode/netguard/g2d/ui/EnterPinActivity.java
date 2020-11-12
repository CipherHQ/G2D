package eu.faircode.netguard.g2d.ui;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import eu.faircode.netguard.R;
import eu.faircode.netguard.ServiceSinkhole;
import eu.faircode.netguard.databinding.ActivityEnterPinBinding;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;
import eu.faircode.netguard.g2d.util.Utils;

public class EnterPinActivity extends BaseActivity {
    private String keyFor = null;
    ActivityEnterPinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_pin);
        setPinBtn(false);
        keyFor = getIntent().getExtras().getString(Utils.pinEnterKey);

        if(keyFor.equalsIgnoreCase("UNINSTALL")) {
            binding.setPinBtn.setText("Uninstall");

        } else if(keyFor.equalsIgnoreCase("STOP_APP")) {
            binding.setPinBtn.setText("Stop App");
        } else if(keyFor.equalsIgnoreCase("LOG_OUT")) {
            binding.setPinBtn.setText("Log Out");
        }



        binding.pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 4) {
                    //TODO: Check leack window issue
                    setPinBtn(true);

                    showProgressdDialog(EnterPinActivity.this, "", "Checking your pin.");
                    checkPin(charSequence.toString());

                } else {
                    setPinBtn(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void setPinBtn(boolean visible) {
        if(visible) {
            binding.setPinBtn.setEnabled(true);
            binding.setPinBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            binding.setPinBtn.setEnabled(false);
            binding.setPinBtn.setBackgroundColor(getResources().getColor(R.color.dark_grey));
        }
    }

    public void onUnInstall(View view) {

        if(binding.pin.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter pin to uninstall", Toast.LENGTH_SHORT).show();
        return;
        }
        checkPin(binding.pin.getText().toString());

    }

        private void checkPin(String pin) {
        String currentPin = LocalStore.getPin(this);
        if(currentPin == null )
        {
            Toast.makeText(this, "Please set pin first.", Toast.LENGTH_SHORT).show();
            onBackPressed();

        }else if(currentPin.equalsIgnoreCase(pin)) {

            if(keyFor.equalsIgnoreCase("UNINSTALL")) {
                routes.unInstall(this);

            } else if(keyFor.equalsIgnoreCase("STOP_APP")) {
            stopAPP();
            } else if(keyFor.equalsIgnoreCase("LOG_OUT")) {
                logOut();
            }
        }
        hideProgressDialog();
    }

    private void stopAPP() {
        ServiceSinkhole.stop("switch off", this, false);
        LocalStore.removeAppRunning(this);
        MainActivity.isAppRunning = false;
        routes.navigateToMain(this);
        finish();
    }

    private void logOut() {
        LocalStore.removeData(this);
        routes.navigateToSignUp(this);
        finish();
    }

    @Override
    public void onBackPressed() {
    //    super.onBackPressed();
    routes.navigateToMain(this);
    finish();
    }
}