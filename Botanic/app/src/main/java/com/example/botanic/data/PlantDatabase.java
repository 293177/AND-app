package com.example.botanic.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.res.ResourcesCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.botanic.MainActivity;
import com.example.botanic.Model.Plant;
import com.example.botanic.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Database(entities = {Plant.class}, exportSchema = false, version = 2)

public abstract class PlantDatabase extends RoomDatabase {
    public static synchronized PlantDatabase getInstance (Context context) {
        PlantDatabase databaseInstance;

        File file = new File (MainActivity.DATABASE_DIRECTORY, MainActivity.DB_NAME);

        if (file.exists()) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), PlantDatabase.class, MainActivity.DB_NAME).fallbackToDestructiveMigration().build();
            return databaseInstance;
        } else {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    PlantDatabase.class, MainActivity.DB_NAME).fallbackToDestructiveMigration().build();

            String[] filePaths = createPhotos(context);


            databaseInstance.PlantDao()._insertPlant(new Plant("Sunflower","Summer","Constant but moderate","6h min","Warm",4,"5-6 months","No","Yes","North America","",filePaths[0]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Avocado","Summer","Once a day","4h min","Warm",10,"2 years","Yes","Yes","South America","",filePaths[1]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Monstera","Summer","Every 1-2 weeks","Indirect light","Warm",3,"5 years","No","Yes","Central America","",filePaths[2]));


            return databaseInstance;
        }
    }

    public static String[] createPhotos(Context context){
        String[] filePaths = new String[3];
        int[] drawableIds = {R.drawable.girasol,R.drawable.avocado,R.drawable.monstera};

        Bitmap photo;
        for (int i = 1; i <= 3; i++){
            File f = new File(MainActivity.PLANT_MEDIA_LOCATION, String.valueOf(i));
            f.mkdir();
            filePaths[i-1] = f.getAbsoluteFile() + "/" + i + ".jpg";
            photo = ((BitmapDrawable) ResourcesCompat.getDrawable(context.getResources(), drawableIds[i-1], null)).getBitmap();

            try (FileOutputStream out = new FileOutputStream(filePaths[i-1])) {
                photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePaths;
    }

    public abstract PlantDao PlantDao();
}
