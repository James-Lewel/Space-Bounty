package com.example.spacebounty;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.spacebounty.Game_View.SCREEN_RATIO_X;
import static com.example.spacebounty.Game_View.SCREEN_RATIO_Y;

public class Spacecraft_Bullet
{
    private int x, y;
    private int width, height;
    private Bitmap bullet;

    Spacecraft_Bullet(Resources resources)
    {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.sprite_spaceship_bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width = (int) (width * SCREEN_RATIO_X);
        height = (int) (height * SCREEN_RATIO_Y);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
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

    public Bitmap getBullet() {
        return bullet;
    }
    public Rect getRect()
    {
        return new Rect(x, y, x + width, y + height);
    }
}
