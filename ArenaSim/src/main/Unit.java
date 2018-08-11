package main;

//import javafx.scene.shape.Rectangle;
import java.util.Arrays;//imports the java library of Arrays

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Unit {
	/*
	 * Creates a class called Unit and uses it to set the stats and other things to
	 * the enemies and player characters, with the following instance variables:
	 */

	/*-Initializes a integer array called baseStats which determines the basic attributes of the current unit,
	 * 1st Int = Health Points, 2nd Int = Attack Points, 3rd Int =Speed Points, 4th Int = Defense Points. 
	 * -Creates a integer variable called currentHP, which is used to register the currentHP value.
	 * -Creates a integer variable called range, which is used for determining the range of how far the 
	 *  current unit is able to attack.
	 * -Creates a MoveType class variable called moveType, used to determine how far the unit can move.
	 * -Creates a boolean variable called isFriendly, used to determine if a unit is on the current user's side or not.
	 * -Creates a boolean variable called hasMoved, used to determine if the current unit has moved or not.
	 * -initializes a integer variable called x and y set to 0, used to record the unit's current x and y position.
	 */
	private int[] baseStats = { 1, 0, 0, 0 };// default unit has 1 HP, 0 in every other stat
	// {HP, Atk, Spd, Def}
	private int currentHP;
	private Rectangle hpBar;
	private int range;
	private MoveType moveType;
	private boolean isFriendly;
	private boolean hasMoved;
	private int x = 0;
	private int y = 0;
	private String name = "";// Initializes the String variable name to be empty, used to register the Unit's
	// name.
	/**
	 * Copy constructor for the Unit Class that takes a Unit class object as a
	 * parameter:
	 * 
	 * @param unit
	 *            takes in a Unit class object parameter which copies the Current
	 *            unit's attributes such as its baseStats array, its current
	 *            position on the map, etc.
	 */
	public Unit(Unit unit) {
		/*
		 * This constructor copies the given Unit class parameter unit's attributes.
		 * -This is used every time a unit engage in combat where one is the defendant
		 * and the other is the attacker.
		 */

		/*
		 * It does the following things with the instance variables when this
		 * constructor is called: -It copies the integer array of the parameter unit's
		 * baseStats, and assigns it to the instance variable baseStats for this object.
		 * -It then replaces the values of the instance variable currentHP with the unit
		 * parameter's currentHP value. -It then copies the parameter unit's range value
		 * and assigns it to the instance variable range. -It Copies the move type of
		 * the parameter unit and assigns it to the instance variable moveType. -It
		 * copies if the unit parameter's status is on the player's side, and assigns to
		 * the instance variable isFriendly. -It copies the unit parameter's location on
		 * the map, and assigns its x location to the instance variable x, and assigns
		 * its y location to the instance variable y. -Finally it then registers the
		 * current unit parameter's name and assigns it to the instance variable name.
		 */
		this.baseStats = Arrays.copyOf(unit.baseStats, unit.baseStats.length);
		this.currentHP = unit.currentHP;
		this.range = unit.range;
		this.moveType = unit.moveType;
		this.isFriendly = unit.isFriendly;
		this.hasMoved = unit.hasMoved;
		this.x = unit.x;
		this.y = unit.y;
		this.name = unit.name;
		this.hpBar=unit.hpBar;
	}
	
	public void setHpBar(Rectangle hpBar) {
		this.hpBar=hpBar;
	}
	public void updateHpBar() {
		hpBar.setX(this.getX() * TerrainGUI.getImagewidth()+TerrainGUI.getImagewidth()/8);
		hpBar.setY(this.getY() * TerrainGUI.getImagewidth());
		}
	/**
	 * @param damage
	 *            is a integer value of how much health points are taken away from
	 *            the current unit after it is attacked.
	 * @return it then returns a boolean value of whether the current units instance
	 *         variable of currentHP is less than or equal to 0, if it is it returns
	 *         True indicating the current unit has died, else it returns false
	 *         meaning the current unit is still alive.
	 */
	public boolean takeDamage(int damage) {
		/*
		 * This boolean function determines how much damage the unit takes in combat
		 * with the parameter damage, and their status on if the unit is still alive
		 * after taking in that amount of damage or not. -It does this by first
		 * subtracting the value of the current Unit's currentHP instance variable by
		 * the integer parameter value of damage. It then returns if the condition of
		 * the unit's currentHP instance parameter is less than or equal to 0. It
		 * returns true if the current Unit's currentHP value is less than or equal to 0
		 * else it returns false.
		 */
		currentHP -= damage;
		/*if(this.currentHP!=this.getBaseHP()) {
			this.hpBar.setWidth(this.hpBar.getWidth()-(this.getBaseHP()-this.getCurrentHP()));
			if(this.currentHP<=0) {
				this.hpBar.setOpacity(100);
			}
	}*/
		return currentHP <= 0;
	}

	/**
	 * @param y
	 *            is a integer parameter which is used to set/record the current
	 *            Unit's y value at that position.
	 * @param x
	 *            is a integer parameter which is used to set/record the current
	 *            Unit's x value at that position.
	 */
	public void setYX(int y, int x) {
		/*
		 * This function records the unit's current location on the map. The unit's
		 * current x position is recorded in the instance variable x. The unit's current
		 * y position is recorded in the instance variable y.
		 */
		this.y = y;
		this.x = x;
	}

	/**
	 * @return the current Unit's x instance variable value
	 */
	public int getX() {
		/* This function returns the unit's current x position */
		return x;
	}

	/**
	 * @return the instance integer array variable baseStats of the current Unit
	 */
	public int[] getBaseStats() {
		/*
		 * This function returns the current unit's base stats, which is used to
		 * determine how much damage a unit does, etc.
		 */
		return baseStats;
	}

	/**
	 * @return the current Unit's range variable value which is a integer which
	 *         determines how far the unit can attack.
	 */
	public int getRange() {
		/*
		 * This function returns the current unit's range of how far they are able to
		 * attack
		 */
		return range;
	}

	/**
	 * @return what the move type of the current Unit is.
	 */
	public MoveType getMoveType() {
		/*
		 * Returns what moveType object they are, which determines how far they are able
		 * to move on the board
		 */
		return moveType;
	}

	/**
	 * @return the boolean instance variable isFriendly, returns True if the current
	 *         Unit is on that current User's team, else it returns false.
	 */
	public boolean isFriendly() {
		/*
		 * this function returns a boolean value of either True for if the unit is on
		 * the player's side, or it returns false for if the unit is on the enemies
		 * side.
		 */
		return isFriendly;
	}

	/**
	 * @return the value of the current Unit's instance variable name which is a
	 *         String.
	 */
	public String getName() {
		/* this function what the name of the current Unit is. */
		return name;
	}

	/**
	 * @param x
	 *            determines the current Unit's x position, and the value that x is,
	 *            is recorded into the current Unit's instance variable of x.
	 */
	public void setX(int x) {
		/* Records the unit's current x position on the map */
		this.x = x;
	}

	/**
	 * @return the current Unit's y location value
	 */
	public int getY() {
		/* This function returns what the y value of the current unit is */
		return y;
	}

	/**
	 * @param y
	 *            determines the current Unit's y position, and the value that y is,
	 *            is recorded into the current Unit's instance variable of y.
	 */
	public void setY(int y) {
		/* This function records what the y location of the current unit is. */
		this.y = y;
	}

	/**
	 * @return a integer value of the current Unit's instance variable currentHP
	 */
	public int getCurrentHP() {
		/*
		 * this function returns what the unit's current Health points are, which
		 * determine if the unit is alive or dead.
		 */
		return currentHP;
	}





	/**
	 * This is another constructor for the Unit class 
	 * set to activate if these parameters are given into it when creating a new Unit Object with the following parameters given:
	 * 
	 * @param name
	 *            sets the name of the current unit to be of the String value of
	 *            this.
	 * @param move
	 *            determines the Unit's moveType instance class variable to be of
	 *            this value, which determines how much the unit as able to move
	 *            around on the map.
	 * @param isFriendly
	 *            determines if the current Unit is on the current user's side or
	 *            not, if the unit is on the player's side that value is set to
	 *            True, else it is False.
	 * @param stats
	 *            sets the base stats of the current Unit to be of this integer
	 *            array.
	 */
	Unit(String name, MoveType move, boolean isFriendly, int[] stats, int range) {
		/*
		 * This is a constructor for when creating a new unit with the following
		 * parameters: -It initializes and records what the name of the current unit is
		 * and records it to the String variable name. -It records what the move type of
		 * the unit is, and records it to the Class variable moveType. -It records if
		 * the unit is friendly or not in the variable isFriendly which is a boolean
		 * variable.
		 */

		/*
		 * Then if the length of both the stats parameter and the current object's
		 * baseStats variable are the same, It goes through every element in the integer
		 * array parameter stats and records that value at that index to be the value of
		 * the object's baseStats array at that index.
		 */

		/*
		 * It then records how far the unit is able to attack by recording the value of
		 * the integer parameter range into the object's current instance variable of
		 * range.
		 */

		/*
		 * It then sets the current Unit's base Health point value in the baseStats
		 * integer array to be the integer value of the instance variable currentHP
		 */
		this.name = name;
		this.moveType = move;
		this.isFriendly = isFriendly;
		if (stats.length == baseStats.length) {
			for (int x = 0; x < baseStats.length; x++) {
				baseStats[x] = stats[x];
			}
		}
		this.range = range;
		currentHP = baseStats[0];
	}

	/**
	 * @return the Unit's baseHP integer that is in the instance variable array
	 *         baseStats
	 */
	public int getBaseHP() {
		/* this function returns what the unit's Base health points are */
		return baseStats[0];
	}

	/**
	 * @return the Unit's base attack point integer that is in the instance variable
	 *         array baseStats
	 */
	public int getAtk() {
		/*
		 * this function returns a integer that determines how much attack points the
		 * unit has
		 */
		return baseStats[1];
	}

	/**
	 * @return the Unit's base Speed integer that is in the instance variable array
	 *         baseStats
	 */
	public int getSpd() {
		/* this function returns a integer value of how fast the unit is in combat */
		return baseStats[2];
	}

	/**
	 * @return the Unit's base Defense integer that is in the instance variable
	 *         array baseStats
	 */
	public int getDef() {
		/*
		 * this function returns a integer that determines how much defense points the
		 * current unit has
		 */
		return baseStats[3];
	}

	/**
	 * @param hp
	 *            sets the current Unit's baseStat hp integer, for the instance
	 *            integer array variable baseStats to be that of hp parameter.
	 */
	public void setBaseHP(int hp) {
		/*
		 * this function records what the unit's current base Health points value are.
		 */
		baseStats[0] = hp;
	}

	/**
	 * @param atk
	 *            sets the current Unit's baseStat attack integer for the instance
	 *            integer array variable baseStats to be that of atk parameter.
	 */
	public void setBaseAtk(int atk) {
		/* this function sets how much attack points the current unit has */
		baseStats[1] = atk;
	}

	/**
	 * @param spd
	 *            sets the current Unit's baseStat speed integer for the instance
	 *            integer array variable baseStats to be that of spd parameter.
	 */
	public void setBaseSpd(int spd) {
		/* this function Sets how much speed the current unit has */
		baseStats[2] = spd;
	}

	/**
	 * @param def
	 *            sets the current Unit's baseStat defense integer for the instance
	 *            integer array variable baseStats to be that of def parameter.
	 */
	public void setBaseDef(int def) {
		/* this function Sets how much defense points the current unit has */
		baseStats[3] = def;
	}

	/**
	 * @return True if the unit's instance variable hasMoved is True, else it
	 *         returns False.
	 */
	public boolean hasMoved() {
		/*
		 * this function returns a boolean value for if the current unit has moved or
		 * not
		 */
		return hasMoved;
	}

	/**
	 * @param hasMoved
	 *            is a boolean value, if the current unit has moved it set's the
	 *            boolean instance variable hasMoved of the current Unit object to
	 *            true, else it records it as false.
	 */
	public void setHasMoved(boolean hasMoved) {
		/*
		 * This function sets a boolean status for if the current unit has moved or not
		 * for the instance variable hasMoved
		 */
		this.hasMoved = hasMoved;
	}

	/**
	 * @return Returns a String of the current unit's stats values and other
	 *         attributes(such as if the unit is the player's unit, the units name,
	 *         etc)
	 */
	@Override
	public String toString() {
		/*
		 * This function returns a string representation of what the current unit's
		 * stats are, as in their base stats, their attack range, what the moveType is,
		 * the name of the unit, and if the current unit is on the player's side or not.
		 */
		return "  " + (isFriendly ? "FRIENDLY\t" : "ENEMY\t\t") + name + ": " + "\tHP: " + currentHP + "/" + getBaseHP()
				+ "  Atk: " + getAtk() + "  Spd: " + getSpd() + "  Def: " + getDef() + "  Range: " + range
				+ "  Move Type: " + moveType;
	}
	
	public boolean isAlive() {
		return currentHP > 0;
	}

}
