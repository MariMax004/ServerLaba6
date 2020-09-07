package Server;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class RouteRow implements Serializable {
    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double distance; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer x;
    private Integer y;
    private String name_from;
    private Double x_from;
    private long y_from;
    private String name_to;
    private Long x_to;
    private Integer y_to;
    private Integer z_to;
    private String owner;

    public RouteRow() {
        this.id = 0;
        this.name =  "";
        this.distance =  0;
        this.creationDate =  null;
        this.x =  0;
        this.y =  0;
        this.name_from =  "";
        this.x_from = null;
        this.y_from =  0;
        this.name_to =  "";
        this.x_to = null;
        this.y_to =  0;
        this.z_to =  0;
        this.owner =  "";
    }


    public RouteRow(Integer id,
                    String name,
                    double distance,
                    ZonedDateTime creationDate,
                    Integer x,
                    Integer y,
                    String name_from,
                    Double x_from,
                    long y_from,
                    String name_to,
                    long x_to,
                    Integer y_to,
                    Integer z_to,
                    String owner) {
        this.id =  id;
        this.name =  name;
        this.distance =  distance;
        this.creationDate =  creationDate;
        this.x =  x;
        this.y =  y;
        this.name_from =  name_from;
        this.x_from =  x_from;
        this.y_from =  y_from;
        this.name_to =  name_to;
        this.x_to =  x_to;
        this.y_to =  y_to;
        this.z_to =  z_to;
        this.owner =  owner;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getName_from() {
        return name_from;
    }

    public double getX_from() {
        return x_from;
    }

    public long getY_from() {
        return y_from;
    }

    public String getName_to() {
        return name_to;
    }

    public long getX_to() {
        return x_to;
    }

    public Integer getY_to() {
        return y_to;
    }

    public Integer getZ_to() {
        return z_to;
    }

    public String getOwner() {
        return owner;
    }
}

