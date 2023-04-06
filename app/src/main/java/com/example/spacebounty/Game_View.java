package com.example.spacebounty;

import static com.example.spacebounty.Menu_Choose.MODEL;
import static com.example.spacebounty.Menu_SaveLoad.SAVE_FILE;
import static com.example.spacebounty.Menu_SaveLoad.EDITOR;
import static com.example.spacebounty.Thread_Enemy.eyeBigList;
import static com.example.spacebounty.Thread_Enemy.eyeSmallList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;


public class Game_View extends SurfaceView implements Runnable
{
    // Threads
    private Thread gameLoopThread;
    private final Thread_Background threadBackground;
    private final Thread_Spacecraft threadSpacecraft;
    private final Thread_Enemy threadEnemy;

    private final SurfaceHolder surfaceHolder;
    private Background[] backgrounds;

    // Point variables
    public static int SCREEN_X, SCREEN_Y;
    public static float SCREEN_RATIO_X, SCREEN_RATIO_Y;

    public static Spacecraft SPACECRAFT;
    public static Joystick JOYSTICK;
    public static Context CONTEXT;
    public static boolean IS_PLAYING = false;

    public static int HIGH_SCORE;
    public static int SCORE = 0;
    Paint paint;

    public int score_timer = 0;

    public Game_View(Context context, int SCREEN_X, int SCREEN_Y)
    {
        super(context);

        SCORE = 0;
        HIGH_SCORE = SAVE_FILE.getInt("HIGH SCORE", 0);

        CONTEXT = context;

        this.SCREEN_X = SCREEN_X;
        this.SCREEN_Y = SCREEN_Y;
        SCREEN_RATIO_X = 1280f / SCREEN_X;
        SCREEN_RATIO_Y = 720f / SCREEN_Y;

        backgrounds = Stages.LEVEL_1.createBackground();
        JOYSTICK = new Joystick(0, 0, 100, 50);
        SPACECRAFT = new Spacecraft(MODEL);

        threadBackground = new Thread_Background(backgrounds);
        threadSpacecraft = new Thread_Spacecraft();
        threadEnemy = new Thread_Enemy();

        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
    }

    public void resume()
    {
        IS_PLAYING = true;

        gameLoopThread = new Thread(this);
        gameLoopThread.start();

        threadBackground.start();
        threadSpacecraft.start();
        threadEnemy.start();
    }

    public void pause()
    {
        try
        {
            IS_PLAYING = false;

            gameLoopThread.join();
            threadBackground.join();
            threadSpacecraft.join();
            threadEnemy.join();
        }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void run()
    {
        while (IS_PLAYING)
        {
            draw();
            update();
            sleep();
        }
    }

    private void update() {
        if(!IS_PLAYING) pause();

        score_timer++;

        if(score_timer > 100)
        {
            SCORE += 10;
            score_timer = 0;
        }

        if(SCORE > HIGH_SCORE)
        {
            HIGH_SCORE = SCORE;
            EDITOR.putInt("HIGH SCORE", HIGH_SCORE);
            EDITOR.commit();
        }
    }

    public void sleep ()
    {
        // 60 FPS
        try { Thread.sleep(16); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void draw ()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            Canvas canvas = surfaceHolder.getSurface().lockHardwareCanvas();

            // Draws Background
            //canvas.drawColor(Color.parseColor("#0d2030"));
            backgrounds[0].draw(canvas);
            backgrounds[1].draw(canvas);

            // Draws Midground
            backgrounds[2].draw(canvas);
            backgrounds[3].draw(canvas);

            // Draws Foreground
            backgrounds[4].draw(canvas);
            backgrounds[5].draw(canvas);

            // Draws Player
            SPACECRAFT.drawSpaceCraft(canvas);
            SPACECRAFT.drawAmmoUI(canvas);
            JOYSTICK.draw(canvas);

            if(!eyeSmallList.isEmpty())
            {
                for(Enemy_Eye_Small eyeSmall : eyeSmallList)
                {
                    eyeSmall.drawEnemy(canvas);
                }
            }

            if(!eyeBigList.isEmpty())
            {
                for(Enemy_Eye_Big eyeBig : eyeBigList)
                {
                    eyeBig.drawEnemy(canvas);
                }
            }

            canvas.drawText("HIGH SCORE: " + String.valueOf(HIGH_SCORE), SCREEN_X/2, 30, paint);
            canvas.drawText("SCORE: " + String.valueOf(SCORE), SCREEN_X/2, 60, paint);

            surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(JOYSTICK.getIsPressed())
                {
                    if(!SPACECRAFT.getGun().getIsReloading())
                        SPACECRAFT.getGun().setIsShooting(true);
                }
                else if (JOYSTICK.isPressed(event.getX(), event.getY()))
                {
                    JOYSTICK.setIsPressed(true);
                }
                else
                    if(!SPACECRAFT.getGun().getIsReloading())
                        SPACECRAFT.getGun().setIsShooting(true);
                return true;

            case MotionEvent.ACTION_MOVE:
                if(JOYSTICK.getIsPressed())
                    JOYSTICK.setActuator(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_UP:
                JOYSTICK.setIsPressed(false);
                JOYSTICK.resetActuator();
                return true;


        }

        return super.onTouchEvent(event);
    }
}
