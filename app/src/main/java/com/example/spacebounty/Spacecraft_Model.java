package com.example.spacebounty;

import static com.example.spacebounty.Game_View.CONTEXT;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public enum Spacecraft_Model
{
    SPEED{ // W.A.S.P
        @Override
        Spacecraft_Stats createStats()
        {
            return new Spacecraft_Stats(
                    100,
                    20,
                    5,
                    10);
        }

        @Override
        Bitmap[] createDrawable()
        {
            Bitmap[] spaceCraft = new Bitmap[4];
            spaceCraft[0] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_a0);
            spaceCraft[1] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_a1);
            spaceCraft[2] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_a2);
            spaceCraft[3] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_a3);
            return spaceCraft;
        }

        @Override
        Spacecraft_Mechanics createMechanics()
        {
            return new Spacecraft_Mechanics();
        }

        @Override
        Spacecraft_Gun createGun()
        {
            return new Spacecraft_Gun(
                    20f,
                    1.10f,
                    5,
                    8,
                    8,
                    48);
        }
    },
    ATTACK{ // M.A.N.T.I.S
        @Override
        Spacecraft_Stats createStats()
        {
            return new Spacecraft_Stats(
                    100,
                    20,
                    10,
                    5);
        }

        @Override
        Bitmap[] createDrawable()
        {
            Bitmap[] spaceCraft = new Bitmap[4];
            spaceCraft[0] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_b0);
            spaceCraft[1] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_b1);
            spaceCraft[2] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_b2);
            spaceCraft[3] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_b3);
            return spaceCraft;
        }

        @Override
        Spacecraft_Mechanics createMechanics()
        {
            return new Spacecraft_Mechanics();
        }

        @Override
        Spacecraft_Gun createGun()
        {
            return new Spacecraft_Gun(
                    25f,
                    1.10f,
                    5,
                    32,
                    16,
                    25);
        }
    },
    DEFENSE{ // B.E.E.T.L.E
        @Override
        Spacecraft_Stats createStats()
        {
            return new Spacecraft_Stats(
                    100,
                    20,
                    15,
                    8);
        }

        @Override
        Spacecraft_Mechanics createMechanics()
        {
            return new Spacecraft_Mechanics();
        }

        @Override
        Spacecraft_Gun createGun() {
            return new Spacecraft_Gun(
                    5.5f,
                    1.5f,
                    5,
                    16,
                    8,
                    25);
        }

        @Override
        Bitmap[] createDrawable()
        {
            Bitmap[] spaceCraft = new Bitmap[4];
            spaceCraft[0] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_c0);
            spaceCraft[1] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_c1);
            spaceCraft[2] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_c2);
            spaceCraft[3] = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.sprite_spaceship_c3);
            return spaceCraft;
        }
    };

    abstract Spacecraft_Stats createStats();
    abstract Spacecraft_Mechanics createMechanics();
    abstract Spacecraft_Gun createGun();
    abstract Bitmap[] createDrawable();
}