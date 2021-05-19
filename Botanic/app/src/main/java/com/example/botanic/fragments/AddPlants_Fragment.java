package com.example.botanic.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.botanic.MainActivity;
import com.example.botanic.Model.Plant;
import com.example.botanic.R;


public class AddPlants_Fragment extends Fragment implements View.OnClickListener {
    
    private String[] CAM_PERMISSION = {Manifest.permission.CAMERA};

    public MainActivity mainActivity;

    public TextView txtName,txtSeasons,txtWatering,txtSunlight,txtTemperature,txtWeekToHarvest,txtCatFriendly, txtPolli, txtLifeSpan,txtOrigin,txtNotes;

    public Bitmap photo = null;

    public Plant tempPlant = null;

    public AddPlants_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_plants_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mainActivity.changeFragment("PlantInfo");            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        view.findViewById(R.id.btnPicture).setOnClickListener(this);
        view.findViewById(R.id.btnGallery).setOnClickListener(this);
        view.findViewById(R.id.btnSavePlant).setOnClickListener(this);

        txtName = view.findViewById(R.id.txtAddPlantName);
        txtCatFriendly = view.findViewById(R.id.txtAddCatFriendly);
        txtTemperature = view.findViewById(R.id.txtAddTemperature);
        txtWeekToHarvest = view.findViewById(R.id.txtAddWeeksToHarvest);
        txtSeasons = view.findViewById(R.id.txtAddSeason);
        txtLifeSpan = view.findViewById(R.id.txtAddLifeSpan);
        txtSunlight = view.findViewById(R.id.txtAddSunlight);
        txtNotes = view.findViewById(R.id.txtAddNotes);
        txtOrigin = view.findViewById(R.id.txtAddOrigin);
        txtPolli = view.findViewById(R.id.txtAddPollination);
        txtWatering= view.findViewById(R.id.txtAddWatering);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btnPicture):{
                if (mainActivity.checkSelfPermission(CAM_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(CAM_PERMISSION, 2);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1970);
                }
            } break;

            case (R.id.btnGallery):{
                if (mainActivity.checkSelfPermission(CAM_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(CAM_PERMISSION, 3);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), 1999);
                }
            } break;


            case (R.id.btnSavePlant): {
                if (txtName.getText().toString().matches("")){
                    mainActivity.makeToast("Please enter plant name!");
                    txtName.requestFocus();
                    return;
                }

                if (txtWatering.getText().toString().matches("")){
                    mainActivity.makeToast("Please introduce the level of water a plant needs!");
                    txtWatering.requestFocus();
                    return;
                }
                if (txtTemperature.getText().toString().matches("")){
                    mainActivity.makeToast("Please introduce the temperature a plant needs!");
                    txtTemperature.requestFocus();
                    return;
                }
                if (txtSunlight.getText().toString().matches("")){
                    mainActivity.makeToast("Please introduce the amount of sunlight a plant needs!");
                    txtSunlight.requestFocus();
                    return;
                }
                if (txtSeasons.getText().toString().matches("")){
                    mainActivity.makeToast("Please introduce the season of the plant!");
                    txtSeasons.requestFocus();
                    return;
                }
                if (txtWeekToHarvest.getText().toString().matches("")){
                    mainActivity.makeToast("Please introduce the amount of weeks a plant needs to harvest!");
                    txtWeekToHarvest.requestFocus();
                    return;
                }
                if (txtCatFriendly.getText().toString().matches("")){
                    mainActivity.makeToast("Please do something");
                    txtCatFriendly.requestFocus();
                    return;
                }
                if (txtPolli.getText().toString().matches("")){
                    mainActivity.makeToast("Please do something");
                    txtPolli.requestFocus();
                    return;
                }
                if (txtLifeSpan.getText().toString().matches("")){
                    mainActivity.makeToast("Please introduce the life span of the plant!");
                    txtLifeSpan.requestFocus();
                    return;
                }
                if (txtOrigin.getText().toString().matches("")){
                    mainActivity.makeToast("Please introduce the origin of the plant!");
                    txtOrigin.requestFocus();
                    return;
                }

                if (photo == null) {
                    photo = ((BitmapDrawable) ResourcesCompat.getDrawable(mainActivity.getResources(), R.drawable.plant, null)).getBitmap();
                }

                tempPlant = new Plant(txtName.getText().toString().trim(),txtSeasons.getText().toString().trim(),txtWatering.getText().toString().trim(),txtSunlight.getText().toString().trim(),txtTemperature.getText().toString().trim(),Integer.parseInt(txtWeekToHarvest.getText().toString().trim()),txtLifeSpan.getText().toString().trim(),txtCatFriendly.getText().toString().trim(),txtPolli.getText().toString().trim(),txtOrigin.getText().toString().trim(),txtNotes.getText().toString().trim(),"");

                mainActivity.editTransaction("InsertPlant", tempPlant);

                resetGUI();

            } break;

            default: {
                mainActivity.makeToast("[ERROR] Menu parameter passed was not found, returning to main menu...");
                mainActivity.changeFragment("MainMenu");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case (1970): {
                if (resultCode == Activity.RESULT_OK)
                {
                    try {
                        photo = (Bitmap) data.getExtras().get("data");
                        if (photo == null)
                            throw new Exception("Photo is Null");
                    } catch (Exception e) {
                        mainActivity.makeToast("Error reading image.");
                        Log.e("CAMERA_REQUEST Intent", "Exception: ", e);
                    }
                }
            } break;

            case (1999): {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri selectedImageUri = data.getData();
                        ParcelFileDescriptor pfd = mainActivity.getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        photo = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                        pfd.close();
                        if (photo == null)
                            throw new Exception("Photo is Null");
                    } catch (Exception e) {
                        mainActivity.makeToast("Error reading selected image.");
                        Log.e("PICK_IMAGE Intent", "Exception: ", e);
                    }
                }
            } break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void resetGUI(){
        txtName.setText("");
        txtOrigin.setText("");
        txtLifeSpan.setText("");
        txtPolli.setText("");
        txtCatFriendly.setText("");
        txtWeekToHarvest.setText("");
        txtSeasons.setText("");
        txtSunlight.setText("");
        txtWatering.setText("");
        txtNotes.setText("");
        txtTemperature.setText("");
    }
}
