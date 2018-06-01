/**
 * @author Samuel Dubuis & Yann Gabbud
 */

public final class Seam {

    /**
     * Compute shortest path between {@code from} and {@code to}
     * @param successors adjacency list for all vertices
     * @param costs weight for all vertices
     * @param from first vertex
     * @param to last vertex
     * @return a sequence of vertices, or {@code null} if no path exists
     */
    public static int[] path(int[][] successors, float[] costs, int from, int to) {
    	int[] bestPredecessor = new int[from];
    	float[] distance = new float[successors.length];
    	for (int i = 0; i < from; i++) {
    		distance[i]=Float.POSITIVE_INFINITY;
    	}	
    	distance[from]=costs[from];
    	boolean modified = true;
    	do {
    		modified = false;
    		for (int i = 0; i < successors.length; i++) {
    			for (int j = 0; j < successors[i].length; j++) {
    				if (distance[j]>(distance[i]+costs[j])) {
    					distance[j]=distance[i]+costs[j];
    					bestPredecessor[j]=successors[i][j];
    					modified = true;
    				}
    			}
    		}
    	} while (modified);
    	return bestPredecessor;
    }
    
    
    /**
     * Find best seam
     * @param energy weight for all pixels
     * @return a sequence of x-coordinates (the y-coordinate is the index)
     */
    public static int[] find(float[][] energy) {
        int[][] successors = new int[energy.length*energy[0].length + 2][];
        float [] costs = new float[energy.length*energy[0].length + 2];
        
        successors[energy.length*energy[0].length]=new int[energy[0].length];
                
        for (int i = 0; i < energy[0].length; i++) {
            successors[energy.length*energy[0].length][i]= i;					            
		}
        costs[energy.length*energy[0].length]=0;
        
        successors[energy.length*energy[0].length + 1]=new int[0];
        costs[energy.length*energy[0].length + 1]=0;
        
        for (int i = 0; i < energy.length; i++) {
			for (int j = 0; j < energy[i].length; j++) {
				if (i==energy.length-1){
					successors[getStateID(i, j, energy.length)]=new int[]{energy.length*energy[0].length + 1};
				}
				if (j==0){
					successors[getStateID(i, j, energy.length)]=new int[]{getStateID(i+1, j, energy.length),
							getStateID(i+1, j+1, energy.length)};
				}
				else if(j==energy[i].length-1){
					successors[getStateID(i, j, energy.length)]=new int[]{getStateID(i+1, j-1, energy.length),
							getStateID(i+1, j, energy.length)};
				}
				else{
				successors[getStateID(i, j, energy.length)]=new int[]{getStateID(i+1, j-1, energy.length),
						getStateID(i+1, j, energy.length),
						getStateID(i+1, j+1, energy.length)};
				}				
				
			}
		}
        
        for (int i = 0; i < energy.length; i++) {
			for (int j = 0; j < energy[0].length; j++) {
				costs[getStateID(i, j, energy.length)]=energy[i][j];
			}
		}
        
        int[] indices = path(successors, costs, energy.length*energy[0].length, energy.length*energy[0].length + 1);
        int[] result = new int[energy.length];
        for (int i = 0; i < result.length; i++) {
			result[i]=getCol(indices[i], energy[0].length, i);
		}
        return result;
    }

    
    /**
     * methode pour trouver le stateID
     * @param row of image, column of image and maxColumn of image
     * @return stateID, numero du sommet
     */
    public static int getStateID (int row, int col, int maxcol){
    	int stateID = row*maxcol + col; //maxcol = image.length
    	return stateID ;
    }
    
    /**
     * methode pour trouver la row 
     * @param stateID et maxcol
     * @return row du stateID
     */
    public static int getCol(int stateID, int maxcol, int row){
    	int col = stateID - (maxcol*row);
    	return col;
    }
    
    
    
    
    /**
     * Draw a seam on an image
     * @param image original image
     * @param seam a seam on this image
     * @return a new image with the seam in blue
     */
    public static int[][] merge(int[][] image, int[] seam) {
        // Copy image
        int width = image[0].length;
        int height = image.length;
        int[][] copy = new int[height][width];
        for (int row = 0; row < height; ++row)
            for (int col = 0; col < width; ++col)
                copy[row][col] = image[row][col];

        // Paint seam in blue
        for (int row = 0; row < height; ++row)
            copy[row][seam[row]] = 0x0000ff;

        return copy;
    }

    /**
     * Remove specified seam
     * @param image original image
     * @param seam a seam on this image
     * @return the new image (width is decreased by 1)
     */
    public static int[][] shrink(int[][] image, int[] seam) {
        int width = image[0].length;
        int height = image.length;
        int[][] result = new int[height][width - 1];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < seam[row]; ++col)
                result[row][col] = image[row][col];
            for (int col = seam[row] + 1; col < width; ++col)
                result[row][col - 1] = image[row][col];
        }
        return result;
    }

}
