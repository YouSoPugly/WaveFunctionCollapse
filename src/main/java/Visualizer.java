import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class Visualizer extends Canvas implements MouseListener {

	private static int squareSize = 2;
	
	public static Visualizer visualizer = new Visualizer();
	private int width = WaveFunctionCollapse.width;
	private int height = WaveFunctionCollapse.height;

	public static Visualizer get() {
		return visualizer;
	}

	private Visualizer() {
		JFrame frame = new JFrame("Visualizer");
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened (WindowEvent e) {

			}

			@Override
			public void windowClosing (WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed (WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowIconified (WindowEvent e) {

			}

			@Override
			public void windowDeiconified (WindowEvent e) {

			}

			@Override
			public void windowActivated (WindowEvent e) {

			}

			@Override
			public void windowDeactivated (WindowEvent e) {

			}
		});
		setSize(width*squareSize, height*squareSize);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		setBackground(Color.BLACK);
		setSize(width * squareSize, height * squareSize);

		createBufferStrategy(1);
		addMouseListener(this);
	}

	public void draw(ArrayList<Node>[][] grid, boolean[][] visited) {

		BufferStrategy bufferStrategy = getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (visited[i][j]) {
					graphics.setColor(grid[i][j].get(0).getColor());
					graphics.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
				}
			}
		}

		bufferStrategy.show();
		graphics.dispose();
	}

	@Override
	public void mouseClicked (MouseEvent e) {
		BufferStrategy bufferStrategy = getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, width * squareSize, height * squareSize);
		bufferStrategy.show();
		graphics.dispose();
		WaveFunctionCollapse.mainLoop();
	}

	@Override
	public void mousePressed (MouseEvent e) {

	}

	@Override
	public void mouseReleased (MouseEvent e) {

	}

	@Override
	public void mouseEntered (MouseEvent e) {

	}

	@Override
	public void mouseExited (MouseEvent e) {

	}
}
