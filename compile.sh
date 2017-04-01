javac -cp core.jar:. Main.java
jar cvfm kmeans.jar manifest.mf Main\$Initialisation.class Main\$Strategy.class Main.class Point.class Visualizer.class core.jar
