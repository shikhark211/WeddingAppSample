package Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shikhar.weddingappsample.R;

import java.util.ArrayList;

import Models.Post;

/**
 * Created by shikharkhetan on 8/25/15.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    int resId;

    public PostsAdapter(int resId, ArrayList<Post> posts, Context context) {
        this.resId = resId;
        this.posts = posts;
        this.context = context;
    }

    int position;
    ArrayList<Post> posts;
    Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.username.setText(posts.get(position).getUser());
        holder.description.setText(posts.get(position).getDescription());
        holder.postPhoto.setImageBitmap(posts.get(position).getImage());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //download Image
            }
        });

        this.position = position;
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView postCard;
        public TextView username;
        public TextView description;
        public ImageView postPhoto;
        public Button download;

        public ViewHolder(View itemView) {
            super(itemView);
            postCard = (CardView) itemView.findViewById(R.id.cv);
            username = (TextView) itemView.findViewById(R.id.username);
            description = (TextView) itemView.findViewById(R.id.description);
            postPhoto = (ImageView) itemView.findViewById(R.id.postPhoto);
            download = (Button) itemView.findViewById(R.id.downloadButton);
        }
    }

}