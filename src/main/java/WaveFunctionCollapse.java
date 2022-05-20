import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WaveFunctionCollapse {

	public static boolean debug = false;

	public static int width = 500,
					  height = 500;

	private static ArrayList<Node> nodes = new ArrayList<>();

	private static ArrayList<Node>[][] grid = new ArrayList[width][height];
	private static boolean[][] visited = new boolean[width][height];

	public static void main (String[] args) {

		init();
		mainLoop();

	}

	public static void mainLoop() {

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				ArrayList<Node> possible = new ArrayList<>();
				possible.addAll(nodes);
				grid[i][j] = possible;
			}
		}

		int y = (int) Math.floor(Math.random() * height);
		int x = (int) Math.floor(Math.random() * width);

		grid[x][y].removeAll(nodes);
		grid[x][y].add(nodes.get(0));

		collapse(x, y);
		int next = findNext();

		while (next != -1) {
			x = next % width;
			y = next / height;

			if (debug)
				System.out.println("Collapsing at " + x + ", " + y + "... from next: " + next);

			Node temp = grid[x][y].get((int) Math.floor((Math.random() * grid[x][y].size())));
			grid[x][y].removeAll(nodes);
			grid[x][y].add(temp);

			collapse(x, y);
			visited[x][y] = true;

			Visualizer.visualizer.draw(grid, visited);

			visited = new boolean[width][height];
			next = findNext();
		}

		Visualizer.visualizer.draw(grid, visited);

	}

	public static void init() {

		nodes.add(new Node('L', Color.GREEN, new Rule(
				new char[] {'L', 'C', 'M', 'F'}
		)));

		nodes.add(new Node('M', Color.gray, new Rule(
				new char[] {'M', 'L', 'P'}
		)));

		nodes.add(new Node('P', Color.lightGray, new Rule(
				new char[] {'M'}
		)));

		nodes.add(new Node('C', Color.yellow, new Rule(
				new char[] {'C', 'L', 'S'}
		)));

		nodes.add(new Node('S', Color.cyan, new Rule(
				new char[] {'S', 'C', 'D'}
		)));

		nodes.add(new Node('D', Color.blue, new Rule(
				new char[] {'D', 'S'}
		)));

	}

	public static void collapse(int x, int y) {

		ArrayList<Node> val = grid[x][y];
		visited[x][y] = true;

		if (debug)
			System.out.println("Found value: " + val.get(0).getValue() + " at " + x + ", " + y);

		if (checkValidity(x, y+1)) {
			collapseRecursive(Direction.SOUTH, x, y+1, val);
		}

		if (checkValidity(x, y-1)) {
			collapseRecursive(Direction.NORTH, x, y-1, val);
		}

		if (checkValidity(x-1, y)) {
			collapseRecursive(Direction.EAST, x-1, y, val);
		}

		if (checkValidity(x+1, y)) {
			collapseRecursive(Direction.WEST, x+1, y, val);
		}



	}

	public static void collapseRecursive(Direction prevDir, int x, int y, ArrayList<Node> prevNodes) {
		ArrayList<Node> prev = (ArrayList<Node>) grid[x][y].clone();

		grid[x][y] = reduce(prevDir, grid[x][y], prevNodes);

		if (grid[x][y].size() == 1) {
			visited[x][y] = true;
		}

		if (debug) {
			System.out.print("At " + x + ", " + y + " from: [ ");
			prev.stream().forEach(e -> System.out.print(e.getValue() + " "));
			System.out.print("]" + " to [ ");
			grid[x][y].stream().forEach(e -> System.out.print(e.getValue() + " "));
			System.out.println("]");
		}

		if (prev.size() == grid[x][y].size()) {
			return;
		}


		if (checkValidity(x, y+1)) {
			collapseRecursive(Direction.NORTH, x, y+1, grid[x][y]);
		}

		if (checkValidity(x, y-1)) {
			collapseRecursive(Direction.SOUTH, x, y-1, grid[x][y]);
		}

		if (checkValidity(x-1, y)) {
			collapseRecursive(Direction.WEST, x-1, y, grid[x][y]);
		}

		if (checkValidity(x+1, y)) {
			collapseRecursive(Direction.EAST, x+1, y, grid[x][y]);
		}


	}

	public static ArrayList<Node> reduce(Direction prevDir, ArrayList<Node> nodes, ArrayList<Node> prevNodes) {

		Character[] prevNodeChars =  prevNodes.stream().map(Node::getValue).toArray(Character[]::new);
		if (debug)
			System.out.println("Prev: " + Arrays.toString(prevNodeChars));

		for (int i = 0; i < nodes.size(); i++) {

			if (!nodes.get(i).getRule().isValid(prevDir, prevNodeChars)) {
				nodes.remove(i);
				i--;
			}
		}
		return nodes;
	}

	public static boolean checkValidity(int x, int y) {
		return  (x < width && y < height && x >= 0 && y >= 0) &&  grid[x][y].size() > 1;
	}

	public static void printGrid() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[j][i].size() == 1)
					System.out.print(grid[j][i].get(0).getValue());
				else
					System.out.print(grid[j][i].size() + "");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static int findNext() {
		int lowest = Integer.MAX_VALUE;
		ArrayList<Integer> locations = new ArrayList<Integer>();
		locations.add(-1);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[j][i].size() == 2) {
					return j + i * width;
				}

				if (grid[j][i].size() == nodes.size()) {
					continue;
				}

				if (grid[j][i].size() < lowest && grid[j][i].size() > 1) {
					locations = new ArrayList<Integer>();
					locations.add(j + i * width);
					lowest = grid[j][i].size();
					continue;
				}

				if (grid[j][i].size() == lowest && grid[j][i].size() > 1) {
					locations.add(j + i * width);
				}
			}
		}
		return locations.get(new Random().nextInt(locations.size()));
	}

}
