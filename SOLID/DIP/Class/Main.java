package SOLID.DIP.Class;

import SOLID.DIP.Interface.InputDevice;

public class Main {
    public static void main(String[] args) {
        InputDevice Keyboard=new Keyboard();
        InputDevice Mouse=new Mouse();
        Computer device1=new Computer(Keyboard);
        device1.useInputDevice();
        Computer device2=new Computer(Mouse);
        device2.useInputDevice();
    }
}
