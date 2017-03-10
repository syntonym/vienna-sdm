import java.lang.System;
import java.util.Random;

public class Main {

	public enum Strategy {
		LLOYD, MACQUEEN
	}

	public enum Initialisation {
		RANDOM_PARTITION,
		RANDOM_CLUSTER_CENTERS
	}

	public static void main(String[] args) {
		int dimensions = 2;
		Point[] points = generateData(4, 1000, dimensions);
		algoKMeans(points, Initialisation.RANDOM_PARTITION, Strategy.LLOYD);
		visualize(points, Initialisation.RANDOM_PARTITION, Strategy.LLOYD);
	}

	/**
	 * return generated point data (exercise part 1)
	 *
	 * @param clusters      number of clusters generated (K on the assignment sheet)
	 * @param points        total number of points (N on the assignment sheet)
	 * @param dimensions    number of dimensions in which a single points lives (D on the assignment sheet)
	 * @param deviation_max maximum of deviation. Should be chosen in such a way that clusters overlap.
	 * @param mean_max      maxiumum of mean. Should be chosen in such a way that clusters overlap.
	 **/
	public static Point[] generateData(int clusters, int points, int dimensions, double deviation_max, double mean_max) {
		Point[] r_points = new Point[points];
		Random r = new Random();
		int cluster_id;
		Point p;

		if (deviation_max == 0.0) {
			deviation_max = 33;
		}
		if (mean_max == 0.0) {
			mean_max = 100;
		}

		double[][] cluster = new double[clusters][2];
		double deviation;
		double mean;

		for (int i=0; i<cluster.length; i++) {

			deviation = r.nextDouble() * deviation_max;
			mean = r.nextDouble() * mean_max;
			cluster[i][0] = deviation;
			cluster[i][1] = mean;
		}

		for (int i=0; i<r_points.length; i++) {
			cluster_id = r.nextInt(clusters);
			p = new Point(dimensions);
			p.original_category = cluster_id;
			for (int d=0; d<dimensions; d++) {
				p.values[d] = r.nextGaussian() * cluster[cluster_id][0] + cluster[cluster_id][1];
			}
			r_points[i] = p;
		}

		return r_points;
	}

	public static Point[] generateData(int clusters, int points, int dimensions) {
		return generateData(clusters, points, dimensions, 0.0, 0.0);
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
	public static void algoKMeans(Point[] points, Initialisation initialisation, Strategy strategy) {
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
	 * If the backend supports some kind of title include the initialisation
	 * strategy and the update strategy in some kind.
	 *
	 * @param points         Array of points to visualise.
	 * @param initialisation strategy to initialisation k-means algo
	 * @param strategy       strategy to update k-means algo
	 **/
	public static void visualize(Point[] points, Initialisation initialisation, Strategy strategy) {
	}
}

