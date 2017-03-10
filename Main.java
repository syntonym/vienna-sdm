import java.lang.System;

public class Main {

	public static void main(String[] args) {
		int dimensions = 2;
		Point[] points = generateData(5, 200, dimensions);
		algoKMeans(points);
		visualize(points);
	}

	/**
	 * return generated point data (exercise part 1)
	 *
	 * @param clusters   number of clusters generated (K on the assignment sheet)
	 * @param points     total number of points (N on the assignment sheet)
	 * @param dimensions number of dimensions in which a single points lives (D on the assignment sheet)
	 **/
	public static Point[] generateData(int clusters, int points, int dimensions) {
		return new Point[points];
	}

	/**
	 * Applies the k-means algorithm on the input data points.
	 * 
	 * The cluster should be indicated by the point.category attribute.
	 * A -1 represents "not-clustered". Points with the same integer in
	 * the point.category attribute should belong to the same cluster.
	 *
	 * Internally you can ofcourse use whatever datastructure you like.
	 *
	 * Return nothing (mutate the array).
	 * 
	 * @param points Array of points to cluster
	 *
	 **/
	public static void algoKMeans(Point[] points) {
	}

	/**
	 * Visualize the clusters.
	 *
	 * Show all points. Each cluster should have a seperate colour.
	 * If you want to use a non-java visualisation solution output the
	 * data you need to a file and in a seperate program do your
	 * visualisation.
	 *
	 * The visualisiation only needs to be 2D. You can use whatever
	 * projection you like.
	 *
	 * @param points Array of points to visualise.
	 **/
	public static void visualize(Point[] points) {
	}
}

