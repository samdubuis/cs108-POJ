package ch.epfl.xblast;

import ch.epfl.xblast.server.Bomb;
import ch.epfl.cs108.Sq;
import ch.epfl.xblast.PlayerID;
public class BombTest {


    public static void main(String[] args) {
        Cell c = new Cell(2, 3);

        Bomb b = new Bomb(PlayerID.PLAYER_1, c, 5, 5);

        
        System.out.println(b.explosion());

        for (int i = 0; i < 4; i++) {

            Sq<Cell> s = b.explosion().get(i).head();

            for (int j = 0; j < 10; j++) {
                System.out.println(s.head());
                s = s.tail();
            }
        }


    }

}
