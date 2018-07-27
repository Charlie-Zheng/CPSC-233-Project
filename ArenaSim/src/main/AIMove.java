package main;

public class AIMove {
	// Instance variables that the AIMove encapsulates
	// The unit is the unit to move
	private Unit unit;
	// x,y represent the location to move to
	// i,j represent the location to attack
	private int x, y, i, j;

	/**
	 * Make a move with the specified values
	 * 
	 * @param unit
	 *            The unit to move and attack
	 * @param x
	 *            The x coordinates of the movement location
	 * @param y
	 *            The y coordinates of the movement location
	 * @param i
	 *            The x coordinates of the attack location
	 * @param j
	 *            The y coordinates of the attack location
	 */
	AIMove(Unit unit, int x, int y, int i, int j) {
		this.unit = unit;
		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
	}


	
	/**
	 * @return The unit to move and attack
	 */
	public Unit getUnit() {
		return unit;
	}



	/**
	 * @return The x coordinates of the movement location
	 */
	public int getX() {
		return x;
	}



	/**
	 * @return The y coordinates of the movement location
	 */
	public int getY() {
		return y;
	}



	/**
	 * @return The x coordinates of the attack location
	 */
	public int getI() {
		return i;
	}



	/**
	 * @return The y coordinates of the attack location
	 */
	public int getJ() {
		return j;
	}


/**
 * @return
 * A String representation of the move and attack
 */
	public String toString() {
		return unit.getName() + " moved to: " + x + ", " + y + " and attacked: " + i + ", " + j;
	}
}
