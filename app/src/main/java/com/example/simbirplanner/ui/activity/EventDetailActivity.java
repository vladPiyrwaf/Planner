package com.example.simbirplanner.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.simbirplanner.R;
import com.example.simbirplanner.models.EventInformation;
import com.example.simbirplanner.mvp.presenters.DetailEventPresenter;
import com.example.simbirplanner.mvp.views.DetailEventView;
import com.example.simbirplanner.ui.fragment.EventDetailFragment;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class EventDetailActivity extends MvpAppCompatActivity implements DetailEventView {

    @InjectPresenter
    DetailEventPresenter detailEventPresenter;
    @ProvidePresenter
    DetailEventPresenter provideDetailsPresenter() {
        return new DetailEventPresenter(getIntent().getStringExtra(EVENT_ID));
    }

    private static final String TAG = "DetailActivity";
    public static final String EVENT_ID = "id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

    }

    @Override
    public void showEventInformation(EventInformation event) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentDetailEvent, EventDetailFragment.newInstance(event))
                .commit();
    }
}
