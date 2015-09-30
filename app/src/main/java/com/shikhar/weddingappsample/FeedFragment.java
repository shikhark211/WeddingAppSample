package com.shikhar.weddingappsample;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import Helper.PostsAdapter;
import Models.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends android.support.v4.app.Fragment {


    public FeedFragment() {
        // Required empty public constructor
    }
    PostsAdapter adapter;
    RecyclerView rv;
    ArrayList<Post> mPosts;
    FrameLayout fl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feed, container, false);
        rv = (RecyclerView) v.findViewById(R.id.home_recycler_view);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.attachToRecyclerView(rv);
        fl = (FrameLayout) v.findViewById(R.id.fl);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        });


        rv.setHasFixedSize(true);
        mPosts = new ArrayList<Post>();
        fetchDataFromServer();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new PostsAdapter(R.layout.post_layout, mPosts, getActivity() );
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);



        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && null != data) {
            if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
                Bitmap thumbnail = data.getParcelableExtra("data");
                Uri fullPhotoUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fullPhotoUri);
                    Toast.makeText(getActivity(), fullPhotoUri.getPath(), Toast.LENGTH_SHORT).show();
                    mPosts.add(new Post("demo", "demo", bitmap));
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "NO file found", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();

                // Do work with photo saved at fullPhotoUri
            }
        }
    }

    private ArrayList<Post> fetchDataFromServer() {

        //Fetch data from server by creating method given below
        //fetchCouponsFromServer();
        //FetchDataFromServer.FetchData(" ", " ");
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));


        return mPosts;
    }



}
