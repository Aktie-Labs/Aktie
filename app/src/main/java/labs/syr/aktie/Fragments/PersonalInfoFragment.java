package labs.syr.aktie.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import labs.syr.aktie.Activity.AktiePagerActivity;
import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu
 * Purpose: Aktie project
 * File Name: PersonalInfoFragment.java
 * Description: Holds the form for setting the personal information
 *
 */
    public class PersonalInfoFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.personalinfo, container, false);

            //all the required details are fetched from here*/
            final EditText deviceName = (EditText) rootView.findViewById(R.id.setPersonalDeviceNameText);
            final EditText firstName = (EditText) rootView.findViewById(R.id.setPersonalFirstNameText);
            final EditText lastName = (EditText) rootView.findViewById(R.id.setPersonalLastNameText);
            final EditText workplace = (EditText) rootView.findViewById(R.id.setPersonalWorkPlaceText);
            final EditText contactPhonePersonal = (EditText) rootView.findViewById(R.id.setPersonalMobileNumber);
            final EditText contactPhoneWork = (EditText) rootView.findViewById(R.id.setPersonalWorkPhoneNumber);
            final EditText emailAddress = (EditText) rootView.findViewById(R.id.setPersonalEmailText);

            final Button resetPersonalInfo = (Button) rootView.findViewById(R.id.resetPersonalInfo);
            final Button doneSettingPersonalInfo = (Button) rootView.findViewById(R.id.doneSetPersonalInfoButton);

            /*once the fragment loads the reset button is simulated as in there we
             read the contents of the file and set the values on screen
              */
            resetPersonalInfo.post(new Runnable() {
                @Override
                public void run() {
                    resetPersonalInfo.performClick();
                }
            });

            /*on done the contents of the file are replaced with the new values*/
            doneSettingPersonalInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject myDeviceInfo = new JSONObject();
                    try {
                        myDeviceInfo.put("deviceName", deviceName.getText());
                        myDeviceInfo.put("contactFirstName", firstName.getText());
                        myDeviceInfo.put("contactLastName", lastName.getText());
                        myDeviceInfo.put("contactPhonePersonal", contactPhonePersonal.getText());
                        myDeviceInfo.put("contactPhoneWork", contactPhoneWork.getText());
                        myDeviceInfo.put("contactWorkPlace", workplace.getText());
                        myDeviceInfo.put("contactEmailId", emailAddress.getText());

                        /*save json data in local files*/
                        FileOutputStream fOut = getActivity().getApplicationContext()
                                .openFileOutput("myPersonalInformation",
                                        getActivity().getApplicationContext().MODE_PRIVATE);
                        fOut.write(myDeviceInfo.toString().getBytes());
                        fOut.close();
                        Toast.makeText(getActivity().getBaseContext(), "Data saved",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity().getApplicationContext(), AktiePagerActivity.class);
                        startActivity(intent);

                    }catch(Exception e)
                    {
                        Log.e("PersonalInfoFragment", "Storing Personal Information Error" + e);
                    }

                }
            });

            /*on reset click the previous data from the file is fetched and filled */
            resetPersonalInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FileInputStream fin = null;
                    try {
                        fin = getActivity().getApplicationContext()
                                .openFileInput("myPersonalInformation");

                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }

                        JSONObject personalInfo = new JSONObject(temp);

                        deviceName.setText(""+personalInfo.get("deviceName"));
                        firstName.setText(""+personalInfo.get("contactFirstName"));
                        lastName.setText(""+personalInfo.get("contactLastName"));
                        contactPhonePersonal.setText(""+personalInfo.get("contactPhonePersonal"));
                        contactPhoneWork.setText(""+personalInfo.get("contactPhoneWork"));
                        workplace.setText(""+personalInfo.get("contactWorkPlace"));

                    } catch (Exception e) {
                       Log.e("PersonalInfoFragment","Reset Personal Info error"+e);
                    }
                }
            });

            return rootView;
        }

    /*get instance method used in fragments to initialize the fragment*/
    public static PersonalInfoFragment getInstance() {
        PersonalInfoFragment personalInfo = new PersonalInfoFragment();
        return personalInfo;
    }
}
