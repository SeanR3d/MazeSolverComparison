package animatedmaze;
/**
 * Sean Reddington
 * February 23, 2018
 * Description: This program uses Threads to run two animated maze solving algorithms simultaneously
 *  to compare solve time between depth-first search and breadth-first search. The mazes can also
 *  be generated using depth-first search (genDFSMaze()).
 * 
 * NOTE:
 * Built upon a homework assignment where we had to implement solveMaze()
 *  using a depth-first search algorithm. For extra credit, we could implement genDFSMaze()
 *  to generate a maze using a depth-first search algorithm as well. The threading and animation was done
 *  out of curiosity.
 * Code marked "provided by professor" was written by Dr. Andrew Rosen (Temple University)
 *  for the original assignment.
 */


//Thread class for AnimatedMaze
public class CompareMazes implements Runnable{
    
    private AnimatedMaze maze;
    private int mazeType; //1 = depth-first search    2 = breadth-first search
    
    
    public CompareMazes(AnimatedMaze m, int t){
        this.maze = m;
        this.mazeType = t;
    }
    
    @Override
    public void run(){
        
        //depth-first search
        if(mazeType == 1){
            maze.setTitle("Depth-first Search");
            maze.setLocation(100,100);
            maze.mgp.genDFSMaze();
            try {
                maze.mgp.solveMaze();
            } catch (InterruptedException ex) {
                System.out.println("Maze 1 solve InterruptedException");
            }
            
        }
        
        //breadth-first search
        if(mazeType == 2){
            maze.setTitle("Breadth-first Search");
            maze.setLocation(950,100);
            maze.mgp.genNWMaze();
            try {
                maze.mgp.solveMazeQueue();
            } catch (InterruptedException ex){
                System.out.println("Maze 2 solve InterruptedException");
            }
        }
    }

    
    public static void main(String[] args) throws InterruptedException{
        
        //creates AnimatedMaze objects
        AnimatedMaze maze1 = new AnimatedMaze();
        AnimatedMaze maze2 = new AnimatedMaze();
        
        //creates Thread objects
        Thread t1 = new Thread(new CompareMazes(maze1,1));
        Thread t2 = new Thread(new CompareMazes(maze2,2));
        
        //runs mazes
        t1.start();
        t2.start();
    }
}
