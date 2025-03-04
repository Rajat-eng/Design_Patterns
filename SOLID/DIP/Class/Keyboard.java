package SOLID.DIP.Class;

import SOLID.DIP.Interface.InputDevice;

public class Keyboard implements InputDevice {
    public void input() {
        System.out.println("Keyboard");
    }
}
