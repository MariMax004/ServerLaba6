package Server;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable {
    private String result;
    private List<RouteRow> routeRowList;

    public Answer(String result) {
        this.result = result;
    }

    public Answer(String result, List<RouteRow> list) {
        this.result = result;
        this.routeRowList = list;
    }

    public String getResult() {
        return result;
    }

    public List<RouteRow> getRouteRowList() {
        return routeRowList;
    }
}