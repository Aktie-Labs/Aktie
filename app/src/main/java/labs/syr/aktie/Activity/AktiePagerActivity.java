package labs.syr.aktie.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import labs.syr.aktie.Adapters.AktiePagerAdapter;
import labs.syr.aktie.Fragments.PersonalInfoFragment;
import labs.syr.aktie.R;
/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: AktiePagerActivity.java
 * Description: activity in which our whole app runs and uses view pager.
 *
 */


public class AktiePagerActivity extends FragmentActivity {

    /*The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.*/
    private ViewPager mPager;

    /*class name for log*/
    private String className="AktiePagerActivity.java";
    /*The pager adapter, which provides the pages to the view pager widget. */
    private PagerAdapter mPagerAdapter;

    /*Wifip2p manager will not be working on emulator so commenting this code*/
    /*WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*initializing the layout*/
        setContentView(R.layout.pager_container);

        /*setting th view pager*/
        mPager = (ViewPager) findViewById(R.id.pagerContainer);
        mPagerAdapter = new AktiePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //used to load the number of screens surrounding the current page*/
        //done for better animation betweem screens*/
        mPager.setOffscreenPageLimit(1);
        getActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(), AktiePagerActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.setPersonalInfoOption)
        {
           /*actionbar option for setting the personal information*/
           /*generates a form[fragment] and displays it*/
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            PersonalInfoFragment personalInfo = PersonalInfoFragment.getInstance();
            transaction.replace(R.id.pageHolderContainer,personalInfo);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

        else if(id==R.id.factoryReset)
        {
            /*factory reset deletes all the user files in the app sandbox*/
            try {
                File dir = this.getApplicationContext().getFilesDir();
                File deviceHistoryFile = new File(dir, "deviceHistory");
                deviceHistoryFile.delete();
                File personalInfoFile = new File(dir, "myPersonalInformation");
                personalInfoFile.delete();
                Toast.makeText(this.getApplicationContext(), "History Cleared", Toast.LENGTH_SHORT).show();
            }catch(Exception e)
            {
                Log.e(className, "Factory Reset error: " + e);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //wifi p2p manager is not supported on emulator
        //registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //wifi p2p manager is not supported on emulator
        //unregisterReceiver(mReceiver);
    }
}
