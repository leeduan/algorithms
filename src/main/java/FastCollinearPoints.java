import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private final Point[] points;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] args) {
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
        final List<LineSegment> segmentList = new ArrayList<>();
        for (final Point origin : points) {
            final List<Point> pointsList = new ArrayList<>(Arrays.asList(points));
            Collections.sort(pointsList, origin.slopeOrder());

            double currSlope = origin.slopeTo(pointsList.get(0));
            final List<Point> adjacentPoints = new ArrayList<>();

            for (final Point other : pointsList) {
                final double slope = origin.slopeTo(other);
                if (currSlope != slope) {
                    addToSegment(origin, adjacentPoints, segmentList);
                    adjacentPoints.clear();
                }

                currSlope = slope;
                adjacentPoints.add(other);
            }

            addToSegment(origin, adjacentPoints, segmentList);
        }

        return segmentList.toArray(new LineSegment[segmentList.size()]);
    }

    private void addToSegment(Point origin, List<Point> adjacentPoints, List<LineSegment> segmentList) {
        if (adjacentPoints.size() < 3) {
            return;
        }
        adjacentPoints.add(origin);
        Collections.sort(adjacentPoints);
        // Only add line segment if origin is the end point in the line.
        if (!adjacentPoints.get(adjacentPoints.size() - 1).equals(origin)) {
            return;
        }

        segmentList.add(new LineSegment(adjacentPoints.get(0), adjacentPoints.get(adjacentPoints.size() - 1)));
    }
}
