package com.example.botanic.data;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.botanic.MainActivity;
import com.example.botanic.Model.Plant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Dao
public abstract class PlantDao {

    @Query("SELECT * FROM Plant")
    public abstract List<Plant> getAllPlants();

    @Insert
    public abstract long _insertPlant(Plant plant);

    @Delete
    public abstract void _deletePlant(Plant plant);

    @Update
    public abstract void _updatePlant(Plant plant);

    public void insertPlant(Plant plant, Bitmap photo) {

        long id = _insertPlant(plant);
        File f = new File(MainActivity.PLANT_MEDIA_LOCATION, String.valueOf(id));
        f.mkdir();
        String filePath = f.getAbsoluteFile() + "/" +  id + ".png";

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            photo.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        plant.setPhotoPath(filePath);
        plant.setPlantID((int)id);
        updatePlant(plant);
    }

    public void deletePlant(Plant plant){
        _deletePlant(plant);
        _deleteDirectory(new File(MainActivity.PLANT_MEDIA_LOCATION + "/" + plant.getPlantID()));
    }

    public void updatePlant(Plant plant){
        _updatePlant(plant);
    }

    private void _deleteDirectory(File directoryToBeDeleted){
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                _deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }
}
