package eu.faircode.netguard.g2d.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import eu.faircode.netguard.R;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.models.AppModel;
import eu.faircode.netguard.g2d.models.AppModelJson;
import eu.faircode.netguard.g2d.util.Utils;

import java.util.List;

public class AppsBlockAdapter extends RecyclerView.Adapter<AppsBlockViewHolder> {

    private Context context;
    private List<AppModel> apps;
    private LayoutInflater layoutInflater;
    private String TAG = AppsBlockAdapter.class.getSimpleName();

    public AppsBlockAdapter(Context context, List<AppModel> apps) {
        Log.d(TAG, "AppsBlockAdapter: "+apps.size());
        this.context = context;
        this.apps = apps;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AppsBlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_apps, parent, false);
        return new AppsBlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppsBlockViewHolder holder, int position) {

        final AppModel app = apps.get(position);
        if(app.isBlock()) {
            holder.appSwitch.setChecked(true);
        }

        holder.appName.setText(app.getAppName());
        holder.appImage.setImageDrawable(app.getAppImage());

        holder.appSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    addToBlackList(app);
                } else {
                    removeFromBlackList(app);
                }
            }
        });

    }

    private void addToBlackList(AppModel appModel) {


        LocalStore.addAppToBlockList(context, new AppModelJson(appModel.getAppName(), true, appModel.getPackageName()));
    }

    private void removeFromBlackList(AppModel appModel) {

        LocalStore.removeAppBlockList(context, new AppModelJson(appModel.getAppName(), true, appModel.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return this.apps.size();
    }
}

class AppsBlockViewHolder extends RecyclerView.ViewHolder {

     ImageView appImage;
     TextView appName;
     Switch appSwitch;
    public AppsBlockViewHolder(@NonNull View itemView) {
        super(itemView);
        appImage = itemView.findViewById(R.id.app_img);
        appName = itemView.findViewById(R.id.app_name);
        appSwitch = itemView.findViewById(R.id.app_switch);

    }
}
