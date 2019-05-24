

import java.util.BitSet;

/**
 * Grid graph implementation. Vertices are represented by integers, edge set by a single bit-set.
 * @author Wikipedia
 */
public class Grid {

	/** Index representing no vertex. */
	public static final int NO_VERTEX = -1;

	public final int numCols;
	public final int numRows;

	private BitSet edges;
	
	private int bit(int v, Direction dir) {
		return 4 * v + dir.ordinal();
	}

	public Grid(int numCols, int numRows) {
		this.numCols = numCols;
		this.numRows = numRows;
		this.edges = new BitSet(numCols * numRows * 4);
	}

	public int vertex(int col, int row) {
		return numCols * row + col;
	}

	public int col(int v) {
		return v % numCols;
	}

	public int row(int v) {
		return v / numCols;
	}

	public int numEdges() {
		return edges.cardinality() / 2;
	}

	public void addEdge(int v, Direction dir) {
		edges.set(bit(v, dir));
		edges.set(bit(neighbor(v, dir), dir.opposite()));
	}
	
	public void removeEdge(int v, Direction dir) {
		edges.clear(bit(v, dir));
		edges.clear(bit(neighbor(v, dir), dir.opposite()));
	}

	public boolean hasEdge(int v, Direction dir) {
		return edges.get(bit(v, dir));
	}

	public int neighbor(int v, Direction dir) {
		int col = col(v) + dir.x, row = row(v) + dir.y;
		return col >= 0 && col < numCols && row >= 0 && row < numRows ? vertex(col, row) : NO_VERTEX;
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		//sb.append(String.format("Grid: %d columns, %d rows, %d edges\n", numCols, numRows, numEdges()));
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				sb.append(!hasEdge(vertex(col, row), Direction.NORTH) ? "+---" : "+   ");
			}
			sb.append("+\n");
			for (int col = 0; col < numCols; col++) {
				sb.append(!hasEdge(vertex(col, row), Direction.WEST) ? "|   " : "    ");
			}
			sb.append("|\n");
		}
		for (int col = 0; col < numCols; col++) {
			sb.append("+---");
		}
		sb.append("+\n");
		return sb.toString();
	}
}
