package main;

public class Unit {
	private int[] baseStats = { 1, 0, 0, 0 };// default unit has 1 HP, 0 in every other stat
	private int currentHP;

	private int range;
	private MoveType moveType;
	private boolean isFriendly;
	private boolean hasMoved;

	private int x = 0;
	private int y = 0;

	// HP,Atk,Spd,Def

	public void setYX(int y, int x) {
		this.y = y;
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public int[] getBaseStats() {
		return baseStats;
	}

	public int getRange() {
		return range;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public boolean isFriendly() {
		return isFriendly;
	}

	public String getName() {
		return name;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	// private Skill[] skills = new Skill[4];
	String name = "";

	Unit(String name, MoveType move, boolean isFriendly) {
		this.name = name;
		this.moveType = move;
		this.isFriendly = isFriendly;
		currentHP = baseStats[0];
	}

	Unit(String name, MoveType move, boolean isFriendly, int[] stats, int range) {
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

	public int getBaseHP() {
		return baseStats[0];
	}

	public int getAtk() {
		return baseStats[1];
	}

	public int getSpd() {
		return baseStats[2];
	}

	public int getDef() {
		return baseStats[3];
	}

	public void setBaseHP(int hp) {
		baseStats[0] = hp;
	}

	public void setBaseAtk(int atk) {
		baseStats[1] = atk;
	}

	public void setBaseSpd(int spd) {
		baseStats[2] = spd;
	}

	public void setBaseDef(int def) {
		baseStats[3] = def;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public String toString() {
		return "  " + (isFriendly ? "FRIENDLY\t" : "ENEMY\t\t") + name + ": " + "\tHP: " + currentHP + "/" + getBaseHP()
				+ "  Atk: " + getAtk() + "  Spd: " + getSpd() + "  Def: " + getDef() + "  Range: " + range
				+ "  Move Type: " + moveType;
	}

}
