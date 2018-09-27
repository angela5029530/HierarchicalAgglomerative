# HierarchicalAgglomerativeAlogrithm #

## Introduction ##

* This README is divided into three parts. 

* Each part contains a dataset of 500 points and they will be created by different way. In addition, there are four methods to determine the distance of two clusters in each part, each method use the same dataset to calculate the distance. 

* k, p, d are user-specified parameter values, and I will be supposed each of them in each part, all parts have the same value. 

* After getting the silhouette coefficient from each method, I will choose the silhouette coefficient which is close to 1, and it means that method has the best performance. 

* Finally, I will give the conclusion. 

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

Suppose: 

k=5
p= 0.75
d = 600

- First, determining the distance of two clusters by the distance between the nearest two points in
the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : -0.2373472915147059
```

- Second, determining the distance of two clusters by the distance between the farthest two points in the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : 0.1758761947988571
```

- Third, determining the distance of two clusters by the average distance between points in the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : 0.24125776971235735
```

- Finally, determining the distance of two clusters by the distance between the centers of the two clusters.

```bash
After remover outliers : 457 points.
SilhouetteCoefficient : 0.20729746545781325
```
In part 1, using the average distance between points to decide the distance of two clusters can get the best performance in comparison with other methods.

## Part 2 ##

In this part, 500 points are divided into five groups with 100 points. The first group is within the range of -300 to 0. The second group is within the range of -200 to 100. The third group is within the range of -100 to 200. The fourth group is within the range of 100 to 400. The fifth group is within the range of 300 to 600. The range of points of each group has duplicated points with other groups.

Suppose:

k=5
p = 0.75 
d = 600

First, determining the distance of two clusters by the distance between the nearest two points in
the two clusters.

```bash
After remover outliers : 500 points.
SilhouetteCoefficient : -0.4567306792720635
```

Second, determining the distance of two clusters by the distance between the farthest two points in the two clusters.

```bash
After remover outliers : 500 points.
SilhouetteCoefficient : 0.22727624960518814
```

Third, determining the distance of two clusters by the average distance between points in the
two clusters.

```bash
After remover outliers : 500 points.
SilhouetteCoefficient : 0.29628494214576495
```

Finally, determining the distance of two clusters by the distance between the centers of the two clusters.


```bash
After remover outliers : 500 points.
SilhouetteCoefficient : 0.31127174894077186
```

In part 2, deciding the distance by the distance between the centers of the two clusters has the best performance.

## Part 3 ##


In this part, 500 points are divide into five groups , and each group contains 100 points. The range of points in each group are described below :

* The first group is from -800 to -500. The second group is from -400 to -100. The third group is from 0 to 300.
* The fourth group is from 400 to 700. The fifth group is from 800 to 1100.
* The range of each group does not overlap.

Suppose: 

k=5
p = 0.75 
d = 600

First, determining the distance of two clusters by the distance between the nearest two points in
the two clusters.

```bash
After remover outliers : 279 points.
SilhouetteCoefficient : 0.6854044335752786
```

Second, determining the distance of two clusters by the distance between the farthest two points in the two clusters.

```bash
After remover outliers : 279 points.
SilhouetteCoefficient : 0.6854044335752774
```

Third, determining the distance of two clusters by the average distance between points in the two clusters.

```bash
After remover outliers : 279 points.
SilhouetteCoefficient : 0.6854044335752768
```

Finally, determining the distance of two clusters by the distance between the centers of the two clusters.

```bash
After remover outliers : 279 points.
SilhouetteCoefficient : 0.685404433575277
```

In part 3, the last method of determining the distance by the distance between the centers of the two clusters has better performance than other method.

## Conclusion ##

* In part 1, points are randomly created, so we can not really know how do they separate into each cluster. Moreover, determining the distance of two clusters by the average distance between points in the two clusters has the best performance. 

* In part 2, I consider that because of overlapping points, and the clusters are probably close to each other. Therefore, there are no outliers. In addition, determining the distance of two clusters by the distance between the centers of the two clusters has the best performance. 

* In part 3, each method of deciding the distance between two clusters has very close silhouette coefficient. I consider that the clusters are clearly separated because there are no duplicated points. Thus, the last method of determining the distance by the distance between the centers of the two clusters is slightly closer to 1 than other method. In the condition of only comparing silhouette coefficient, part 3 has the best performance.
