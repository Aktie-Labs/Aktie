package labs.syr.aktie.Activity;

/**
 * Created by bharat on 12/13/14.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.nio.channels.Channel;


/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: WiFiDirectBroadcastReceiver.java
 * Description: A BroadcastReceiver that notifies of important Wi-Fi p2p events.
 *              since the Wi-Fi Peer-to-Peer cannot run on the emulator
 *              we are hard coding the data
 *
 */


public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private AktiePagerActivity mActivity;
    private String className = "WiFiDirectBroadcastReceiver.java";

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
                                       AktiePagerActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            Log.e(className, "Wifi status check");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            Log.e(className, "Scan for devices");
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            Log.e(className, "Device is trying to send files");
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
            Log.e(className, "Wifi device status changed");
        }
    }
}