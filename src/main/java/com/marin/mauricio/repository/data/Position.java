package com.marin.mauricio.repository.data;

public class Position extends Point{

    private String facing;

    public Position(int x, int y) {
        super(x, y);
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public Point getPoint(){
        return new Point(this.getX() , this.getY() );
    }
}
