import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BruteCollinearPointsTest {

    @Test(expected = NullPointerException.class)
    public void testNullArgument() {
        new BruteCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void testPointsContainsNull() {
        Point point1 = new Point(10000, 0);
        Point point2 = new Point(0, 10000);
        Point point4 = new Point(7000, 3000);
        Point point5 = new Point(0, 10000);
        Point[] points = new Point[] {point1, point2, null, point4, point5};

        new BruteCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicatePoints() {
        Point point1 = new Point(10000, 0);
        Point point2 = new Point(0, 10000);
        Point point3 = new Point(3000, 7000);
        Point point4 = new Point(7000, 3000);
        Point point5 = new Point(0, 10000);
        Point[] points = new Point[] {point1, point2, point3, point4, point5};

        new BruteCollinearPoints(points);
    }

    @Test
    public void testSegmentsOnePoints() {
        Point point1 = new Point(16000, 16000);
        Point[] points = new Point[] {point1};

        assertTrue(new BruteCollinearPoints(points).numberOfSegments() == 0);
    }

    @Test
    public void testSegmentsThreePoints() {
        Point point1 = new Point(16000, 16000);
        Point point2 = new Point(10000, 10000);
        Point point3 = new Point(20000, 20000);
        Point[] points = new Point[] {point1, point2, point3};

        assertTrue(new BruteCollinearPoints(points).numberOfSegments() == 0);
    }

    @Test
    public void testSegmentsEightPoints() {
        Point point1 = new Point(10000, 0);
        Point point2 = new Point(0, 10000);
        Point point3 = new Point(3000, 7000);
        Point point4 = new Point(7000, 3000);
        Point point5 = new Point(20000, 21000);
        Point point6 = new Point(3000, 4000);
        Point point7 = new Point(14000, 15000);
        Point point8 = new Point(6000, 7000);
        Point[] points = new Point[] {point1, point2, point3, point4, point5, point6, point7, point8};

        assertTrue(new BruteCollinearPoints(points).numberOfSegments() == 2);
    }

    @Test
    public void testSegmentsEquidistant() {
        Point point1 = new Point(10000, 0);
        Point point2 = new Point(8000, 2000);
        Point point3 = new Point(2000, 8000);
        Point point4 = new Point(0, 10000);
        Point point5 = new Point(20000, 0);
        Point point6 = new Point(18000, 2000);
        Point point7 = new Point(2000, 18000);
        Point point8 = new Point(10000, 20000);
        Point point9 = new Point(30000, 0);
        Point point10 = new Point(0, 30000);
        Point point11 = new Point(20000, 10000);
        Point point12 = new Point(13000, 0);
        Point point13 = new Point(11000, 3000);
        Point point14 = new Point(5000, 12000);
        Point point15 = new Point(9000, 6000);
        Point[] points = new Point[] {point1, point2, point3, point4, point5, point6, point7, point8, point9, point10,
                        point11, point12, point13, point14, point15};

        assertTrue(new BruteCollinearPoints(points).numberOfSegments() == 4);
    }

}
