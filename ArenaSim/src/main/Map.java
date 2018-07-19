package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Map {
	public final int MAXX = 6;
	public final int MAXY = 8;

	private TerrainType[][] terrainMap;
	private Unit[][] unitMap = new Unit[MAXY][MAXX];
	private ArrayList<Unit> unitList = new ArrayList<Unit>();
	private ArrayList<int[]> friendlySpawns = new ArrayList<int[]>();
	private ArrayList<int[]> enemySpawns = new ArrayList<int[]>();

	public Map(String TerrainFile) {
		// read map from text file
		terrainMap = readMap(TerrainFile);
		readHeroSpawns(TerrainFile);
		spawnHeroes("src/assets/friendlyHeroes.txt", true);
		spawnHeroes("src/assets/enemyHeroes.txt", false);
	}

	public void updateHeroDeaths() {
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				Unit unit = unitMap[y][x];
				if (unit != null && unit.getCurrentHP() < 0) {
					unitList.remove(unitMap[y][x]);
					unitMap[y][x] = null;
				}
			}
		}
	}

	private void readHeroSpawns(String mapName) {
		File mapFile = new File(mapName.substring(0, mapName.indexOf(".")) + "_spawn.txt");
		try {
			Scanner fileReader = new Scanner(mapFile);
			for (int lineNum = 0; lineNum < MAXY; lineNum++) {
				String[] lineArray = fileReader.nextLine().split(" ");
				for (int x = 0; x < MAXX; x++) {
					if (lineArray[x].startsWith("p")) {
						int[] temp = new int[] { lineNum, x };
						friendlySpawns.add(temp);
					} else if (lineArray[x].startsWith("e")) {
						int[] temp = new int[] { lineNum, x };
						enemySpawns.add(temp);
					}
				}

			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void spawnHeroes(String heroFileName, boolean isFriendly) {
		File heroFile = new File(heroFileName);
		Scanner fileReader;
		ArrayList<int[]> spawns;
		if (isFriendly)
			spawns = friendlySpawns;
		else
			spawns = enemySpawns;
		try {
			fileReader = new Scanner(heroFile);
			int numHeroes = fileReader.nextInt();
			fileReader.nextLine();
			for (int x = 0; x < numHeroes; x++) {
				String[] lineArray = fileReader.nextLine().split(" ");
				int[] stats = new int[4];
				for (int y = 0; y < 4; y++) {
					stats[y] = Integer.parseInt(lineArray[y + 1]);
				}
				Unit unit = new Unit(lineArray[0], MoveType.getMoveType(lineArray[7]), isFriendly, stats,
						Integer.parseInt(lineArray[6]));

				unit.setYX(spawns.get(x)[0], spawns.get(x)[1]);
				unitMap[spawns.get(x)[0]][spawns.get(x)[1]] = unit;
				unitList.add(unit);

			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println(heroFileName + " file is not set up correctly");

			e.printStackTrace();
		}
	}

	public boolean checkMoveLegal(Unit unit, int xOffset, int yOffset) {
		boolean[][] availableMoves = findAvailableMoves(unit);
		try {
			return availableMoves[unit.getY() + yOffset][unit.getX() + xOffset];
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public void displayAvailableMoves(Unit unit) {
		System.out.println("\n\n\n");
		boolean[][] availableMoves = findAvailableMoves(unit);

		for (int j = 0; j < terrainMap.length; j++) {
			for (int i = 0; i < terrainMap[0].length; i++) {
				if (unitMap[j][i] != null) {
					System.out.print(unitMap[j][i].getName());
				} else if (availableMoves[j][i]) {
					System.out.print("@");
				}
				System.out.print("\t");
			}
			System.out.println();
			for (int i = 0; i < terrainMap[0].length; i++) {
				System.out.print(terrainMap[j][i] + "\t");
			}
			System.out.print("\t\t");
			if (j < unitList.size()) {
				System.out.println(unitList.get(j));
			}
			System.out.println("\n\n");
		}

	}


	
	/**
	 * finds the available targets of the given unit if the unit were at the x, y
	 * coordinate
	 * 
	 * @param unit
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean[][] findAvailableTargets(Unit unit, int unitX, int unitY) {

		boolean[][] availableTargets = new boolean[MAXY][MAXX];
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				if (Math.abs(unitX - x) + Math.abs(unitY - y) == unit.getRange())
					availableTargets[y][x] = true;
			}
		}
		return availableTargets;

	}
	/**
	 * Finds available moves of the given unit using a
	 * breadth-first search from the origin coordinates
	 * 
	 * @param unit
	 *            The unit to find the possible moves for
	 */
	public boolean[][] findAvailableMoves(Unit unit) {
		int y = unit.getY();
		int x = unit.getX();
		if (unitMap[y][x] != null) {
			Queue<int[]> queue = new LinkedList<int[]>();
			boolean[][] availableMoves = new boolean[MAXY][MAXX];
			availableMoves[y][x] = true;
			int[] temp = { x, y, MoveRules.initialMoves(unit.getMoveType()) };
			queue.add(temp);
			while (!queue.isEmpty()) {
				int[] values = queue.remove();
				for (int i = 0; i < 4; i++) {
					int newX = values[0] + (int) Math.round(Math.cos(i / 2d * Math.PI));
					int newY = values[1] + (int) Math.round(Math.sin(i / 2d * Math.PI));
					int movesLeft = values[2];

					if (0 <= newX && newX < MAXX && 0 <= newY && newY < MAXY && !availableMoves[newY][newX]) {
						int moveCost = MoveRules.moveCost(terrainMap[newY][newX], unit.getMoveType());

						if (moveCost <= movesLeft) {
							if (unitMap[newY][newX] != unit && unitMap[newY][newX] != null) {
								availableMoves[newY][newX] = false;
							} else
								availableMoves[newY][newX] = true;
							if (movesLeft - moveCost > 0) {
								int[] temp2 = { newX, newY, movesLeft - moveCost };
								queue.add(temp2);
							}
						}
					}
				}
			}

			return availableMoves;
		}
		return new boolean[MAXY][MAXX];

	}

	/**
	 * Moves the given hero at the given location to the new location. Does not
	 * check legality of move.
	 * 
	 * @param map
	 * @param x
	 * @param y
	 * @param newX
	 * @param newY
	 */
	public void moveHero(int x, int y, int newX, int newY) {
		if (x != newX || y != newY) {
			unitMap[newY][newX] = unitMap[y][x];
			unitMap[y][x] = null;
			unitMap[newY][newX].setYX(newY, newX);
		}
	}

	public void moveHero(Unit unit, int xOffset, int yOffset) {

		moveHero(unit.getX(), unit.getY(), unit.getX() + xOffset, unit.getY() + yOffset);

	}

	public ArrayList<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(ArrayList<Unit> unitList) {
		this.unitList = unitList;
	}

	public TerrainType[][] getTerrainMap() {
		return terrainMap;
	}

	public Unit[][] getUnitMap() {
		return unitMap;
	}

	public void displayMap() {
		System.out.println("\n\n\n");
		for (int y = 0; y < terrainMap.length; y++) {
			for (int x = 0; x < terrainMap[0].length; x++) {
				if (unitMap[y][x] != null) {
					System.out.print(unitMap[y][x].getName());
				}
				System.out.print("\t");
			}
			System.out.println();
			for (int x = 0; x < terrainMap[0].length; x++) {
				System.out.print(terrainMap[y][x] + "\t");
			}
			System.out.print("\t\t");
			if (y < unitList.size()) {
				System.out.println(unitList.get(y));
			}
			System.out.println("\n\n");
		}
	}

	// private void displayUnitStats(Unit) {
	// System.out.println();
	// }
	public void displayAttackOptions(Unit unit) {
		System.out.println("\n\n\n");
		boolean[][] availableTargets = findAvailableTargets(unit);

		for (int j = 0; j < terrainMap.length; j++) {
			for (int i = 0; i < terrainMap[0].length; i++) {
				if (unitMap[j][i] != null) {
					System.out.print(unitMap[j][i].getName());
				}
				System.out.print("\t");
			}
			System.out.println();
			for (int i = 0; i < terrainMap[0].length; i++) {
				if (availableTargets[j][i]) {
					System.out.print("X\t");
				} else
					System.out.print(terrainMap[j][i] + "\t");
			}
			System.out.print("\t\t");
			if (j < unitList.size()) {
				System.out.println(unitList.get(j));
			}
			System.out.println("\n\n");
		}

	}

	public boolean[][] findAvailableTargets(Unit unit) {
		int unitX = unit.getX();
		int unitY = unit.getY();
		boolean[][] availableTargets = new boolean[MAXY][MAXX];
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				if (Math.abs(unitX - x) + Math.abs(unitY - y) == unit.getRange())
					availableTargets[y][x] = true;
			}
		}
		return availableTargets;

	}

	private TerrainType[][] readMap(String mapName) {
		File mapFile = new File(mapName);

		try {
			Scanner fileReader = new Scanner(mapFile);
			TerrainType[][] map = new TerrainType[MAXY][MAXX];
			for (int lineNum = 0; lineNum < MAXY; lineNum++) {
				String[] lineArray = fileReader.nextLine().split(" ");
				for (int x = 0; x < MAXX; x++) {
					for (TerrainType terrain : TerrainType.values()) {
						if (lineArray[x] != null && lineArray[x].equals(terrain.toString())) {
							map[lineNum][x] = terrain;
							break;
						}
						
					}

				}

			}
			fileReader.close();
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Unit getUnit(String unitName) {
		for (Unit unit : unitList) {
			if (unit.getName().equals(unitName))
				return unit;
		}
		return null;
	}
}
