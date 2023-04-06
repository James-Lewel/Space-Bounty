package com.example.spacebounty;

import static com.example.spacebounty.Game_View.SCREEN_X;
import static com.example.spacebounty.Game_View.SCREEN_Y;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background {
    private int x = 0;
    private int y = 0;
    private int backgroundWidth, backgroundHeight;
    private Bitmap background;

    Background(Resources resources, int drawable)
    {
        background = BitmapFactory.decodeResource(resources, drawable);

        backgroundWidth = (int) (SCREEN_X);
        backgroundHeight = (int) (SCREEN_Y);

        background = Bitmap.createScaledBitmap(background, backgroundWidth, backgroundHeight, false);

        background.prepareToDraw();
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(background, x, y, null);
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

    public Bitmap getBackground() {
        return background;
    }
}
