package com.example.spacebounty;

import static com.example.spacebounty.Menu_Choose.MODEL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Menu_SaveLoad extends AppCompatActivity {
    public static int CURRENT_SAVE_FILE;

    public static SharedPreferences SAVE_FILE;
    public static SharedPreferences.Editor EDITOR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_save_load);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        checkFiles();
    }

    public void checkFiles()
    {
        for(int i = 0; i < 3; i++)
        {
            SAVE_FILE = getSharedPreferences("SLOT" + i, MODE_PRIVATE);
            EDITOR = SAVE_FILE.edit();

            if(!SAVE_FILE.contains("CREATED"))
            {
                EDITOR.putBoolean("CREATED", false);
                EDITOR.commit();
            }
        }

        for(int i = 0; i < 3; i++)
        {
            int newButtonId = getResources().getIdentifier("newButton_" + i, "id", getPackageName());
            Button newButton = (Button) findViewById(newButtonId);

            int loadButtonId = getResources().getIdentifier("loadButton_" + i, "id", getPackageName());
            Button loadButton = (Button) findViewById(loadButtonId);

            int deleteButtonId = getResources().getIdentifier("deleteButton_" + i, "id", getPackageName());
            Button deleteButton = (Button) findViewById(deleteButtonId);

            SAVE_FILE = getSharedPreferences("SLOT" + i, MODE_PRIVATE);
            if(SAVE_FILE.getBoolean("CREATED", false))
            {
                newButton.setEnabled(false);
                loadButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
            else
            {
                newButton.setEnabled(true);
                loadButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        }
    }


    public void createSaveSlot(View view)
    {
        Button createButton = (Button) findViewById(view.getId());
        String createButtonName = createButton.getResources().getResourceEntryName(view.getId());

        CURRENT_SAVE_FILE = Integer.parseInt(String.valueOf(createButtonName.charAt(createButtonName.length() - 1)));

        startActivity(new Intent(Menu_SaveLoad.this, Menu_Choose.class));

        checkFiles();
    }

    public void deleteSaveFile(View view)
    {
        Button deleteButton = (Button) findViewById(view.getId());
        String deleteButtonName = deleteButton.getResources().getResourceEntryName(view.getId());

        CURRENT_SAVE_FILE = Integer.parseInt(String.valueOf(deleteButtonName.charAt(deleteButtonName.length() - 1)));
        SAVE_FILE = getSharedPreferences("SLOT" + CURRENT_SAVE_FILE, MODE_PRIVATE);
        EDITOR = SAVE_FILE.edit();

        if(SAVE_FILE.getBoolean("CREATED", false))
        {
            EDITOR.clear();
            EDITOR.commit();
        }

        checkFiles();
    }

    public void loadSaveFile(View view)
    {
        Button loadButton = (Button) findViewById(view.getId());
        String loadButtonName = loadButton.getResources().getResourceEntryName(view.getId());

        CURRENT_SAVE_FILE = Integer.parseInt(String.valueOf(loadButtonName.charAt(loadButtonName.length() - 1)));
        SAVE_FILE = getSharedPreferences("SLOT" + CURRENT_SAVE_FILE, MODE_PRIVATE);
        EDITOR = SAVE_FILE.edit();

        switch (SAVE_FILE.getString("MODEL", ""))
        {
            case "WASP":
                MODEL = Spacecraft_Model.SPEED;
                break;
            case "MANTIS":
                MODEL = Spacecraft_Model.ATTACK;
                break;
            case "BEETLE":
                MODEL = Spacecraft_Model.DEFENSE;
                break;
            default:
                return;
        }

        startActivity(new Intent(Menu_SaveLoad.this, Game_Activity.class));

    }
}