import com.sun.org.apache.bcel.internal.generic.SWITCH;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    //Size of objects in our game
    static final int UNIT_SIZE = 30 ;
    //calculate how many objects can fit in our screen
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    //SET time delay
    //the higher the number for the delay the slower the game and vice versa
    static final int DELAY =175;
    /** create two Arrays that will hold all the coordinates for all of the body parts of our snake including the head.
    for the size of the arrays i'll set equal to the size of our game units coz the snake is never going to be bigger
    than the game itself.
    */
     // this array will hold all the x coordinates of the body parts including the head of the snake
    final int [] x = new int[GAME_UNITS];
    // this array will hold all the y coordinates of the body parts
    final int [] y  = new int[GAME_UNITS];

    //set the initial body parts of the snake
    int bodyParts = 6;
    int applesEaten;
    //These will appear randomly to be eaten
    int appleX;
    int appleY;
    //Direction of the snake when the game is started.set it to right
    char direction = 'R';
    boolean running = false;
    Timer timer;
    //Declare   An instance of the Random class
    Random random;


    //create a constructor of the class
    GamePanel() {
       // create An instance of the Random class
        random = new Random();
        //Set the size of the game panel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        //set the background color
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MykeyAdapter());
        //call the start game method
        startGame();

    }

    //declaring the methods we are going do need for the game

    public void startGame() {

        //once the game is started ,call the newApple method to create a new apple on the screen
        newApple();
        //the snake(game) to start to move
        running = true;
       //finish creating the instance of the timer which dictates how much the game is running
        timer = new Timer(DELAY, this);
        timer.start();


    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {
        //set the game over screen
        if (running) {
            /**
            //turn the game panel into a grid to easily see things
            //The loopS will draw lines across the x and y axes
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
             */
            //Lets draw the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Lets draw the snake head(i = 0) and body (i>0)
            for (int i = 0; i < bodyParts; i++) {
                //for the head
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                //for the body
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Score text
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            //Alingh text on center of the screen
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

        }else {
            gameOver(g);

        }


    }
    /**This method is called when the game starts, or when the snake eats an apple.*/
    public void newApple() {
        //This make an apple to appear anywhere along the x axis
        //We multiply by UNIT_SIZE coz we want the apple to be placed evenly within the grid
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;

        //This make an apple to appear anywhere along the Y axis
        appleY = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;

    }

    /** This Method moves the snake*/
    public void moveSnake() {

        //create a for loop to iterate through the body parts of the snake
        //this shifts the body parts of the snake around
        for (int i = bodyParts; i>0; i--) {
            //Basically this shifts all the coordinates in this array over by one spot in both axes
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
            //Create a switch(condition) that will change the direction of which the snake is headed
            switch (direction) {
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                    break;
                case 'L':
                    x[0] = x[0] - UNIT_SIZE;
                    break;
                case 'R':
                    x[0] = x[0] + UNIT_SIZE;
                    break;

            }



    }
    public void checkApple() {
        //examine the coordinates of the snake and the apple
        if ((x[0] == appleX) && (y[0] == appleY)) {
            //increase the snake size
            bodyParts++;
            //increase the score
            applesEaten++;
            //call the apple method to generate a new apple
            newApple();
        }

    }
    public void checkCollisions() {
        //check if the head collides with the body
        for (int i = bodyParts; i>0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                //cause a game over
                running = false;
            }
        }
        //Check if head touches the left border
        if (x[0] < 0) {
            running = false;
        }
        //Check if head touches the right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if (y[0] < 0){
            running = false;
        }
        //check if head touches buttom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if(!running) {
            timer.stop();
        }


    }

    public void gameOver(Graphics g) {

        //Score text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        //Alingh text on center of the screen
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

        //Game over text
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        //Alingh text on center of the screen
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            moveSnake();
            checkApple();
            checkCollisions();
        }
            repaint();
    }

    public class MykeyAdapter extends KeyAdapter {
        @Override
        /**Control the snake by keyboard arrow keys */
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    //limit the user from turning 180 degrees in the opposite direction
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}
