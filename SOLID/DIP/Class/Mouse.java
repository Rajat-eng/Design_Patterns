package SOLID.DIP.Class;

import SOLID.DIP.Interface.InputDevice;

public class Mouse implements InputDevice{
    public void input() {
        System.out.println("Mouse");
    }
}
