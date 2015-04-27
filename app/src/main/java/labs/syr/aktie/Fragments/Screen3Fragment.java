package labs.syr.aktie.Fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import labs.syr.aktie.Adapters.AktieEventDetailAdapter;
import labs.syr.aktie.Calendar.CalendarProvider;
import labs.syr.aktie.Calendar.Day;
import labs.syr.aktie.Calendar.Event;
import labs.syr.aktie.Calendar.AktieCalendarView;
import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: Screen3Fragment.java
 * Description: Custome calendar implementation
 *
 */
public class Screen3Fragment extends Fragment{

    private ArrayList<Event> details;
    private AktieEventDetailAdapter mAdapter;
    private ListView eventDetails = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.screen3fragment, container, false);

        final AktieCalendarView aktieCalendarView = (AktieCalendarView) rootView.findViewById(R.id.screen3calendar);
        aktieCalendarView.setOnDayClickListener(new AktieCalendarView.OnDayClickListener() {
            @Override
            public void onDayClicked(AdapterView<?> adapter, View view, int position, long id, Day day) {

                //events previously added in the history file are fetched and shown on the calendar*/

                //new event creation form is shown
                details = new ArrayList<Event>();
                try {
                    FileInputStream fin = getActivity().getApplicationContext()
                            .openFileInput("myPersonalInformation");
                    int c;
                    String temp = "";
                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }

                   JSONObject aktieEvents = new JSONObject(temp);
                    JSONArray eventsArray = (JSONArray)aktieEvents.get("aktieEvents");
                    if (eventsArray != null) {
                        for (int i=0;i<eventsArray.length();i++){

                            JSONObject tempobj = eventsArray.getJSONObject(i);
                            Calendar cal = Calendar.getInstance();
                           Event mEvent = new Event(cal.getTimeInMillis(),cal.getTimeInMillis(),cal.getTimeInMillis());
                            mEvent.setName(""+tempobj.get("EventName"));
                            mEvent.setEventDay(Integer.parseInt(tempobj.get("EventDay")+""));
                            mEvent.setEventMonth(Integer.parseInt(""+tempobj.get("EventMonth")));
                            mEvent.setDescription(""+tempobj.get("EventDescription"));
                            mEvent.setStartHour(Integer.parseInt(""+tempobj.get("EventStartHour")));
                            mEvent.setStartMinute(Integer.parseInt(""+tempobj.get("EventStartMinute")));

                            //details.clear();
                            if(day.getMonth() == Integer.parseInt(""+tempobj.get("EventMonth")))
                            {
                            if(day.getDay() == Integer.parseInt(tempobj.get("EventDay")+"")) {

                                details.add(mEvent);
                                if(eventDetails!=null)
                                eventDetails.invalidate();
                            }
                            }
                        }
                    }
                }catch (Exception ex)
                {
                    Log.e("Screen3Fragment","Loading events error: "+ex);
                }

                eventDetails = (ListView) rootView.findViewById(R.id.eventDetailsList);
                mAdapter = new AktieEventDetailAdapter(getActivity().getApplicationContext(), details);
                eventDetails.setAdapter(mAdapter);

                //new event ceation form
                final Dialog setEvent = new Dialog(getActivity());
                final Day setDay = day;

                setEvent.setContentView(R.layout.setevent);
                setEvent.setTitle("Almost Done...");

                final EditText setName = (EditText)setEvent.findViewById(R.id.setNameText);
                final EditText setDescription = (EditText)setEvent.findViewById(R.id.setDescriptionText);

                final TimePicker setTime = (TimePicker) setEvent.findViewById(R.id.timePicker);


                //add event to calendar and dismiss the dialog
                Button dialogDoneButton = (Button) setEvent.findViewById(R.id.doneSetEventButton);
                dialogDoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ContentValues values = new ContentValues();
                        values.put(CalendarProvider.EVENT, ""+setName.getText());
                        values.put(CalendarProvider.DESCRIPTION,""+setDescription.getText());
                        values.put(CalendarProvider.LOCATION, "Syracuse");

                        Calendar cal = Calendar.getInstance();
                        //noinspection ResourceType
                        cal.set(setDay.getYear(), setDay.getMonth(), setDay.getDay(), setTime.getCurrentHour(), setTime.getCurrentMinute());
                        values.put(CalendarProvider.START_DAY, setDay.getDay());

                        //noinspection ResourceType
                        cal.set(setDay.getYear(), setDay.getMonth(), setDay.getDay(), setTime.getCurrentHour(), setTime.getCurrentMinute());
                        values.put(CalendarProvider.END_DAY, setDay.getDay());

                        Uri uri = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, values);


                        Event mEvent = new Event(cal.getTimeInMillis(),cal.getTimeInMillis(),cal.getTimeInMillis());

                        mEvent.setName(""+setName.getText());
                        mEvent.setDescription("" + setDescription.getText());
                        mEvent.setEventMonth(setDay.getMonth());
                        mEvent.setEventDay(setDay.getDay());
                        mEvent.setStartHour(setTime.getCurrentHour());
                        mEvent.setStartMinute(setTime.getCurrentMinute());
                        setDay.addEvent(mEvent);

                        //details.add()
                        //details = setDay.getEvents();

                        //updating events in the file
                        try {

                            FileInputStream personalInfoTempFile = getActivity().getApplicationContext()
                                    .openFileInput("myPersonalInformation");
                            int tp;
                            String tempString = "";
                            while ((tp = personalInfoTempFile.read()) != -1) {
                                tempString= tempString + Character.toString((char) tp);
                            }


                        /*create json data*/
                        JSONObject myDeviceInfo = new JSONObject(tempString);

                        JSONArray eventsArray;
                            if(myDeviceInfo.has("aktieEvents")) {
                            eventsArray = myDeviceInfo.getJSONArray("aktieEvents");
                            }
                            else {
                            eventsArray = new JSONArray();
                            }
                          JSONObject tempObject = new JSONObject();
                                    tempObject.put("EventName", mEvent.getName());
                                    tempObject.put("EventDescription",mEvent.getDescription());
                                    tempObject.put("EventStartHour", mEvent.getStartHour());
                                    tempObject.put("EventStartMinute",mEvent.getStartMinute());
                                    tempObject.put("EventMonth",mEvent.getEventMonth());
                                    tempObject.put("EventDay",mEvent.getEventDay());
                                    eventsArray.put(tempObject);
                        myDeviceInfo.put("aktieEvents", eventsArray);

                        /*save json data in local files*/
                            FileOutputStream fOut = getActivity().getApplicationContext()
                                    .openFileOutput("myPersonalInformation",
                                            getActivity().getApplicationContext().MODE_PRIVATE);
                            fOut.write(myDeviceInfo.toString().getBytes());
                            fOut.close();
                            Toast.makeText(getActivity().getBaseContext(), "Event added to calendar",
                                    Toast.LENGTH_SHORT).show();

                            FileInputStream fin = getActivity().getApplicationContext()
                                    .openFileInput("myPersonalInformation");
                            int c;
                            String temp = "";
                            while ((c = fin.read()) != -1) {
                                temp = temp + Character.toString((char) c);
                            }

                        }catch(Exception e)
                        {
                            Log.e("Screen3Fragment","Error is json creation at calendar events"+e);
                        }

                        aktieCalendarView.refreshCalendar();
                        setEvent.dismiss();
                    }
                });

                //on cancel the event is simply discarded
                Button dialogCancelButton = (Button) setEvent.findViewById(R.id.cancelEventButton);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setEvent.dismiss();
                    }
                });

                setEvent.show();
            }
        });

        return rootView;
    }

}

