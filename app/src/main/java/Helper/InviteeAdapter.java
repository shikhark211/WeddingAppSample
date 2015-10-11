package Helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shikhar.weddingappsample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Models.Invitee;

/**
 * Created by utkarshnath on 03/10/15.
 */
public class InviteeAdapter extends RecyclerView.Adapter<InviteeAdapter.ViewHolder> {


    private ArrayList<Invitee> inviteeList;
    private LayoutInflater inflater;
    private Context context;

    public InviteeAdapter(Context context,ArrayList<Invitee> inviteeList){
        this.inviteeList = inviteeList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.invitee_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context).load("https://graph.facebook.com/" + inviteeList.get(i).getFbId() + "/picture?type=small")
                .placeholder(R.mipmap.ic_launcher)
                .transform(new CircleTransform())
                .into(viewHolder.dp);
        viewHolder.Inviteename.setText(inviteeList.get(i).getName());
    }



    @Override
    public int getItemCount() {
        return inviteeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView dp;
        public TextView Inviteename;

        public ViewHolder(View itemView) {
            super(itemView);

            Inviteename = (TextView) itemView.findViewById(R.id.invitee_name);
            dp = (ImageView) itemView.findViewById(R.id.invitee_dp);

        }
    }
}
