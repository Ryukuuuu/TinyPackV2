package pt.isec.pa.tinypac.model.data.maze;

import pt.isec.pa.tinypac.utils.Position;

import java.util.Objects;

public abstract class Element implements IMazeElement{
    private int x;
    private int y;

    protected Element(int y,int x){
        this.y=y;
        this.x=x;
    }

    //Getters | Setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Position getXY(){
        return new Position(y,x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return x == element.x && y == element.y;
    }
}
