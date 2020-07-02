package com.example.simbirplanner.ui.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.simbirplanner.R;
import com.example.simbirplanner.mvp.presenters.CreateEventPresenter;
import com.example.simbirplanner.mvp.views.CreateEventView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class CreateCaseActivity extends MvpAppCompatActivity implements CreateEventView {

    @InjectPresenter
    CreateEventPresenter createEventPresenter;

    @BindView(R.id.editTextDescription)
    TextInputEditText edDescription;
    @BindView(R.id.editTextTitle)
    TextInputEditText edTitle;
    @BindView(R.id.txtTimeStart)
    TextView txtTimeStart;
    @BindView(R.id.txtTimeFinish)
    TextView txtTimeFinish;
    @BindView(R.id.buttonCreateCase)
    Button btnCreateCase;
    @BindView(R.id.imageEvent)
    ImageView imageView;
    @BindView(R.id.imageBtnAdd)
    ImageButton imageButton;
    @BindView(R.id.progressEventCreate)
    ProgressBar progressBar;

    private static final String TAG = "CreateCaseActivity";
    private static final int GALLERY_REQUEST = 1;

    private TimePickerDialog timePickerDialogStart;
    private TimePickerDialog timePickerDialogFinish;

    private Calendar dateStart = Calendar.getInstance();
    private Calendar dateFinish = Calendar.getInstance();

    private long selectDay;
    private Uri selectedImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_case);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create new Event");

        selectDay = getIntent().getLongExtra("selectDay", 0L);
        Log.d(TAG, "onCreate: " + selectDay);
        dateStart.setTimeInMillis(selectDay);
        dateFinish.setTimeInMillis(selectDay);
        Log.d(TAG, "onCreate dateStart: " + dateStart.getTime());
        Log.d(TAG, "onCreate dateFinish: " + dateFinish.getTime());

    }

    @OnClick(R.id.imageBtnAdd)
    public void loadImage() {
        Intent imagePickerIntent = new Intent(Intent.ACTION_PICK);
        imagePickerIntent.setType("image/*");
        startActivityForResult(imagePickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                imageButton.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);

                selectedImage = data.getData();
                Picasso.get().load(selectedImage).into(imageView);
            }
        }
    }


    @Override
    public void showErrorInput(Integer titleError, Integer descriptionError) {
        edTitle.setError(titleError == null ? null : getString(titleError));
        edDescription.setError(descriptionError == null ? null : getString(descriptionError));
    }

    @OnClick(R.id.buttonCreateCase)
    void createCase() {
        attemptAddEvent();
    }

    private void attemptAddEvent() {
        createEventPresenter.addEvent(edTitle.getText().toString(),
                edDescription.getText().toString(),
                selectDay,
                dateStart.getTimeInMillis(),
                dateFinish.getTimeInMillis(),
                selectedImage);
    }

    @OnClick(R.id.txtTimeStart)
    void clickTimeStart() {
        createEventPresenter.showTimePickerDialogStart();
    }

    @OnClick(R.id.txtTimeFinish)
    void clickTimeFinish() {
        createEventPresenter.showTimePickerDialogFinish();
    }

    @Override
    public void showTimePickerDialogStart() {
        timePickerDialogStart = new TimePickerDialog(CreateCaseActivity.this,
                timeStartListener,
                dateStart.get(Calendar.HOUR_OF_DAY),
                dateStart.get(Calendar.MINUTE),
                true);
        timePickerDialogStart.setOnDismissListener(dialog -> createEventPresenter.onDismissTimePicker());
        timePickerDialogStart.show();
    }

    private TimePickerDialog.OnTimeSetListener timeStartListener = (view, hourOfDay, minute) -> {
        dateStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateStart.set(Calendar.MINUTE, minute);
        Log.d(TAG, "timeStart: " + dateStart.getTime());
        setInitialTimeStart();
    };

    private void setInitialTimeStart() {
        dateStart.set(Calendar.SECOND, dateStart.getActualMinimum(Calendar.SECOND));
        dateStart.set(Calendar.MILLISECOND, dateStart.getActualMinimum(Calendar.MILLISECOND));

        txtTimeStart.setText(DateUtils.formatDateTime(this,
                dateStart.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    @Override
    public void showTimePickerDialogFinish() {
        timePickerDialogFinish = new TimePickerDialog(CreateCaseActivity.this,
                timeFinishListener,
                dateFinish.get(Calendar.HOUR_OF_DAY),
                dateFinish.get(Calendar.MINUTE),
                true);
        timePickerDialogFinish.setOnDismissListener(dialog -> createEventPresenter.onDismissTimePicker());
        timePickerDialogFinish.show();
    }

    private TimePickerDialog.OnTimeSetListener timeFinishListener = (view, hourOfDay, minute) -> {
        dateFinish.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateFinish.set(Calendar.MINUTE, minute);
        Log.d(TAG, "timeFinish: " + dateFinish.getTime());
        setInitialTimeFinish();
    };

    private void setInitialTimeFinish() {
        dateFinish.set(Calendar.SECOND, dateStart.getActualMinimum(Calendar.SECOND));
        dateFinish.set(Calendar.MILLISECOND, dateFinish.getActualMinimum(Calendar.MILLISECOND));

        txtTimeFinish.setText(DateUtils.formatDateTime(this,
                dateFinish.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    @Override
    public void successSaveEvent() {
        Intent intent = new Intent();
        intent.putExtra("title", edTitle.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void hideTimePicker() {
        if (timePickerDialogStart != null) {
            timePickerDialogStart.dismiss();
        } else if (timePickerDialogFinish != null) {
            timePickerDialogFinish.dismiss();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timePickerDialogStart != null) {
            timePickerDialogStart.setOnDismissListener(null);
            timePickerDialogStart.dismiss();
        }
        if (timePickerDialogFinish != null) {
            timePickerDialogFinish.setOnDismissListener(null);
            timePickerDialogFinish.dismiss();
        }
    }

    @Override
    public void showErrorCreate(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHideProgressBar(boolean visibility) {
        progressBar.setVisibility(visibility ? ProgressBar.VISIBLE : ProgressBar.INVISIBLE);
    }

    @Override
    public void clickableButton(boolean clickable) {
        if (clickable) {
            btnCreateCase.setEnabled(true);
        } else {
            btnCreateCase.setEnabled(false);
        }
    }

}
