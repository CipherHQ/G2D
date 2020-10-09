package com.app.g2d.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.g2d.R;
import com.app.g2d.ui.base.BaseActivitty;

public class PinProtection extends BaseActivitty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_protection);
    }

    public void onCreatePin(View view) {
        routes.navigateToPinCreate(this);
    }
}