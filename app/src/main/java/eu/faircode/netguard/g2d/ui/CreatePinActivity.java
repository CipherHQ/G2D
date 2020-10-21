package eu.faircode.netguard.g2d.ui;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivityCreatePinBinding;
import eu.faircode.netguard.g2d.listener.UpdatePinViewListenner;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.ui.base.BaseActivitty;

public class CreatePinActivity extends BaseActivitty {

    ActivityCreatePinBinding binding;
    private static UpdatePinViewListenner updatePinViewListenner;
    private String firstPin = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_pin);


        binding.pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() == 4) {
                    //TODO: Check leack window issue

                    validatePin(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void validatePin(String pin) {
        if(firstPin == null) {
            binding.textView.setText("Re-enter 4-digit pin");
            firstPin = pin;
            binding.pin.setText("");

        } else if(firstPin.equalsIgnoreCase(pin)) {


            showProgressdDialog(CreatePinActivity.this, "", "Creating pin.");
            LocalStore.setPin(CreatePinActivity.this, firstPin);
            updateView();
             onBackPressed();
        } else {
            binding.pin.setText("");
            Toast.makeText(this, "Please enter same pin.", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateView() {
        if(updatePinViewListenner != null) { updatePinViewListenner.update();}
    }
    public static void setUpdatePinViewListenner(UpdatePinViewListenner updatePinViewListenner) {
        CreatePinActivity.updatePinViewListenner = updatePinViewListenner;
    }
}