package com.example.simbirplanner.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simbirplanner.R;
import com.example.simbirplanner.models.EventInformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;

public class ListAllEventAdapter extends RecyclerView.Adapter<ListAllEventAdapter.AllEventViewHolder>
implements RealmChangeListener {

    private static final String DATE_FORMAT = "dd-MMM-YYYY HH:mm";
    private List<EventInformation> events;
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

    public void setDate(List<EventInformation> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new AllEventViewHolder(
                inflater.inflate(R.layout.item_event_information, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllEventViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }

    class AllEventViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtDateStart)
        TextView txtDateStart;
        @BindView(R.id.txtDateFinish)
        TextView txtDateFinish;
        @BindView(R.id.txtEventTitle)
        TextView txtEventTitle;
        @BindView(R.id.txtEventDescription)
        TextView txtEventDescription;

        public AllEventViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(EventInformation event) {
            Log.d("bind", "bind: " + event.getName());
            Date dateStart = new Date(event.getDateStart());
            Date dateFinish = new Date(event.getDateFinish());
            txtEventTitle.setText(event.getName());
            txtDateStart.setText(dateFormat.format(dateStart));
            txtDateFinish.setText(dateFormat.format(dateFinish));
            txtEventDescription.setText(event.getDescription());
        }
    }
}
