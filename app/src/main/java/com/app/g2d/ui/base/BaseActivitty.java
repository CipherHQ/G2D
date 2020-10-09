package com.app.g2d.ui.base;


import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.routes.Routes;

public class BaseActivitty extends AppCompatActivity {

    public Routes routes = Routes.getInstance();

    public void setUpToolbarWithBackButton(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        super.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void setUpToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        super.setSupportActionBar(toolbar);
    }


}
