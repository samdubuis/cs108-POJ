/**
 * classe servant à retourner les images nécessaire à l'affichage du jeu
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

public final class ImageCollection {

    private Collection<Image> collection;
    public final static ImageCollection s = new ImageCollection("score");
    public final static ImageCollection ex = new ImageCollection("explosion");
    public final static ImageCollection b = new ImageCollection("block");                
    public final static ImageCollection p = new ImageCollection("player");
    
    /**
     * methode permettant de créer une collection d'image
     * @param str
     */
    public ImageCollection(String str){
        if (str.equals("block") || str.equals("explosion") || str.equals("player") || str.equals("score")) {
            String dirName = str;
            try {
                File dir = new File(ImageCollection.class
                        .getClassLoader()
                        .getResource(dirName)
                        .toURI());

                File[] temp = dir.listFiles();
                Collection<Image> rendu = new ArrayList<>();

                List<Integer> numero1 = new ArrayList<>();
                int max = 0 ;

                for (File file : temp) {
                    String s = file.getName().substring(0, 3);
                    int num = Integer.parseInt(s);
                    numero1.add(num);
                    if (num>max) {
                        max = num;
                    }
                }

                for (int i = 0; i <= max; i++) {
                    if (numero1.contains(i)) {
                        for (File file : temp) {
                            if (Integer.parseInt(file.getName().substring(0, 3))==i) {
                                try {
                                    rendu.add(ImageIO.read(file));
                                } catch (IOException e) {
                                    throw new Error(e);
                                }
                            }
                        }
                    }
                    else {
                        rendu.add(null);
                    }
                }
                collection = rendu;
            } catch (URISyntaxException e) {
                throw new Error(e);
            }
        }
    }
    
    /**
     * methode qui retourne l'image d'une collection en lui donnant un index. Lance une expetion en cas d'erreur
     * @param index
     * @return
     */
    public Image image(int index){
        if (index<0 || index>collection.size()-1) {
            throw new NoSuchElementException();
        }
        int i = 0;
        for (Image image : collection) {
            if (index== i) {
                return image;
            }
            else {
                i++;
            }
        }
        return null;


    }
    
    /**
     * methode qui retourne l'image d'une collection en lui donnant un index. Retourne null en cas d'erreur
     * @param index
     * @return
     */
    public Image imageOrNull(int index){
        if (index<0 || index>collection.size()-1) {
            return null;
        }
        int i = 0;
        for (Image image : collection) {
            if (index%7 == i%7) {
                return image;
            }
            else {
                i++;
            }
        }
        return null;
    }







}
