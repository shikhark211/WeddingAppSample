package Models;

/**
 * Created by shikharkhetan on 8/25/15.
 */
public class Post {
    public Post(String user, String description, int imageId) {
        this.user = user;
        this.description = description;
        this.imageId = imageId;
    }

    String user;
    String description;
    int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

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
