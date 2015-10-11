package com.shikhar.weddingappsample;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import Helper.PostsAdapter;
import Models.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends android.support.v4.app.Fragment  {


    public FeedFragment() {
        // Required empty public constructor
    }
    PostsAdapter adapter;
    RecyclerView rv;
    ArrayList<Post> mPosts;
    SharedPreferences sp;
    FrameLayout fl;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View v;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosts = new ArrayList<Post>();
        fetchDataFromServer();
        adapter = new PostsAdapter( mPosts, getActivity() );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_feed, container, false);
        rv = (RecyclerView) v.findViewById(R.id.home_recycler_view);
        sp = this.getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.Feed_swipe_refresh_layout);
        //setColorSchemeResources();

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

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);


        rv.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataFromServer();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        });

        return v;
    }


    private byte[] LoadByteArrayFromFile(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
        byte[] image = stream.toByteArray();
        return image;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2 && requestCode == 2)  {
            adapter.notifyDataSetChanged();
        }
        else if (requestCode == 1 && resultCode == getActivity().RESULT_OK && null != data) {
            if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {

                Bitmap thumbnail = data.getParcelableExtra("data");
                Uri fullPhotoUri = data.getData();
                Intent postAdd = new Intent();
                postAdd.setClass(getActivity(), CropActivity.class);
                postAdd.putExtra("fullPhotoUri", fullPhotoUri);
                startActivityForResult(postAdd, 2);
//                try {
//
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fullPhotoUri);
//                    Toast.makeText(getActivity(), fullPhotoUri.getPath(), Toast.LENGTH_SHORT).show();
//                    byte[] image = LoadByteArrayFromFile(bitmap);
//                    ParseFile file = new ParseFile("resume.jpg", image);
//                    file.saveInBackground();
//                    ParseObject feedTemplate = new ParseObject("FeedTemplate");
//                    feedTemplate.put("UploaderName",sp.getString("UserName",null));
//                    feedTemplate.put("UploaderProfile",sp.getString("UserFbId",null));
//                    feedTemplate.put("Image",file);
//                    feedTemplate.saveInBackground();
//
//
//                                //mPosts.add(new Post("demo", "demo", bitmap));
//                            }catch(IOException e){
//                                Toast.makeText(getActivity(), "NO file found", Toast.LENGTH_SHORT).show();
//                            }
//                            adapter.notifyDataSetChanged();
//
//                            // Do work with photo saved at fullPhotoUri
                        }
                    }
                }

//    @Override
//    public void onResume() {
//        super.onResume();
//        fetchDataFromServer();
//    }

    private void fetchDataFromServer() {
                //Fetch data from server by creating method given below
                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Downloading Image...", true);
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("FeedTemplate");
                query.orderByDescending("createdAt");
                query.setLimit(10);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            mPosts.clear();
                            for (int i = 0; i < list.size(); i++) {
                                ParseFile fileObject = (ParseFile) list.get(i).get("Image");
                                final int finalI = i;
                                fileObject.getDataInBackground(new GetDataCallback() {
                                    public void done(byte[] data, ParseException e) {
                                        if (e == null) {
                                            Log.d("test", "We've got data in data.");
                                            // Decode the Byte[] into
                                            // Bitmap
                                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            // Close progress dialog
                                            progressDialog.dismiss();
                                            byte[] bytes = LoadByteArrayFromFile(bmp);
                                            mPosts.add(new Post(list.get(finalI).getString("UploaderName"),
                                                    list.get(finalI).getString("UploaderProfile"), "demo",
                                                    list.get(finalI).getObjectId(), bmp,bytes));
//                                      adapter.notifyDataSetChanged();

                                        } else {
                                            Log.d("test",
                                                    "There was a problem downloading the data.");
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else {

                        }
                    }
                });

                return ;
            }

        }