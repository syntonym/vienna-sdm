import processing.core.*;
import java.util.*;

public class Visualizer extends PApplet
{
	private final int sizeX = 1024;
	private final int sizeY = 768;
    
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
    
    public Visualizer () {
    
    }

	public static void updateK (String k) {
		kString = k;
	}
    
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
        	points[i].values[0] *= 1024/(2*(max_x - min_x));
            points[i].values[1] *= 768/(2*(max_y - min_y));
        }
/*
		for (int j = 0; j < points_it.size(); j++){	
			for (int i = 0; i < points_it.get(j).length; i++) {
				System.out.println(j+": Kategorie: " + points_it.get(j)[i].category);
	    		points_it.get(j)[i].values[0] += -min_x + 50;
	    		points_it.get(j)[i].values[1] += -min_y + 50;
        		points_it.get(j)[i].values[0] *= 1024/(2*(max_x - min_x));
            	points_it.get(j)[i].values[1] *= 768/(2*(max_y - min_y));
        	}
		}
*/
		for (int j = 0; j < centroid_it.size(); j++){		
			for (int i = 0; i < centroid_it.get(j).size(); i++) {
	    		centroid_it.get(j).get(i).values[0] += -min_x + 50;
	    		centroid_it.get(j).get(i).values[1] += -min_y + 50;
        		centroid_it.get(j).get(i).values[0] *= 1024/(2*(max_x - min_x));
            	centroid_it.get(j).get(i).values[1] *= 768/(2*(max_y - min_y));
        	}
		}

    }
    
    public static void main (String[] args) {
        PApplet.main(args);
    }
    
    public void settings(){
        size(sizeX, sizeY);
    }

    public void setup(){
        textSize (16);
    }

	public void keyReleased () {
		if (key == ENTER) it_counter = (++it_counter) % points_it.size();
	}

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
            
            ellipse(new Float(points[i].values[0]), new Float(points[i].values[1]), 3, 3);
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
					3, 
					3
				);
			}
		}


        fill(0);
        
		if (it_counter == -1) {
		    text(
		        "Strategy: " + strategyName + ", Initialisation: " + 
		            initialisationName + ", n=" + nString + ", k=" + 
					kString + ", step=" + it_counter + " HIT ENTER TO ITERATE", 
		        10, 
		        26
		    );
		} else if (it_counter == points_it.size() - 1) {
		    text(
		        "Strategy: " + strategyName + ", Initialisation: " + 
		            initialisationName + ", n=" + nString + ", k=" + 
					kString + ", step=" + it_counter + " FINAL STEP REACHED", 
		        10, 
		        26
		    );
		} else {
		    text(
		        "Strategy: " + strategyName + ", Initialisation: " + 
		            initialisationName + ", n=" + nString + ", k=" + 
					kString + ", step=" + it_counter, 
		        10, 
		        26
		    );
		}
    }
}
