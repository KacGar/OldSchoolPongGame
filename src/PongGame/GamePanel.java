package PongGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Panel which is displayed in a frame. Main content of a game, contains player paddles, ball and scoreboard
 */
public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    HumanPaddle p1;
    HumanPaddle p2;
    Ball ball;
    Score score;

    /**
     * Constructor which initialized every component needed for a game
     */
    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Creates new ball. Everytime a player scores - new ball is created.
     */
    public void newBall(){
        //random = new Random(); //optional if we want to ball appear at random Y position, this variable should be passed in constructor below
        ball = new Ball((GAME_WIDTH / 2)-(BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (BALL_DIAMETER / 2), BALL_DIAMETER, BALL_DIAMETER);
    }

    /**
     * Creates paddles for players - everytime one of the players scores, new paddles are created which puts them in default position
     */
    public void newPaddles(){
        p1 = new HumanPaddle(0,(GAME_HEIGHT / 2)-(PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        p2 = new HumanPaddle((GAME_WIDTH-PADDLE_WIDTH),(GAME_HEIGHT / 2)-(PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    /**
     * Paints components in a frame
     * @param g Graphics object
     */
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    /**
     * Draw method of graphics API, draws components in a panel.
     * @param g
     */
    public void draw(Graphics g){
        p1.draw(g);
        p2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    /**
     * Groups move methods of player paddles and ball
     */
    public void move(){
        p1.move();
        p2.move();
        ball.move();
    }

    /**
     * Method which handles collision checks of paddles and ball. Paddles are stoping at the edges of the frame,
     * and ball bounces whenever it touches up and bottoms edges of a frame or if it touches player paddle
     */
    public void checkCollision(){
        //bounce ball off top & bottom edges
        if (ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }

        if (ball.y >= (GAME_HEIGHT - BALL_DIAMETER)){
            ball.setYDirection(-ball.yVelocity);
        }

        // bounces ball off paddles
        if (ball.intersects(p1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // optional for more difficulty - speeds up ball
            if (ball.yVelocity > 0){
                ball.yVelocity++; // optional for more difficulty - speeds up ball
            } else{
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if (ball.intersects(p2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // optional for more difficulty - speeds up ball
            if (ball.yVelocity > 0){
                ball.yVelocity++; // optional for more difficulty - speeds up ball
            } else{
                ball.yVelocity--;
            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //stops paddles at window edges
        if (p1.y <= 0){
            p1.y = 0;
        }
        if (p1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)){
            p1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        if (p2.y <= 0){
            p2.y = 0;
        }
        if (p2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)){
            p2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        //give player a point and creates new paadles and ball
        if (ball.x <= 0){
            score.player2++;
            newPaddles();
            newBall();
        }

        if (ball.x >= (GAME_WIDTH - BALL_DIAMETER)){
            score.player1++;
            newPaddles();
            newBall();
        }
    }

    /**
     * Implementation of Runnable interface method.
     */
    public void run(){
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Public class which handles firing KeyEvent objects whenever keys are pressed or released. Keys are defined in {@link HumanPaddle} class.
     */
    public class AL extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            p1.keyPressed(e);
            p2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            p1.keyReleased(e);
            p2.keyReleased(e);
        }
    }
}
