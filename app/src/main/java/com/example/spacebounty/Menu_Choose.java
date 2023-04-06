package com.example.spacebounty;

import static com.example.spacebounty.Menu_SaveLoad.CURRENT_SAVE_FILE;
import static com.example.spacebounty.Menu_SaveLoad.SAVE_FILE;
import static com.example.spacebounty.Menu_SaveLoad.EDITOR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class Menu_Choose extends AppCompatActivity {
    public static Spacecraft_Model MODEL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_menu);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void chooseModel(View view)
    {
        ImageButton shipButton = (ImageButton) findViewById(view.getId());
        String shipButtonName = shipButton.getResources().getResourceEntryName(view.getId());
        String modelName = "";

        int shipNumber = Integer.parseInt(String.valueOf(shipButtonName.charAt(shipButtonName.length() - 1)));

        switch (shipNumber)
        {
            case 0:
                MODEL = Spacecraft_Model.SPEED;
                modelName = "WASP";
                break;
            case 1:
                MODEL = Spacecraft_Model.ATTACK;
                modelName = "MANTIS";
                break;
            case 2:
                MODEL = Spacecraft_Model.DEFENSE;
                modelName = "BEETLE";
                break;
        }

        SAVE_FILE = getSharedPreferences("SLOT" + CURRENT_SAVE_FILE, MODE_PRIVATE);
        EDITOR = SAVE_FILE.edit();

        EDITOR.putBoolean("CREATED", true);
        EDITOR.putString("MODEL", modelName);
        EDITOR.putInt("HIGH SCORE", 0);
        EDITOR.commit();

        startActivity(new Intent(Menu_Choose.this, Game_Activity.class));
        this.finish();
    }
}