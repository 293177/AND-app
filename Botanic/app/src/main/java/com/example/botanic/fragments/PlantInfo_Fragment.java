package com.example.botanic.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.botanic.MainActivity;
import com.example.botanic.Model.Plant;
import com.example.botanic.R;

import java.util.List;

public class PlantInfo_Fragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {

    public MainActivity mainActivity;
    public List<Plant> plantList;
    public String[] plantNames;
    public Spinner spnrSelectPlant;
    public ImageView imageView;
    public TextView txtSeasons,txtWatering,txtSunlight,txtTemperature,txtLifeSpan,txtPollination,txtCatFriendly,txtOrigin,txtHarvest,txtNotes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.plant_info_fragment, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        plantList = mainActivity.PlantList;

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mainActivity.changeFragment("MainMenu");            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        imageView = view.findViewById(R.id.plantCrop);
        txtHarvest =view.findViewById(R.id.txtHarvest);
        txtLifeSpan = view.findViewById(R.id.txtLifeSpan);
        txtNotes = view.findViewById(R.id.txtNotes);
        txtOrigin = view.findViewById(R.id.txtOrigin);
        txtPollination = view.findViewById(R.id.txtPollination);
        txtSeasons = view.findViewById(R.id.txtSeasons);
        txtSunlight = view.findViewById(R.id.txtSunlight);
        txtTemperature = view.findViewById(R.id.txtTemperature);
        txtWatering = view.findViewById(R.id.txtWatering);
        txtCatFriendly = view.findViewById(R.id.txtCatFriendly);
        view.findViewById(R.id.btnAddPlant).setOnClickListener(this);
        view.findViewById(R.id.btnDelete).setOnClickListener(this);
        view.findViewById(R.id.btnNext).setOnClickListener(this);
        view.findViewById(R.id.btnPrev).setOnClickListener(this);
        view.findViewById(R.id.arrowNext).setOnClickListener(this);
        view.findViewById(R.id.arrowPrev).setOnClickListener(this);

        spnrSelectPlant = view.findViewById(R.id.spnrSelect);
        plantNames = new String[plantList.size()];
        for (int i = 0; i < plantList.size(); i++){
            plantNames[i] = plantList.get(i).getPlantName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter <String> (getActivity(), R.layout.spinner_item, plantNames);
        spnrSelectPlant.setAdapter(adapter);
        spnrSelectPlant.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onClick (View view) {
        Integer id = view.getId();

        if (id == R.id.btnAddPlant) {
            mainActivity.changeFragment("AddPlants");
        }

        else if (id == R.id.btnPrev || id == R.id.arrowPrev) {
            int position = spnrSelectPlant.getSelectedItemPosition();
            if (position > 0)
                spnrSelectPlant.setSelection(spnrSelectPlant.getSelectedItemPosition() - 1);
        }

        else if (id == R.id.btnNext || id == R.id.arrowNext) {
            int position = spnrSelectPlant.getSelectedItemPosition();
            if (position < spnrSelectPlant.getAdapter().getCount() - 1)
                spnrSelectPlant.setSelection(spnrSelectPlant.getSelectedItemPosition() + 1);
        }

        else if (id == R.id.btnDelete) {

            if (spnrSelectPlant.getAdapter().getCount() == 1){
                makeToast("You must have at least 1 plant.");
            } else {
                openConfirmationDialog(mainActivity);
            }
        }

        else {
            makeToast("[ERROR] Menu parameter passed was not found, returning to main menu...");

            mainActivity.changeFragment("MainMenu");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Plant plant = plantList.get(position);
        Drawable plantImage = Drawable.createFromPath(plant.getPhotoPath());
        imageView.setImageDrawable(plantImage);
        txtHarvest.setText(Integer.toString(plant.getWeeksToHarvest()));
        txtWatering.setText(plant.getWatering());
        txtLifeSpan.setText(plant.getLifeSpan());
        txtTemperature.setText(plant.getTemperature());
        txtSunlight.setText(plant.getSunlight());
        txtSeasons.setText(plant.getSeason());
        txtCatFriendly.setText(plant.getCatFriendly());
        txtPollination.setText(plant.getPollination());
        txtOrigin.setText(plant.getOrigin());
        if (plant.getNotes().matches("")){
            txtNotes.setText("No notes.");
        } else {
            txtNotes.setText(plant.getNotes());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void makeToast(String Message) {
        Toast toast = Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }


    private void openConfirmationDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Are You Sure You Want To Delete Plant " + plantList.get(spnrSelectPlant.getSelectedItemPosition()).getPlantName() + "?")
                .setMessage(Html.fromHtml("All corresponding information and notes will be deleted. This action cannot be undone."))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int position = spnrSelectPlant.getSelectedItemPosition();
                        Plant plant = plantList.get(position);
                        mainActivity.editTransaction("DeletePlant", plant);
                        plantList = mainActivity.PlantList;
                        mainActivity.changeFragment("MainMenu");
                    }
                }).setNegativeButton("Cancel", null).setIcon(R.drawable.ic_baseline_warning_amber_24).show();
    }
}
