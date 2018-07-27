package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Map {
	public final int MAXX = 6; // maximum size of the X-Axis (on the 2 dimension list)
	public final int MAXY = 8; // maximum size of the Y-Axis (on the 2 dimension list)

	/**
	 * Setting up Terrain as a 2 dimensions matrix
	 */
	private TerrainType[][] terrainMap;

	private Unit[][] unitMap = new Unit[MAXY][MAXX];

	/**
	 * Create new object from unit class, put them into an ArrayList
	 */
	private ArrayList<Unit> unitList = new ArrayList<Unit>();

	/**
	 * spawning enemies and friendly units defined these units as integer.
	 */

	private ArrayList<int[]> friendlySpawns = new ArrayList<int[]>(); // adding friendly units to an ArrayList
	private ArrayList<int[]> enemySpawns = new ArrayList<int[]>(); // adding enemies units to an ArrayList

	/**
	 * constructor of Map class
	 * 
	 * @param TerrainFile
	 *            take 1 string parameter
	 */
	public Map(String TerrainFile) {
		// read map from text file
		// adding units from the file
		terrainMap = readMap(TerrainFile); // call the method ReadMap -> read positions of the terrain on the 2
											// dimension list
		readHeroSpawns(TerrainFile); // call the method readHeroSpawns -> read positions of all the units

		// calling spawnHeroes method, 2 parameters is the directory of the file +
		// boolean value
		spawnHeroes("src/assets/friendlyHeroes.txt", true); // boolean value is true -> spawn friendly units only
		spawnHeroes("src/assets/enemyHeroes.txt", false); // boolean value is false -> spawn enemies only

	}

	public void updateHeroDeaths() { // check if unit is "alive" or "dead"
		for (int y = 0; y < MAXY; y++) { // loop through y Axis of the 2 dimension map
			for (int x = 0; x < MAXX; x++) { // loop through X Axis of the 2 dimension map
				Unit unit = unitMap[y][x];
				if (unit != null && unit.getCurrentHP() < 0) { // if unit is there but their health bar (getting health
																// by calling method getCurrentHP) is < 0
					unitList.remove(unitMap[y][x]); // remove unit
					unitMap[y][x] = null;
				}
			}
		}
	}

	/**
	 * method readHeroSpawns
	 * 
	 * @param mapName
	 *            take 1 string parameter
	 */

	private void readHeroSpawns(String mapName) {
		File mapFile = new File(mapName.substring(0, mapName.indexOf(".")) + "_spawn.txt");

		try {
			Scanner fileReader = new Scanner(mapFile); // try to find the map file
			for (int lineNum = 0; lineNum < MAXY; lineNum++) { // loop through each line in the map
				String[] lineArray = fileReader.nextLine().split(" ");
				for (int x = 0; x < MAXX; x++) {
					if (lineArray[x].startsWith("p")) { // spawning condition 1
						int[] temp = new int[] { lineNum, x }; // adding friendly units to an array
						friendlySpawns.add(temp); // spawning friendly units
					} else if (lineArray[x].startsWith("e")) { // spawning condition 2
						int[] temp = new int[] { lineNum, x }; // adding enemies units to an array
						enemySpawns.add(temp); // spawning enemy units
					}
				}

			}
			fileReader.close();
		} catch (FileNotFoundException e) { // error if Java can't find the map file
			e.printStackTrace();
		}

	}

	/**
	 * Spawning Heroes methods
	 * 
	 * @param heroFileName:
	 *            name of the File java is going to import
	 * @param isFriendly:
	 *            checking if the unit is friendly or not
	 */
	private void spawnHeroes(String heroFileName, boolean isFriendly) {
		File heroFile = new File(heroFileName);
		Scanner fileReader;
		ArrayList<int[]> spawns;
		if (isFriendly) // if boolean condition match
			spawns = friendlySpawns; // spawn friendly units only
		else
			spawns = enemySpawns; // if boolean condition doesn't met, spawn enemies instead
		try { // try catching possible error
			fileReader = new Scanner(heroFile); // create new object
			int numHeroes = fileReader.nextInt(); // get number of units
			fileReader.nextLine(); // read next line
			for (int x = 0; x < numHeroes; x++) { // loop through the file
				String[] lineArray = fileReader.nextLine().split(" "); // split the line after every empty String
				int[] stats = new int[4]; // get the stats of the units (contains 4 type of stats)
				for (int y = 0; y < 4; y++) {
					stats[y] = Integer.parseInt(lineArray[y + 1]); // its a String since we read it from file, cast it
																	// to Integer
				}
				Unit unit = new Unit(lineArray[0], MoveType.getMoveType(lineArray[7]), isFriendly, stats,
						Integer.parseInt(lineArray[6])); // get all the stats AND attributes of the unit(what its type,
															// friendly or foe, stats) and add it to an Array

				unit.setYX(spawns.get(x)[0], spawns.get(x)[1]); // set spawning position of the unit
				unitMap[spawns.get(x)[0]][spawns.get(x)[1]] = unit;
				unitList.add(unit); // adding unit to the list

			}
			fileReader.close(); // close the file
		} catch (FileNotFoundException e) {
			System.out.println("friendlyHeroes.txt file is not set up correctly");
			e.printStackTrace(); // error caught
		}

	}

	/**
	 * checkMoveLegal method: checking if unit can move to the designated spot.
	 * 
	 * @param unit:
	 *            unit
	 * @param xOffset:
	 *            spot on the X axis
	 * @param yOffset:
	 *            spot on the Y axis
	 * @return boolean value to determine if unit can be moved or not
	 */

	public boolean checkMoveLegal(Unit unit, int xOffset, int yOffset) {
		boolean[][] availableMoves = findAvailableMoves(unit); // calling findAvailableMoves method
		try {
			return availableMoves[unit.getY() + yOffset][unit.getX() + xOffset]; // return boolean value by computing
																					// the position on the map (in case
																					// its off the map)
		} catch (ArrayIndexOutOfBoundsException e) {
			return false; // return false otherwise
		}
	}

	/**
	 * Displays the map and the available moves of a given unit print "@" around our
	 * unit as a representation for the available moves
	 * 
	 * @param unit
	 *            The unit to display the moves for
	 */
	public void displayAvailableMoves(Unit unit) {
		boolean[][] availableMoves = findAvailableMoves(unit); // calling findAvailableMoves method

		for (int j = 0; j < terrainMap.length; j++) { // loop through the rows of terrainMap (a 2 dimension matrix)
			for (int i = 0; i < terrainMap[0].length; i++) { // loop through columns of that matrix
				if (unitMap[j][i] != null) { // if the spot is on the map is not available
					System.out.print(unitMap[j][i].getName()); // get whatever is inside that spot
				} else if (availableMoves[j][i]) { // if the spot is avaible for the unit to move in
					System.out.print("@"); // display what are the positions unit can move to (represent by "@")
				}
				System.out.print("\t");
			}
			System.out.println();
			for (int i = 0; i < terrainMap[0].length; i++) { // loop through the columns of that matrix
				System.out.print(terrainMap[j][i] + "\t"); // formating the terrains on the console
			}
			System.out.print("\t\t"); // formating the position of the units on the console
			if (j < unitList.size()) {
				System.out.println(unitList.get(j));
			}
			System.out.println("\n\n"); //// formating the position of the unit on the console
		}

	}

	/**
	 * Finds the available moves of a given unit
	 * 
	 * @param unit
	 *            The units to find the moves for
	 * @return boolean value if the spot on the map is possible to move the unit in
	 *         or not
	 */
	public boolean[][] findAvailableMoves(Unit unit) {
		int y = unit.getY(); // get unit position of the Y-axis on the 2 dimension list
		int x = unit.getX(); // get unit position of the X-axis on the 2 dimension list
		if (unitMap[y][x] != null) { // if the position in the map is not empty
			Queue<int[]> queue = new LinkedList<int[]>(); // creating a new queue, as a preparation for the
															// breadth-first search algorithm

			boolean[][] availableMoves = new boolean[MAXY][MAXX];
			availableMoves[y][x] = true; // set value of the position to True to prepare for the loop
			int[] temp = { x, y, MoveRules.initialMoves(unit.getMoveType()) }; // creating temp as an integer array that
																				// contain x and y positions, also get
																				// the unit moves type
			queue.add(temp); // add temp as the first value in the queue
			while (!queue.isEmpty()) { // checking if the queue is empty or not
				int[] values = queue.remove(); // start again with the new loop, delete the first temp element
				for (int i = 0; i < 4; i++) { // 4 directions to move, so loop from 0 to 3
					int newX = values[0] + (int) Math.round(Math.cos(i / 2d * Math.PI)); // math to calculate where is
																							// the suitable moving
																							// position
					int newY = values[1] + (int) Math.round(Math.sin(i / 2d * Math.PI));
					int movesLeft = values[2];

					if (0 <= newX && newX < MAXX && 0 <= newY && newY < MAXY && !availableMoves[newY][newX]) {
						int moveCost = MoveRules.moveCost(terrainMap[newY][newX], unit.getMoveType()); // get the
																										// movement cost
																										// by find out
																										// what is the
																										// unit's type

						if (moveCost <= movesLeft) {
							if (unitMap[newY][newX] != unit && unitMap[newY][newX] != null) { // checking if the newX
																								// and newY position has
																								// // an unit in in yet
								availableMoves[newY][newX] = false;
							} else
								availableMoves[newY][newX] = true;
							if (movesLeft - moveCost > 0 && (unitMap[newY][newX] == null
									|| unitMap[newY][newX].isFriendly() == unit.isFriendly())) {
								int[] temp2 = { newX, newY, movesLeft - moveCost };
								queue.add(temp2); // add another temp element to the queue
							}
						}
					}
				}
			}

			return availableMoves;
		}
		return new boolean[MAXY][MAXX]; // return boolean value, determine that position is available to move in or not

	}

	/**
	 * Moves the given hero at the given location to the new location. Does not
	 * check legality of move
	 * 
	 * @param x
	 * @param y
	 * @param newX
	 * @param newY
	 */
	public void moveHero(int x, int y, int newX, int newY) {
		if (x != newX || y != newY) { 
			//&& unitMap[y][x] != null && unitMap[newY][newX] != null) { // Move only if different
		//}
																								// locations

			unitMap[newY][newX] = unitMap[y][x]; // copy heroes into new position
			unitMap[y][x] = null; // previous heroes' position set to null
			unitMap[newY][newX].setYX(newY, newX); // setting new position for heroes
		}
	}

	/**
	 * moveHero method: moving the unit, get their position of the 2 dimension map 3
	 * parameters
	 * 
	 * @param unit:
	 *            an object of Unit class
	 * @param xOffset
	 * @param yOffset
	 */
	public void moveHero(Unit unit, int xOffset, int yOffset) {

		moveHero(unit.getX(), unit.getY(), unit.getX() + xOffset, unit.getY() + yOffset);

	}

	/**
	 * getUnitList method
	 * 
	 * @return an ArrayList of unit
	 */
	public ArrayList<Unit> getUnitList() {
		return unitList;
	}

	/**
	 * setUnitList method
	 * 
	 * @param unitList:
	 *            take an ArrayList as a parameter update the unit List return
	 *            nothing
	 */
	public void setUnitList(ArrayList<Unit> unitList) {
		this.unitList = unitList;
	}

	/**
	 * take 0 parameter getTerraingMap method
	 * 
	 * @return an ArrayList of the map
	 */
	public TerrainType[][] getTerrainMap() {
		return terrainMap;
	}

	/**
	 * take 0 parameter
	 * 
	 * @return an ArrayList of Unit
	 */
	public Unit[][] getUnitMap() {
		return unitMap;
	}

	/**
	 * displayMap method: display the map and map of the unit return nothing, takes
	 * 0 parameter
	 */

	public void displayMap() {
		for (int y = 0; y < terrainMap.length; y++) { // loop through the terrainMap (a 2 dimension matrix)
			for (int x = 0; x < terrainMap[0].length; x++) {
				if (unitMap[y][x] != null) { // if the position is being checked on the map is not empty (null == empty)
					System.out.print(unitMap[y][x].getName()); // getName of the unit
				}
				System.out.print("\t"); // formatting
			}
			System.out.println();
			for (int x = 0; x < terrainMap[0].length; x++) { // loop through the terrainMap length
				System.out.print(terrainMap[y][x] + "\t"); // formatting while printing the terrain on the console
			}
			System.out.print("\t\t");
			if (y < unitList.size()) {
				System.out.println(unitList.get(y)); // get the Y position so we can format what is being printed on the
														// console
			}
			System.out.println("\n\n"); // add 2 new lines every time the loop running -> easier for user to see the map
		}
	}

	/**
	 * Display possible attack options for each unit In this method, we also want
	 * the attack options is being printed on the right side of the map (on console)
	 * 
	 * @param unit:
	 *            as an Object of Unit class return nothing
	 */
	public void displayAttackOptions(Unit unit) {
		boolean[][] availableTargets = findRange(unit); // find available target

		for (int j = 0; j < terrainMap.length; j++) { // loop through then 2 dimension matrix (which is the map)
			for (int i = 0; i < terrainMap[0].length; i++) {
				if (unitMap[j][i] != null) {
					System.out.print(unitMap[j][i].getName()); // printing out Attack Options on the console
				}
				System.out.print("\t");
			}
			System.out.println();
			for (int i = 0; i < terrainMap[0].length; i++) {
				if (availableTargets[j][i]) {
					System.out.print("X\t");
				} else
					System.out.print(terrainMap[j][i] + "\t"); // formatting
			}
			System.out.print("\t\t");
			if (j < unitList.size()) {
				System.out.println(unitList.get(j)); // get the j position so we can format what is being printed on the
														// console
			}
			System.out.println("\n\n");
		}

	}

	/**
	 * Find the available tiles the unit can attack
	 * 
	 * @param unit
	 *            as an Object of Unit class
	 * @return a boolean array representing tiles the unit can attack
	 */
	public boolean[][] findRange(Unit unit) {
		boolean[][] availableTargets = new boolean[MAXY][MAXX]; // cannot let the target be outside of the map
		for (int y = 0; y < MAXY; y++) { // loop through the map on the Y axis
			for (int x = 0; x < MAXX; x++) { // loop through the map on the X axis
				if (Math.abs(unit.getX() - x) + Math.abs(unit.getY() - y) == unit.getRange()) // get the Range that unit
																								// can make actions
					availableTargets[y][x] = true; // If the range is correct, set the target to be true
			}
		}
		return availableTargets; // return true if thats the case, false otherwise

	}

	/**
	 * readMap method
	 * 
	 * @param mapName
	 *            as a String
	 * @return the map. but return null if error occurs
	 */
	private TerrainType[][] readMap(String mapName) {
		File mapFile = new File(mapName); // create new Object as the map name

		try {
			Scanner fileReader = new Scanner(mapFile);
			TerrainType[][] map = new TerrainType[MAXY][MAXX]; // terrain has to be inside the map maximum range
			for (int lineNum = 0; lineNum < MAXY; lineNum++) { // loop through the map file
				String[] lineArray = fileReader.nextLine().split(" "); // split the line to get character after the
																		// blank space, add them to a new Array
				for (int x = 0; x < MAXX; x++) {
					for (TerrainType terrain : TerrainType.values()) { // get value of that terrain
						if (lineArray[x] != null && lineArray[x].equals(terrain.toString())) { // compare what inside
																								// the list to the
																								// terrain.
							map[lineNum][x] = terrain;
							break; // If we find a terrain type, stop the loop
						}
					}

				}

			}
			fileReader.close(); // close the file
			return map;
		} catch (FileNotFoundException e) { // if error occurs
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * get Unit name method
	 * 
	 * @param unitName,
	 *            a String value
	 * @return Unit as an object
	 */
	public Unit getUnit(String unitName) {
		for (Unit unit : unitList) {
			if (unit.getName().equals(unitName)) // if the new unit has the same name as the unit
				return unit; // return it
		}
		return null; // else return nothing
	}
}
