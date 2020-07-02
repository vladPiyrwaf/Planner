package com.example.simbirplanner.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simbirplanner.R;
import com.example.simbirplanner.models.EventInformation;
import com.example.simbirplanner.ui.activity.CreateCaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetailFragment extends Fragment {

    @BindView(R.id.txtDetailTitle)
    TextView txtDetailTitle;
    @BindView(R.id.txtDetailDateStart)
    TextView txtDetailDate;
    @BindView(R.id.txtDetailTimeStart)
    TextView txtDetailTime;
    @BindView(R.id.txtDetailDateFinish)
    TextView txtDetailDateFinish;
    @BindView(R.id.txtDetailTimeFinish)
    TextView txtDetailTimeFinish;
    @BindView(R.id.txtDetailDescription)
    TextView txtDetailDescription;
    @BindView(R.id.imageDetailEvent)
    ImageView imageView;
    @BindView(R.id.flActBtnEditEvent)
    FloatingActionButton flActBtnEditEvent;

    private static final String EVENT = "event";

    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, new Locale("EN"));
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    private EventInformation event;

    public static EventDetailFragment newInstance(EventInformation event) {
        Bundle args = new Bundle();
        args.putParcelable(EVENT, event);

        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(EVENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtDetailTitle.setText(event.getName());
        txtDetailDescription.setText(event.getDescription());
        txtDetailDate.setText(dateFormat.format(event.getPosition()));
        txtDetailTime.setText(timeFormat.format(event.getDateStart()));
        txtDetailDateFinish.setText(dateFormat.format(event.getPosition()));
        txtDetailTimeFinish.setText(timeFormat.format(event.getDateFinish()));
        if (event.getImageUrl()== null){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_white__cat));
        } else {
            Picasso.get().load(event.getImageUrl()).into(imageView);
        }
    }

    @OnClick(R.id.flActBtnEditEvent)
    void editEvent(){
        Intent intent = new Intent(getActivity(), CreateCaseActivity.class);
        intent.putExtra("id", event.getId());
        startActivityForResult(intent, 1);
    }
}
