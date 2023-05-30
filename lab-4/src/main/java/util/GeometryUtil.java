package util;

import model.impl.Point;

public class GeometryUtil {
    public static double distanceFromPoint(Point point1, Point point2) {
        Point diff = point1.difference(point2);
        return Math.sqrt(diff.getX() * diff.getX() + diff.getY() * diff.getY());
    }

    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        double x1 = s.getX();
        double y1 = s.getY();

        double x2 = e.getX();
        double y2 = e.getY();

        double x0 = p.getX();
        double y0 = p.getY();

        double orthogonalSlope = (x1 - x2) / (y2 - y1);
        double lowerBound = orthogonalSlope * (x0 - x1) + y1;
        double upperBound = orthogonalSlope * (x0 - x2) + y2;

        if (y0 > lowerBound && y0 < upperBound) {
            return Math.abs((x2 - x1) * (y1 - y0) - (x1 - x0) * (y2 - y1)) / distanceFromPoint(s, e);
        } else {
            return Math.min(distanceFromPoint(s, p), distanceFromPoint(e, p));
        }
    }
}
