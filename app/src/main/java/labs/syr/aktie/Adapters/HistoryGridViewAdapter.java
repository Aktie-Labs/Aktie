package labs.syr.aktie.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;

import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: HistoryGridViewAdapter.java
 * Description: This is the history adapter and holds the information about devices which were
 *              previously connected.
 *
 */



public class HistoryGridViewAdapter extends BaseAdapter {

    /*holds the applicaiton context*/
    private Context context;

    private String className = "HistoryGridViewAdapter.java";

    /*flag to check if the history file exists*/
    private boolean historyExists = true;

    /*json array will hold the device history*/
    private JSONArray deviceHistoryArray;



    /*constructor is used to initialize the history*/
    public HistoryGridViewAdapter(Context context, JSONArray deviceHistoryArray, boolean historyExists) {
        this.context = context;
        this.deviceHistoryArray = deviceHistoryArray;
        this.historyExists = historyExists;
    }


    /*here we populate the contents of the gird view with the device history*/
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.gridview_history, null);

            /*if history exists then we fetch the data from the file*/
            if(historyExists) {
                try {
                /*here we get the detailed history and populate it on the grid view*/
                JSONObject deviceHistoryObject = deviceHistoryArray.getJSONObject(position);
                TextView deviceName = (TextView) gridView
                        .findViewById(R.id.historyDeviceName);
                    deviceName.setText(deviceHistoryObject.get("deviceName")+"");

                TextView deviceTime = (TextView) gridView
                            .findViewById(R.id.historyDeviceTime);
                    deviceTime.setText(deviceHistoryObject.get("date")+" "+deviceHistoryObject.get("time"));

                ImageView deviceLogo = (ImageView) gridView
                            .findViewById(R.id.historyDeviceImage);

                String deviceOs = deviceHistoryObject.get("deviceOs")+"";

                /*based on the device os the respective image is loaded*/
                if(deviceOs.equalsIgnoreCase("ios"))
                {
                    deviceLogo.setImageResource(R.drawable.ios_logo);
                }
                else if(deviceOs.equalsIgnoreCase("android"))
                {
                    deviceLogo.setImageResource(R.drawable.android_logo);
                }
                else if(deviceOs.equalsIgnoreCase("blackberry"))
                {
                    deviceLogo.setImageResource(R.drawable.blackberry_logo);
                }
                else if(deviceOs.equalsIgnoreCase("windows"))
                {
                    deviceLogo.setImageResource(R.drawable.windows_logo);
                }

                } catch (JSONException e) {
                Log.e(className,"Exception while parsing the file: "+e);
                }
            }
            else
            {
                /*if no history is found then we simply show a message stating the same*/
                TextView textView = (TextView) gridView
                        .findViewById(R.id.historyDeviceName);
                textView.setText("No history found");
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    /*returns the number of devices previously connected*/
    @Override
    public int getCount() {

        if(historyExists)
        return deviceHistoryArray.length();
        else
         return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
