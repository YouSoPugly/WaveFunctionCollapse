import java.awt.*;

public class Node {

	private char value;
	private Rule rule;
	private Color color;


	public Node(char value, Color color, Rule rule) {
		this.value = value;
		this.rule = rule;
		this.color = color;
	}

	public char getValue() {
		return value;
	}

	public Color getColor () {
		return color;
	}

	public Rule getRule () {
		return rule;
	}

}
