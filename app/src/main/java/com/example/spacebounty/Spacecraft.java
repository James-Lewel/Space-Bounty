package com.example.spacebounty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import static com.example.spacebounty.Game_View.CONTEXT;
import static com.example.spacebounty.Game_View.SCREEN_Y;
import static com.example.spacebounty.Game_View.SCREEN_RATIO_X;
import static com.example.spacebounty.Game_View.SCREEN_RATIO_Y;

public class Spacecraft
{
    private int x, y;

    // SpaceCraft and Ammo size
    private int spaceCraftWidth, spaceCraftHeight;
    private int ammoWidth, ammoHeight;

    int frameCounter = 0;

    private final Spacecraft_Model model;
    private final Spacecraft_Stats stats;
    private final Spacecraft_Mechanics mechanics;
    private final Spacecraft_Gun gun;

    private final Bitmap[] spaceCraft;
    private final Bitmap[] ammo;

    Spacecraft(Spacecraft_Model model)
    {
        spaceCraft = model.createDrawable();
        this.model = model;

        ammo = new Bitmap[2];

        ammo[0] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_ammo_empty);
        ammo[1] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_ammo_full);

        spaceCraftWidth = spaceCraft[0].getWidth();
        spaceCraftHeight = spaceCraft[0].getHeight();

        ammoWidth = ammo[0].getWidth();
        ammoHeight = ammo[0].getHeight();

        spaceCraftWidth = (int) (spaceCraftWidth * SCREEN_RATIO_X);
        spaceCraftHeight = (int) (spaceCraftHeight * SCREEN_RATIO_Y);
        ammoWidth = (int) (ammoWidth * SCREEN_RATIO_X);
        ammoHeight = (int) (ammoHeight * SCREEN_RATIO_Y);

        for(int i = 0; i < 4; i++)
            spaceCraft[i] = Bitmap.createScaledBitmap(spaceCraft[i], spaceCraftWidth, spaceCraftHeight, false);
        for(int i = 0; i < 2; i++)
            ammo[i] = Bitmap.createScaledBitmap(ammo[i], ammoWidth, ammoHeight, false);

        x = (int) (64);
        y = (int) (SCREEN_Y / 2);

        stats = model.createStats();
        mechanics = model.createMechanics();
        gun = model.createGun();
    }

    public Spacecraft_Stats getStats() {
        return stats;
    }
    public Spacecraft_Mechanics getMechanics() {
        return mechanics;
    }

    // Controls SpaceCraft Animation
    public Bitmap spaceCraftAnim()
    {
        switch (frameCounter)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                frameCounter++;
                return spaceCraft[0];
            case 4:
            case 5:
            case 6:
            case 7:
                frameCounter++;
                return spaceCraft[1];
            case 8:
            case 9:
            case 10:
            case 11:
                frameCounter++;
                return spaceCraft[2];
            case 12:
            case 13:
            case 14:
            case 15:
                if(frameCounter >= 15)frameCounter = 0;
                else frameCounter++;
                return spaceCraft[3];
        }

        return spaceCraft[0];
    }

    public void drawSpaceCraft(Canvas canvas)
    {
        // Draws SpaceCraft
        canvas.drawBitmap(spaceCraftAnim(), x, y, null);
    }

    public void drawAmmoUI(Canvas canvas)
    {
        // Draws SpaceCraft Bullets
        for (Spacecraft_Bullet b: this.getGun().getBulletList())
            canvas.drawBitmap(b.getBullet(), b.getX(), b.getY(), null);

        // Draws Empty Ammo Count
        for (int i = (this.getGun().getMaxAmmo()) - this.getGun().getAmmoCount(); i < this.getGun().getMaxAmmo(); i++)
            canvas.drawBitmap(ammo[0], (ammoWidth + 10) * (i + .5f), (10), null);

        // Draws Full Ammo Count
        for (int i = (this.getGun().getMaxAmmo() - 1) - this.getGun().getAmmoCount(); i >= 0; i--)
            canvas.drawBitmap(ammo[1], (ammoWidth + 10) * (i + .5f), (10), null);
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y;}
    public void setY(int y) { this.y = y; }

    public int getSpaceCraftWidth() { return spaceCraftWidth; }
    public int getSpaceCraftHeight() { return spaceCraftHeight; }
    public Rect getRect()
    {
        return new Rect(x, y, x + spaceCraftWidth, y + spaceCraftHeight);
    }
    public Spacecraft_Gun getGun() { return gun; }
}
