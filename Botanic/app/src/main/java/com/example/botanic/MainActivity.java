package com.example.botanic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.botanic.Model.Plant;
import com.example.botanic.data.MainActivityViewModel;
import com.example.botanic.data.PlantDatabase;
import com.example.botanic.fragments.AddPlants_Fragment;
import com.example.botanic.fragments.Menu_Fragment;
import com.example.botanic.fragments.PlantInfo_Fragment;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String ROOT_MEDIA_LOCATION,
            TEMP_MEDIA_LOCATION,
            PLANT_MEDIA_LOCATION,
            LOG_MEDIA_LOCATION,
            DB_NAME = "botanic.db",
            DATABASE_DIRECTORY = "./data/data/" + BuildConfig.APPLICATION_ID + "/databases/";

    public Menu_Fragment menu_fragment;
    public AddPlants_Fragment addPlants_fragment;
    public PlantInfo_Fragment plantInfo_fragment;
    private MainActivityViewModel viewModel;
    public List<Plant> PlantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();
        setContentView(R.layout.activity_main);
        menu_fragment = new Menu_Fragment();
        addPlants_fragment = new AddPlants_Fragment();
        plantInfo_fragment = new PlantInfo_Fragment();

        File ext_folder = this.getFilesDir();
        File media_loc = new File(ext_folder, "media");
        media_loc.mkdir();
        ROOT_MEDIA_LOCATION =  media_loc.getAbsolutePath() + "/";

        File temp_loc = new File(media_loc, "Temp");
        temp_loc.mkdir();
        TEMP_MEDIA_LOCATION =  temp_loc.getAbsolutePath() + "/";

        File plant_loc = new File(media_loc, "Plants");
        plant_loc.mkdir();
        PLANT_MEDIA_LOCATION = plant_loc.getAbsolutePath() + "/";

        File log_loc = new File(media_loc, "Logs");
        log_loc.mkdir();
        LOG_MEDIA_LOCATION = log_loc.getAbsolutePath() + "/";

        editTransaction("UpdateAllLists", null);

        changeFragment("MainMenu");

    }
    private void checkIfSignedIn() {
        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                String message = "Welcome " + user.getDisplayName();
                Toast.makeText(this,message,Toast.LENGTH_SHORT);
            } else
                startLoginActivity();
        });
    }
    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
    public void signOut(View v) {
        viewModel.signOut();
    }
    public void changeFragment(String menuFragment) {
        switch (menuFragment) {
            case "MainMenu": {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentActivity, menu_fragment).commit();
            } break;

            case "PlantInfo": {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentActivity, plantInfo_fragment).commit();
            } break;

            case "AddPlants": {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentActivity, addPlants_fragment).commit();
            } break;
            default: {
                makeToast("[ERROR] Menu parameter passed was not found, returning to main menu...");
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentActivity, menu_fragment).commit();
            }
        }
    }

    public void makeToast(String Message) {
        Toast toast = Toast.makeText(this, Message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

    public void deleteDirectory(File directoryToBeDeleted){
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }


    public void editTransaction(String xTransactionType, Object xObject){
        new DatabaseTransaction(xTransactionType, xObject).execute();
    }

    public class DatabaseTransaction extends AsyncTask {

        ProgressDialog progress;
        String transactionType;
        Object object;

        public DatabaseTransaction(String xTransactionType, Object xObject) {
            super();
            transactionType = xTransactionType;
            object = xObject;
            progress = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setTitle("Loading " + transactionType + " Transaction.");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            switch (transactionType){
                case ("InsertPlant"): {

                    Bitmap photo = addPlants_fragment.photo;

                    PlantDatabase.getInstance(getApplicationContext()).PlantDao().insertPlant((Plant) object, photo);
                    addPlants_fragment.photo = null;
                    MainActivity.this.changeFragment("PlantInfo");

                } break;

                case ("DeletePlant"): {
                    PlantDatabase.getInstance(getApplicationContext()).PlantDao().deletePlant((Plant) object);
                } break;

                case ("UpdateAllLists"): {
                } break;

            }

            File tempDir = new File (MainActivity.TEMP_MEDIA_LOCATION);
            if (tempDir.isDirectory()){
                deleteDirectory(tempDir);
                tempDir.mkdir();
            }
            PlantList = PlantDatabase.getInstance(MainActivity.this).PlantDao().getAllPlants();

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progress.dismiss();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progress.dismiss();
        }
    }
}