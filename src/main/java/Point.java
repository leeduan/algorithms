import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException("Point cannot be null");
        }
        if (that.x == x && that.y == y) {
            return Double.NEGATIVE_INFINITY;
        } else if (that.x == x) {
            return Double.POSITIVE_INFINITY;
        } else if (that.y == y) {
            return 0D;
        }

        return ((double) that.y - y) / ((double) that.x - x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        return y == that.y ? Integer.compare(x, that.x) : Integer.compare(y, that.y);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (c1, c2) -> Double.compare(this.slopeTo(c1), this.slopeTo(c2));
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
