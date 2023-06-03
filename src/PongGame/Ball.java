package PongGame;

import java.awt.*;
import java.util.Random;

/**
 * Handles displaying ball in window and initializes movement in random direction
 */
public class Ball extends Rectangle{

    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;

    /**
     * Constructor of Ball class which set its dimensions and determined starting movement direction
     * @param x Point on the x axis
     * @param y Point on the y axis
     * @param width Width of the ball
     * @param height height of the ball
     */
    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0){
            randomXDirection--;
        }
        setXDirection(randomXDirection*initialSpeed);

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0){
            randomYDirection--;
        }
        setYDirection(randomYDirection*initialSpeed);

    }

    /**
     * Sets velocity on X axis
     * @param randomXDirection Speed value in pixels
     */
    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;
    }

    /**
     * Sets velocity on Y axis
     * @param randomYDirection Speed value in pixels
     */
    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;
    }

    /**
     * Moves ball by incrementing x and y values by amount of velocity initialized in setXDirection and SetYDirection methods
     */
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    /**
     * Draws ball into frame.
     * @param g Graphics object
     */
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x,y,width,height);
    }
}
