package main;

public enum MoveType {
	INFANTRY,ARMOR,CAVALRY,FLIER;
	
	public String toShortenedString() {
		if (this.equals(MoveType.CAVALRY)) {
			return "c";
		} else if (this.equals(MoveType.ARMOR)) {
			return "a";
		} else if (this.equals(MoveType.FLIER)) {
			return "f";
		}
		return "i";
	}
	
	
	public static MoveType getMoveType(String input) {
		for(MoveType move : MoveType.values()) {
			if (move.toShortenedString().equalsIgnoreCase(input)) {
				return move;
			}
		}
		return null;
	}
}
