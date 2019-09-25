import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class MazeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		StdDraw.enableDoubleBuffering();
	}

	@After
	public void tearDown() throws Exception {
		StdDraw.clear();
	}

	@Test
	public void test3x3_stack() {
		int[][] data = {{1,1,0,0,0,0,0,0,0,0},
				{0,1,1,1,1,0,1,1,1,0},
				{0,1,1,1,1,0,1,1,0,0},
				{0,1,0,1,1,1,1,1,1,0},
				{0,1,0,0,0,0,0,1,1,0},
				{0,1,1,0,1,1,1,1,1,0},
				{0,0,1,0,0,1,0,1,0,0},
				{0,1,1,0,1,1,0,1,1,0},
				{0,1,1,0,1,1,0,1,1,0},
				{0,0,0,0,0,0,0,0,1,1}};


        Maze maze = new Maze(data.length, data[0].length, data);
        maze.draw();
        assertEquals(true, maze.findPathWithStack(0, 0));
	}

	@Test
	public void test3x3_queue() {
		int[][] data = {{1,1,0,0,0,0,0,0,0,0},
				{0,1,1,1,1,0,1,1,1,0},
				{0,1,1,1,1,0,1,1,0,0},
				{0,1,0,1,1,1,1,1,1,0},
				{0,1,0,0,0,0,0,1,1,0},
				{0,1,1,0,1,1,1,1,1,0},
				{0,0,1,0,0,1,0,1,0,0},
				{0,1,1,0,1,1,0,1,1,0},
				{0,1,1,0,1,1,0,1,1,0},
				{0,0,0,0,0,0,0,0,1,1}};
        Maze maze = new Maze(data.length, data[0].length, data);
        maze.draw();
        assertEquals(true, maze.findPathWithQueue(0, 0));
	}

	@Test
	public void test10x10_hasPath_stack() {
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
        Maze maze = new Maze(data.length, data[0].length, data);
        maze.draw();
        assertEquals(true, maze.findPathWithStack(0, 0));
	}

	@Test
	public void test10x10_hasPath_queue() {
		int mazeLength = (int)(Math.random() * 30);
		int[][] data = new int[mazeLength][mazeLength];
		for (int i = 0; i < data.length; i++){
			for (int a = 0; a < data[0].length; a++){
				double chance = Math.random();
				if (i == data.length -1 && a == data[0].length-1){
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
        Maze maze = new Maze(data.length, data[0].length, data);
        maze.draw();
        assertEquals(true, maze.findPathWithQueue(0, 0));
	}

	@Test
	public void test10x10_noPath_stack() {
		int mazeLength = (int)(Math.random() * 30);
		int[][] data = new int[mazeLength][mazeLength];
		for (int i = 0; i < data.length; i++){
			for (int a = 0; a < data[0].length; a++){
				double chance = Math.random();
				if (i == data.length -1 && a == data[0].length-1){
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

        Maze maze = new Maze(data.length, data[0].length, data);
        maze.draw();
        assertEquals(false, maze.findPathWithStack(0, 0));
	}


	@Test
	public void test10x10_noPath_queue() {
		int mazeLength = (int)(Math.random() * 30);
		int[][] data = new int[mazeLength][mazeLength];
		for (int i = 0; i < data.length; i++){
			for (int a = 0; a < data[0].length; a++){
				double chance = Math.random();
				if (i == data.length -1 && a == data[0].length-1){
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

        Maze maze = new Maze(data.length, data[0].length, data);
        maze.draw();
        assertEquals(false, maze.findPathWithQueue(0, 0));
	}

}
