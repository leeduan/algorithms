import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Optional;

public class KdTree {
    // private final SET<Point2D> points;
    private int size;
    private Node root;

    // construct an empty set of points
    public KdTree() { }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validateArg(p);

        if (this.root == null) {
            this.root = new Node(p, new RectHV(0D, 0D, 1D, 1D));
            this.size++;
            return;
        }

        insert(p, this.root);
    }

    private void insert(Point2D p, Node currNode) {
        // prevent insertion of duplicate
        if (currNode.getP().equals(p)) {
            return;
        }

        if (isGoLeft(p, currNode)) {
            if (currNode.getLb() != null) {
                insert(p, currNode.getLb());
            } else {
                final Orientation orientation = Orientation.other(currNode.getOrientation());
                currNode.setLb(new Node(p, rectHV(currNode, orientation, true), orientation));
                this.size++;
            }
        } else {
            if (currNode.getRt() != null) {
                insert(p, currNode.getRt());
            } else {
                final Orientation orientation = Orientation.other(currNode.getOrientation());
                currNode.setRt(new Node(p, rectHV(currNode, orientation, false), orientation));
                this.size++;
            }
        }
    }

    private RectHV rectHV(Node node, Orientation orientation, boolean goLeft) {
        double xmin = node.getRect().xmin();
        double ymin = node.getRect().ymin();
        double xmax = node.getRect().xmax();
        double ymax = node.getRect().ymax();
        if (goLeft) {
            // xmin, ymin, xmax, ymax
            if (orientation == Orientation.X) {
                ymax = node.getP().y();
            } else {
                xmax = node.getP().x();
            }
        } else {
            if (orientation == Orientation.X) {
                ymin = node.getP().y();
            } else {
                xmin = node.getP().x();
            }
        }

        return new RectHV(xmin, ymin, xmax, ymax);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateArg(p);

        return !isEmpty() && contains(p, this.root);
    }

    private boolean contains(Point2D p, Node currNode) {
        if (currNode.getP().equals(p)) {
            return true;
        }

        if (isGoLeft(p, currNode)) {
            return currNode.getLb() != null && contains(p, currNode.getLb());
        } else {
            return currNode.getRt() != null && contains(p, currNode.getRt());
        }
    }

    private boolean isGoLeft(Point2D p, Node node) {
        if (node.getOrientation() == Orientation.X) {
            return p.x() < node.getP().x();
        } else {
            return p.y() < node.getP().y();
        }
    }

    // draw all points to standard draw
    public void draw() {
        if (this.root == null) {
            return;
        }

        draw(this.root);
    }

    private void draw(Node node) {
        // draw dot
        node.draw();

        // draw line
        StdDraw.setPenColor(node.getOrientation() == Orientation.X ? StdDraw.RED : StdDraw.BLUE);
        StdDraw.setPenRadius();

        // default to outer rectangle
        double x1 = node.getRect().xmin();
        double y1 = node.getRect().ymin();
        double x2 = node.getRect().xmax();
        double y2 = node.getRect().ymax();
        // set x or y depending on orientation
        if (node.getOrientation() == Orientation.X) {
            x1 = node.getP().x();
            x2 = node.getP().x();
        } else {
            y1 = node.getP().y();
            y2 = node.getP().y();
        }
        StdDraw.line(x1, y1, x2, y2);

        // draw children
        Optional.ofNullable(node.getLb()).ifPresent(this::draw);
        Optional.ofNullable(node.getRt()).ifPresent(this::draw);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);

        final SET<Point2D> points = new SET<>();
        if (this.root == null) {
            return points;
        }

        addToRange(points, this.root, rect);
        return points;
    }

    private void addToRange(SET<Point2D> points, Node node, RectHV rect) {
        if (rect.intersects(node.getRect())) {
            if (rect.contains(node.getP())) {
                points.add(node.getP());
            }
            Optional.ofNullable(node.getLb()).ifPresent(n -> addToRange(points, n, rect));
            Optional.ofNullable(node.getRt()).ifPresent(n -> addToRange(points, n, rect));
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }

        return nearest(p, this.root, this.root).getP();
    }

    private Node nearest(Point2D p, Node nearest, Node currNode) {
        if (nearest.getP().distanceSquaredTo(p) < currNode.getRect().distanceSquaredTo(p)) {
            return nearest;
        }

        // set nearest
        nearest = p.distanceSquaredTo(nearest.getP()) < p.distanceSquaredTo(currNode.getP()) ? nearest : currNode;

        if (currNode.getLb() != null && currNode.getRt() != null) {
            // if two subtrees to go down, always choose the subtree that is on the same side of the
            // splitting line as the query point as the first subtree to explore
            if (isGoLeft(p, currNode)) {
                nearest = nearest(p, nearest, currNode.getLb());
                nearest = nearest(p, nearest, currNode.getRt());
            } else {
                nearest = nearest(p, nearest, currNode.getRt());
                nearest = nearest(p, nearest, currNode.getLb());
            }
        } else if (currNode.getLb() != null) {
            nearest = nearest(p, nearest, currNode.getLb());
        } else if (currNode.getRt() != null) {
            nearest = nearest(p, nearest, currNode.getRt());
        }

        return nearest;
    }

    private void validateArg(Object a) {
        Optional.ofNullable(a).orElseThrow(() -> new NullPointerException("Cannot be null"));
    }

    private enum Orientation {
        X, Y;

        public static Orientation other(Orientation orientation) {
            return orientation == X ? Y : X;
        }
    }

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private Orientation orientation;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.orientation = Orientation.X;
        }

        public Node(Point2D p, RectHV rect, Orientation orientation) {
            this(p, rect);
            this.orientation = orientation;
        }

        public Node(Point2D p, RectHV rect, Node lb, Node rt, Orientation orientation) {
            this(p, rect, orientation);
            this.lb = lb;
            this.rt = rt;
        }

        public Point2D getP() {
            return p;
        }

        public void setP(Point2D p) {
            this.p = p;
        }

        public RectHV getRect() {
            return rect;
        }

        public void setRect(RectHV rect) {
            this.rect = rect;
        }

        public Node getLb() {
            return lb;
        }

        public void setLb(Node lb) {
            this.lb = lb;
        }

        public Node getRt() {
            return rt;
        }

        public void setRt(Node rt) {
            this.rt = rt;
        }

        public Orientation getOrientation() {
            return orientation;
        }

        public void setOrientation(Orientation orientation) {
            this.orientation = orientation;
        }

        public void draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(p.x(), p.y());
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.206107, 0.095492));
        kdTree.insert(new Point2D(0.975528, 0.654508));
        kdTree.insert(new Point2D(0.024472, 0.345492));
        kdTree.insert(new Point2D(0.793893, 0.095492));
        kdTree.insert(new Point2D(0.793893, 0.904508));
        kdTree.insert(new Point2D(0.975528, 0.345492));
        kdTree.insert(new Point2D(0.206107, 0.904508));
        kdTree.insert(new Point2D(0.500000, 0.000000));
        kdTree.insert(new Point2D(0.024472, 0.654508));
        kdTree.insert(new Point2D(0.500000, 1.000000));
        kdTree.draw();
    }
}
