package labs.syr.aktie.Dialog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import labs.syr.aktie.Adapters.DevicesAdapter;
import labs.syr.aktie.DeviceDetails.RowItem;
import labs.syr.aktie.Fragments.PersonalInfoFragment;
import labs.syr.aktie.Fragments.Screen2Fragment;
import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: AsyncDevicesLoad.java
 * Description: Uses async task to load the devices, hard coded
 *
 */

public class AsyncDevicesLoad extends AsyncTask<Void, Integer, Long>{

    LayoutInflater inflater;
    FragmentActivity activity;
    private boolean flag;
    private ListView devicesListView;
    String[] deviceTitles;
    int[] deviceImages;
    DevicesAdapter adapter;
    List<RowItem> rowItems;


        /*wifi p2p manager is not supported in sumlator so commenting the below*/
        /*initialize mManager,mChannel and mReceiver*/
        /*mManager;
        mChannel;
        mReceiver;*/


    /*wifi p2p manager is not supported in sumlator so commenting the below*/
    /*WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;

    */



    public AsyncDevicesLoad(LayoutInflater inflater,boolean flag,ListView devicesListView,FragmentActivity activity) {
        this.inflater = inflater;
        this.flag = flag;
        this.devicesListView = devicesListView;
        this.activity = activity;
    }


    //before execution of the task we add two device names*/
    @Override
    protected void onPreExecute() {

        rowItems = new ArrayList<RowItem>();


                //scan for and obtain a peer from the WifiP2pDeviceList
                /*WifiP2pDevice device;
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        //Devices are added to an arraylist
                           RowItem device = new RowItem(1,"DeviceName");
                        rowItems.add(device);
                    }

                    @Override
                    public void onFailure(int reason) {
                        Log.e("Screen1Fragment","Device scan error, with code: "+reason);
                    }
                });
                */


        if(flag)
        {
            deviceTitles = new String[]{"Bharat's Phone",
                    "Max's Android",};

            deviceImages = new int[]{R.drawable.phone_image1,R.drawable.phone_image2};


            for (int i = 0; i < deviceTitles.length; i++) {
                RowItem item = new RowItem(deviceImages[i], deviceTitles[i]);
                rowItems.add(item);
            }
        }
        else
        {
            deviceTitles = new String[]{};

            deviceImages = new int[]{};

            for (int i = 0; i < deviceTitles.length; i++) {
                RowItem item = new RowItem(deviceImages[i], deviceTitles[i]);
                rowItems.add(item);
            }
        }

        adapter = new DevicesAdapter(inflater, rowItems);
        devicesListView.setAdapter(adapter);

        /*on device seletion*/
        devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView phoneName = (TextView)view.findViewById(R.id.phoneName);
                String value = phoneName.getText().toString();

                try {
                    /*contact details are sent to th selected device*/
                    FileInputStream fin = activity.getApplicationContext().openFileInput("myPersonalInformation");
                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }

                    Toast.makeText(activity.getApplicationContext(),"Contact information is transferred to "+value,
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("AsyncDevicesLoad", "error in sending personal information " + e);
                    Toast.makeText(activity.getApplicationContext(),"There was an error "+value,
                            Toast.LENGTH_SHORT).show();
                }

                /*the history is updated with the device name and time*/
                try {
                /*create json data*/
                    JSONArray device = null;

                try {
                    FileInputStream fin = activity.getApplicationContext().openFileInput("deviceHistory");
                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }
                    device = new JSONArray(temp);
                }catch(Exception e)
                {
                    device = new JSONArray();
                    Log.e("AsyncDevicesLoad","no previous file found"+e);
                }

                    /*randomly assigning the data*/
                    JSONObject deviceInfo = new JSONObject();
                    String deviceOs;
                    Random rand = new Random();
                    int randomNum = rand.nextInt((4 - 1) + 1) + 1;
                    switch(randomNum)
                    {
                        case 1:
                            deviceOs="ios";
                            break;
                        case 2:
                            deviceOs="windows";
                            break;
                        case 3:
                            deviceOs="blackberry";
                            break;
                        default:
                            deviceOs="android";
                    }


                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    Date date = new Date();

                    deviceInfo.put("deviceName", value);
                    deviceInfo.put("deviceOs",deviceOs);
                    deviceInfo.put("date",date.getMonth()+"/"+date.getMonth()+"/"+date.getYear());
                    deviceInfo.put("time",date.getTime());
                    device.put(deviceInfo);

                    /*writing device details to the history file*/
                    FileOutputStream fOut = activity.getApplicationContext()
                            .openFileOutput("deviceHistory",
                                    activity.getApplicationContext().MODE_PRIVATE);
                    fOut.write(device.toString().getBytes());
                    fOut.close();
                }catch(Exception e)
                {
                    Log.e("AsyncDevicesLoad","Error is json creation on save "+e);
                }

                android.support.v4.app.FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                Screen2Fragment screen2 = Screen2Fragment.getInstance(value);
                transaction.replace(R.id.pageHolderContainer,screen2);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
    }

    /*asnchronously add the devices*/
    @Override
    protected Long doInBackground(Void... params) {
        if(flag) {
            int i = 3;
            while (i < 7 && flag == true) {
                try {
                    publishProgress(i);
                    Thread.sleep(3000);
                    i++;
                } catch (Exception e) {
                    Log.i("AsyncDevicesLoad", "DoInBackground--" + e.getMessage());
                }
            }
        }
        return null;
    }

    /*as time passes*/
    @Override
    protected void onProgressUpdate(Integer... values) {

        RowItem item;

        switch(values[0])
        {
            case 3:
                 item = new RowItem(R.drawable.phone_image3,"Harry's Moto");
                rowItems.add(item);
                        break;
            case 4:
                item = new RowItem(R.drawable.phone_image4,"Balckberry Curve");
                rowItems.add(item);
                break;
            case 5:
                item = new RowItem(R.drawable.phone_image5,"My Ubuntu Phone");
                rowItems.add(item);
                break;
            case 6:
                 item = new RowItem(R.drawable.phone_image6,"XX_XX__XX");
                rowItems.add(item);
                break;

        }
        /*updating the list view as the devices are found*/
        adapter.notifyDataSetChanged();
   }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
    }

}
