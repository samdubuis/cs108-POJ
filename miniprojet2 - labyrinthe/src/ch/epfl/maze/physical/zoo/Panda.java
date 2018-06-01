package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Panda A.I. that implements Trémeaux's Algorithm.
 * 
 */
public class Panda extends Animal {
	
	private ArrayList<Vector2D> couleurUne;
	private ArrayList<Vector2D> couleurDeux;

	/**
	 * Constructs a panda with a starting position.
	 * 
	 * @param position
	 *            Starting position of the panda in the labyrinth
	 */

	public Panda(Vector2D position) {
		super(position);
		couleurUne = new ArrayList<>();
		couleurDeux = new ArrayList<>();
	}

	/**
	 * Moves according to <i>Trémeaux's Algorithm</i>: when the panda
	 * moves, it will mark the ground at most two times (with two different
	 * colors). It will prefer taking the least marked paths. Special cases
	 * have to be handled, especially when the panda is at an intersection.
	 */

	@Override
	public Direction move(Direction[] choices) {
		Random random = new Random();
		if (getPreviousChoice()==null){
			int rand = random.nextInt(choices.length);
			setPreviousChoice(choices[rand]);
			if (couleurUne.contains(getPosition())) {
				couleurDeux.add(getPosition());
			}
			else {
				couleurUne.add(getPosition());
			}
			if (choices.length==1) {
				couleurDeux.add(getPosition());
			}
			return choices[rand];
		}
		if (getPreviousChoice() != null && choices.length==1) {
			couleurDeux.add(getPosition());
			setPreviousChoice(choices[0]);
			return choices[0];
		}
		if (getPreviousChoice() != null && choices.length==2) {
			ArrayList<Direction> temp = new ArrayList<Direction>();
			for (int i = 0; i < choices.length; i++) {
				temp.add(choices[i]);
			}
			for (int i = 0; i < choices.length; i++) {
				if (couleurDeux.contains(getPosition().addDirectionTo(choices[i]))) {
					couleurDeux.add(getPosition());
				}
				else {
					couleurUne.add(getPosition());
				}
			}
			temp.remove(getPreviousChoice().reverse());
			setPreviousChoice(temp.get(0));
			return temp.get(0);			
		}
		if (getPreviousChoice() != null && choices.length>2) {
			ArrayList<Direction> temp = new ArrayList<Direction>();
			ArrayList<Direction> temp2 = new ArrayList<Direction>();
			ArrayList<Direction> temp3 = new ArrayList<Direction>();
			ArrayList<Direction> temp4 = new ArrayList<Direction>();


			for (int i = 0; i < choices.length; i++) {
				temp.add(choices[i]);
			}
			for (int i = 0; i < choices.length; i++) {
				if (!couleurUne.contains(getPosition().addDirectionTo(choices[i])) && 
						!couleurDeux.contains(getPosition().addDirectionTo(choices[i]))) {
					temp2.add(choices[i]);
				}
				if (couleurUne.contains(getPosition().addDirectionTo(choices[i])) && 
						!couleurDeux.contains(getPosition().addDirectionTo(choices[i]))) {
					temp3.add(choices[i]);
				}
				else {
					temp4.add(choices[i]);
				}
			}
			
			if (temp3.size() == choices.length) {
				couleurUne.add(getPosition());
				setPreviousChoice(getPreviousChoice().reverse());
				return getPreviousChoice().reverse();
			}
			if (temp3.size() == 1) {
				couleurDeux.add(getPosition());
			}
			temp2.remove(getPreviousChoice().reverse());
			temp3.remove(getPreviousChoice().reverse());
			temp4.remove(getPreviousChoice().reverse());

			
			if (!temp2.isEmpty()) {
				couleurUne.add(getPosition());
				int rand = random.nextInt(temp2.size());
				setPreviousChoice(temp2.get(rand));
				return temp2.get(rand);
			}
			if (temp2.isEmpty() && !temp3.isEmpty()) {
				couleurUne.add(getPosition());
				int rand = random.nextInt(temp3.size());
				setPreviousChoice(temp3.get(rand));
				return temp3.get(rand);
			}
			if (temp2.isEmpty() && temp3.isEmpty()) {
				int rand = random.nextInt(temp4.size());
				setPreviousChoice(temp4.get(rand));
				return temp4.get(rand);
			}
			else
				return Direction.NONE;
		}
		else
			return Direction.NONE;
	}

	@Override
	public Animal copy() {
		Animal a = new Panda(getPosition());
		return a;
	}
}
