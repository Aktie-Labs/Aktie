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
 *
 */


public class Screen1Fragment extends Fragment {

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
        Screen1Fragment myFragment = new Screen1Fragment();
        return myFragment;
    }



    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflater = mInflater;
        rootView = inflater.inflate(R.layout.screen1fragment, container, false);

        /*on scan button click the devices are scanned and shown in the list view as results*/
        final Button scan_button = (Button) rootView.findViewById(R.id.scanButton);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView devicesFoundTitle = (TextView) rootView.findViewById(R.id.devicesFoundTitle);
                devicesFoundTitle.setVisibility(View.VISIBLE);

                /*scrollable list view which shows the list of devices found*/
                ListView devicesList = (ListView) rootView.findViewById(R.id.devicesList);

                /*since the Wi-Fi Peer-to-Peer cannot run on the emulator we are hard coding the device data*/
                loadList = new AsyncDevicesLoad(inflater,true,devicesList,getActivity());
                loadList.execute();

                createDialog();
            }
        });

        Button receiveButton = (Button) rootView.findViewById(R.id.receiveButton);

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*on receive the personal information fetched is synced and
                contact is added to the addressbook*/

                String DisplayName;
                String MobileNumber;
                String HomeNumber;
                String WorkNumber;
                String emailID;
                String company;

                try
                {
                try {
                    FileInputStream fin = getActivity().getApplicationContext().openFileInput("myPersonalInformation");

                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }
                    JSONObject contactData = new JSONObject(temp);
                    DisplayName = contactData.getString("contactFirstName")+ contactData.getString("contactLastName");
                    MobileNumber = contactData.getString("contactPhonePersonal");
                    HomeNumber = contactData.getString("contactPhonePersonal");
                    WorkNumber = contactData.getString("contactPhoneWork");
                    emailID = contactData.getString("contactEmailId");
                    company = contactData.getString("contactWorkPlace");


                }catch(Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "No configuration file found. Creating one",
                            Toast.LENGTH_LONG).show();
                    Log.e("Screen1Framgent","device details not set: "+e);
                    DisplayName = "Aktie";
                    MobileNumber = "0000000000";
                    HomeNumber = "0000000000";
                    WorkNumber = "0000000000";
                    emailID = "username@aktie.com";
                    company = "Aktie";

                }
                    Toast.makeText(getActivity().getApplicationContext(), "Contact information added",
                            Toast.LENGTH_LONG).show();



                ArrayList<ContentProviderOperation> ops = new ArrayList < ContentProviderOperation > ();

                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                //Name
                if (DisplayName != null) {
                    ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(
                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                    DisplayName).build());
                }

                //Mobile Number
                if (MobileNumber != null) {
                    ops.add(ContentProviderOperation.
                            newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());
                }

                //Home Number
                if (HomeNumber != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                            .build());
                }

                //Work Numbers
                if (WorkNumber != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                            .build());
                }

                //Email
                if (emailID != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .build());
                }

                //Organization
                if (!company.equals("")) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                            .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                            .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                            .build());
                }
                // Asking the Contact provider to create a new contact
                    getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                } catch (Exception e) {
                    Log.e("Screen1Fragment", "error in receiving data" + e);
                    Toast.makeText(getActivity().getApplicationContext(),"There was an error ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    //dismiss the dialog being shown
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_DISMISS_DIALOG:
                    if (customDialog != null && customDialog.isShowing()) {
                        customDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //dialog is created which shows scanning gif
    private void createDialog() {
        customDialog = new CustomDialogClass(getActivity(),inflater,loadList);
        customDialog.show();
        mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DIALOG, TIME_OUT);
    }

}