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
		int n = 1000;
		int k = 4;
		Point[] points = generateData(k, n, dimensions);
		algoKMeans(points, Initialisation.RANDOM_PARTITION, Strategy.MACQUEEN, k, n);
		//algoKMeans(points, Initialisation.RANDOM_PARTITION, Strategy.LLOYD, k, n);
		visualize(points, Initialisation.RANDOM_PARTITION, Strategy.LLOYD, n, k);
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
	public static void algoKMeans(Point[] points, Initialisation initialisation, Strategy strategy, int k, int n) {

		if (strategy == Strategy.LLOYD && initialisation == Initialisation.RANDOM_CLUSTER_CENTERS) {

			Point[] centroids = new Point[k];
			for (int z = 0; z<k; z++) centroids[z] = new Point(2);
			int randomIndex;
			int i = 0;

			//find k random start centroids
			while (i<k) {
				randomIndex = (int) ((Math.random()*n));

				if (points[randomIndex].category == -1) {
					centroids[i].values[0] = points[randomIndex].values[0];
					centroids[i].values[1] = points[randomIndex].values[1];
					centroids[i].category = i;
					points[randomIndex].category = i++;
				}

			}

			Boolean change = true;
			int count = 0;

			//iterate until there is no change in category
			while (change) {

				change = false;

				//Zuordnung der Datenpunkte
				for (int j = 0; j<n; j++) {

					double distance = Double.MAX_VALUE;
					double distance_old;
					double[] vector = new double[2];
					points[j].original_category = points[j].category;

					for (int l = 0; l < k; l++) {

						distance_old = distance;
						vector[0] = centroids[l].values[0] - points[j].values[0];
						vector[1] = centroids[l].values[1] - points[j].values[1];

						//distance between point and centroid
						distance = Math.sqrt(Math.pow(vector[0],2) + Math.pow(vector[1],2));
			
						if (distance < distance_old) {
							points[j].category = centroids[l].category;
							distance_old = distance;
						}
					}

					if (points[j].category != points[j].original_category) {
							change = true;
					}
				}


				//Neu Berechnung der Centroide

				for (int z=0; z<2; z++) { //2 <- dimensions
					for (int y = 0; y<k; y++) {
						double mean = 0;
						double elementCount = 0;
	
						for (int j = 0; j<n; j++) {
							if (points[j].category == y) {
								mean += points[j].values[z];
								elementCount++;
							}
						}
						centroids[y].values[z] = mean / elementCount;
	
					}
	
				}

				count++;
				System.out.println("Iterate " + count + "\n");	
	
			}

		} else {
			if (strategy == Strategy.LLOYD && initialisation == Initialisation.RANDOM_PARTITION) {
				Point[] centroids = new Point[k];
				for (int z = 0; z<k; z++) {
					centroids[z] = new Point(2);
					centroids[z].category = z;
				}

				int randomIndex;
				int i = 0;

				//random partition to clusters
				for (int a=0; a<n; a++) {
					points[a].category = (int) ((Math.random()*k));
				}
				
				Boolean change = true;

				//iterate until there is no change in category
				while (change) {

					change = false;

					//Neu Berechnung der Centroide

					for (int z=0; z<2; z++) { //2 <- dimensions
						for (int y = 0; y<k; y++) {
							double mean = 0;
							double elementCount = 0;
	
							for (int j = 0; j<n; j++) {
								if (points[j].category == y) {
									mean += points[j].values[z];
									elementCount++;
								}
							}	
							centroids[y].values[z] = mean / elementCount;
	
						}
	
					}

					//Zuordnung der Datenpunkte
					for (int j = 0; j<n; j++) {

						double distance = Double.MAX_VALUE;
						double distance_old;
						double[] vector = new double[2];
						points[j].original_category = points[j].category;

						for (int l = 0; l < k; l++) {

							distance_old = distance;
							vector[0] = centroids[l].values[0] - points[j].values[0];
							vector[1] = centroids[l].values[1] - points[j].values[1];

							//distance between point and centroid
							distance = Math.sqrt(Math.pow(vector[0],2) + Math.pow(vector[1],2));
			
							if (distance < distance_old) {
								points[j].category = centroids[l].category;
								distance_old = distance;
							}
						}
	
						if (points[j].category != points[j].original_category) {
								change = true;
						}
					}
	
				}
			} else {
				if (strategy == Strategy.MACQUEEN && initialisation == Initialisation.RANDOM_CLUSTER_CENTERS) {
					Point[] centroids = new Point[k];
					for (int z = 0; z<k; z++) centroids[z] = new Point(2);
					int randomIndex;
					int i = 0;

					//find k random start centroids
					while (i<k) {
						randomIndex = (int) ((Math.random()*n));

						if (points[randomIndex].category == -1) {
							centroids[i].values[0] = points[randomIndex].values[0];
							centroids[i].values[1] = points[randomIndex].values[1];
							centroids[i].category = i;
							points[randomIndex].category = i++;
						}

					}

					Boolean change = true;
					int count = 0;

					//iterate until there is no change in category
					while (change) {

						change = false;

						//Zuordnung der Datenpunkte
						for (int j = 0; j<n; j++) {

							double distance = Double.MAX_VALUE;
							double distance_old;
							double[] vector = new double[2];
							points[j].original_category = points[j].category;

							for (int l = 0; l < k; l++) {

								distance_old = distance;
								vector[0] = centroids[l].values[0] - points[j].values[0];
								vector[1] = centroids[l].values[1] - points[j].values[1];

								//distance between point and centroid
								distance = Math.sqrt(Math.pow(vector[0],2) + Math.pow(vector[1],2));
			
								if (distance < distance_old) {
									points[j].category = centroids[l].category;
									distance_old = distance;
								}
							}

							if (points[j].category != points[j].original_category) {
								change = true;
				
								//if there's a change -> calculate centroids

								for (int z=0; z<2; z++) { //2 <- dimensions
									for (int y = 0; y<k; y++) {
										double mean = 0;
										double elementCount = 0;
	
										for (int x = 0; x<n; x++) {
											if (points[x].category == y) {
												mean += points[x].values[z];
												elementCount++;
											}
										}
										centroids[y].values[z] = mean / elementCount;
	
									}
	
								}

							}
						}

						count++;
						System.out.println("Iterate " + count + "\n");	
					}
				} else {
					if (strategy == Strategy.MACQUEEN && initialisation == Initialisation.RANDOM_PARTITION) {
						
						Point[] centroids = new Point[k];
						for (int z = 0; z<k; z++) {
							centroids[z] = new Point(2);
							centroids[z].category = z;
						}

						int randomIndex;
						int i = 0;

						//random partition to clusters
						for (int a=0; a<n; a++) {
							points[a].category = (int) ((Math.random()*k));
						}

						//calculate centroids

						for (int z=0; z<2; z++) { //2 <- dimensions
							for (int y = 0; y<k; y++) {
								double mean = 0;
								double elementCount = 0;
	
								for (int j = 0; j<n; j++) {
									if (points[j].category == y) {
										mean += points[j].values[z];
										elementCount++;
									}
								}	
								centroids[y].values[z] = mean / elementCount;
	
							}
	
						}
				
						Boolean change = true;
						int count = 0;
	
						//iterate until there is no change in category
						while (change) {

							change = false;

							//Zuordnung der Datenpunkte
							for (int j = 0; j<n; j++) {

								double distance = Double.MAX_VALUE;
								double distance_old;
								double[] vector = new double[2];
								points[j].original_category = points[j].category;

								for (int l = 0; l < k; l++) {

									distance_old = distance;
									vector[0] = centroids[l].values[0] - points[j].values[0];
									vector[1] = centroids[l].values[1] - points[j].values[1];

									//distance between point and centroid
									distance = Math.sqrt(Math.pow(vector[0],2) + Math.pow(vector[1],2));
			
									if (distance < distance_old) {
										points[j].category = centroids[l].category;
										distance_old = distance;
									}
								}
	
								if (points[j].category != points[j].original_category) {
										change = true;

										//if there's a change -> calculate centroids

										for (int z=0; z<2; z++) { //2 <- dimensions
											for (int y = 0; y<k; y++) {
												double mean = 0;
												double elementCount = 0;
	
												for (int x = 0; x<n; x++) {
													if (points[x].category == y) {
														mean += points[x].values[z];
														elementCount++;
													}
												}
												centroids[y].values[z] = mean / elementCount;
	
											}
	
										}
								}
							}
			
							count++;
							System.out.println("Iterate " + count + "\n");	
	
						}
					}	
				}

			}
		}

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
	public static void visualize(Point[] points, Initialisation initialisation, Strategy strategy, int n, int k) {
	    System.out.println("line missing");
	    Visualizer visualizer = new Visualizer();
	    
	    Visualizer.setInformation(
	        strategy.name(), 
	        initialisation.name(), 
	        new Integer(n).toString(), 
	        new Integer(k).toString(),
	        points
        );
        
        
	    Visualizer.main("Visualizer");
	    
	    
	}
}

