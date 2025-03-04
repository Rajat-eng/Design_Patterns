package SOLID.DIP.Class;

import SOLID.DIP.Interface.InputDevice;

public class Computer {
    private InputDevice inputDevice;
    public Computer(InputDevice inputDevice){
        this.inputDevice=inputDevice;
    }

    public void useInputDevice() {
        inputDevice.input();
    }
}
