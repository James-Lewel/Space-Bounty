package com.example.spacebounty;

import static com.example.spacebounty.Game_View.SCORE;

import android.graphics.Rect;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import static com.example.spacebounty.Game_View.CONTEXT;
import static com.example.spacebounty.Game_View.SCREEN_X;
import static com.example.spacebounty.Game_View.SCREEN_RATIO_X;
import static com.example.spacebounty.Game_View.SPACECRAFT;
import static com.example.spacebounty.Thread_Enemy.eyeBigList;
import static com.example.spacebounty.Thread_Enemy.eyeBigTrash;
import static com.example.spacebounty.Thread_Enemy.eyeSmallList;
import static com.example.spacebounty.Thread_Enemy.eyeSmallTrash;

public class Spacecraft_Gun {
    private final List<Spacecraft_Bullet> spacecraftBulletList;
    private final List<Spacecraft_Bullet> spacecraftBulletTrashList;

    MediaPlayer gunSFX;

    // Gun Positions
    private final float leftGunY;
    private final float rightGunY;

    // Gun Variables
    private int ammoCount = 0;
    private int maxAmmo;
    private int shotPerSecond = 0;
    private int maxShotPerSecond;
    private int reloadTime = 0;
    private int maxReloadTime;
    private double bulletSpeed;
    private boolean isShooting;
    private boolean isReloading;
    private boolean changeGun = false;

    Spacecraft_Gun(float leftGunY, float rightGunY, int maxAmmo, int maxShotPerSecond, int maxReloadTime, double bulletSpeed)
    {
        this.leftGunY = leftGunY;
        this.rightGunY = rightGunY;
        this.maxAmmo = maxAmmo;
        this.maxShotPerSecond = maxShotPerSecond;
        this.maxReloadTime = maxReloadTime;
        this.bulletSpeed = bulletSpeed;

        spacecraftBulletList = new ArrayList<>();
        spacecraftBulletTrashList = new ArrayList<>();

        gunSFX = MediaPlayer.create(CONTEXT, R.raw.spaceship_gun);
    }

    public void update()
    {
        // Handles the gun
        if(isReloading) reload();
        else
        {
            shoot();
        }

        // Updates bullets
        if(!spacecraftBulletList.isEmpty())
        {
            for(Spacecraft_Bullet spacecraftBullet : spacecraftBulletList)
            {
                // Checks bullets offscreen
                if(spacecraftBullet.getX() > SCREEN_X) spacecraftBulletTrashList.add(spacecraftBullet);

                // Moves bullets
                spacecraftBullet.setX((int) (spacecraftBullet.getX() + bulletSpeed * SCREEN_RATIO_X));

                if(!eyeSmallList.isEmpty())
                {
                    for(Enemy_Eye_Small es : eyeSmallList)
                    {
                        if(Rect.intersects(es.getRect(), spacecraftBullet.getRect()))
                        {
                            es.getEnemyStats().setHp(es.getEnemyStats().getHp() - SPACECRAFT.getStats().getAttack());
                            spacecraftBullet.setX(SCREEN_X + 100);

                            if(es.getEnemyStats().getHp() < 12.5 && es.getEnemyStats().getHp() > 0)
                            {
                                es.setBroken(true);
                            }
                            if(es.getEnemyStats().getHp() <= 0)
                            {
                                es.setX(-100);
                                es.setBroken(false);
                                eyeSmallTrash.add(es);
                                SCORE += 10;
                            }
                        }
                    }
                }

                if(!eyeBigList.isEmpty())
                {
                    for(Enemy_Eye_Big eb : eyeBigList)
                    {
                        if(Rect.intersects(eb.getRect(), spacecraftBullet.getRect()))
                        {
                            eb.getEnemyStats().setHp(eb.getEnemyStats().getHp() - SPACECRAFT.getStats().getAttack());
                            spacecraftBullet.setX(SCREEN_X + 100);

                            if(eb.getEnemyStats().getHp() < 12.5 && eb.getEnemyStats().getHp() > 0)
                            {
                                eb.setBroken(true);
                            }
                            if(eb.getEnemyStats().getHp() <= 0)
                            {
                                eb.setX(-100);
                                eb.setBroken(false);
                                eyeBigTrash.add(eb);
                                SCORE += 10;
                            }
                        }
                    }
                }
            }

            if(!spacecraftBulletTrashList.isEmpty())
            {
                for(Spacecraft_Bullet spacecraftBullet : spacecraftBulletTrashList)
                    spacecraftBulletList.remove(spacecraftBullet);
            }
        }
    }

    public void shoot()
    {
        if(!isReloading && shotPerSecond == 0)
        {
            if(isShooting && shotPerSecond == 0)
            {
                isShooting = false;
                gunSFX.start();

                if(changeGun)
                {
                    // Creates new bullet
                    Spacecraft_Bullet spacecraftBulletRight = new Spacecraft_Bullet(CONTEXT.getResources());

                    // Positions right bullet
                    spacecraftBulletRight.setX(SPACECRAFT.getX() + SPACECRAFT.getSpaceCraftWidth());
                    spacecraftBulletRight.setY((int)(SPACECRAFT.getY() + (SPACECRAFT.getSpaceCraftHeight() / rightGunY)));

                    spacecraftBulletList.add(spacecraftBulletRight);
                    changeGun = false;
                }
                else
                {
                    // Creates new bullet
                    Spacecraft_Bullet spacecraftBulletLeft = new Spacecraft_Bullet(CONTEXT.getResources());

                    // Positions left bullet
                    spacecraftBulletLeft.setX(SPACECRAFT.getX() + SPACECRAFT.getSpaceCraftWidth());
                    spacecraftBulletLeft.setY((int)(SPACECRAFT.getY() + (SPACECRAFT.getSpaceCraftHeight() / leftGunY)));

                    spacecraftBulletList.add(spacecraftBulletLeft);
                    changeGun = true;
                }

                ammoCount++;
                shotPerSecond = maxShotPerSecond;
            }

            if(ammoCount >= maxAmmo)
            {
                isReloading = true;
            }
        }

        if(shotPerSecond > 0) shotPerSecond--;
    }

    public void reload()
    {
        if(isReloading) reloadTime++;

        if(reloadTime >= maxReloadTime)
        {
            reloadTime = 0;

            if(ammoCount > 0) ammoCount--;
            else isReloading = false;
        }
    }

    public List<Spacecraft_Bullet> getBulletList() { return spacecraftBulletList; }

    public int getAmmoCount() {
        return ammoCount;
    }

    public void setAmmoCount(int ammoCount) { this.ammoCount = ammoCount; }

    public int getMaxAmmo() { return maxAmmo; }

    public void setMaxAmmo(int maxAmmo) { this.maxAmmo = maxAmmo;  }

    public int getShotPerSecond() {
        return shotPerSecond;
    }

    public void setShotPerSecond(int shotPerSecond) {
        this.shotPerSecond = shotPerSecond;
    }

    public int getMaxShotPerSecond() {
        return maxShotPerSecond;
    }

    public void setMaxShotPerSecond(int maxShotPerSecond) {
        this.maxShotPerSecond = maxShotPerSecond;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getMaxReloadTime() {
        return maxReloadTime;
    }

    public void setMaxReloadTime(int maxReloadTime) {
        this.maxReloadTime = maxReloadTime;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public boolean getIsShooting() {
        return isShooting;
    }

    public void setIsShooting(boolean isShooting) {
        this.isShooting = isShooting;
    }

    public boolean getIsReloading() {
        return isReloading;
    }

    public void setIsReloading(boolean isReloading) {
        this.isReloading = isReloading;
    }


}
