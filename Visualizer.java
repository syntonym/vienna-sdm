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
    
    public static void setInformation (
        String strategy, 
        String initialisation, 
        String n, 
        String k, 
        Point [] pointsBla
    ) {
        strategyName = strategy;
        initialisationName = initialisation;
        nString = n;
        kString = k;
        points = pointsBla;
        
        System.out.println(pointsBla.length);
        
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
/*        
        System.out.println ("Max X: " + max_x);
        System.out.println ("Max Y: " + max_y);
        System.out.println ("Min X: " + min_x);
        System.out.println ("Min Y: " + min_y);
*/        
        for (int i = 0; i < points.length; i++) {
	    points[i].values[0] += -min_x + 50;
	    points[i].values[1] += -min_y + 50;
            points[i].values[0] *= 1024/(2*(max_x - min_x));
            points[i].values[1] *= 768/(2*(max_y - min_y));
        }

        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i].values[0] + ":" + points[i].values[1]);
        }
    }
    
    public static void main (String[] args) {
        System.out.println("moin");
        PApplet.main(args);
    }
    
    public void settings(){
        size(sizeX, sizeY);
    }

    public void setup(){
        background(255);
        textSize (16);
        noStroke();
    }

    public void draw(){
        fill (0);

        for (int i = 0; i < points.length; i++) {
            if (points[i].category >= 0) {
                fill(
                    colors[points[i].category][0],
                    colors[points[i].category][1],
                    colors[points[i].category][2]
                );
            }
            
            ellipse(new Float(points[i].values[0]), new Float(points[i].values[1]), 3, 3);
        }
        
        fill(0);
        
        text(
            "Strategy: " + strategyName + ", Initialisation: " + 
                initialisationName + ", n=" + nString + ", k=" + kString, 
            10, 
            26
        );
    }
}
