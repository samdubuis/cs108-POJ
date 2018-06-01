/**
 * classe créant un composant permettant d'afficher le jeu à l'écran
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.client.GameState.Player;

public final class XBlastComponent extends JComponent {

    private GameState gs = null;
    private PlayerID id = null;
    
    /**
     * méthode definissant la taille par défaut du composant
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(960, 688);
    }
    
    /**
     * méthode qui positionne les images du plateau dans le bon ordre dans le composant
     */
    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        if (!Objects.isNull(gs)) {
            int x = gs.getPlateau().get(0).getWidth(null);
            int y = gs.getPlateau().get(0).getHeight(null);
            for (int i = 0; i < gs.getPlateau().size(); i++) {
                g.drawImage(gs.getPlateau().get(i), (i%Cell.COLUMNS)*x, (i/Cell.COLUMNS)*y, null);
                g.drawImage(gs.getBombeEtExplosion().get(i), (i%Cell.COLUMNS)*x, (i/Cell.COLUMNS)*y, null);
            }

            List<Player> players = gs.getPlayers();

            Comparator<GameState.Player> c1 = (s1, s2) -> Integer.compare(s1.getPosition().y(), s2.getPosition().y());
            Comparator<GameState.Player> c2 = (s1, s2) -> Integer.compare(Math.floorMod(s2.getID().ordinal()*7+id.ordinal(), 4), Math.floorMod(s2.getID().ordinal()*7+id.ordinal(), 4));
            Comparator<GameState.Player> c3 = c1.thenComparing(c2);
            players.sort(c3);

            for (Player player : players) {
                g.drawImage(player.getImage(), 4*player.getPosition().x()-24, 3*player.getPosition().y()-52, null);
            }

            int x2 = gs.getScores().get(0).getWidth(null);
            int y2 = gs.getScores().get(0).getHeight(null);
            for (int i = 0; i < gs.getScores().size(); i++) {
                g.drawImage(gs.getScores().get(i), (i%20)*x2, 13*y, null);
            }
            
            int x3 = gs.getTemps().get(0).getWidth(null);
            for (int i = 0; i < gs.getTemps().size(); i++) {
                g.drawImage(gs.getTemps().get(i), (i%60)*x3, 13*y+y2, null);
            }

            Font font = new Font("Arial", Font.BOLD, 25);
            g.setColor(Color.WHITE);
            g.setFont(font);
            int yEcriture = 659;
            g.drawString(Integer.toString(gs.getPlayers().get(0).getVies()), 96, yEcriture);
            g.drawString(Integer.toString(gs.getPlayers().get(1).getVies()), 240, yEcriture);
            g.drawString(Integer.toString(gs.getPlayers().get(2).getVies()), 768, yEcriture);
            g.drawString(Integer.toString(gs.getPlayers().get(3).getVies()), 912, yEcriture);

        }
        
    }
    /**
     * methode permettant d'actualiser le composant avec le nouvelle etat du jeu
     */
    public void setGameState(GameState gs, PlayerID id){
        this.gs = gs;
        this.id = id;
        repaint(); 
    }




}