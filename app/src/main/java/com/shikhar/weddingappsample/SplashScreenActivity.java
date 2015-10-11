package com.shikhar.weddingappsample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.parse.Parse;

public class SplashScreenActivity extends AppCompatActivity {


    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_splash_screen);
        Parse.initialize(this, "OCvhok5Gh0FhJhfAA6cKWysZiL53xehGC9zM3OO1", "EmROHXSF6RclFFvsZXu30qv9ZMPbWBI2a25LyCYF");
        sp = getSharedPreferences("USER", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent;
                if(sp.getString("UserName",null)==null){
                    mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }else {
                    mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, 1000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
