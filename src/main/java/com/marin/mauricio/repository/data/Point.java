package com.marin.mauricio.repository.data;

public class Point implements Comparable<Point> {
    private int X;
    private int Y;

    public Point(int x, int y) {
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public String getKey(){
        return X+","+Y;
    }

    @Override
    public int compareTo(Point p) {
        if(this.X == p.X && this.Y == p.Y)
            return 0;
        else if(this.Y > p.Y || ( this.Y == p.Y && this.X > p.X))
            return 1;
        else
            return -1;
    }
}
