package eu.faircode.netguard.g2d.ui.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import eu.faircode.netguard.g2d.routes.Routes;


public class BaseActivity extends AppCompatActivity {

    public Routes routes = Routes.getInstance();
    private ProgressDialog gdProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgressdDialog(Context context, String title, String msg) {
        if(gdProgressDialog == null) {
            gdProgressDialog = new ProgressDialog(context);
        }

        gdProgressDialog.setCanceledOnTouchOutside(false);
        gdProgressDialog.setTitle(title);
        gdProgressDialog.setMessage(msg);
        gdProgressDialog.show();
    }

    public void hideProgressDialog() {

        if(gdProgressDialog != null) {gdProgressDialog.dismiss();}
    }
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
