import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PointTest {

    @Test
    public void testCompareToLessThan() {
        Point point1 = new Point(2, 3);
        Point point2 = new Point(4, 5);

        assertTrue(point1.compareTo(point2) == -1);
    }

    @Test
    public void testCompareToEquals() {
        Point point1 = new Point(4, 5);
        Point point2 = new Point(4, 5);

        assertTrue(point1.compareTo(point2) == 0);
    }

    @Test
    public void testCompareToGreaterThan() {
        Point point1 = new Point(4, 5);
        Point point2 = new Point(2, 3);

        assertTrue(point1.compareTo(point2) == 1);
    }

    @Test
    public void testCompareToYEqualsLessThan() {
        Point point1 = new Point(2, 5);
        Point point2 = new Point(4, 5);

        assertTrue(point1.compareTo(point2) == -1);
    }

    @Test
    public void testCompareToYEqualsGreaterThan() {
        Point point1 = new Point(4, 5);
        Point point2 = new Point(2, 5);

        assertTrue(point1.compareTo(point2) == 1);
    }

    @Test
    public void testSlopeTo() {
        Point point1 = new Point(4, 8);
        Point point2 = new Point(2, 5);


        assertTrue(point1.slopeTo(point2) == 1.5);
    }

    @Test
    public void testSlopeToInfinity() {
        Point point1 = new Point(262, 5);
        Point point2 = new Point(262, 36);

        assertTrue(point1.slopeTo(point2) == Double.POSITIVE_INFINITY);
    }

    @Test
    public void testSlopeToNegativeInfinity() {
        Point point1 = new Point(80, 8);

        assertTrue(point1.slopeTo(point1) == Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testSlopeZero() {
        Point point1 = new Point(5, 8);
        Point point2 = new Point(80, 8);

        assertTrue(point1.slopeTo(point2) == 0D);
    }

    @Test
    public void testSlopeOrder() {
        Point point1 = new Point(2, 8);
        Point point2 = new Point(4, 12);
        Point point3 = new Point(7, 5);
        List<Point> sortedPoints = Stream.of(point1, point3).sorted(point2.slopeOrder()).collect(Collectors.toList());

        assertEquals(point3, sortedPoints.get(0));
        assertEquals(point1, sortedPoints.get(1));
        assertTrue(sortedPoints.size() == 2);
    }
}
