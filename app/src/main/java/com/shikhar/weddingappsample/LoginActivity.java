package com.shikhar.weddingappsample;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import Helper.CircleTransform;

public class LoginActivity extends AppCompatActivity {

    TextView textView;
    Boolean checkLogin ;
    LoginButton loginButton;
    ImageView dp;
    Profile profile = null;
    myHelper Helper;
    SQLiteDatabase db;
    CallbackManager callbackManager;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Helper = new myHelper(getApplicationContext());
        db = Helper.getWritableDatabase();
        sp = getSharedPreferences("USER", Context.MODE_PRIVATE);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();
       // textView = (TextView) findViewById(R.id.login_textview);
        loginButton.registerCallback(callbackManager, mcallback);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken =loginResult.getAccessToken();
            profile = Profile.getCurrentProfile();
            if(profile==null){
                textView.setText("null");
            }
            if(profile!=null){
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("UserName", profile.getFirstName());
                editor.putString("UserFbId", profile.getId());
                if(sp.getBoolean("fileExists", false)==false){
                    File folder = new File(Environment.getExternalStorageDirectory() + "/Wedding App");
                    folder.mkdir();
                    editor.putBoolean("fileExists",true);
                }
                editor.commit();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("InviteeProfile");
                query.whereEqualTo("Id", profile.getId());
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object == null) {
                            ParseObject inviteeProfile = new ParseObject("InviteeProfile");
                            inviteeProfile.put("Name", profile.getName());
                            inviteeProfile.put("Id", profile.getId());
                            inviteeProfile.saveInBackground();


                        } else {
                            Toast.makeText(getApplicationContext(), "already there", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ImageView imageView = (ImageView) findViewById(R.id.user_dp);
                Picasso.with(getApplicationContext()).load("https://graph.facebook.com/" + profile.getId() + "/picture?type=large").placeholder(R.mipmap.ic_launcher).transform(new CircleTransform()).into(imageView);
                fetchInviteeFromServer();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                }, 5000);

            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {
            textView.setText("err"+e);
        }
    };




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void fetchInviteeFromServer(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InviteeProfile");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if(db!=null){
                        db.execSQL("DELETE FROM " + myHelper.Table_Name);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Helper.InviteeName,list.get(i).getString("Name"));
                        contentValues.put(Helper.InviteeId,list.get(i).getString("Id"));
                        db.insert(Helper.Table_Name, null, contentValues);
                    }
                } else {

                }
            }
        });
    }
}
