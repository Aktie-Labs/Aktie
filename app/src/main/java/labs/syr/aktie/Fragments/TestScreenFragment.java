package labs.syr.aktie.Fragments;

import android.content.ContentProviderOperation;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;

import labs.syr.aktie.Dialog.AsyncDevicesLoad;
import labs.syr.aktie.Dialog.CustomDialogClass;
import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu
 * Purpose: Aktie project
 * File Name: Screen1Fragment.java
 * Description: This fragment is the controller for the Holds the scan and receive actions
 */


public class TestScreenFragment extends Fragment {

    View rootView;
    public static String deviceName;
    LayoutInflater inflater;
    AsyncDevicesLoad loadList;
    CustomDialogClass customDialog;
    static final int TIME_OUT = 7000;
    static final int MSG_DISMISS_DIALOG = 0;

    //Initial Declaration
    //used to create a new instance for the fragment*/
    public static Fragment newInstance() {
        TestScreenFragment myFragment = new TestScreenFragment();
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflater = mInflater;
        rootView = inflater.inflate(R.layout.testlayout, container, false);


        return rootView;
    }

}