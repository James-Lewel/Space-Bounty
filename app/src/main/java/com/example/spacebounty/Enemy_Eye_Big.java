package com.example.spacebounty;

import static com.example.spacebounty.Game_View.CONTEXT;
import static com.example.spacebounty.Game_View.IS_PLAYING;
import static com.example.spacebounty.Game_View.SCREEN_RATIO_X;
import static com.example.spacebounty.Game_View.SCREEN_RATIO_Y;
import static com.example.spacebounty.Game_View.SCREEN_X;
import static com.example.spacebounty.Game_View.SCREEN_Y;
import static com.example.spacebounty.Game_View.SPACECRAFT;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Enemy_Eye_Big {


    private int x = 0, y = 0;
    private int bigEyeWidth, bigEyeHeight;

    private int frameCounter = 0;

    private boolean isBroken = false;
    private boolean isHit = false;

    Random random = new Random();
    private Enemy_Stats enemyStats;
    private Bitmap[] bigEye;
    Enemy_Eye_Big()
    {
        bigEye = new Bitmap[6];
        bigEye[0] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_enemy_big_full_eye_0);
        bigEye[1] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_enemy_big_full_eye_1);
        bigEye[2] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_enemy_big_full_eye_2);
        bigEye[3] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_enemy_big_brokenl_eye_0);
        bigEye[4] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_enemy_big_brokenl_eye_1);
        bigEye[5] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_enemy_big_brokenl_eye_2);

        bigEyeWidth = (int) (bigEye[0].getWidth() * SCREEN_RATIO_X);
        bigEyeHeight = (int) (bigEye[0].getHeight() * SCREEN_RATIO_Y);

        x = SCREEN_X;
        y = random.nextInt(SCREEN_Y - bigEyeHeight);

        for(int i = 0; i < 6; i++)
            bigEye[i] = Bitmap.createScaledBitmap(bigEye[i], bigEyeWidth, bigEyeHeight, false);

        enemyStats = new Enemy_Stats(50, 50, 100, 5);

    }

    public Bitmap bigEyeAnim() {
        if(isBroken)
        {
            switch (frameCounter)
            {
                case 0:
                case 1:
                case 2:
                case 3:
                    frameCounter++;
                    return bigEye[3];
                case 4:
                case 5:
                case 6:
                case 7:
                    frameCounter++;
                    return bigEye[4];
                case 8:
                case 9:
                case 10:
                case 11:
                    frameCounter++;
                    return bigEye[5];
                case 12:
                case 13:
                case 14:
                case 15:
                    if(frameCounter >= 15)frameCounter = 0;
                    else frameCounter++;
                    return bigEye[4];
            }
        }
        else
        {
            switch (frameCounter)
            {
                case 0:
                case 1:
                case 2:
                case 3:
                    frameCounter++;
                    return bigEye[0];
                case 4:
                case 5:
                case 6:
                case 7:
                    frameCounter++;
                    return bigEye[1];
                case 8:
                case 9:
                case 10:
                case 11:
                    frameCounter++;
                    return bigEye[2];
                case 12:
                case 13:
                case 14:
                case 15:
                    if(frameCounter >= 15)frameCounter = 0;
                    else frameCounter++;
                    return bigEye[1];
            }
        }

        return bigEye[0];
    }

    public void update()
    {
        x -= enemyStats.getSpeed();

        if(Rect.intersects(getRect(), SPACECRAFT.getRect()))
        {
            SPACECRAFT.getStats().setHp(SPACECRAFT.getStats().getHp() - enemyStats.getAttack());

            if(SPACECRAFT.getStats().getHp() <= 0)
            {
                IS_PLAYING = false;
                return;
            }
        }
    }

    public void drawEnemy(Canvas canvas)
    {
        canvas.drawBitmap(bigEyeAnim(), x, y, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public Enemy_Stats getEnemyStats()
    {
        return enemyStats;
    }

    public Rect getRect()
    {
        return new Rect(x, y, x + bigEyeWidth, y + bigEyeHeight);
    }
}


