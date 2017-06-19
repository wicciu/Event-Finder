package pl.witoldbrzezinski.eventfinder.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.witoldbrzezinski.eventfinder.R;
import pl.witoldbrzezinski.eventfinder.model.Event;

/**
 * Created by Wiciu on 17.06.2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder> {

    Context mContext;
    private List<Event> mEventList;

    public EventsAdapter(List<Event> eventList) {
        mEventList = eventList;
    }

    @Override
    public EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        EventsHolder eventsholder = new EventsHolder(view);
        return eventsholder;
    }

    @Override
    public void onBindViewHolder(EventsHolder holder, int position) {
        mContext = holder.itemView.getContext();
        Event event = mEventList.get(position);
        holder.url = event.getUrl();
        holder.name = event.getTitle();
        holder.eventName.setText(holder.name);
        holder.venue = event.getVenueName();
        holder.eventVenue.setText(holder.venue);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public static class EventsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView eventImage;
        TextView eventName;
        TextView eventVenue;
        String name = "";
        String url = "";
        String venue = "";
        Context mContext;

        public EventsHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            eventImage = itemView.findViewById(R.id.event_image);
            eventImage.setImageResource(R.drawable.ic_event_black_24dp);
            eventVenue = itemView.findViewById(R.id.event_venue);
            eventName =  itemView.findViewById(R.id.event_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }
}
