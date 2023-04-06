package com.example.spacebounty;

import static com.example.spacebounty.Game_View.IS_PLAYING;

import java.util.ArrayList;
import java.util.List;

public class Thread_Enemy extends Thread{
    private int spawnTimer1 = 0;
    private int spawnTimer2 = 0;
    public static List<Enemy_Eye_Small> eyeSmallList;
    public static List<Enemy_Eye_Small> eyeSmallTrash;
    public static List<Enemy_Eye_Big> eyeBigList;
    public static List<Enemy_Eye_Big> eyeBigTrash;
    public Thread_Enemy()
    {
        eyeSmallList = new ArrayList<>();
        eyeSmallTrash = new ArrayList<>();
        eyeBigList = new ArrayList<>();
        eyeBigTrash = new ArrayList<>();
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
        spawnTimer1++;
        spawnTimer2++;

        if(spawnTimer1 > 64)
        {
            spawnTimer1 = 0;
            Enemy_Eye_Small eyeSmall = new Enemy_Eye_Small();
            eyeSmallList.add(eyeSmall);
        }
        if(spawnTimer2 > 120)
        {
            spawnTimer2 = 0;
            Enemy_Eye_Big eyeBig = new Enemy_Eye_Big();
            eyeBigList.add(eyeBig);
        }

        if(!eyeBigList.isEmpty())
        {
            for(Enemy_Eye_Small eyeSmall : eyeSmallList)
            {
                if(eyeSmall.getX() < 0)
                {
                    eyeSmallTrash.add(eyeSmall);
                }
                eyeSmall.update();
            }
        }

        if(!eyeBigList.isEmpty())
        {
            for(Enemy_Eye_Big eyeBig : eyeBigList)
            {
                if(eyeBig.getX() < 0)
                {
                    eyeBigTrash.add(eyeBig);
                }
                eyeBig.update();
            }
        }

        if(!eyeSmallTrash.isEmpty())
        {
            for(Enemy_Eye_Small eyeSmall : eyeSmallTrash)
            {
                eyeSmallList.remove(eyeSmall);
            }
        }
        if(!eyeBigTrash.isEmpty())
        {
            for(Enemy_Eye_Big eyeBig : eyeBigTrash)
            {
                eyeBigList.remove(eyeBig);
            }
        }
    }
}
