package idv.peiju;

import java.util.ArrayList;

public class Cluster {
    private ArrayList<Point> points;

    public Cluster(){
        this.points = new ArrayList<>();
    }

    public Cluster(ArrayList<Point> points) {
        this.points = points;
    }

    public Point getCenterPoint(){
        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        double totalPoints = points.size();

        for(int i = 0; i < totalPoints; i++){
            double x = points.get(i).getX();
            double y = points.get(i).getY();
            double z = points.get(i).getZ();
            sumX += x;
            sumY += y;
            sumZ += z;
        }

        //center point
        Point center = new Point((sumX / totalPoints), (sumY / totalPoints), (sumZ / totalPoints));

        return center;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int size() {
        return points.size();
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "points=" + points +
                '}';
    }
}

