package labs.syr.aktie.DeviceDetails;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: RowItem.java
 * Description: Holds the hardcoded device information
 *
 */
public class RowItem {
    //holds the imageid, used to show the device icon later
    private int imageId;

    //holds teh name of the device
    private String title;

    public RowItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    //getters and setters
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
