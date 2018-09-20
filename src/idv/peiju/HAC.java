package idv.peiju;

import java.util.ArrayList;
import java.util.Collections;

public class HAC {

    public enum ClusterDistance {
        NEAREST_POINT, FAREST_POINT, AVERAGE, CENTER_POINT
    }

    private int k;
    private ArrayList<Point> listOfPoints;
    private ClusterDistance clusterDistance;
    private ArrayList<Point> listOfOutliers = new ArrayList<>();
    private ArrayList<Cluster> listOfClusters = new ArrayList<>();

    public HAC(ArrayList<Point> points, int k, ClusterDistance clusterDistance) {
        this.listOfPoints = points;
        this.k = k;
        this.clusterDistance = clusterDistance;

        if (clusterDistance == null) {
            this.clusterDistance = ClusterDistance.CENTER_POINT;
        }
    }

    public ArrayList<Point> removeOutlier(double p, double d) {
        for (int i = 0; i < listOfPoints.size(); i++) {
            int counter = 0;

            for (int j = 0; j < listOfPoints.size(); j++) {

                // Ignore the same points
                if (j == i) {
                    continue;
                }

                //Get points
                Point point1 = listOfPoints.get(i);
                Point point2 = listOfPoints.get(j);

                // Count the distance between two points
                double distanceOfPoint = distance(point1, point2);

                if (distanceOfPoint > d) {
                    counter++;
                }
            }

            //how many points of distance are greater than d
            double fraction = (double) counter / listOfPoints.size();

            //find outliers
            if (fraction >= p) {
                Point outliers = listOfPoints.get(i);

                listOfOutliers.add(outliers);
            }
        }


        //remove outliers
        listOfPoints.removeAll(listOfOutliers);

        return listOfPoints;
    }


    public double distance(Point point1, Point point2) {
        return Math.sqrt(Math.pow((point1.getX() - point2.getX()), 2) +
                Math.pow((point1.getY() - point2.getY()), 2) + Math.pow((point1.getZ() - point2.getZ()), 2));
    }

    public void clustering() {
        putIntoCluster();

        while (listOfClusters.size() > k) {

            //the shortest distance
            double min = 0;

            //two closest listOfClusters
            Cluster minCluster1 = null;
            Cluster minCluster2 = null;

            for (int i = 0; i < listOfClusters.size(); i++) {
                for (int j = i + 1; j < listOfClusters.size(); j++) {
                    Cluster c1 = listOfClusters.get(i);
                    Cluster c2 = listOfClusters.get(j);

                    //calculate distance
                    double distance = 0;
                    switch (clusterDistance) {
                        case NEAREST_POINT:
                            distance = calculateDistance1(c1, c2);
                            break;
                        case FAREST_POINT:
                            distance = calculateDistance2(c1, c2);
                            break;
                        case AVERAGE:
                            distance = calculateDistance3(c1, c2);
                            break;
                        case CENTER_POINT:
                            distance = calculateDistance4(c1, c2);
                            break;
                    }

                    //find closest listOfClusters by distance
                    if ((minCluster1 == null && minCluster2 == null) || distance < min) {

                        min = distance;

                        minCluster1 = c1;
                        minCluster2 = c2;
                    }
                }
            }

            //merge two closest listOfClusters
            Cluster newCluster = mergeClusters(minCluster1.getPoints(), minCluster2.getPoints());

            //remove closest listOfClusters
            listOfClusters.remove(minCluster1);
            listOfClusters.remove(minCluster2);

            //add a new cluster into cluster list
            listOfClusters.add(newCluster);
        }
    }

    //the distance between the nearest two points in the two clusters
    public double calculateDistance1(Cluster c1, Cluster c2) {
        double min = 0;

        for (int i = 0; i < c1.getPoints().size(); i++) {
            for (int j = 0; j < c2.getPoints().size(); j++) {
                Point point1 = c1.getPoints().get(i);
                Point point2 = c2.getPoints().get(j);

                double distance = distance(point1, point2);

                if (min == 0 || distance < min) {
                    min = distance;
                }
            }
        }
        return min;
    }

    //the distance between the farthest two points in the two clusters
    public double calculateDistance2(Cluster c1, Cluster c2) {
        double max = 0;

        for (int i = 0; i < c1.getPoints().size(); i++) {
            for (int j = 0; j < c2.getPoints().size(); j++) {
                Point point1 = c1.getPoints().get(i);
                Point point2 = c2.getPoints().get(j);

                double distance = distance(point1, point2);

                if (max == 0 || distance > max) {
                    max = distance;
                }
            }
        }

        return max;
    }

