/**
 * 
 */
package main;

import javafx.animation.PathTransition;
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
	private static MapGUI mapGUI;

	public CombatGUI(MapGUI mapGUI) {
		this.mapGUI = mapGUI;
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
//		int damage = calculateDamage(initiator, defender);
//		defender.takeDamage(damage);
//
//		if (applyCombat) {
//			addAttackAnimation(initiator);
//		}
		super.attack(initiator, defender, applyCombat);
	}

	private static void addAttackAnimation(Unit unit) {
		ImageView image = mapGUI.getUnitImage(unit);
		Path path = new Path();
		path.getElements().add(new MoveTo(0,0));
		path.getElements().add(new LineTo(100.0f, 100.0f));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(500));
		pathTransition.setPath(path);
		pathTransition.setNode(image);
		pathTransition.setOrientation(PathTransition.OrientationType.NONE);
		pathTransition.setCycleCount(2);
		pathTransition.setAutoReverse(true);
		pathTransition.play();
	}

}
