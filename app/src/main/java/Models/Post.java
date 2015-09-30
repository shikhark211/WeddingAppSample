package Models;

import android.graphics.Bitmap;

/**
 * Created by shikharkhetan on 8/25/15.
 */
public class Post {


    public Post(String user, String description, Bitmap image) {
        this.image = image;
        this.user = user;
        this.imageId = 0;

        this.description = description;
    }

    Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    String user;
    String description;
    int imageId;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


}
