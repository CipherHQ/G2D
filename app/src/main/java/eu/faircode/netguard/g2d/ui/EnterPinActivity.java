package eu.faircode.netguard.g2d.ui;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivityEnterPinBinding;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.ui.base.BaseActivitty;

public class EnterPinActivity extends BaseActivitty {

    ActivityEnterPinBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_pin);
        binding.pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 4) {
                    //TODO: Check leack window issue
                    binding.setPinBtn.setEnabled(true);
                    showProgressdDialog(EnterPinActivity.this, "", "Checking your pin.");
                    checkPin(charSequence.toString());

                } else {
                    binding.setPinBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void onUnInstall(View view) { checkPin(binding.pin.getText().toString());}
    private void checkPin(String pin) {
        String currentPin = LocalStore.getPin(this);
        if(currentPin.equalsIgnoreCase(pin)) {
            routes.unInstall(this);
        }
        hideProgressDialog();
    }
}