package eu.faircode.netguard.g2d.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eu.faircode.netguard.R;
import eu.faircode.netguard.g2d.listener.DeleteDomainListener;
import eu.faircode.netguard.g2d.localstore.LocalStore;
import eu.faircode.netguard.g2d.models.AppModel;
import eu.faircode.netguard.g2d.models.AppModelJson;

public class WebsiteBlackListAdapter extends RecyclerView.Adapter<WebsiteBlackListHolder> {

    private Context context;
    private List<String> domains;
    private LayoutInflater layoutInflater;
    private String TAG = AppsBlockAdapter.class.getSimpleName();
    private DeleteDomainListener listener;

    public WebsiteBlackListAdapter(Context context, List<String> domains, DeleteDomainListener listener) {
        Log.d(TAG, "AppsBlockAdapter: "+domains.size());
        this.context = context;
        this.domains = domains;
        this.layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public WebsiteBlackListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_blacklist_domain, parent, false);
        return new WebsiteBlackListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WebsiteBlackListHolder holder, int position) {

      final String domain = domains.get(position);
      holder.websiteName.setText(domain);
      holder.deleteImg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              listener.onDelete(domain);

          }
      });

    }



    @Override
    public int getItemCount() {
        return this.domains.size();
    }
}



class WebsiteBlackListHolder extends RecyclerView.ViewHolder{
    ImageView deleteImg;
    TextView websiteName;

    public WebsiteBlackListHolder(@NonNull View itemView) {
        super(itemView);
       deleteImg = itemView.findViewById(R.id.delete_btn);
       websiteName = itemView.findViewById(R.id.domain_name);
    }


}
