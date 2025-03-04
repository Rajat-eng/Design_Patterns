package Patterns.Adapter;

import Patterns.Adapter.Class.IPhone6s;
import Patterns.Adapter.Class.Iphone4sto6sCharger;

public class Main {
    public static void main(String args[])
    {
        IPhone6s iphone6s = new IPhone6s(new Iphone4sto6sCharger());
        iphone6s.OnCharge();
    }
}
