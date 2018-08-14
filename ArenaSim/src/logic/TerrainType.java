package logic;

public enum TerrainType {										//enumeration defining the collection of Terrain type
	FLAT, TREE, MOUNTAIN, WALL;									//4 types of terrain
	
	/**
	 * method to convert terrain type read from the text file to the valid terrain type to display on the map
	 * take 0 parameter
	 * @return THe Terrain type as a String value
	 */
	public String toString() {
		if (this.equals(TerrainType.TREE)) {					//if terrain is Tree type				
			return "t";
		} else if (this.equals(TerrainType.MOUNTAIN)) {			//if terrain is Mountain type
			return "m";
		} else if (this.equals(TerrainType.WALL)) {				//if terrain is wall type
			return "w";
		}
		return "-";												//return terrain Flat type if nothing else match
	}
	
	
}
