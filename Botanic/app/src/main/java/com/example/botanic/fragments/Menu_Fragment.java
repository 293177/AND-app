package com.example.botanic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.botanic.MainActivity;
import com.example.botanic.R;
import com.example.botanic.data.MainActivityViewModel;

public class Menu_Fragment extends Fragment implements View.OnClickListener {

    public MainActivity mainActivity;
    private MainActivityViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.menu_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        view.findViewById(R.id.btnGarden).setOnClickListener(this);
        view.findViewById(R.id.btnPlantInfo).setOnClickListener(this);
        view.findViewById(R.id.btnLogOut).setOnClickListener(this);
    }
    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case (R.id.btnGarden): {
                mainActivity.changeFragment("Garden");
            } break;

            case (R.id.btnPlantInfo): {
                mainActivity.changeFragment("PlantInfo");
            } break;

            case (R.id.btnLogOut): {
                mainActivity.signOut(view);
            } break;

            default: {
                mainActivity.makeToast("[ERROR] Menu parameter passed was not found, returning to main menu...");
                mainActivity.changeFragment("MainMenu");
            }
        }
    }

}
