package com.example.spacebounty;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class Joystick {

    private int outerCircleCenterX;
    private int outerCircleCenterY;
    private int innerCircleCenterX;
    private int innerCircleCenterY;

    private final int outerCircleRadius;
    private final int innerCircleRadius;

    private final Paint outerCirclePaint;
    private final Paint innerCirclePaint;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerX, int centerY, int outerCircleRadius, int innerCircleRadius)
    {
        outerCircleCenterX = centerX;
        outerCircleCenterY = centerY;
        innerCircleCenterX = centerX;
        innerCircleCenterY = centerY;

        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.LTGRAY);
        innerCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void update()
    {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition()
    {
        innerCircleCenterX = (int) (outerCircleCenterX + actuatorX * outerCircleRadius);
        innerCircleCenterY = (int) (outerCircleCenterY + actuatorY * outerCircleRadius);
    }

    public void draw(Canvas canvas)
    {
        if(isPressed)
        {
            canvas.drawCircle(
                    outerCircleCenterX,
                    outerCircleCenterY,
                    outerCircleRadius,
                    outerCirclePaint
            );

            canvas.drawCircle(
                    innerCircleCenterX,
                    innerCircleCenterY,
                    innerCircleRadius,
                    innerCirclePaint
            );
        }
    }


    public boolean getIsPressed() { return isPressed; }
    public void setIsPressed(boolean isPressed) { this.isPressed = isPressed; }

    public boolean isPressed(double touchX, double touchY)
    {
        outerCircleCenterX = (int) touchX;
        outerCircleCenterY = (int) touchY;
        double touchDistance = getDistanceBetweenPoints(outerCircleCenterX, outerCircleCenterY, touchX, touchY);
        return touchDistance < outerCircleRadius;
    }

    public void setActuator(double touchX, double touchY)
    {
        double deltaX = touchX - outerCircleCenterX;
        double deltaY = touchY - outerCircleCenterY;
        double deltaDistance = getDistanceBetweenPoints(0, 0, deltaX, deltaY);

        if(deltaDistance < outerCircleRadius)
        {
            actuatorX = deltaX / outerCircleRadius;
            actuatorY = deltaY / outerCircleRadius;
        }
        else
        {
            actuatorX = deltaX / deltaDistance;
            actuatorY = deltaY / deltaDistance;
        }
    }

    private double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y)
    {
        return Math.sqrt(
                Math.pow(p1x - p2x, 2) +
                Math.pow(p1y - p2y, 2)
        );
    }

    public void resetActuator()
    {
        actuatorX = 0;
        actuatorY = 0;
    }



    public double getActuatorX() { return actuatorX; }
    public double getActuatorY() { return actuatorY; }
}
