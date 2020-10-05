package app.model;

import app.pointData.Point;

import java.util.ArrayList;


public class Model {
    public final static ArrayList<Point> points = new ArrayList<Point>();

    public void setPoint(Point point){
        points.add(point);
    }

}
