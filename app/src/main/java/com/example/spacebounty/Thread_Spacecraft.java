package com.example.spacebounty;

import static com.example.spacebounty.Game_View.IS_PLAYING;
import static com.example.spacebounty.Game_View.JOYSTICK;
import static com.example.spacebounty.Game_View.SPACECRAFT;

public class Thread_Spacecraft extends Thread{

    public Thread_Spacecraft()
    {

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

    private void update() {
        JOYSTICK.update();
        SPACECRAFT.getMechanics().update();
    }
}
