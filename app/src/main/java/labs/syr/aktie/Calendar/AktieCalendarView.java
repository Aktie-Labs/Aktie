package labs.syr.aktie.Calendar;

import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import labs.syr.aktie.R;


/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: AktieCalendarView.java
 * Description: This class is the main calendar view for the project and is the central controller
 *
 */
public class AktieCalendarView extends RelativeLayout implements OnItemClickListener,
	OnClickListener{

    /*initializers*/
	private Context context;
	private OnDayClickListener dayListener;
	private GridView calendar;
	private CalendarAdapter mAdapter;
	private Calendar cal;
	private TextView month;
	private RelativeLayout base;
	private ImageView next,prev;

    /*interface for the day click event*/
	public interface OnDayClickListener{
		public void onDayClicked(AdapterView<?> adapter, View view, int position, long id, Day day);
	}

    /*various constructors*/
	public AktieCalendarView(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	public AktieCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	public AktieCalendarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

    /*initializer which populates the calendar*/
	private void init(){

        /*setting all the required values*/
		cal = Calendar.getInstance();
		base = new RelativeLayout(context);
		base.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		base.setMinimumHeight(50);
		
		base.setId(4);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.leftMargin = 16;
		params.topMargin = 50;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		prev = new ImageView(context);
		prev.setId(1);
		prev.setLayoutParams(params);
		prev.setImageResource(R.drawable.navigation_previous_item);
		prev.setOnClickListener(this);
		base.addView(prev);
		
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		month = new TextView(context);
		month.setId(2);
		month.setLayoutParams(params);
		month.setTextAppearance(context, android.R.attr.textAppearanceLarge);
		month.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "+cal.get(Calendar.YEAR));
		month.setTextSize(25);
		
		base.addView(month);
		
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.rightMargin = 16;
		params.topMargin = 50;
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		next = new ImageView(context);
		next.setImageResource(R.drawable.navigation_next_item);
		next.setLayoutParams(params);
		next.setId(3);
		next.setOnClickListener(this);
		
		base.addView(next);
		
		addView(base);
		
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.bottomMargin = 20;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.BELOW, base.getId());
		
		calendar = new GridView(context);
		calendar.setLayoutParams(params);
		calendar.setVerticalSpacing(4);
		calendar.setHorizontalSpacing(4);
		calendar.setNumColumns(7);
		calendar.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
		calendar.setDrawSelectorOnTop(true);



		mAdapter = new CalendarAdapter(context,cal);
		calendar.setAdapter(mAdapter);
		/*actually adding it to the view*/
        addView(calendar);
	}

    /*identify click on day to add event*/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(dayListener != null){
			Day d = (Day) mAdapter.getItem(arg2);
			if(d.getDay() != 0){
				dayListener.onDayClicked(arg0, arg1, arg2, arg3,d);
			}
		}
	}
	
	/*implementation of the setOnDayClickListener interface*/
	public void setOnDayClickListener(OnDayClickListener listener){
		if(calendar != null){
			dayListener = listener;
			calendar.setOnItemClickListener(this);
		}
	}

    /*navigate between the months*/
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case 1:
			previousMonth();
			break;
		case 3:
			nextMonth();
			break;
		default:
			break;
		}
	}

    /*shows the previous month*/
	private void previousMonth(){
		if(cal.get(Calendar.MONTH) == cal.getActualMinimum(Calendar.MONTH)) {				
			cal.set((cal.get(Calendar.YEAR)-1),cal.getActualMaximum(Calendar.MONTH),1);
		} else {
			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
		}
		rebuildCalendar();
	}

    /*shows the next month*/
	private void nextMonth(){
		if(cal.get(Calendar.MONTH) == cal.getActualMaximum(Calendar.MONTH)) {				
			cal.set((cal.get(Calendar.YEAR)+1),cal.getActualMinimum(Calendar.MONTH),1);
		} else {
			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
		}
		rebuildCalendar();
	}

    /*used to rebuild the calendar based on the changed month*/
	private void rebuildCalendar(){
		if(month != null){
			month.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())+" "+cal.get(Calendar.YEAR));
			refreshCalendar();
		}
	}
	
	/*Refreshes the month*/
	public void refreshCalendar(){
		mAdapter.refreshDays();
		mAdapter.notifyDataSetChanged();
	}
}
