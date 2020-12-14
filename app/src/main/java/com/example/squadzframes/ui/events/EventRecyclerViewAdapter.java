package com.example.squadzframes.ui.events;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.squadzframes.R;
import com.example.squadzframes.model.Event;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.MyViewHolder> {

    Context context;

    List<Event> eventDataList;

    public EventRecyclerViewAdapter(Context ct, List<Event> eventDataList){
        context = ct;
        this.eventDataList = eventDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_events, parent, false);
        //ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Event eventData = eventDataList.get(position);
        String loc, time, host, party;

        holder.locationInfo.setText(eventData.getLocation());
        loc = (String) holder.locationInfo.getText();
        holder.hostInfo.setText(eventData.getHost());
        host = (String) holder.hostInfo.getText();
        holder.timeInfo.setText(eventData.getTime());
        time = (String) holder.timeInfo.getText();
        holder.partyInfo.setText(eventData.getPartySize());
        party = (String) holder.partyInfo.getText();
        //holder.gameplayInfo.setText(eventData.getComplvl());
        //holder.locationInfo.setText(eventData.getLocation());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailsPage.class);
                intent.putExtra("location", loc);
                intent.putExtra("host",host);
                intent.putExtra("time",time);
                intent.putExtra("party",party);
                context.startActivity(intent);
            }
        });

        /*holder.eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailsPage.class);
                intent.putExtra("Time",(Parcelable) holder.timeInfo);
                intent.putExtra("Location", (Parcelable) holder.locationInfo);
                context.startActivity(intent);
            }
        });*/

        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView locationInfo, hostInfo, timeInfo, partyInfo, gameplayInfo;
        ConstraintLayout eventLayout;
        Button details, join;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            locationInfo = itemView.findViewById(R.id.location_info);
            hostInfo = itemView.findViewById(R.id.host_info);
            timeInfo = itemView.findViewById(R.id.time_info);
            partyInfo = itemView.findViewById(R.id.party_info);
            //myText2 = itemView.findViewById(R.id.text_id);
            eventLayout = itemView.findViewById(R.id.eventLayout);
            //TODO
            details = itemView.findViewById(R.id.details_button);
            //TODO
            join = itemView.findViewById(R.id.join_button);
        }
    }
}
