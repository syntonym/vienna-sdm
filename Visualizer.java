import processing.core.*;

public class Visualizer extends PApplet
{
    private final int sizeX = 1024;
    private final int sizeY = 768;
    
    private static String strategyName = new String();
    private static String initialisationName = new String();
    private static String nString = new String();
    private static String kString = new String();
    
    public Visualizer () {
    
    }
    
    public static void setInformation (String strategy, String initialisation, String n, String k) {
        strategyName = strategy;
        initialisationName = initialisation;
        nString = n;
        kString = k;
    }
    
    public static void main (String[] args) {
        System.out.println("moin");
        PApplet.main(args);
    }
    
    public void settings(){
        size(sizeX, sizeY);
    }

    public void setup(){
        fill(120,50,240);
        textSize (16);
        noStroke();
    }

    public void draw(){
        fill(mouseX%255, mouseY%255, mouseX-mouseY%255);
        ellipse(sizeX/2, sizeY/2, mouseX, mouseY);
        fill(0);
        text(
            "Strategy: " + strategyName + ", Initialisation: " + 
                initialisationName + ", n=" + nString + ", k=" + kString, 
            10, 
            50
        );
    }
}
