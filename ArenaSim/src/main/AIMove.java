package main;

public class AIMove {

	Unit unit;
	int x, y, i, j;

	AIMove(Unit unit, int x, int y, int i, int j) {
		this.unit = unit;
		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public int getX() {
		return x;
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

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	public String toString() {
		return unit.getName()+" moved to: " + x + ", " + y + " and attacked: " + i + ", " +j;
	}
}
