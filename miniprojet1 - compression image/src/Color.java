
/**
 * @author Samuel Dubuis & Yann Gabbud
 */
public final class Color {

    /**
     * Returns red component from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(float, float, float)
     */
    public static float getRed(int rgb) {
        rgb = rgb >> 16;
        rgb = rgb & 0xff;
        float red = rgb/255.0f;
        return red;
    }

    /**
     * Returns green component from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(float, float, float)
     */
    public static float getGreen(int rgb) {
        rgb = rgb >> 8;
        rgb = rgb & 0xff;
        float green = rgb/255.0f;
        return green;
    }

    /**
     * Returns blue component from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(float, float, float)
     */
    public static float getBlue(int rgb) {
        rgb = rgb & 0xff;
        float blue = rgb/255.0f;
        return blue;
    }
    
    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(float)
     */
    public static float getGray(int rgb) {
        float red = getRed(rgb);
        float green = getGreen(rgb);
        float blue = getBlue(rgb);
        float gray = (red + green + blue)/3;
        return gray;
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red a float between 0.0 and 1.0
     * @param green a float between 0.0 and 1.0
     * @param blue a float between 0.0 and 1.0
     * @return 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(float red, float green, float blue) {
    	if (red<0)
    		red =0;
    	if (red>1)
    		red = 1;
    	if (green<0)
    		green=0;
    	if (green>1)
    		green=1;
    	if (blue<0)
    		blue =0;
    	if (blue>1)
    		blue=1;
    	
    	
    	red = red*255;
        int redtemp = (int)red;
        green = green*255;
        int greentemp = (int)green;
        blue = blue*255;
        int bluetemp = (int)blue;
        
        redtemp = redtemp << 16;
        greentemp = greentemp << 8;
        
        int rgb = redtemp + greentemp + bluetemp;
        return rgb;
    }
    
    /**
     * Returns packed RGB components from given grayscale value.
	 * @param gray a float between 0.0 and 1.0
     * @return 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(float gray) {
    	
    	if (gray>1)
    		gray=1;
    	if (gray<0)
    		gray=0;
    	
        gray = gray*255;
        int red = (int)gray;
        int green = (int)gray;
        int blue = (int)gray;
        
        red = red <<16;
        green = green <<8;
        
        int rgb = red + green + blue;
        return rgb;
    }

    /**
     * Converts packed RGB image to grayscale float image.
     * @param image a HxW int array
     * @return a HxW float array
     * @see #toRGB
     * @see #getGray
     */
    public static float[][] toGray(int[][] image) {
        float[][] imagetemp = new float[image.length][image[0].length];
        for (int i=0; i<image.length; ++i){
        	for (int j=0; j<image[i].length; ++j){
        		imagetemp[i][j] = getGray(image[i][j]);
        	}
        }
        return imagetemp;
    }

    /**
     * Converts grayscale float image to packed RGB image.
     * @param channels a HxW float array
     * @return a HxW int array
     * @see #toGray
     * @see #getRGB(float)
     */
    public static int[][] toRGB(float[][] gray) {
        int[][] graytemp = new int[gray.length][gray[0].length];
        for (int i=0; i<gray.length; ++i){
        	for (int j=0; j<gray[i].length; ++j){
        		graytemp[i][j] = getRGB(gray[i][j]);
        	}
        }
        return graytemp;
    }

}
