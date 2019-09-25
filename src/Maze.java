import javafx.geometry.Pos;

import java.awt.*;

public class Maze
{
    private Cell[][] board;

    private final int DELAY = 200;

    public Maze(int rows, int cols, int[][] map){
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        board = new Cell[rows][cols];
        //grab number of rows to invert grid system with StdDraw (lower-left, instead of top-left)
        int height = board.length - 1;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                board[r][c] = map[r][c] == 1 ? new Cell(c , height - r, r, c, 0.5, false) : new Cell(c, height - r, r, c, 0.5, true);
            }
    }

    public void draw()
    {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++){
                Cell cell = board[r][c];
                StdDraw.setPenColor(cell.getColor());
                StdDraw.filledSquare(cell.getX(), cell.getY(), cell.getRadius());
            }
            StdDraw.show();
    }

    /*
     * This method uses a stack to manage the order of the cells that are visited. 
     * Returns a boolean indicating whether a path to the exit has been found beginning 
     * at the location (row, col) of board.
     */
    public boolean findPathWithStack(int row, int col) {
        Stack<Position> visited = new Stack<Position>();
        boolean found = false;

        int currentRow = row;
        int currentCol = col;
        boolean changed = false;

        Position init = new Position(board[row][col], null);
        visited.push(init);
        board[row][col].setColor(StdDraw.RED);
        board[row][col].visitCell();

        while (!found) {
            changed = false;

            if (isExit(currentRow, currentCol)) {

                while (!visited.isEmpty()) {
                    Cell current = visited.pop().getCell();
                    current.setColor(StdDraw.GREEN);
                    this.draw();
                    StdDraw.pause(100);

                }
                return true;
            }

            if (visited.isEmpty()) {
                return false;
            }

            if (isValid(currentRow, currentCol + 1)) {


                Cell right = board[currentRow][currentCol + 1];
                right.setColor(StdDraw.RED);
                currentCol++;
                Position p = new Position(right, visited.peek());
                visited.push(p);


            }

            else if (isValid(currentRow + 1, currentCol)) {
                Cell down = board[currentRow + 1][currentCol];
                down.setColor(StdDraw.RED);
                currentRow++;
                Position p = new Position(down, visited.peek());
                visited.push(p);
            }

            else if (isValid(currentRow, currentCol - 1)) {
                Cell left = board[currentRow][currentCol - 1];
                left.setColor(StdDraw.RED);
                currentCol--;
                Position p = new Position(left, visited.peek());
                visited.push(p);
            }

            else if (isValid(currentRow - 1, currentCol)) {
                Cell up = board[currentRow - 1][currentCol];
                up.setColor(StdDraw.RED);
                currentRow--;
                Position p = new Position(up, visited.peek());
                visited.push(p);
            } else {

                changed = true;
                visited.pop();
                if (visited.isEmpty()) {
                    return false;
                }
                Cell p = visited.peek().getCell();
                currentRow = p.getRow();
                currentCol = p.getCol();


            }


            if (changed) {
                continue;
            }
            else {
                board[currentRow][currentCol].setColor(StdDraw.PINK);
                this.draw();
                StdDraw.pause(50);
                board[currentRow][currentCol].setColor(StdDraw.RED);
                board[currentRow][currentCol].visitCell();
            }

        }

        if (found) {

        }
        return true;
    }

    
    /*
     * This method uses a queue to manage the order of the cells that are visited. 
     * Returns a boolean indicating whether a path to the exit has been found beginning 
     * at the location (row, col) of board.
     */
    public boolean findPathWithQueue(int row, int col)
    {
        Queue<Position> visited = new Queue<Position>();
        visited.enqueue(new Position(board[row][col],null));

        while (!visited.isEmpty()) {
            Position p = visited.dequeue();
            if (p.getCell().isVisited()) { continue; }

            int r = p.getCell().getRow();
            int c = p.getCell().getCol();

            board[r][c].setColor(StdDraw.PINK);
            this.draw();
            StdDraw.pause(10);
            board[r][c].visitCell();
            this.draw();
            StdDraw.pause(10);

            if (isExit(r, c)){
                while (p != null){
                    Cell s = p.getCell();
                    s.setColor(StdDraw.GREEN);
                    this.draw();
                    StdDraw.pause(100);
                    p = p.getPrevious();
                }
                return true;
            }

            if (isValid(r + 1, c)) {
                Cell temp1 = board[r + 1][c];
                visited.enqueue(new Position(temp1,p));
            }

            if (isValid(r - 1, c)) {
                Cell temp1 = board[r - 1][c];
                visited.enqueue(new Position(temp1,p));
            }

            if (isValid(r, c + 1)) {
                Cell temp1 = board[r][c + 1];
                visited.enqueue(new Position(temp1,p));
            }

            if (isValid(r, c - 1)) {
                Cell temp1 = board[r][c - 1];
                visited.enqueue(new Position(temp1,p));
            }
        }
        return false;
    }

    /* 
     * Returns a boolean whether or not position (row, col) is an open cell in the
     * board.
     */
    private boolean isValid(int row, int col)
    {
        if ((col >= board[0].length) || (row >= board.length) || (col < 0) || (row < 0)) {
            return false;
        }

        else{
            if (board[row][col].isWall()
                    || board[row][col].isVisited() == true) {
                return false;
            }
            return true;
        }

    }
    
    /*
     * Returns a boolean whether position (row, col) is the exit in the board.
     */
    private boolean isExit(int row, int col)
    {
        if ((col > board[0].length) || (row > board.length) || (col < 0) || (row < 0)){
            return false;
        }

        if ((row == (board.length - 1)) && (col == (board[0].length - 1))){
            return true;
        }
        return false;
    }

    private int[][] randomMaze(){
        int mazeLength = (int)(Math.random() * 30);
        int[][] data = new int[mazeLength][mazeLength];
        for (int i = 0; i < data.length; i++){
            for (int a = 0; a < data[0].length; a++){
                double chance = Math.random();
                if (i == 0 || i == mazeLength-1 || a == 0 || a == mazeLength -1){
                    data[i][a] = 0;
                }
                else if (i == data.length -1 && a == data[0].length-1){
                    data[i][a] = 1;
                }
                else if (chance < 0.7){
                    data[i][a] = 1;
                }

                else{
                    data[i][a] = 0;
                }
            }
        }
        return data;
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        int[][] maze = {{1,1,0,0,0,0,0,0,0,0},
                        {0,1,1,1,1,0,1,1,1,0},
                        {0,1,1,1,1,0,1,1,0,0},
                        {0,1,0,1,1,1,1,1,1,0},
                        {0,1,0,0,0,0,0,1,1,0},
                        {0,1,1,0,1,1,1,1,1,0},
                        {0,0,1,0,0,1,0,1,0,0},
                        {0,1,1,0,1,1,0,1,1,0},
                        {0,1,1,0,1,1,0,1,1,0},
                        {0,0,0,0,0,0,0,0,1,1}};
        Maze geerid = new Maze(maze.length, maze[0].length, maze);
        geerid.draw();
        // Change the commenting to test your queue method instead of the stack method
        geerid.findPathWithStack(0, 0);
        //geerid.findPathWithQueue(0, 0);
        geerid.draw();
    }
}
