package labs.syr.aktie.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;

import labs.syr.aktie.Adapters.HistoryGridViewAdapter;
import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu
 * Purpose: Aktie project
 * File Name: Screen4Fragment.java
 * Description: History of the devices connected
 *
 */

public class Screen4Fragment extends Fragment {


    GridView gridView;

    //initializing the history array
    private JSONArray deviceHistoryArray = null;
    private boolean historyExists = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.screen4fragment, container, false);
        Button historyButton = (Button) rootView.findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //on button click the history is read from the file and displayed
                gridView = (GridView) rootView.findViewById(R.id.gridView1);
                deviceHistoryArray = populateHistory();//reading the history file
                gridView.setAdapter(new HistoryGridViewAdapter(getActivity().getApplicationContext(),deviceHistoryArray,historyExists));

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        try {
                            JSONObject deviceHistoryObject = deviceHistoryArray.getJSONObject(position);
                            Toast.makeText(getActivity().getApplicationContext(), "This was a " + deviceHistoryObject.get("deviceOs")
                                    + " device", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("Screen4Fragment", "Error on Grid item click: " + e);
                        }
                    }
                });

            }
        });


        //on click this deletes the history
        Button clearHistoryButton = (Button) rootView.findViewById(R.id.clearHistoryButton);
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    File dir = getActivity().getApplicationContext().getFilesDir();
                    File file = new File(dir, "deviceHistory");
                    file.delete();

                    Toast.makeText(getActivity().getApplicationContext(),"History Cleared",Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Log.e("Screen4Fragment","History Delete Exception");
                }

            }
        });
        return rootView;
    }

//method is used to read the file and populate the history into an array
    private JSONArray populateHistory() {
        JSONArray deviceHistory = null;
        try {
            FileInputStream fin = getActivity().getApplicationContext().openFileInput("deviceHistory");
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            deviceHistory = new JSONArray(temp);
            historyExists = true;

        } catch (Exception e) {
            Log.e("Screen1Fragment", "error in reading device history" + e);
            historyExists = false;
        }
        return deviceHistory;
    }

}
