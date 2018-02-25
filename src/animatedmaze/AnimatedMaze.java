package animatedmaze;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

//JFrame for mazes
public class AnimatedMaze extends JFrame {
    
    public MazeGridPanel mgp;
    //provided by professor
    public AnimatedMaze() throws InterruptedException{
        mgp = new MazeGridPanel(15,15);
        this.add(mgp);
        this.setSize(800, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    
}

