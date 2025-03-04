package Patterns.Adapter.Class;

import Patterns.Adapter.Interface.Charger;
import Patterns.Adapter.Interface.IPhone;

public class IPhone6s implements IPhone  {
    Charger Iphone4sTo6sAdapter;
    public IPhone6s(Charger iphone4sTo6sAdapter)
    {
        this.Iphone4sTo6sAdapter = iphone4sTo6sAdapter;
    };

    @Override
    public void OnCharge()
    {
        Iphone4sTo6sAdapter.charge();
    }
}
