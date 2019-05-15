import java.awt.Dimension;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

//import MazeGenerators.Algorithm;
//import MazeGenerators.Algorithm;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class main {
	enum Algorithm {
		RANDOM_DFS_RECURSIVE,
		RANDOM_DFS_NONRECURSIVE,
		RANDOM_BFS,
		PRIM,
		RECURSIVE_DIVISION,
		ALDOUS_BRODER,
		WILSON,
		SIDEWINDER,
		BINARY_TREE
	}

	static Grid maze(int numCols, int numRows, int startCol, int startRow, Algorithm algorithm) {
		Grid grid = new Grid(numCols, numRows);
		int startVertex = grid.vertex(startCol, startRow);
		BitSet visited = new BitSet(numCols * numRows);
		randomDFSRecursive(grid, startVertex, visited);
		return grid;
	}
	
	static void randomDFSRecursive(Grid grid, int v, BitSet visited) {
		visited.set(v);
		for (Direction dir = unvisitedDir(grid, v, visited); dir != null; dir = unvisitedDir(grid, v,
				visited)) {
			grid.addEdge(v, dir);
			randomDFSRecursive(grid, grid.neighbor(v, dir), visited);
		}
	}
	
	static Direction unvisitedDir(Grid grid, int v, BitSet visited) {
		List<Direction> unvisitedDirections = unvisitedDirections(grid, v, visited);
		return unvisitedDirections.isEmpty() ? null : unvisitedDirections.get(0);
	}
	
	static List<Direction> unvisitedDirections(Grid grid, int v, BitSet visited) {
		List<Direction> candidates = new ArrayList<>(4);
		for (Direction dir : Direction.values()) {
			int neighbor = grid.neighbor(v, dir);
			if (neighbor != Grid.NO_VERTEX && !visited.get(neighbor)) {
				candidates.add(dir);
			}
		}
		Collections.shuffle(candidates);
		return candidates;
	}
	
	public static void main(String[] args) {
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(1000, 1000);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

		window.setVisible(true);
		
		String maze = maze(10, 10, 0, 0, Algorithm.values()[0]).toString();
		System.out.println(maze);
		
		int y = 100;
		int x = 100;
		for(int i=0; i<882; i++) {
			x=100;
			y+=20;
			while(!maze.substring(i, i+1).equals("\n")) {
				if(!maze.substring(i, i+1).equals(" ")) {
					if(maze.substring(i, i+1).equals("-")) {
						drawing.line(x, y, x+10, y);
					}
					else {
						drawing.line(x, y, x, y+10);
					}
				}
				i++;
				x+=10;
			}
		}
	}

}
