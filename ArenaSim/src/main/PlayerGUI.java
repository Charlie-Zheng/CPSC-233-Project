package main;

public class PlayerGUI extends TurnGUI {
	private static int selectedX = 0;
	private static int selectedY = 0;
	public PlayerGUI(Map inputMap) {
		super(inputMap, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getOneMove() {
		// TODO Auto-generated method stub
		System.out.println("X: " + selectedX+ "\tY: " + selectedY);

	}

	public int getSelectedX() {
		return selectedX;
	}

	public  void setSelectedX(int selectedX) {
		this.selectedX = selectedX;
	}

	public int getSelectedY() {
		return selectedY;
	}

	public  void setSelectedY(int selectedY) {
		this.selectedY = selectedY;
	}
}
