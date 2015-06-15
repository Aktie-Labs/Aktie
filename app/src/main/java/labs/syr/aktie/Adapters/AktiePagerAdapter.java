package labs.syr.aktie.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import labs.syr.aktie.Fragments.Screen1Fragment;
import labs.syr.aktie.Fragments.Screen2Fragment;
import labs.syr.aktie.Fragments.Screen3Fragment;
import labs.syr.aktie.Fragments.Screen4Fragment;
import labs.syr.aktie.Fragments.TestScreenFragment;


/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: AktiePagerAdapter.java
 * Description: This is the pager adapter and holds the information about the fragments.
 *              This is basically the control through which the system identifies the page swipe
 *              and loads the corresponding fragment
 *
 */


public class AktiePagerAdapter extends FragmentStatePagerAdapter {

    /*predefining the total number of screens the view pager will hold*/
    private static final int NUM_SCREENS = 5;

    /*constructor which takes the support.fragment manager and passes it to super*/
    public AktiePagerAdapter(FragmentManager manager) {
        super(manager);
    }

    /*Overriding FragmentStatePagerAdapter method*
    *returns the corresponding fragment to load on page swipe
    *based on the position
    */
    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                return new Screen1Fragment();
            case 1:
                return new Screen2Fragment();
            case 2:
                return new Screen3Fragment();
            case 3:
                return new Screen4Fragment();
            default:
                //return new TestScreenFragment();
                return new Screen1Fragment();
        }
    }

    /*Overriding FragmentStatePagerAdapter method*/
    /*returns the total number of screens, which was predefined*/
    @Override
    public int getCount() {
        return NUM_SCREENS;
    }
}
