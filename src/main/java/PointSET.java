import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private final SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        this.points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validateArg(p);

        this.points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateArg(p);

        return this.points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        this.points.forEach(Point2D::draw);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);

        final SET<Point2D> pointsInRange = new SET<>();
        for (final Point2D point : this.points) {
            if (rect.contains(point)) {
                pointsInRange.add(point);
            }
        }

        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validateArg(p);

        if (isEmpty()) {
            return null;
        }

        Point2D nearestPoint = null;
        for (final Point2D point : this.points) {
            if (nearestPoint == null) {
                nearestPoint = point;
                continue;
            }

            if (p.distanceTo(point) < p.distanceTo(nearestPoint)) {
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    private void validateArg(Object a) {
        if (a == null) {
            throw new NullPointerException("Cannot be null");
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(0.206107, 0.095492));
        pointSET.insert(new Point2D(0.975528, 0.654508));
        pointSET.insert(new Point2D(0.024472, 0.345492));
        pointSET.insert(new Point2D(0.793893, 0.095492));
        pointSET.insert(new Point2D(0.793893, 0.904508));
        pointSET.insert(new Point2D(0.975528, 0.345492));
        pointSET.insert(new Point2D(0.206107, 0.904508));
        pointSET.insert(new Point2D(0.500000, 0.000000));
        pointSET.insert(new Point2D(0.024472, 0.654508));
        pointSET.insert(new Point2D(0.500000, 1.000000));
        pointSET.draw();
    }
}