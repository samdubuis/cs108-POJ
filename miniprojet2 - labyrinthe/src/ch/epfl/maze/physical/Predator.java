package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.Random;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Predator that kills a prey when they meet with each other in the labyrinth.
 * 
 */

abstract public class Predator extends Animal {

	private Vector2D position;
	private Vector2D caseCible;
	private Vector2D caseMaison;
	private Prey animalCible;

	

	/* constants relative to the Pac-Man game */
	public static final int SCATTER_DURATION = 14;
	public static final int CHASE_DURATION = 40;

	/**
	 * Constructs a predator with a specified position.
	 * 
	 * @param position
	 *            Position of the predator in the labyrinth
	 */

	public Predator(Vector2D position) {
		super(position);
		position = null;
		caseMaison = getPosition();
		caseCible = null;
		animalCible = null;
	}

	/**
	 * Moves according to a <i>random walk</i>, used while not hunting in a
	 * {@code MazeSimulation}.
	 * 
	 */

	@Override
	public final Direction move(Direction[] choices) {
		Random random = new Random();
		if (getPreviousChoice()==null){
			int rand = random.nextInt(choices.length);
			setPreviousChoice(choices[rand]);
			return choices[rand];
		}
		if (getPreviousChoice()!=null && choices.length == 1){
			setPreviousChoice(choices[0]);
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
			return temp.get(rand);
		}
		else
			return Direction.NONE;
	}

	/**
	 * Fonction de deplacement identique chez tous les fantomes	
	 * @param choices
	 * @param emplacementCible
	 * @return La direction suivante selon la case ciblee avec l'appel des methodes de FindBestDirection
	 */
	public Direction deplacement(Direction choix[], Vector2D cibleVectoriel){
				
		caseCible = cibleVectoriel;
		Direction[] choices = new Direction[choix.length];
		for (int i = 0; i < choix.length; i++) {
			choices[i] = choix[i];
		}


		if (getPreviousChoice()==null){
			setPreviousChoice(findBestDirection(choices, caseCible, getPosition()));
			setEmplacementPredator(getPosition()).addDirectionTo(getPreviousChoice());
			return findBestDirection(choices, caseCible, getPosition());
		}
		if (getPreviousChoice()!=null && choices.length == 1){
			setPreviousChoice(choices[0]);
			setEmplacementPredator(getPosition().addDirectionTo(choices[0]));
			return choices[0];
		}
		if (getPreviousChoice() != null && choices.length > 1){
			ArrayList<Direction> temp = new ArrayList<Direction>();
			for (int i = 0; i < choices.length; i++) {
				temp.add(choices[i]);
			}
			Direction opposite = getPreviousChoice().reverse();
			temp.remove(opposite);
			Direction[] temp2 = new Direction[choices.length-1];
			for (int i = 0; i < choices.length-1; i++) {
				temp2[i] = temp.get(i);
			}
			setPreviousChoice(findBestDirection(temp2, caseCible, getPosition()));
			setEmplacementPredator(getPosition()).addDirectionTo(getPreviousChoice());
			return findBestDirection(temp2, caseCible, getPosition());
		}
		else{
			return Direction.NONE;
		}
	}
	




	public Direction findBestDirection(Direction[] choices, Vector2D emplacementCible, Vector2D emplacementPredator){
		Direction best = null;
		double bestint = 0;
		for (int i = 0; i < choices.length; i++) {
			double e = Math.sqrt(((emplacementPredator.addDirectionTo(choices[i])).sub(emplacementCible)).dist());
			if(best == null || e < bestint){
				best = choices[i];
				bestint = e;
			}
		}
		return best;
	}
	
	public Vector2D getEmplacementPredator(){
		return position;
	}
	
	public Vector2D setEmplacementPredator (Vector2D unePosition){
		position = unePosition;
		return position;
	}
	
	public Vector2D getCaseCible(){
		return caseCible;
	}
	
	public void setCaseCible(Vector2D v){
		caseCible = v;
	}
	
	public Vector2D getCaseMaison(){
		return caseMaison;
	}
	
	public void setCaseMaison(Vector2D v){
		caseMaison = v;
	}

	public Prey getAnimalCible() {
		return animalCible;
	}

	public void setAnimalCible(Prey animalCible) {
		this.animalCible = animalCible;
	}
	
	/**
	 * Retrieves the next direction of the animal, by selecting one choice among
	 * the ones available from its position.
	 * <p>
	 * In this variation, the animal knows the world entirely. It can therefore
	 * use the position of other animals in the daedalus to hunt more
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
