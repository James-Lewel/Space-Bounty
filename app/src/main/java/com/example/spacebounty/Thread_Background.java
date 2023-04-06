package com.example.spacebounty;

import static com.example.spacebounty.Game_View.IS_PLAYING;
import static com.example.spacebounty.Game_View.SCREEN_X;
import static com.example.spacebounty.Game_View.SCREEN_RATIO_X;

public class Thread_Background extends Thread
{
    private boolean bgSwitch = true, mgSwitch = true, fgSwitch = true;;
    private Background[] backgrounds;

    Thread_Background(Background[] backgrounds)
    {
        this.backgrounds = backgrounds;
    }

    @Override
    public void run()
    {
        while(IS_PLAYING)
        {
            update();
            sleep();
        }
    }

    public void sleep ()
    {
        // 60 FPS
        try { Thread.sleep(16); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    private void update()
    {
        // For Background
        if(bgSwitch)
        {
            backgrounds[0].setX((int) (backgrounds[0].getX() - 5 * SCREEN_RATIO_X));
            backgrounds[1].setX(SCREEN_X + backgrounds[0].getX());

        }
        else
        {
            backgrounds[1].setX((int) (backgrounds[1].getX() - 5 * SCREEN_RATIO_X));
            backgrounds[0].setX(SCREEN_X + backgrounds[1].getX());
        }
            // Checks if Out of Bounds
            if(backgrounds[0].getX() + backgrounds[0].getBackground().getWidth() < 0)
            {
                bgSwitch = false;
            }
            // Checks if Out of Bounds
            if(backgrounds[1].getX() + backgrounds[1].getBackground().getWidth() < 0)
            {
                bgSwitch = true;
            }

        // For Midground
        if(mgSwitch)
        {
            backgrounds[2].setX((int) (backgrounds[2].getX() - 7.5 * SCREEN_RATIO_X));
            backgrounds[3].setX(SCREEN_X + backgrounds[2].getX());
        }
        else
        {
            backgrounds[3].setX((int) (backgrounds[3].getX() - 7.5 * SCREEN_RATIO_X));
            backgrounds[2].setX(SCREEN_X + backgrounds[3].getX());
        }
            // Checks if Out of Bounds
            if(backgrounds[2].getX() + backgrounds[2].getBackground().getWidth() < 0)
            {
                mgSwitch = false;
            }
            // Checks if Out of Bounds
            if(backgrounds[3].getX() + backgrounds[3].getBackground().getWidth() < 0)
            {
                mgSwitch = true;
            }

        // For Foreground
        if(fgSwitch)
        {
            backgrounds[4].setX((int) (backgrounds[4].getX() - 10 * SCREEN_RATIO_X));
            backgrounds[5].setX(SCREEN_X + backgrounds[4].getX());
        }
        else
        {
            backgrounds[5].setX((int) (backgrounds[5].getX() - 10 * SCREEN_RATIO_X));
            backgrounds[4].setX(SCREEN_X + backgrounds[5].getX());
        }
            // Checks if Out of Bounds
            if(backgrounds[4].getX() + backgrounds[4].getBackground().getWidth() < 0)
            {
                fgSwitch = false;
            }
            // Checks if Out of Bounds
            if(backgrounds[5].getX() + backgrounds[5].getBackground().getWidth() < 0)
            {
                fgSwitch = true;
            }
    }
}
