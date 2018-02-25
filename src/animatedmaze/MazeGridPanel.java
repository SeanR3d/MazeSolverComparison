package animatedmaze;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import javax.swing.JPanel;


//provided by professor
public class MazeGridPanel extends JPanel{
    private int rows;
    private int cols;
    private Cell[][] maze;
    private long sleepTime = 60;
    
    
    // generates a maze using depth-first search (extra credit)
    //algorithm based off: https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
    public void genDFSMaze(){
        boolean[][] visited = new boolean[15][15];
        Stack<Cell> stack  = new Stack<Cell>();
        Cell start = maze[0][0];
        start.setBackground(Color.GREEN);
        Cell finish = maze[rows-1][cols-1];
        finish.setBackground(Color.RED);
        boolean done = false;
        Cell current = start;
        visited[current.row][current.col] = true;
        
        while(!done){
            if(hasUnvisitedNeighbors(current, visited)){
                Cell next = setNext(current, visited);
                stack.push(current);
                removeWalls(current, next);
                current = next;
                visited[current.row][current.col] = true;
            }else if(!stack.empty()){
                current = stack.pop();
            }
            else{
                done = true;
            }
        }
        this.repaint();
    }
    
    //Checks to see if a Cell has unvisited neighbors (for genDFSMaze())
    public boolean hasUnvisitedNeighbors(Cell c, boolean[][] v){
        if(c.row > 0 && !v[c.row-1][c.col])
            return true;
        else if(c.row < rows-1 && !v[c.row+1][c.col])
            return true;
        else if(c.col < cols-1 && !v[c.row][c.col+1])
            return true;
        else if(c.col > 0 && !v[c.row][c.col-1])
            return true;
        else
            return false;
    }
    
    //Random number generator
    public static int RNG(int n){
        Random rand = new Random();
        int r = rand.nextInt(n+1);
        return r;
    }    
    
    //Randomly sets the next cell to visit (for genDFSMaze())
    public Cell setNext(Cell c, boolean[][] v){
        int r = RNG(4);
        Cell n;
            if(r == 1  && c.row > 0 && !v[c.row-1][c.col]){
                n = maze[c.row-1][c.col];
            }
            else if(r == 2  && c.row < rows-1 && !v[c.row+1][c.col]){
                n = maze[c.row+1][c.col];
            }
            else if(r == 3  && c.col < cols-1 && !v[c.row][c.col+1]){
                n = maze[c.row][c.col+1];
            }
            else if(r == 4  && c.col > 0 && !v[c.row][c.col-1]){
                n = maze[c.row][c.col-1];
            }
            else{
                n = setNext(c, v);
            }
        return n;
    }
    
    //Removes the "walls" between the current and next cells (for genDFSMaze())
    public void removeWalls(Cell c, Cell n){
        
        if(n.row == c.row-1){
            c.northWall = false;
            n.southWall = false;
        }
        else if(n.row == c.row+1){
            c.southWall = false;
            n.northWall = false;
        }        
        else if(n.col == c.col+1){
            c.eastWall = false;
            n.westWall = false;
        }
        else if(n.col == c.col-1){
            c.westWall = false;
            n.eastWall = false;
        }
    }
    
    //Solves a maze using depth-first search (homework)
    public void solveMaze() throws InterruptedException{
        Stack<Cell> stack  = new Stack<Cell>();
        Cell start = maze[0][0];
        start.setBackground(Color.GREEN);
        Cell finish = maze[rows-1][cols-1];
        finish.setBackground(Color.RED);
        stack.push(start);
        boolean done = false;
        
        while(!done && !stack.empty()){
            Cell current = stack.peek();
            
            if(current == finish){
                done = true;
            } else{
                //North
                if(!current.northWall && !this.visited(current.row-1,current.col)){
                    maze[current.row][current.col].setBackground(Color.GREEN);
                    maze[current.row-1][current.col].setBackground(Color.BLUE);
                    stack.push(maze[current.row-1][current.col]);
                    
                }
                //South
                else if(!current.southWall && !this.visited(current.row+1,current.col)){
                    maze[current.row][current.col].setBackground(Color.GREEN);
                    maze[current.row+1][current.col].setBackground(Color.BLUE);
                    stack.push(maze[current.row+1][current.col]);
                }
                //East
                else if(!current.eastWall && !this.visited(current.row,current.col+1)){
                    maze[current.row][current.col].setBackground(Color.GREEN);
                    maze[current.row][current.col+1].setBackground(Color.BLUE);
                    stack.push(maze[current.row][current.col+1]);
                }
                //West
                else if(!current.westWall && !this.visited(current.row,current.col-1)){
                    maze[current.row][current.col].setBackground(Color.GREEN);
                    maze[current.row][current.col-1].setBackground(Color.BLUE);
                    stack.push(maze[current.row][current.col-1]);
                }
                else{
                    maze[current.row][current.col].setBackground(Color.GRAY);
                    current = stack.pop();
                }
            }
            Thread.sleep(sleepTime);
        }
    }
    
