package ch.minepvp.spout.kingdoms.protection;

import org.spout.api.entity.Player;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;

public class Selection {

    private Player owner;

    private Point point1 = null;
    private Point point2 = null;

    public Selection( Player owner ) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public World getWorld() {
        return point1.getWorld();
    }

    public Point getMinPoint() {
        return new Point(getWorld(), Math.min(point1.getX(), point2.getX()), Math.min(point1.getY(), point2.getY()), Math.min(point1.getZ(), point2.getZ()));
    }

    public Point getMaxPoint() {
        return new Point(getWorld(), Math.max(point1.getX(), point2.getX()), Math.max(point1.getY(), point2.getY()), Math.max(point1.getZ(), point2.getZ()));
    }

}
