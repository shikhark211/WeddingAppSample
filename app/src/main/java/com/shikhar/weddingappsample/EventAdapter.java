package com.shikhar.weddingappsample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by utkarshnath on 27/08/15.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.myViewHolder>  {

    private ArrayList<Event> EventList;
    private LayoutInflater inflater;
    private Context context;

    public EventAdapter(Context context,ArrayList<Event> EventList){
        this.EventList = EventList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.event_item,viewGroup,false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventAdapter.myViewHolder myViewHolder, int i) {
        myViewHolder.EventName.setText(EventList.get(i).EventName);
        myViewHolder.eventRelativeLayout.setBackground(EventList.get(i).EventPhoto);
        myViewHolder.EventDate.setText(EventList.get(i).EventDate);
        myViewHolder.EventMonth.setText(EventList.get(i).EventMonth);
        myViewHolder.EventTime.setText(EventList.get(i).EventTime);
        myViewHolder.EventVenue.setText(EventList.get(i).EventVenue);
//        myViewHolder.EventDiscription.setText(EventList.get(i).EventDiscription);
    }



    @Override
    public int getItemCount()
    {
        return EventList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView EventName;
        TextView EventDate;
        TextView EventMonth;
        TextView EventTime;
        TextView EventVenue;
        TextView EventDiscription;
        ImageView EventPhoto;
        RelativeLayout eventRelativeLayout;

        public myViewHolder(View itemView) {
            super(itemView);
            eventRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.event_relativeView);
            EventName = (TextView) itemView.findViewById(R.id.event_name);
            EventDate = (TextView) itemView.findViewById(R.id.event_date);
            EventPhoto = (ImageView) itemView.findViewById(R.id.event_Photo);
            EventMonth = (TextView) itemView.findViewById(R.id.event_month);
            EventTime = (TextView) itemView.findViewById(R.id.event_Time);
            EventVenue = (TextView) itemView.findViewById(R.id.event_venue);
        }


    }
}
