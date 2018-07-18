package main;

public enum TerrainType {
	FLAT, TREE, MOUNTAIN, WALL;

	public String toString() {
		if (this.equals(TerrainType.TREE)) {
			return "t";
		} else if (this.equals(TerrainType.MOUNTAIN)) {
			return "m";
		} else if (this.equals(TerrainType.WALL)) {
			return "w";
		}
		return "-";
	}
	
	
}
