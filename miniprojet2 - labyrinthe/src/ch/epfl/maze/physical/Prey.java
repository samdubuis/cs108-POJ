package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Prey that is killed by a predator when they meet each other in the labyrinth.
 * 
 */

abstract public class Prey extends Animal {
	
	private Vector2D caseMaison;
	private Vector2D emplacement;

	/**
	 * Constructs a prey with a specified position.
	 * 
	 * @param position
	 *            Position of the prey in the labyrinth
	 */

	public Prey(Vector2D position) {
		super(position);
		emplacement = getPosition();
		caseMaison = getPosition();
		}

	/**
	 * Moves according to a <i>random walk</i>, used while not being hunted in a
	 * {@code MazeSimulation}.
	 * 
	 */

	@Override
	public final Direction move(Direction[] choices) {
		Random random = new Random();

		if (getPreviousChoice()==null){
			int rand = random.nextInt(choices.length);
			setPreviousChoice(choices[rand]);
			setEmplacement(getPosition().addDirectionTo(choices[rand]));
			return choices[rand];
		}
		if (getPreviousChoice()!=null && choices.length == 1){
			setPreviousChoice(choices[0]);
			setEmplacement(getPosition().addDirectionTo(choices[0]));
			return choices[0];}

		if (getPreviousChoice() != null && choices.length>1){
			ArrayList<Direction> temp = new ArrayList<Direction>();
			for (int i = 0; i < choices.length; i++) {
				temp.add(choices[i]);
			}
			Direction opposite = getPreviousChoice().reverse();
			temp.remove(opposite);

			int rand = random.nextInt(temp.size());
			setPreviousChoice(temp.get(rand));
			setEmplacement(getPosition().addDirectionTo(choices[rand]));
			return temp.get(rand);
		}
		else{
			setEmplacement(getPosition());
			return Direction.NONE;
		}
	}
	
	public final Direction move2(Direction[] choices) {
		Random random = new Random();

		if (getPreviousChoice()==null){
			int rand = random.nextInt(choices.length);
			setPreviousChoice(choices[rand]);
			setEmplacement(getPosition().addDirectionTo(choices[rand]));
			return choices[rand];
		}
		if (getPreviousChoice()!=null && choices.length == 1){
			setPreviousChoice(choices[0]);
			setEmplacement(getPosition().addDirectionTo(choices[0]));
			return choices[0];
		}

		if (getPreviousChoice() != null && choices.length>1){
			ArrayList<Direction> temp = new ArrayList<Direction>();
			for (int i = 0; i < choices.length; i++) {
				temp.add(choices[i]);
			}

			int rand = random.nextInt(temp.size());
			setPreviousChoice(temp.get(rand));
			setEmplacement(getPosition().addDirectionTo(choices[rand]));
			return temp.get(rand);
		}
		else{
			setEmplacement(getPosition());
			return Direction.NONE;
		}
	}
	
	public Vector2D getCaseMaison() {
		return caseMaison;
	}

	public void setCaseMaison(Vector2D caseMaison) {
		this.caseMaison = caseMaison;
	} 
	
	public Vector2D getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(Vector2D v){
		emplacement = v;
	}
	/**
	 * Retrieves the next direction of the animal, by selecting one choice among
	 * the ones available from its position.
	 * <p>
	 * In this variation, the animal knows the world entirely. It can therefore
	 * use the position of other animals in the daedalus to evade predators more
	 * effectively.
	 * 
	 * @param choices
	 *            The choices left to the animal at its current position (see
	 *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
	 *            World.getChoices(Vector2D)})
	 * @param daedalus
	 *            The world in which the animal moves
	 * @return The next direction of the animal, chosen in {@code choices}
	 */

	abstract public Direction move(Direction[] choices, Daedalus daedalus);

	
}
