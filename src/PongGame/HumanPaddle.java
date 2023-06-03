package PongGame;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Paddle that is controlled by a player. The placement of paddle is determined in object creation
 */
public class HumanPaddle extends Rectangle {

    int id;
    int yVelocity;
    int speed = 10;

    /**
     * Constructor of a paddle
     * @param x Starting point on X axis
     * @param y Starting point on Y axis
     * @param PADDLE_WIDTH Paddle width
     * @param PADDLE_HEIGHT Paddle Height
     * @param id Player id number, either 1 or 2
     */
    HumanPaddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.id = id;
    }

    /**
     * Moves paddle while user presses up / down key (up/W, down/S), longer it holds, faster it moves.
     * @param e KeyEvent object
     */
    public void keyPressed(KeyEvent e){
        switch (id){
            case 1 -> {
                if (e.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(speed);
                    move();
                }
            }
            case 2 -> {
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(speed);
                    move();
                }
            }
        }
    }

    /**
     * Slows down movement of a paddle when user stops pressing key.
     * @param e
     */
    public void keyReleased(KeyEvent e){
        switch (id){
            case 1 -> {
                if (e.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(0);
                    move();
                }
            }
            case 2 -> {
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(0);
                    move();
                }
            }
        }
    }

    /**
     * Sets direction of a paddle on Y axis
     * @param yDirection Value which determines either paddle move up or down
     */
    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }

    /**
     * Moves paddle
     */
    public void move(){
        y = y + yVelocity;
    }

    /**
     * Draws paddle in a frame
     * @param g Graphics object
     */
    public void draw(Graphics g){
        if (id == 1){
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(x,y,width,height);
    }
}
