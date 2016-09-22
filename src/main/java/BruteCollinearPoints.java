import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final Point[] points;

    // finds all line segments containing 4 points, assuming no input
    // has 5 or more collinear points
    public BruteCollinearPoints(Point[] args) {
        if (args == null) {
            throw new NullPointerException("Cannot pass null points");
        }
        this.points = Arrays.copyOf(args, args.length);
        Arrays.sort(points);

        // validate
        final int length = points.length;
        for (int i = 0; i < length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("Points contains null point");
            }
            if (i > 0 && points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Points contains repeated point");
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments().length;
    }

    // the line segments
    public LineSegment[] segments() {
        final int length = points.length;
        // n^4 quadratic nesting
        final List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    for (int h = k + 1; h < length; h++) {
                        final double slope1 = points[i].slopeTo(points[j]);
                        final double slope2 = points[j].slopeTo(points[k]);
                        final double slope3 = points[k].slopeTo(points[h]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            lineSegments.add(new LineSegment(points[i], points[h]));
                        }
                    }
                }
            }
        }

        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}