    //Solves a maze using breadth-first search (provided by professor)
    public void solveMazeQueue() throws InterruptedException {
        Queue<Cell> toVisitNext = new LinkedList<Cell>();
        Cell start = maze[0][0];
        start.setBackground(Color.CYAN);
        Cell finish = maze[rows -1][cols - 1];
        finish.setBackground(Color.RED);
        toVisitNext.offer(start);
        boolean done = false;
        while(!done && !toVisitNext.isEmpty()) {
            Cell current = toVisitNext.poll();
            current.setBackground(Color.BLUE);

            if(current == finish) {
            done = true;
            } else {
                if( !current.northWall  
                &&  !this.visited(current.row-1, current.col) ) {
                toVisitNext.offer(maze[current.row-1][current.col]);
                maze[current.row][current.col].setBackground(Color.GREEN);                
                maze[current.row-1][current.col].setBackground(Color.BLUE);
                }
                if( !current.southWall  
                &&  !this.visited(current.row+1, current.col) ) {
                toVisitNext.offer(maze[current.row+1][current.col]);
                maze[current.row][current.col].setBackground(Color.GREEN);
                maze[current.row+1][current.col].setBackground(Color.BLUE);
                }
                if( !current.eastWall  
                &&  !this.visited(current.row, current.col+1) ) {
                toVisitNext.offer(maze[current.row][current.col+1]);
                maze[current.row][current.col].setBackground(Color.GREEN);
                maze[current.row][current.col+1].setBackground(Color.BLUE);
                }
                if( !current.westWall  
                &&  !this.visited(current.row, current.col-1) ) {
                toVisitNext.offer(maze[current.row][current.col-1]);
                maze[current.row][current.col].setBackground(Color.GREEN);
                maze[current.row][current.col-1].setBackground(Color.BLUE);
                }
            }
            Thread.sleep(sleepTime);
        }
    }
    
    //Checks if a cell has been 'visited' (provided by professor)
    public boolean visited(int row, int col) {
        Cell c = maze[row][col];
        Color status = c.getBackground();
        if(status.equals(Color.WHITE)  || status.equals(Color.RED)  ) {
            return false;
        }
        return true;
    }
    
    //Generates a maze (provided by professor)
    public void genNWMaze() {
        for(int row = 0; row  < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(row == 0 && col ==0) {
                    continue;
                }
                else if(row ==0) {
                    maze[row][col].westWall = 
                    false;
                    maze[row][col-1].eastWall = 
                    false;
                } else if(col == 0) {
                    maze[row][col].northWall = 
                    false;
                    maze[row-1][col].southWall = 
                    false;
                }else {
                    boolean north = 
                    Math.random()  < 0.5;
                    if(north ) {
                        maze[row][col].northWall = false;
                        maze[row-
                        1][col].southWall = false;
                    } else {  // remove west
                        maze[row][col].westWall = false;
                        maze[row][col-
                        1].eastWall = false;
                    }
                    maze[row][col].repaint();
                }
            }
        }
        this.repaint();
    }
    
    //Constructor (provided by professor)
    public MazeGridPanel(int rows, int cols) throws InterruptedException{
        this.setPreferredSize(new Dimension(800,800));
        this.rows = rows;
        this.cols = cols;
        this.setLayout(new GridLayout(rows,cols));
        this.maze =  new Cell[rows][cols];
        for(int row = 0 ; row  < rows ; row++) {
            for(int col = 0; col < cols; col++) {
                maze[row][col] = new Cell(row,col);
                this.add(maze[row][col]);
            }
        }
    }
    
}

