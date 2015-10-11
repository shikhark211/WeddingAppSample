package Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shikhar.weddingappsample.Comments;
import com.shikhar.weddingappsample.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Models.Post;

/**
 * Created by shikharkhetan on 8/25/15.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    public PostsAdapter( ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    private byte[] LoadByteArrayFromFile(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
        byte[] image = stream.toByteArray();
        return image;
    }

    int position;
    ArrayList<Post> posts;
    Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;

    }

    private File downloadImage(Post post) {


        try {
            //First we create a new Folder named Speed Share if it does not exist
             File folder = new File(Environment.getExternalStorageDirectory() + "/Wedding App");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }

            //Now prepare to receive data
            if (success) {
                Toast.makeText(context,post.getFeedId()+"",Toast.LENGTH_SHORT).show();
                //Initialize output Stream and Output file's full path
                FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Wedding App" + "/" + post.getFeedId()+".jpg");
                BufferedOutputStream bufOutputStream = new BufferedOutputStream(fileOutputStream);

                //Byte Buffer to read from the input stream
                byte[] contents = new byte[1024];

                //No. of bytes in one read() call
                int bytesRead = 0;
                InputStream myInputStream = new ByteArrayInputStream(post.getBytes());

                //Start reading from inputStream and write on the output Stream
                while ((bytesRead = myInputStream.read(contents)) != -1) {
                    bufOutputStream.write(contents, 0, bytesRead);
                }

                //Hopefully we are done receiving so lets do the housekeeping stuff
                bufOutputStream.flush();
                myInputStream.close();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File folder1 = new File(Environment.getExternalStorageDirectory() + "/Wedding App" + "/" + post.getFeedId()+".jpg");
        return folder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.Uploadername.setText(posts.get(position).getUser());
        Toast.makeText(context, "efff", Toast.LENGTH_SHORT).show();
        holder.description.setText(posts.get(position).getDescription());
        holder.uploadedPhoto.setImageBitmap(posts.get(position).getImage());
        Picasso.with(context).load("https://graph.facebook.com/" + posts.get(position).getUploaderFbId() + "/picture?type=small")
                .placeholder(R.mipmap.ic_launcher)
                .transform(new CircleTransform())
                .into(holder.UploaderPhoto);
        holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, Comments.class);
                intent.putExtra("postId", posts.get(position).getFeedId());
                context.startActivity(intent);
            }
        });
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = downloadImage(posts.get(position));
                Toast.makeText(context,"file",Toast.LENGTH_SHORT).show();
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);

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
        public ImageView UploaderPhoto;
        public TextView Uploadername;
        public TextView description;
        public ImageView uploadedPhoto;
        public Button likeButton;
        public Button commentButton;
        public ImageButton downloadButton;

        public ViewHolder(View itemView) {
            super(itemView);
            int x =  getPosition();
            postCard = (CardView) itemView.findViewById(R.id.cv);
            Uploadername = (TextView) itemView.findViewById(R.id.uploaderName);
            UploaderPhoto = (ImageView) itemView.findViewById(R.id.uploaderdp);
            description = (TextView) itemView.findViewById(R.id.discription);
            uploadedPhoto = (ImageView) itemView.findViewById(R.id.uploadedImage);
            likeButton = (Button) itemView.findViewById(R.id.likebutton);
            commentButton = (Button) itemView.findViewById(R.id.commentbutton);
            downloadButton = (ImageButton) itemView.findViewById(R.id.downloadbutton);
        }
    }

}