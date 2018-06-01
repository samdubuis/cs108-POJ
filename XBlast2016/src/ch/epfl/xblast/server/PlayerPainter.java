/**
 * classe permettant de faire le lien entre un joueur et ses images en fonction de son etat
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.server;

import ch.epfl.xblast.server.Player.LifeState.State;

public final class PlayerPainter {
    
    /**
     * methode retournant le byte associé é l'image du joueur
     * @param tick
     * @param player
     * @return
     */
    public static byte byteOfPlayer(int tick, Player player){
        if (player.lifeState().state()==State.DEAD) {
            return (byte) 15;
        }
        else {
            int b = 0 + player.id().ordinal()*20;
            if (player.lifeState().state()==State.DYING && player.lives()>1) {
                b+=12;
                return (byte) b;
            }
            else if (player.lifeState().state()==State.DYING && player.lives()==1) {
                b+=13;
                return (byte) b;
            }
            else {
                int reste = 0;
                if (tick%2!=0 && player.lifeState().state()==State.INVULNERABLE) {
                    b=80;
                }
                if (player.direction().isHorizontal()) {
                    reste = player.position().x()%4;
                }
                else {
                    reste = player.position().y()%4;
                }
                if (reste==3) {
                    b+=(player.direction().ordinal()*3 +2);
                    return (byte) b;
                }
                else if (reste==1) {
                    b+=(player.direction().ordinal()*3 +1);
                    return (byte)b;
                }
                else {
                    b+=(player.direction().ordinal()*3);
                    return (byte)b;
                }
            }
        }
    }
}



