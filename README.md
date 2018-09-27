# HierarchicalAgglomerativeAlogrithm #

## Introduction ##

This README is divided into three parts. Each part contains a dataset of 500 points and they will be created by different way. In addition, there are four methods to determine the distance of two clusters in each part, each method use the same dataset to calculate the distance. Moreover, k, p, d are user-specified parameter values, and I will be supposed each of them in each part, all parts have the same value . After getting the silhouette coefficient from each method, I will choose the silhouette coefficient which is close to 1, and it means that method has the best performance. Finally, I will give the conclusion. 

## Step ##
* First, read points from a file.
* Second, choose k, and a method of determining the distance of two clusters from four methods. Third, decide p and d for removing outliers.
* Fourth, do clustering.
* Finally, get silhouette coefficient.

```bash
ArrayList<Point> points = readFromFile("resources/points_part1.txt");

HAC hac = new HAC(points, 5, HAC.ClusterDistance.NEAREST_POINT);

hac.removeOutlier(0.75, 600);

System.out.println("After remove outliers : " + hac.printPoints() + " points.");

hac.clustering();

double result = hac.silhouetteCoefficient();

System.out.println("SilhouetteCoefficient : " + result);
```

## Part 1 ##

In this part, 500 points are created by random, and the range is -500 to 500.
Suppose: k=5
p= 0.75
d = 600

First, determining the distance of two clusters by the distance between the nearest two points in
the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : -0.2373472915147059
```

Second, determining the distance of two clusters by the distance between the farthest two points in the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : 0.1758761947988571
```

Third, determining the distance of two clusters by the average distance between points in the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : 0.24125776971235735
```

Finally, determining the distance of two clusters by the distance between the centers of the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : 0.20729746545781325
```
In part 1, using the average distance between points to decide the distance of two clusters can get the best performance in comparison with other methods.
