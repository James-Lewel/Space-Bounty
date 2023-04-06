package com.example.spacebounty;

import static com.example.spacebounty.Game_View.JOYSTICK;
import static com.example.spacebounty.Game_View.SPACECRAFT;

import static com.example.spacebounty.Game_View.SCREEN_X;
import static com.example.spacebounty.Game_View.SCREEN_Y;


public class Spacecraft_Mechanics {
    long last_time = System.nanoTime();

    Spacecraft_Mechanics()
    {
    }

    public void update()
    {
        // Handles the movements
        movement();
        SPACECRAFT.getGun().update();
    }

    public void movement()
    {
        long time = System.nanoTime();
        int delta_time = (int) ((time - last_time) / 1000000);
        last_time = time;

        if(JOYSTICK.getIsPressed())
        {
            // Moves and Controls Spacecraft
            int moveX = (int) (SPACECRAFT.getX() + (JOYSTICK.getActuatorX() * (SPACECRAFT.getStats().getSpeed())));
            int moveY = (int) (SPACECRAFT.getY() + (JOYSTICK.getActuatorY() * (SPACECRAFT.getStats().getSpeed())));

            SPACECRAFT.setX ((int) ((moveX)));
            SPACECRAFT.setY ((int) ((moveY)));
        }
        else
        {
        }

        // Checks and Resets Position of Spacecraft
        if(SPACECRAFT.getX() < 0) SPACECRAFT.setX(0);
        if(SPACECRAFT.getX() >= SCREEN_X - SPACECRAFT.getSpaceCraftWidth()) SPACECRAFT.setX(SCREEN_X - SPACECRAFT.getSpaceCraftWidth());
        if(SPACECRAFT.getY() < 0) SPACECRAFT.setY(0);
        if(SPACECRAFT.getY() >= SCREEN_Y - SPACECRAFT.getSpaceCraftHeight()) SPACECRAFT.setY(SCREEN_Y - SPACECRAFT.getSpaceCraftHeight());
    }

}
