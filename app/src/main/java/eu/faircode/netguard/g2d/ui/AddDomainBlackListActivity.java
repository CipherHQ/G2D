package eu.faircode.netguard.g2d.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.faircode.netguard.R;
import eu.faircode.netguard.ServiceSinkhole;
import eu.faircode.netguard.databinding.ActivityAddDomainBlackListBinding;
import eu.faircode.netguard.g2d.listener.DeleteDomainListener;
import eu.faircode.netguard.g2d.ui.adapter.WebsiteBlackListAdapter;
import eu.faircode.netguard.g2d.ui.base.BaseActivity;

public class AddDomainBlackListActivity extends BaseActivity implements DeleteDomainListener {

    ActivityAddDomainBlackListBinding binding;
    String TAG = AddDomainBlackListActivity.class.getSimpleName();
    List<String> domains = new ArrayList<>();
    WebsiteBlackListAdapter websiteBlackListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_domain_black_list);
        Toolbar toolbar = binding.appBar.findViewById(R.id.toolbar);
        super.setUpToolbarWithBackButton(toolbar, getResources().getString(R.string.blacklist));
       setupDomains();
    }


    public  void setupDomains() {

       showProgressdDialog(this, "", "Loading domain list..");
        try {
            File hosts = new File(getFilesDir(), "hosts.txt");
            if(!hosts.exists()) {hosts.createNewFile();}
            Scanner myReader = new Scanner(hosts);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                String[] dataArray = data.split(" ");
                domains.add(dataArray[1]);
            }
            myReader.close();
        }catch (Exception ex) {ex.printStackTrace();}

        Set<String> set = new HashSet<>(domains);
        domains.clear();
        domains.addAll(set);
         websiteBlackListAdapter = new WebsiteBlackListAdapter(this, domains, this);
         binding.rv.setLayoutManager(new LinearLayoutManager(this));
         binding.rv.setAdapter(websiteBlackListAdapter);
         websiteBlackListAdapter.notifyDataSetChanged();
         hideProgressDialog();
    }

    public void addDomain(View view) {

        if(domains.contains(binding.site.getText().toString())) {
            Toast.makeText(this, "Domain already exists.", Toast.LENGTH_SHORT).show();
            return;}
        if(validateURL(binding.site.getText().toString()) && !isEmailValid(binding.site.getText().toString())) {

            String data = "0.0.0.0 "+binding.site.getText().toString()+"\r\n";
            Log.d(TAG, "addDomain: "+binding.site.getText().toString());
            showProgressdDialog(this, "", "adding to website.");
            File hosts = new File(getFilesDir(), "hosts.txt");
            if(!hosts.exists()) {
                try {
                    hosts.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {

                FileOutputStream outputStream = new FileOutputStream(getFilesDir()+"/hosts.txt", true);
                byte[] strToBytes = data.getBytes();
                outputStream.write(strToBytes);

                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ServiceSinkhole.reload("hosts file download", AddDomainBlackListActivity.this, false);
            domains.add(binding.site.getText().toString());
            websiteBlackListAdapter.notifyDataSetChanged();
            hideProgressDialog();
            binding.site.setText("www.");

        } else {
            Toast.makeText(this, "Please enter valid URL!", Toast.LENGTH_SHORT).show();
        }

          //onBackPressed();

    }

    @Override
    public void onDelete(String domain) {

        showProgressdDialog(this, "", "Updating blacklist..");
        domains.remove(domain);
        websiteBlackListAdapter.notifyDataSetChanged();

        try {

        File hosts = new File(getFilesDir(), "hosts.txt");
        FileOutputStream outputStream = new FileOutputStream(getFilesDir()+"/hosts.txt", false);

        for (String d : domains) {
            String prefix = "0.0.0.0 " + d;
            prefix += "\r\n";
            byte[] strToBytes = prefix.getBytes();
            outputStream.write(strToBytes);
        }

        outputStream.close();
        }catch (Exception ex) {ex.printStackTrace();}
        ServiceSinkhole.reload("hosts file download", AddDomainBlackListActivity.this, false);

        hideProgressDialog();
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean validateURL(String website) {

        String regex = "(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?";
        Pattern REGEX = Pattern.compile(regex);
        Log.d(TAG, "validateURL: "+REGEX.matcher(website).matches());
        return REGEX.matcher(website).find();


    }
}