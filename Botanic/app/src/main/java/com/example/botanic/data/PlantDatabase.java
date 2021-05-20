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

            databaseInstance.PlantDao()._insertPlant(new Plant("Alove vera","Summer","Once 2-3 weeks","Indirect","Warm",30,"5-25 years","No","No","Arabian desert","It is important to mention that the soil must be sandy \n In the spring and summer it can be places outside",filePaths[3]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Peace lillies","Spring","Constant but moderate","Indirect","Warm",7,"3-5 years","No","No","South America","It can also grow in water alone \n The water must be filtered, they are sensitive to chemicals! \n The flower blooms in spring and needs sunlight to grow",filePaths[9]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Snake plant","Spring,Summer","Once a week","Indirect","Warm",40,"5-10 years","No","No","West Africa","Clean the dust from the leaves \n Water from the bottom of the pot if possible \n Water less in winter",filePaths[8]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Blueberry","Spring,Summer","Twice a week","Full sun","Mild, can tolerate low temperatures",7,"45 years","Yes","Yes","North America","Takes 2-4 years to produce fruit \n Scarecrow is a must, birds favorite snack are blueberries! \n June-August are the best months to pick blueberries",filePaths[5]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Strawberry","Spring,Summer","Once a day","Full sun","Mild, can tolerate low temperatures",5,"5-6 years","Yes","Yes","North America","",filePaths[7]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Basil","Summer","Once a day","Full sun","Warm",6,"Less than a year","No","Yes","India","Perfect for cooking",filePaths[4]));

            databaseInstance.PlantDao()._insertPlant(new Plant("Cilantro","Spring","Once a day","Full sun","Mild",3,"6-7 months","No","Yes","Southern Europe","Perfect for Mexican and Asian cuisine",filePaths[6]));

            return databaseInstance;
        }
    }

    public static String[] createPhotos(Context context){
        String[] filePaths = new String[10];
        int[] drawableIds = {R.drawable.sunflower,R.drawable.avocado,R.drawable.monstera,R.drawable.aloevera,R.drawable.basil,R.drawable.blueberry,R.drawable.cilantro,R.drawable.strawberry,R.drawable.plantsnake,R.drawable.peacelilly};

        Bitmap photo;
        for (int i = 1; i <= 11; i++){
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
