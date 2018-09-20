package idv.peiju;

public class Point {
    double x = 0;
    double y = 0;
    double z = 0;

    public Point(){
    }

    //given point
    public Point( double x,  double y,  double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void randomPoint(double a , double b){
        x = Math.random() * a - b;
        y = Math.random() * a - b;
        z = Math.random() * a - b;
    }

    public  double getX() {
        return x;
    }

    public  double getY() {
        return y;
    }

    public  double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x = " + x +
                ", y = " + y +
                ", z = " + z +
                '}';
    }
}

