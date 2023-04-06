package com.example.spacebounty;

import static com.example.spacebounty.Game_View.CONTEXT;

public enum Stages {
    LEVEL_1{
        @Override
        Background[] createBackground()
        {
            Background[] backgrounds = new Background[6];

            // Background
            backgrounds[0] = new Background(CONTEXT.getResources(), R.drawable.sprite_background_lvl1);
            backgrounds[1] = new Background(CONTEXT.getResources(), R.drawable.sprite_background_lvl1);

            // Midground
            backgrounds[2] = new Background(CONTEXT.getResources(), R.drawable.sprite_midground_lvl1);
            backgrounds[3] = new Background(CONTEXT.getResources(), R.drawable.sprite_midground_lvl1);

            // Foreground
            backgrounds[4] = new Background(CONTEXT.getResources(), R.drawable.sprite_foreground_lvl1);
            backgrounds[5] = new Background(CONTEXT.getResources(), R.drawable.sprite_foreground_lvl1);
            return backgrounds;
        }
    },
    LEVEL_2{
        @Override
        Background[] createBackground()
        {
            return null;

        }
    },
    LEVEL_3{
        @Override
        Background[] createBackground()
        {
            return null;

        }
    },
    LEVEL_4{
        @Override
        Background[] createBackground()
        {
            return null;

        }
    },
    LEVEL_5{
        @Override
        Background[] createBackground()
        {
            return null;

        }
    };

    abstract Background[] createBackground();
}
