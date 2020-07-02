package com.example.simbirplanner.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.simbirplanner.R;
import com.example.simbirplanner.adapters.ListAllEventAdapter;
import com.example.simbirplanner.adapters.ListEventAdapter;
import com.example.simbirplanner.models.EventInformation;
import com.example.simbirplanner.mvp.presenters.MainPresenter;
import com.example.simbirplanner.mvp.views.MainView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    @ProvidePresenter
    MainPresenter provideMainPresenter() {
        return new MainPresenter(selectDay);
    }

    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.rvDayPlans)
    RecyclerView recyclerView;
    @BindView(R.id.fabCreateCase)
    FloatingActionButton floatingActionButton;

    private static final String TAG = "MainActivity";

    private long selectDay;

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        TouchHelperCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.d(TAG, "onSwiped: viewHolder: " + viewHolder.getItemId());
//            mainPresenter.deleteEvent(viewHolder.getItemId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        selectDay = calendarView.getFirstSelectedDate().getTimeInMillis();
        mainPresenter.getAllEvents();

        floatingActionButton.setOnClickListener((view) -> {
            mainPresenter.openCreateEventScreen();
        });

        calendarView.setOnDayClickListener((eventDay) -> {
            Log.d(TAG, "eventDay: " + eventDay.getCalendar().getTimeInMillis());
            selectDay = eventDay.getCalendar().getTimeInMillis();
            mainPresenter.loadEventSelectedDay(selectDay);

        });
    }

    ListEventAdapter.OnEventClickListener onEventClickListener = (id) -> {
            Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EVENT_ID, id);
            startActivity(intent);
    };

    @Override
    public void showSelectedEvents(List<EventInformation> events) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RealmResults<EventInformation> results = (RealmResults<EventInformation>) events;
        ListEventAdapter eventsAdapter = new ListEventAdapter(results, onEventClickListener);
        recyclerView.setAdapter(eventsAdapter);

        TouchHelperCallback callback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void showAllEvents(List<EventInformation> events) {
        LinearLayoutManager allEventsManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(allEventsManager);
        ListAllEventAdapter eventAdapter = new ListAllEventAdapter();
        recyclerView.setAdapter(eventAdapter);
        eventAdapter.setDate(events);
    }

    @Override
    public void openCreateEventScreen() {
        Intent intent = new Intent(MainActivity.this, CreateCaseActivity.class);
        intent.putExtra("selectDay", selectDay);
        startActivityForResult(intent, 1);
    }

    @Override
    public void successDeleteEvent() {
        Toast.makeText(this, "this event was deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDeleteEvent(Throwable throwable) {
        Log.d(TAG, "showErrorDeteEvent: " + throwable.getMessage());
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            Toast.makeText(this, "Event \"" + data.getStringExtra("title") + "\"was added", Toast.LENGTH_SHORT).show();
    }

}
