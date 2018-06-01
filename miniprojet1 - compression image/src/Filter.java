
/**
 * @author Samuel Dubuis & Yann Gabbud
 */
public final class Filter {

    /**
     * Get a pixel without accessing out of bounds
     * @param gray a HxW float array
     * @param row Y coordinate
     * @param col X coordinate
     * @return nearest valid pixel color
     */
    public static float at(float[][] gray, int row, int col) {
    	if (row>=gray.length) {
			row=row-1;
		}
    	if (row<=0) {
			row=0;
		}
    	if (col<=0) {
			col=0;
		}
    	if (col>=gray[0].length) {
			col = col-1;
		}
    	return gray[row][col];
    }
    /**
     * Convolve a single-channel image with specified kernel.
     * @param gray a HxW float array
     * @param kernel a MxN float array, with M and N odd
     * @return a HxW float array
     */
    public static float[][] filter(float[][] gray, float[][] kernel) {
    	
        float[][] mod = new float[gray.length][gray[0].length];
    	
    	for (int i = 0; i < mod.length; i++) {
			for (int j = 0; j < mod[i].length; j++) {
						mod[i][j] = at(gray, i-1,j-1)*kernel[0][0]+at(gray, i-1,j)*kernel[0][1]+
								at(gray,i-1,j+1)*kernel[0][2]+at(gray,i,j-1)*kernel[1][0]+at(gray, i, j)*kernel[1][1]+
								at(gray, i, j+1)*kernel[1][2]+at(gray, i+1, j-1)*kernel[2][0]+at(gray, i+1, j)*kernel[2][1]+
								at(gray, i+1, j+1)*kernel[2][2];
					}
				}
			
    	
        return mod;
    }

    /**
     * Smooth a single-channel image
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] smooth(float[][] gray) {
        float kernel[][] = {{0.1f,0.1f, 0.1f},{0.1f, 0.2f, 0.1f}, {0.1f, 0.1f, 0.1f}};
        
        float[][] imagesmooth = new float[gray.length][gray[0].length];
        imagesmooth = filter(gray, kernel);
        return imagesmooth;
    
    }

    /**
     * Compute horizontal Sobel filter
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] sobelX(float[][] gray){
        float kernel[][] = {{-1f,0f, 1f},{-2f, 0f, 2f}, {-1f, 0f, 1f}};
        
        float[][] imagesobelx = new float[gray.length][gray[0].length];
        imagesobelx = filter(gray, kernel);
        return imagesobelx;
    }

    /**
     * Compute vertical Sobel filter
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] sobelY(float[][] gray) {
        float kernel[][] = {{-1f,-2f, -1f},{0f, 0f, 0f}, {1f, 2f, 1f}};

        
        float[][] imagesobely = new float[gray.length][gray[0].length];
        imagesobely = filter(gray, kernel);
        return imagesobely;
    }

    /**
     * Compute the magnitude of combined Sobel filters
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] sobel(float[][] gray) {
    	float[][] temp1 = sobelX(gray);
    	float[][] temp2 = sobelY(gray);

        float[][] sobel = new float[gray.length][gray[0].length];
        
        for (int i = 0; i < gray.length; i++) {
			for (int j = 0; j < gray[0].length; j++) {
				sobel[i][j]= (float) Math.sqrt(temp1[i][j]*temp1[i][j]+temp2[i][j]*temp2[i][j]);
			}
		}
        
        return sobel;
    }

}
