package com.shikhar.weddingappsample;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.melnykov.fab.FloatingActionButton;

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

            }
        });
        rv.setHasFixedSize(true);
        mPosts = new ArrayList<>();
        fetchDataFromServer();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        PostsAdapter adapter = new PostsAdapter(R.layout.post_layout, mPosts, getActivity() );
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);



        return v;
    }

    private ArrayList<Post> fetchDataFromServer() {

        //Fetch data from server by creating method given below
        //fetchCouponsFromServer();
        //FetchDataFromServer.FetchData(" ", " ");
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
//        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.demo));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));
        mPosts.add(new Post("Demo Company", "Demo Details", R.drawable.download));


        return mPosts;
    }



}
