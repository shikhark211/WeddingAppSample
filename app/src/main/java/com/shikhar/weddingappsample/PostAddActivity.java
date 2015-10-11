package com.shikhar.weddingappsample;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class PostAddActivity extends ActionBarActivity implements View.OnClickListener {

    Uri fullPhotoUri;
    SharedPreferences sp;
    Bitmap selectedBitmap;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        Button cropButton = (Button) findViewById(R.id.crop_button);
        cropButton.setOnClickListener(this);
        Intent i = getIntent();
        sp = getSharedPreferences("USER", Context.MODE_PRIVATE);
        fullPhotoUri = (Uri) i.getExtras().get("fullPhotoUri");
        imageView = (ImageView) findViewById(R.id.imageView);
        try {
            selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
        } catch (IOException e) {
        }
        imageView.setImageBitmap(selectedBitmap);
    }

    final int PIC_CROP = 1;

    private void performCrop(Uri picUri) {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestCode == PIC_CROP) {
            if (data != null) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                selectedBitmap = extras.getParcelable("data");

                //  imgView.setImageBitmap(selectedBitmap);

//            try {

                Toast.makeText(this, fullPhotoUri.getPath(), Toast.LENGTH_SHORT).show();
                byte[] image = LoadByteArrayFromFile(selectedBitmap);
                ParseFile file = new ParseFile("resume.jpg", image);
                file.saveInBackground();
                ParseObject feedTemplate = new ParseObject("FeedTemplate");
                feedTemplate.put("UploaderName", sp.getString("UserName", null));
                feedTemplate.put("UploaderProfile", sp.getString("UserFbId", null));
                feedTemplate.put("Image", file);
                feedTemplate.saveInBackground();


                //mPosts.add(new Post("demo", "demo", bitmap));
//            }
//            catch(IOException e){
//                Toast.makeText(this, "No file found", Toast.LENGTH_SHORT).show();
//            }
                setResult(2);
                finish();
            }
            // Do work with photo saved at fullPhotoUri
        }
    }
    private byte[] LoadByteArrayFromFile(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
        byte[] image = stream.toByteArray();
        return image;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_add, menu);
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

    @Override
    public void onClick(View v) {
       // performCrop(fullPhotoUri);
    }
}
