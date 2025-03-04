package SOLID.LSP.Class;

import SOLID.LSP.Interface.Shape;

public class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    public int getArea() {
        return side * side;
    }
}
