package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.Desk;

public class OfficeDesk implements Desk {
    public void workOn(){
        System.err.println("office desk");
    }
}
