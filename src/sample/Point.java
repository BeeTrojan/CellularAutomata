package sample;

import java.util.Objects;

public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    public static void main(String[] args) {
        Point p = new Point(1,2);
        Point p1 = new Point(1,2);
        Point p2 = new Point(12,2);
        System.out.println("");
    }

}
