/**
 * 
 */
package main;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * @author charl
 */
public class CombatGUI extends Combat {
	private MapGUI mapGUI;
	private SequentialTransition seq;
	private AIGUI ai ;
	public CombatGUI(MapGUI mapGUI) {
		this.mapGUI = mapGUI;
		seq = new SequentialTransition();
		ai = new AIGUI(mapGUI,this);
	}

	@Override
	protected void attack(Unit initiator, Unit defender, boolean applyCombat) {
		/*
		 * This function is used when the attack stage of a unit begins. -The unit
		 * object parameter initiator is the unit that is attacking on that turn. -The
		 * unit object parameter defender is the unit that is taking the attack by the
		 * initiator unit on that turn. -The boolean value of applyCombat determines on
		 * if the battle-log messages should display or not. This function does the
		 * following with the indicated parameters: -It goes to the function
		 * calculateDamage to determine the damage the defender parameter unit takes,
		 * and the value that is returned is registered under the integer variable
		 * damage. -It then calls the takeDamage method on the defender unit object
		 * parameter, where the defender unit takes the value of damage saved in the
		 * variable damage.
		 */
		int damage = calculateDamage(initiator, defender);
		defender.takeDamage(damage);

		if (applyCombat) {
			addAttackAnimation(initiator, defender);
		}
		// super.attack(initiator, defender, applyCombat);
	}

	/**
	 * This function actually applies the combat results
	 * 
	 * @param initiator
	 *            is a Unit class object who is starting the battle against another
	 *            unit
	 * @param defender
	 *            is another Unit class object parameter who is defending against
	 *            the unit attacking it.
	 */
	public void doCombat(Unit initiator, Unit defender) {
		/*
		 * This function is used to calculate the battle between 2 units that have
		 * battled before. -The parameter initiator is the Unit object initiating the
		 * battle -The parameter defender is the Unit object defending against the
		 * initiator Unit. -It does this by going to another function called combat
		 * where it does all the operations for a battle between 2 units: Where it takes
		 * the initiator unit and the defender unit, aswell as a boolean value that
		 * determines if the Units have battled before/just started, since the battle
		 * was initiated its set to true.
		 */
		if (initiator != null && defender != null) {
			combat(initiator, defender, true);
			seq.play();
			mapGUI.setAnimating(true);
		}
	}

	private void addAttackAnimation(Unit initiator, Unit defender) {
		ImageView image = mapGUI.getUnitImage(initiator);
		Path path = new Path();
		// path.getElements().add(new MoveTo(image.getFitWidth() / 2,
		// image.getFitHeight() / 2));
		path.getElements().add(new MoveTo(image.getImage().getWidth() / 2, image.getImage().getHeight() / 2));
		path.getElements()
				.add(new LineTo(
						image.getImage().getWidth() / 2
								+ (defender.getX() - initiator.getX()) * TerrainGUI.getImagewidth() / 2,
						image.getImage().getHeight() / 2
								+ (defender.getY() - initiator.getY()) * TerrainGUI.getImageheight() / 2));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(250));
		pathTransition.setPath(path);
		pathTransition.setNode(image);
		pathTransition.setCycleCount(2);
		pathTransition.setAutoReverse(true);
		seq.setOnFinished((event) -> {
			seq.getChildren().clear();
			mapGUI.setAnimating(false);
			mapGUI.updateUnitsOnMap();
			System.out.println("\n1"+(!mapGUI.gameOver() && !mapGUI.factionHasUnmovedUnits(true)));
			if (!mapGUI.gameOver() && !mapGUI.factionHasUnmovedUnits(true)) {
				
				ai.computerTurn();
				
			}
			
		});
		seq.getChildren().add(pathTransition);

	}

}
