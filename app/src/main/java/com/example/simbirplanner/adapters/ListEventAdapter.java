package com.example.simbirplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simbirplanner.R;
import com.example.simbirplanner.models.EventInformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class ListEventAdapter extends RealmRecyclerViewAdapter<EventInformation,ListEventAdapter.CasesViewHolder> {

    private static final String DATE_FORMAT = "H:mm";
    private static final String TAG = "EventAdapter";

//    private List<EventInformation> results = new ArrayList<>();
    private OnEventClickListener onEventClickListener;
    private DateFormat dateFormat;

    public interface OnEventClickListener{
        void onEventClick(String id);
    }

    public ListEventAdapter(@Nullable OrderedRealmCollection<EventInformation> data,
                            OnEventClickListener onEventClickListener) {
        super(data, true);
        this.onEventClickListener = onEventClickListener;
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        setHasStableIds(true);
    }

    //    public ListEventAdapter(OnEventClickListener onEventClickListener) {
//        this.dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
//        this.onEventClickListener = onEventClickListener;
//    }

    @NonNull
    @Override
    public CasesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CasesViewHolder(inflater.inflate(R.layout.item_case, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CasesViewHolder holder, int position) {
        final EventInformation event = getItem(position);
        holder.eventInformation = event;

        String interval = dateFormat.format(event.getDateStart()) + " – " +
                dateFormat.format(event.getDateFinish());
        holder.bind(interval, event.getName());
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getPosition();
    }

    class CasesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtItemTimestamp)
        TextView txtTimestamp;
        @BindView(R.id.txtItemTitle)
        TextView txtTitle;

        public EventInformation eventInformation;

        public CasesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                String title = eventInformation.getId();
                onEventClickListener.onEventClick(title);
            });
        }

        void bind(String interval, String title) {
            txtTimestamp.setText(interval);
            txtTitle.setText(title);
        }
    }
}