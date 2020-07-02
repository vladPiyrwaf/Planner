package com.example.simbirplanner.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simbirplanner.R;
import com.example.simbirplanner.models.EventInformation;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateEditEventFragment extends Fragment {

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

    private static final String CREATE_EDIT = "createEditEvent";

    private EventInformation event;

    public static Fragment newInstance(EventInformation event) {
        Bundle args = new Bundle();
        args.putParcelable(CREATE_EDIT, event);

        CreateEditEventFragment createEditEventFragment = new CreateEditEventFragment();
        createEditEventFragment.setArguments(args);
        return createEditEventFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(CREATE_EDIT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_case, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (event != null) {
            imageButton.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            edTitle.setText(event.getName());
            edDescription.setText(event.getDescription());
            txtTimeStart.setOnClickListener(null);
            txtTimeFinish.setOnClickListener(null);
            Picasso.get().load(event.getImageUrl()).into(imageView);
        }
    }

    @OnClick(R.id.buttonCreateCase)
    public void createEditEvent() {
        String title = edTitle.getText().toString();
        String description = edDescription.getText().toString();
    }
}
