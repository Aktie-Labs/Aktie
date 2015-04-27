
package labs.syr.aktie.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import labs.syr.aktie.R;


/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: CustomDialogClass.java
 * Description: popup displaying the scanning bar, this is timed.
 *
 */


public class CustomDialogClass extends Dialog implements View.OnClickListener {


    public Button button_done, button_cancel;
    AsyncDevicesLoad loadList;
    ListView devicesList;
    FragmentActivity activity;
    LayoutInflater inflater;


    public CustomDialogClass(FragmentActivity activity,LayoutInflater inflater,AsyncDevicesLoad loadList) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.inflater = inflater;
        this.loadList = loadList;
        devicesList = (ListView) activity.findViewById(R.id.devicesList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan_custom_dialog);

        /*android does not support gif images, we use webview and then display the gif as a file*/
        WebView loading_view = (WebView)findViewById(R.id.loadingImageView);
        loading_view.loadUrl("file:///android_asset/loading.gif");

        button_done = (Button) findViewById(R.id.dialog_custom_done);
        button_cancel = (Button) findViewById(R.id.dialog_custom_cancel);

        button_done.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
    }

    /*actions on the dialog to reset or stop the scanner*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_custom_done:
                dismiss();
                loadList.cancel(true);
                break;
            case R.id.dialog_custom_cancel:
                activity.findViewById(R.id.devicesFoundTitle).setVisibility(View.INVISIBLE);
                AsyncDevicesLoad loadList = new AsyncDevicesLoad(inflater,false,devicesList,activity);
                loadList.execute();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