    //the average distance between points in the two clusters
    public double calculateDistance3(Cluster c1, Cluster c2) {
        double totalDistance = 0;

        int c1Size = c1.getPoints().size();
        int c2Size = c2.getPoints().size();

        for (int i = 0; i < c1Size; i++) {
            for (int j = 0; j < c2Size; j++) {
                Point pointC1 = c1.getPoints().get(i);
                Point pointC2 = c2.getPoints().get(j);

                double distance = distance(pointC1, pointC2);

                totalDistance += distance;
            }
        }

        double averageDistance = totalDistance / (c1Size * c2Size);

        return averageDistance;
    }

    //the distance between the centers of the two clusters
    public double calculateDistance4(Cluster c1, Cluster c2) {

        Point centerC1 = c1.getCenterPoint();
        Point centerC2 = c2.getCenterPoint();

        return distance(centerC1, centerC2);
    }

    //----------------------------------------------------------
    // Evaluation
    //----------------------------------------------------------

    public double silhouetteCoefficient() {
        double coefficientOfAPoint = 0;
        double totalCoefficient = 0;

        //get a cluster
        for (int i = 0; i < listOfClusters.size(); i++) {
            Cluster cluster = listOfClusters.get(i);

            //get a point
            for (int j = 0; j < cluster.getPoints().size(); j++) {

                //calculate inner and outer distance
                double innerDistance = innerDistance(cluster, j); //a1
                double outerDistance = outerDistance(cluster, j); //b1

                coefficientOfAPoint = (outerDistance - innerDistance) / Math.max(innerDistance, outerDistance);

                totalCoefficient += coefficientOfAPoint;
            }
        }

        return totalCoefficient / listOfPoints.size();
    }

    //----------------------------------------------------------
    // private method
    //----------------------------------------------------------

    // points = clusters
    private void putIntoCluster() {
        Point point = null;
        for (int i = 0; i < listOfPoints.size(); i++) {
            point = listOfPoints.get(i);

            Cluster cluster = new Cluster();
            cluster.getPoints().add(point);
            listOfClusters.add(cluster);
        }
    }

    private Cluster mergeClusters(ArrayList<Point> points1, ArrayList<Point> points2) {
        Cluster newCluster = new Cluster();

        //add points into a new cluster
        for (int i = 0; i < points1.size(); i++) {
            Point newPoint1 = points1.get(i);
            newCluster.getPoints().add(newPoint1);
        }

        for (int j = 0; j < points2.size(); j++) {
            Point newPoint2 = points2.get(j);
            newCluster.getPoints().add(newPoint2);
        }

        return newCluster;
    }


    //calculate distance from a point to other points in the same cluster
    private double innerDistance(Cluster c, int p) {

        if(c.size() == 1){
            return 0;
        }

        Point p1 = c.getPoints().get(p);

        double innerSum = 0;

        for (int i = 0; i < c.size(); i++) {

            //get another point
            Point p2 = c.getPoints().get(i);

            if(p1 == p2){
                //same point, ignore
                continue;
            }

            double distance = distance(p1, p2);

            //total distance
            innerSum += distance;
        }


        return innerSum / (c.size() - 1); //a1

    }

    //calculate distance from a point to other points in different cluster
    private double outerDistance(Cluster c, int p) {
        double totalDistance = 0;

        ArrayList<Double> listOfOtherClusterDistance = new ArrayList<>(); //m1, n1..

        Point p1 = c.getPoints().get(p);


        //get other cluster
        for (int i = 0; i < listOfClusters.size(); i++) {

            Cluster cluster = listOfClusters.get(i);

            if (c == cluster) {
                //same cluster, ignore
                continue;
            }

            double outerSum = 0;

            //get points in other cluster
            for (int j = 0; j < cluster.getPoints().size(); j++) {

                //get a point in other cluster
                Point p2 = cluster.getPoints().get(j);

                double distance = distance(p1, p2);

                outerSum += distance;
            }

            totalDistance = outerSum / (cluster.size());

            listOfOtherClusterDistance.add(totalDistance); //m1, n1...
        }

        return Collections.min(listOfOtherClusterDistance); //b1
    }

    public int printPoints(){
        return listOfPoints.size();
    }

}
