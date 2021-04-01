package top.wikl.neo4j.entity;


import java.io.Serializable;

/**
 * @ClassName: Point2D
 * @Description: neo4j原生接口封装-地理位置
 * @date: 2020/8/6 18:15
 * @author DengYaJun
*/
public class Point2D implements Serializable {
    double srid;
    double x;
    double y;

    public Point2D() {
    }

    public Point2D(int srid, double x, double y) {
        this.x = x;
        this.y = y;
        this.srid = srid;
    }

    public double getSrid() {
        return srid;
    }

    public void setSrid(double srid) {
        this.srid = srid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return 0.0D / 0.0;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Point2D that = (Point2D) o;
            return this.srid == that.srid && Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Point{srid=" + this.srid + ", x=" + this.x + ", y=" + this.y + '}';
    }
}
