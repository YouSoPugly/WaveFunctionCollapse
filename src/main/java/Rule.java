public class Rule {

	char[] north, south, east, west;

	public Rule(char[] north, char[] south, char[] east, char[] west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	public Rule(char[] adjacent) {
		this.north = adjacent;
		this.south = adjacent;
		this.east = adjacent;
		this.west = adjacent;
	}

	public boolean isValid(Direction prevDir, Character[] possibilities) {
		return switch (prevDir) {
			case NORTH -> anyNonOverlap(possibilities, north);
			case SOUTH -> anyNonOverlap(possibilities, south);
			case EAST -> anyNonOverlap(possibilities, east);
			case WEST -> anyNonOverlap(possibilities, west);
		};
	}

	public boolean anyNonOverlap(Character[] possibilities, char[] rule) {
		boolean found = false;

		for (char c : possibilities) {
			for (char r : rule) {
				if (c == r) {
					found = true;
				}
			}
			if (found) {
				return true;
			}
		}
		return false;
	}
}
