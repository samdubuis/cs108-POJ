package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pink ghost from the Pac-Man game, targets 4 squares in front of its target.
 * 
 */

public class Pinky extends Predator {

	int k ;
	int i ;
	/**
	 * Constructs a Pinky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Pinky in the labyrinth
	 */

	public Pinky(Vector2D position) {
		super(position);
		k=0;
		i=0;
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		if (daedalus.getPreys().size()>0){
			int size = daedalus.getPreys().size();
			setAnimalCible(daedalus.getPreys().get(size-1));
			setCaseCible(getAnimalCible().getEmplacement());
			
			if (getAnimalCible().getPreviousChoice()!=null) { 

				if (getAnimalCible().getPreviousChoice()==Direction.UP) {
					Direction ajout = getAnimalCible().getPreviousChoice().relativeDirection(Direction.UP);
					Vector2D ajout2 = ajout.toVector().mul(4);
					setCaseCible(getCaseCible().add(ajout2));
				}

				if (getAnimalCible().getPreviousChoice()==Direction.RIGHT) {
					Direction ajout = getAnimalCible().getPreviousChoice().relativeDirection(Direction.DOWN);
					Vector2D ajout2 = ajout.toVector().mul(4);
					setCaseCible(getCaseCible().add(ajout2));
				}

				if (getAnimalCible().getPreviousChoice()==Direction.DOWN) {
					Direction ajout = getAnimalCible().getPreviousChoice().relativeDirection(Direction.UP);
					Vector2D ajout2 = ajout.toVector().mul(4);
					setCaseCible(getCaseCible().add(ajout2));
				}

				if (getAnimalCible().getPreviousChoice()==Direction.LEFT) {
					Direction ajout = getAnimalCible().getPreviousChoice().relativeDirection(Direction.DOWN);
					Vector2D ajout2 = ajout.toVector().mul(4);
					setCaseCible(getCaseCible().add(ajout2));
				}

				if (k==0) {
					if (i == CHASE_DURATION-1) {
						k=1;
						i=0;
					}
					i++;
					Direction choix = deplacement(choices, getCaseCible());
					return choix;
				}
				else {
					if (i == SCATTER_DURATION-1) {
						k=0;
						i=0;
					}
					i++;
					Direction choix = deplacement(choices, getCaseMaison());
					return choix;
				}
			}
			else {
				Vector2D ajouttemp = Direction.DOWN.toVector().mul(4);
				setCaseCible(getCaseCible().add(ajouttemp));
				return deplacement(choices, getCaseCible());
			}
		}

		else{
			return move(choices);
		}
	}

	@Override
	public Animal copy() {
		return new Pinky(this.getPosition());
	}
}
