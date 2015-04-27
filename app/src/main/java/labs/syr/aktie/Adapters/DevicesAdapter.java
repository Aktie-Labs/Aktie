package labs.syr.aktie.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import labs.syr.aktie.DeviceDetails.RowItem;
import labs.syr.aktie.R;


/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: DevicesAdapter.java
 * Description: This is the device adapter and holds the information scanned devices.
 *
 */


public class DevicesAdapter extends BaseAdapter {

   /*The layout inflater*/
    private LayoutInflater inflater;
    private List<RowItem> rowItems;

    /*constructor used to initialize the layout and the devices*/
    public DevicesAdapter(LayoutInflater inflater, List<RowItem> rowItems) {
        this.inflater = inflater;
        this.rowItems = rowItems;
    }

    /*returns the total number of devices*/
    public int getCount() {
        return rowItems.size();
    }

    /*returns each device details*/
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /*based on the position will return the device details*/
    public View getView(int position, View convertView, ViewGroup parent) {

        /*view holder is being used to improve performance by caching*/
        ViewHolder holder = null;

        /*if the view to be populated is null then we create and initialize a view holder*/
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.devices_rows, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.phoneName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.phoneImage);
            convertView.setTag(holder);
        }
        else {
            /*if view already exists then we fetch it form the holder*/
            holder = (ViewHolder) convertView.getTag();
        }

        /*here we get the device based on the position*/
        RowItem rowItem = (RowItem) getItem(position);

        /*the title of the device and the respective image is set*/
        holder.txtTitle.setText(rowItem.getTitle());
        holder.txtTitle.setTextColor(Color.WHITE);
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtTitle;
    }

}
