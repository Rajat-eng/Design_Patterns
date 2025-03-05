package Patterns.Command.Class;

import java.util.Stack;

import Patterns.Command.Interface.OrderCommand;

public class Waiter {
    private Stack<OrderCommand> orderStack;
    public Waiter(){
        this.orderStack=new Stack<>();
    }
    public void takeOrder(OrderCommand orderCommand){
        orderCommand.execute();// chef prapares or cancels dish
        orderStack.push(orderCommand); 
    }
    public void undoLastOrder(){
        if(!orderStack.isEmpty()){
            OrderCommand lastOrder=orderStack.pop();
            lastOrder.undo();
        }else {
            System.out.println("No orders to undo.");
        }
        
    }
}
