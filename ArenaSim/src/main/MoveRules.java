package main;

public final class MoveRules {

	private MoveRules() {

	}

	public static boolean canMove(MoveType moveType, TerrainType terrain, int movesLeft) {
		return movesLeft > moveCost(terrain, moveType);
	}
	public static int initialMoves(MoveType moveType) {
		switch(moveType) {
		case ARMOR:
			return 1;
		case INFANTRY:
			return 2;
		case CAVALRY:
			return 3;
		case FLIER:
			return 2;
		default:
			return 0;
		}
	}
	public static int moveCost(TerrainType terrain, MoveType moveType) {
		switch (terrain) {
		case FLAT:
			switch (moveType) {
			case ARMOR:
				return 1;
			case INFANTRY:
				return 1;
			case CAVALRY:
				return 1;
			case FLIER:
				return 1;
			default:
				return Integer.MAX_VALUE;
			}
		case TREE:
			switch (moveType) {
			case ARMOR:
				return 1;
			case INFANTRY:
				return 2;
			case FLIER:
				return 1;
			default:
				return Integer.MAX_VALUE;
			}
		case MOUNTAIN:
			switch (moveType) {
			case FLIER:
				return 1;
			default:
				return Integer.MAX_VALUE;
			}
		default:
			return Integer.MAX_VALUE;
		}
	}

}
