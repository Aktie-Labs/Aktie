package labs.syr.aktie.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Random;

import labs.syr.aktie.Adapters.ImageAdapter;
import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu
 * Purpose: Aktie project
 * File Name: Screen2Fragment.java
 * Description: Multiple image selector from the gallery
 *
 */

public class Screen2Fragment extends Fragment {

    //initializers*/
    private int count;
    private ImageAdapter imageAdapter;
    private boolean[] thumbnailsselection;
    private Bitmap[] thumbnails;
    private String[] arrPath;
    private static String deviceName = "Bharat's Phone";


    public static Screen2Fragment getInstance(String value) {
        Screen2Fragment screen2 = new Screen2Fragment();
        deviceName = value;
        return screen2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.screen2fragment, container, false);

        /*all the images from the gallery are taken and diplayed here*/
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor imagecursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,null, orderBy);
            int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.thumbnails = new Bitmap[this.count];
        this.arrPath = new String[this.count];
        this.thumbnailsselection = new boolean[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
                    getActivity().getApplicationContext().getContentResolver(), id,
                    MediaStore.Images.Thumbnails.MICRO_KIND, null);
            arrPath[i]= imagecursor.getString(dataColumnIndex);
        }
        /*the images are popluated into the gird view here*/
        GridView imagegrid = (GridView) rootView.findViewById(R.id.PhoneImageGrid);
        imageAdapter = new ImageAdapter(getActivity(),count,thumbnailsselection,
        thumbnails,arrPath);
        imagegrid.setAdapter(imageAdapter);
        imagecursor.close();

        /*on selec button the images selected are shown*/
        final Button selectBtn = (Button) rootView.findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                final int len = thumbnailsselection.length;
                int cnt = 0;
                String selectImages = "";
                for (int i =0; i<len; i++)
                {
                    if (thumbnailsselection[i]){
                        cnt++;
                        selectImages = selectImages + arrPath[i] + "|";
                    }
                }
                if (cnt == 0){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please select at least one image",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "" + cnt + " image(s). are sent to "+deviceName,
                            Toast.LENGTH_LONG).show();
                    Log.d("SelectedImages", selectImages);
                }
            }
        });
        return rootView;
    }


}
