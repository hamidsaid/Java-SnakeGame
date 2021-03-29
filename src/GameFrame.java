import javax.swing.*;

public class GameFrame extends JFrame {

    //create an instance of the class
    GameFrame() {
        //create an object of the Game panel class
       GamePanel panel = new GamePanel();

       this.add(panel);
       //set title of the panel
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //set the window to appear on the middle of the computer screen
        this.setLocationRelativeTo(null);


    }
}
