package com.shikhar.weddingappsample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LoginActivity extends AppCompatActivity {

    TextView textView;
    Boolean checkLogin ;
    LoginButton loginButton;
    ImageView dp;
    Profile profile = null;
    CallbackManager callbackManager;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "OCvhok5Gh0FhJhfAA6cKWysZiL53xehGC9zM3OO1", "EmROHXSF6RclFFvsZXu30qv9ZMPbWBI2a25LyCYF");
        sp = getSharedPreferences("USER", Context.MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.textview);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, mcallback);

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("InviteeProfile");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    for(int i=0;i<list.size();i++){
//                        ParseObject inviteeList = new ParseObject("InviteeListinDataBase");
//                        inviteeList.put("Name", list.get(i).get("Name"));
//                        inviteeList.put("Id", list.get(i).get("Id"));
//                        inviteeList.pinInBackground();
//                        Toast.makeText(getApplicationContext(), inviteeList.get("Name")+"",Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // something went wrong
//                    textView.setText("ERROR");
//                }
//            }
//        });

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

                editor.commit();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("InviteeProfile");
                query.whereEqualTo("Id", profile.getLinkUri().toString());
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (object == null) {
                            ParseObject inviteeProfile = new ParseObject("InviteeProfile");
                            inviteeProfile.put("Name", profile.getName());
                            inviteeProfile.put("Id", profile.getLinkUri().toString());
                            inviteeProfile.put("fb_dp",profile.getProfilePictureUri(1,1).toString());
                            inviteeProfile.saveInBackground();
                        } else {
                            Toast.makeText(getApplicationContext(), "already there", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Intent i = new Intent();
                i.setClass(getApplicationContext(), MainActivity.class);
                startActivity(i);
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
}
