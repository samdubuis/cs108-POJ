/**
 * Gamestate du client
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.client;
 
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
 
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
 
public final class GameState {
    private final List<Player> players;
    private final List<Image> plateau;
    private final List<Image> bombeEtExplosion;
    private final List<Image> scores;
    private final List<Image> temps;
    
    /**
     * constructeur du gamestate
     * @param players
     * @param plateau
     * @param bombeEtExplosion
     * @param scores
     * @param temps
     */
    public GameState(List<Player> players, List<Image> plateau, List<Image> bombeEtExplosion, List<Image> scores, List<Image> temps){
        if (players.size() != 4) throw new IllegalArgumentException();
        this.players = new ArrayList<>(players);
        if (plateau.size() != 195) throw new IllegalArgumentException();
        this.plateau = new ArrayList<>(plateau);
        this.bombeEtExplosion = new ArrayList<>(bombeEtExplosion);
        if (scores.size() != 20) throw new IllegalArgumentException();
        this.scores = new ArrayList<>(scores);
        if (temps.size() != 60) throw new IllegalArgumentException();
        this.temps = new ArrayList<>(temps);
 
    }
    /**
     * retourne les joueurs
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }
    
    /**
     * retourne le plateau du jeu
     * @return
     */
    public List<Image> getPlateau() {
        return plateau;
    }
    /**
     * retourne les bombes et les explosions sur le plateau
     * @return
     */
    public List<Image> getBombeEtExplosion() {
        return bombeEtExplosion;
    }
    /**
     * retourne le score de la partie
     * @return
     */
    public List<Image> getScores() {
        return scores;
    }
    /**
     * retourne le temps restant
     * @return
     */
    public List<Image> getTemps() {
        return temps;
    }
    
    /**
     * sous classe du gamestate : player
     * @author Samuel Dubuis (259157)
     * @author Yann Gabbud  (260036)
     */
    public static final class Player { 
        private PlayerID ID;
        private int vies;
        private SubCell position;
        private Image image;
        
        /**
         * constructeur d'un joueur
         * @param ID
         * @param vies
         * @param position
         * @param image
         */
        public Player(PlayerID ID, int vies, SubCell position, Image image){
            this.ID = ID;
            this.vies = vies;
            this.position = position;
            this.image = image;            
        }
        
        /**
         * retourne l'id du joueur
         * @return
         */
        public PlayerID getID() {
            return ID;
        }
        
        /**
         * retourne le nombre de vie du joueur
         * @return
         */
        public int getVies() {
            return vies;
        }
        /**
         * retourne la postion du joueur
         * @return
         */
        public SubCell getPosition() {
            return position;
        }
        /**
         * retourn l'image du joueur
         * @return
         */
        public Image getImage() {
            return image;
        }         
    }    
}