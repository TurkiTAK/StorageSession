package com.turki.storageday;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button generateButton;
    private Button saveButton;
    private TextView textView;
    private EditText editText;
    private LinearLayout linearLayout;

    private static final String KEY_COLOR = "color";

    public static MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Building our database object
        database = Room.databaseBuilder(this, MyDatabase.class, "tasks-db")
                .allowMainThreadQueries() //it is better to move database queries off the main thread, but this is out of the scope of our lesson
                .build();

        generateButton = findViewById(R.id.color_change_button);
        saveButton = findViewById(R.id.save_button);
        textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.edit_text);
        linearLayout = findViewById(R.id.main);


        setTextViewText();//get items from database and add it to textview

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length() > 0){//if edit text jas something written in it
                    Task task = new Task(editText.getText().toString(), false, false);

                    database.getTaskDao().save(task);

                    editText.setText("");//resetting edit text

                    setTextViewText();//refresh textview
                }
            }
        });


        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        int previousColor = preferences.getInt(KEY_COLOR, Color.WHITE);
        linearLayout.setBackgroundColor(previousColor);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int red = random.nextInt(255);//0 to 255
                int blue = random.nextInt(255);//0 to 255
                int green = random.nextInt(255);//0 to 255

                int color = Color.argb(100, red, blue, green);//combine all three values to one color

                linearLayout.setBackgroundColor(color);

                preferences.edit().putInt(KEY_COLOR, color).apply();//save to shared preference

            }
        });

    }

    private void setTextViewText() {
        List<Task> allTasks = database.getTaskDao().getAllTasks();

        String string = "";
        for(Task task : allTasks){
            Log.i("ITEM",task.getText());
            string += task.getText() + "\n\n";
        }

        textView.setText(string);
    }
}
