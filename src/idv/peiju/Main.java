package idv.peiju;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Point> points = readFromFile("resources/points_part1.txt");

        HAC hac = new HAC(points, 5, HAC.ClusterDistance.NEAREST_POINT);

        hac.removeOutlier(0.75, 600);

        System.out.println("After remove outliers : " + hac.printPoints() + " points.");

        hac.clustering();

        double result = hac.silhouetteCoefficient();

        System.out.println("SilhouetteCoefficient : " + result);

    }



    //write out
    private static void writeOut(List<Point> points, String path) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        for (Point p : points) {

            double x = p.getX();
            double y = p.getY();
            double z = p.getZ();

            String s = "(" + x + "," + y +"," + z + ")";

            writer.write(s);

            writer.newLine();
        }

        writer.close();
    }

    private static ArrayList<Point> readFromFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<Point> result = new ArrayList<>();

        String line = null; // ( x, y, z )

        while ((line = reader.readLine()) != null) {

            if (line.isEmpty()) {
                continue;
            }

            String[] points = line.substring(1, line.length() - 1).split(",");

            double x = Double.parseDouble(points[0]);
            double y = Double.parseDouble(points[1]);
            double z = Double.parseDouble(points[2]);

            Point p = new Point(x, y, z);

            result.add(p);
        }


        return result;
    }
}

