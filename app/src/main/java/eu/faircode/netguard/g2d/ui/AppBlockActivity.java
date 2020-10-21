package eu.faircode.netguard.g2d.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import eu.faircode.netguard.R;

import eu.faircode.netguard.R;
import eu.faircode.netguard.databinding.ActivityAppBlockBinding;
import eu.faircode.netguard.g2d.models.AppModel;
import eu.faircode.netguard.g2d.ui.adapter.AppsBlockAdapter;
import eu.faircode.netguard.g2d.ui.base.BaseActivitty;
import eu.faircode.netguard.g2d.util.Utils;

import java.util.List;

public class AppBlockActivity extends BaseActivitty {

    ActivityAppBlockBinding binding;
    AppsBlockAdapter adapter;
    List<AppModel> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_block);
        showProgressdDialog(this, "", "Loading your apps.");
        setUpToolbarWithBackButton((Toolbar) binding.appBar.findViewById(R.id.toolbar), "Block apps");
        data = Utils.getInstalledAppList(this);
        adapter = new AppsBlockAdapter(this, data);

        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        hideProgressDialog();

    }
}