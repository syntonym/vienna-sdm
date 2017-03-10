public class Point {
	public float[] values;
	public int category;

	/**
	 * represents a single point. 
	 *
	 * To create a new point you need to pass the dimensions so that the 
	 * values array can be initialized. values is public, so you can
	 * directly access and mutate it (point.values). The category is a
	 * single int, -1 means it is currently in no cluster, positive numbers
	 * indicate the id of the cluster the point belongs to.
	 *
	 * @param dimensions amount of dimensions the points lives in.
	 **/
	public Point(int dimensions) {
		this.values = new float[dimensions];
		this.category = -1;
	}
}
