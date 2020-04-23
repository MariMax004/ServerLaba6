import CollectionObjects.Route;

import java.io.Serializable;

public class Container implements Serializable {
    private String command;
    private int id;
    private Route route;
    private double parametr;
    private String parametrString;

    public Container(String command) {
        this.command = command;

    }

    public Container(String command, int id) {
        this.command = command;
        this.id = id;
    }

    public Container(String command, Route route) {
        this.command = command;
        this.route = route;
    }

    public Container(String command, double parametr) {
        this.command = command;
        this.parametr = parametr;
    }

    public Container(String command, String parametrString) {
        this.command = command;
        this.parametrString = parametrString;
    }

    public String getCommand() {
        return command;
    }

    public Route getRoute() {
        return route;
    }

    public double getParametr() {
        return parametr;
    }

    public void setParametr(double parametr) {
        this.parametr = parametr;
    }

    public String getParametrString() {
        return parametrString;
    }

    public void setParametrString(String parametrString) {
        this.parametrString = parametrString;
    }
}
