package Patterns.Abstract_Factory.Class;

import Patterns.Abstract_Factory.Interface.Chair;
import Patterns.Abstract_Factory.Interface.Desk;

public class OfficeRoom implements Desk,Chair{
    public void workOn() {
        System.out.println("Working on an Office Desk.");
    }
    public void sitOn() {
        System.out.println("Sitting on an ergonomic Office Chair.");
    }
}
