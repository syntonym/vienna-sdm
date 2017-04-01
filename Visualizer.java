import processing.core.*;
import java.util.*;

public class Visualizer extends PApplet
{
	private static final int sizeX = 640;
	private static final int sizeY = 640;
    
	private static String strategyName = new String();
	private static String initialisationName = new String();
	private static String nString = new String();
	private static String kString = new String();
    
	private static Point [] points = new Point[0];
	private static ArrayList<ArrayList<Integer>> points_it = new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<Point>> centroid_it = new ArrayList<ArrayList<Point>>();
	private static int it_counter = -1;
    
    private int[][] colors =
        {
            {255, 0, 0},
            {0, 255, 0},
            {0, 0, 255},
            {128, 128, 0},
            {128, 0, 128},
            {0, 128, 128},
            {128, 64, 64},
            {64, 128, 64},
            {64, 64, 128},
        };
    
	/**
	 * used to update the k-parameter in the visualization, so we can display the data points
	 * before the algorithm has started calculating
	 *
	 * @param k     parameter k, number of centroids/clusters which is later parsed to integer
	 **/
	public static void updateK (String k) {
		kString = k;
	}
    
    /**
     * used to set all the necessary data for visualization
     * 
     * @param strategy          name of the used strategy
     * @param initialisation    name of the used initialisation strategy
     * @param n                 amount of data points
     * @param k                 amount of centroids/clusters
     * @param pointsBla         data points
     * @param points_itBla      contains the nearest centroid of every point at each iteration
     * @param centroid_itBla    contains the position of every centroid at each iteration
     **/
    public static void setInformation (
		String strategy, 
		String initialisation, 
		String n, 
		String k, 
		Point [] pointsBla,
		ArrayList<ArrayList<Integer>> points_itBla,
		ArrayList<ArrayList<Point>> centroid_itBla
    ) {
		strategyName = strategy;
		initialisationName = initialisation;
		nString = n;
		kString = k;
		points = pointsBla;
		centroid_it = centroid_itBla;
		points_it = points_itBla;
        
        float max_x = 0;
        float min_x = 0;
        float max_y = 0;
        float min_y = 0;

        for (int i = 0; i < points.length; i++) {
            if (points[i].values[0] > max_x) max_x = new Float(points[i].values[0]);
            if (points[i].values[1] > max_y) max_y = new Float(points[i].values[1]);
            if (points[i].values[0] < min_x) min_x = new Float(points[i].values[0]);
            if (points[i].values[1] < min_y) min_y = new Float(points[i].values[1]);
        }
      
        for (int i = 0; i < points.length; i++) {
	    	points[i].values[0] += -min_x + 50;
	    	points[i].values[1] += -min_y + 50;
        	points[i].values[0] *= sizeX/(2*(max_x - min_x));
		    points[i].values[1] *= sizeY/(2*(max_y - min_y));
        }

		for (int j = 0; j < centroid_it.size(); j++){		
			for (int i = 0; i < centroid_it.get(j).size(); i++) {
					centroid_it.get(j).get(i).values[0] += -min_x + 50;
					centroid_it.get(j).get(i).values[1] += -min_y + 50;
					centroid_it.get(j).get(i).values[0] *= sizeX/(2*(max_x - min_x));
            		centroid_it.get(j).get(i).values[1] *= sizeY/(2*(max_y - min_y));
        	}
		}

    }
    
    /**
     * main function like the framework defines it to be to work properly.
     **/
    public static void main (String[] args) {
        PApplet.main(args);
    }
    
    /**
     * sets up the window size for the visualization
     **/
    public void settings(){
        size(sizeX, sizeY);
    }

    /**
     * sets up text size of text used during the visualization
     **/
    public void setup(){
        textSize (12);
    }

    /**
     * increases the iteration counter it_counter everytime somebody hits ENTER in the visualization window
     **/
	public void keyReleased () {
		if (key == ENTER && points_it.size() != 0) it_counter = (++it_counter) % points_it.size();
	}

    /**
     * function which is called in an endless loop by the processing framework which does the real visualization job
     **/
    public void draw(){
        background(255);
        fill (0);
        
        noStroke();
        for (int i = 0; i < points.length; i++) {
			if (it_counter == -1) {
/*		        if (points[i].category >= 0 && points[i].category < 8) {
		            fill(
		                colors[points[i].category][0],
		                colors[points[i].category][1],
		                colors[points[i].category][2]
		            );
		        }
*/			} else {
		        if (points_it.get(it_counter).get(i) >= 0 && points_it.get(it_counter).get(i) < 8) {
		            fill(
		                colors[points_it.get(it_counter).get(i)][0],
		                colors[points_it.get(it_counter).get(i)][1],
		                colors[points_it.get(it_counter).get(i)][2]
		            );
		        }
			}
            
            ellipse(new Float(points[i].values[0]), new Float(points[i].values[1]), 5, 5);
        }
        
		stroke(0);
        for (int i = 0; i < Integer.parseInt(kString); i++) {
			if (it_counter > -1) {
				fill(
	                colors[centroid_it.get(it_counter).get(i).category][0],
	                colors[centroid_it.get(it_counter).get(i).category][1],
	                colors[centroid_it.get(it_counter).get(i).category][2]
				);
	            rect(
					new Float(centroid_it.get(it_counter).get(i).values[0]), 
					new Float(centroid_it.get(it_counter).get(i).values[1]), 
					8, 
					8
				);
			}
		}


        fill(0);
        
		if (it_counter == -1) {
		    text(
		        "Strategy: " + strategyName + "\nInitialisation: " + 
		            initialisationName + "\nn=" + nString + ", k=" + 
					kString + ", step=" + (it_counter+1) + " HIT ENTER TO ITERATE", 
		        6, 
		        20
		    );
		} else if (it_counter == points_it.size() - 1) {
		    text(
		        "Strategy: " + strategyName + "\nInitialisation: " + 
		            initialisationName + "\nn=" + nString + ", k=" + 
					kString + ", step=" + (it_counter+1) + " FINAL STEP REACHED", 
		        6, 
		        20
		    );
		} else {
		    text(
		        "Strategy: " + strategyName + "\nInitialisation: " + 
		            initialisationName + "\nn=" + nString + ", k=" + 
					kString + ", step=" + (it_counter+1), 
		        6, 
		        20
		    );
		}
    }
}
