import processing.core.*;

public class Visualizer extends PApplet
{
    public int sizeX = 512;
    public int sizeY = 512;

    public static void main(String[] args) {
        System.out.println("moin");
        PApplet.main("Visualizer");
    }
    
    public void settings(){
        size(sizeX, sizeY);
    }

    public void setup(){
        fill(120,50,240);
        textSize (32);
        noStroke();
    }

    public void draw(){
        fill(mouseX%255, mouseY%255, mouseX-mouseY%255);
        ellipse(sizeX/2, sizeY/2, mouseX, mouseY);
        fill(0);
        text(mouseX + " : " + mouseY, sizeX/2, sizeY/2);
    }
}
