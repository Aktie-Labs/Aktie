package labs.syr.aktie.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import labs.syr.aktie.Calendar.Event;
import labs.syr.aktie.R;

/**
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: AktieEventDetailAdapter.java
 * Description: This is the adapter and holds the information about the events
 *              such as event name, event day, event time and event description
 *
 */

public class AktieEventDetailAdapter extends ArrayAdapter<Event> {

    private final Context context;
    private final ArrayList<Event> events;

    /*constructor used to pass the application context and the events to hold in the adapter*/
    public AktieEventDetailAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.events_details, events);
        this.context = context;
        this.events = events;
    }

    /*Base adapter method overridden here*/
    /*holds the views and gets the details of the event */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*inflating the layout here, and is displayed as a list view at the bottom of the calendar*/
        View rowView = inflater.inflate(R.layout.events_details, parent, false);

        /*Fetching the views from the layout*/
        TextView eventDetailName = (TextView) rowView.findViewById(R.id.eventDetailName);
        TextView eventDetailHour = (TextView) rowView.findViewById(R.id.eventDetailHour);
        TextView eventDetailMinute = (TextView) rowView.findViewById(R.id.eventDetailMinute);
        TextView eventDetailDescription = (TextView) rowView.findViewById(R.id.eventDetailDescription);

        /*setting values for the views to be displayed*/
        eventDetailName.setText(events.get(position).getName()+"");
        eventDetailHour.setText(events.get(position).getStartHour()+":");
        eventDetailMinute.setText(events.get(position).getStartMinute()+"");
        eventDetailDescription.setText(events.get(position).getDescription()+"");

        return rowView;
    }
}