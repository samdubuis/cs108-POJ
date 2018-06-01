package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Monkey A.I. that puts its hand on the left wall and follows it.
 * 
 */

public class Monkey extends Animal {

	/**
	 * Constructs a monkey with a starting position.
	 * 
	 * @param position
	 *            Starting position of the monkey in the labyrinth
	 */

	public Monkey(Vector2D position) {
		super(position);
		}

	/**
	 * Moves according to the relative left wall that the monkey has to follow.
	 */

	@Override
	public Direction move(Direction[] choices) {
		Random random = new Random();
		if (getPreviousChoice()==null){
			int rand = random.nextInt(choices.length);
			setPreviousChoice(choices[rand]);
			return choices[rand];
		}
		else{
			ArrayList<Direction> temp = new ArrayList<Direction>();
			for (int i = 0; i < choices.length; i++) {
				temp.add(getPreviousChoice().relativeDirection(choices[i]));				
			}
			if (temp.contains(Direction.LEFT)) {
				setPreviousChoice(getPreviousChoice().unRelativeDirection(Direction.LEFT));
				return getPreviousChoice();
			}
			else {
				if (temp.contains(Direction.UP)) {
					setPreviousChoice(getPreviousChoice().unRelativeDirection(Direction.UP));
					return getPreviousChoice();
				}
				else {
					if (temp.contains(Direction.RIGHT)){
						setPreviousChoice(getPreviousChoice().unRelativeDirection(Direction.RIGHT));
						return getPreviousChoice();
					}
					else {
						if (temp.contains(Direction.DOWN)) {
							setPreviousChoice(getPreviousChoice().unRelativeDirection(Direction.DOWN));
							return getPreviousChoice();
						}
						else
							return Direction.NONE;
					}
				}
			}
		}
	}

	@Override
	public Animal copy() {
		return new Monkey(this.getPosition());
	}
}
