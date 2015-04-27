package labs.syr.aktie.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import labs.syr.aktie.R;


/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: ImageAdapter.java
 * Description: Displays the images currently in the gallery so that the user can pick
 *              the image[s] he wants
 *
 */


public class ImageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity activity;
    private int count;
    private boolean[] thumbnailsselection;
    private Bitmap[] thumbnails;
    private String[] arrPath;


    //constructor to initialize the data
    public ImageAdapter(Activity activity,int count,boolean[] thumbnailsselection,
                        Bitmap[] thumbnails,String[] arrPath) {
        this.activity = activity;
        this.count = count;
        this.thumbnailsselection = thumbnailsselection;
        this.thumbnails = thumbnails;
        this.arrPath = arrPath;
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //returns the number of images in the gallery
    public int getCount() {
        return count;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    //uses view holder to improve performance
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            //inflates the layout for thumbnail and checkbox
            convertView = mInflater.inflate(
                    R.layout.galleryitem, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkbox.setId(position);
        holder.imageview.setId(position);
        //selects the image on click of the checkbox
        holder.checkbox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckBox cb = (CheckBox) v;
                int id = cb.getId();
                if (thumbnailsselection[id]){
                    cb.setChecked(false);
                    thumbnailsselection[id] = false;
                } else {
                    cb.setChecked(true);
                    thumbnailsselection[id] = true;
                }
            }
        });

        //opens up the image in full screen on click
        holder.imageview.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
                activity.startActivity(intent);
            }
        });
        holder.imageview.setImageBitmap(thumbnails[position]);
        holder.checkbox.setChecked(thumbnailsselection[position]);
        holder.id = position;
        return convertView;
    }
}
class ViewHolder {
    ImageView imageview;
    CheckBox checkbox;
    int id;
}