package com.atm.Chain;

public abstract class NoteDispenser implements DispenseChain {
    private DispenseChain nextChain;
    private int numNotes;
    private int denomination;

    public NoteDispenser(int numNotes, int denomination){
        this.denomination = denomination;
        this.numNotes = numNotes;
    }

    public void setNextChain(DispenseChain nextChain){
        this.nextChain = nextChain;
    }

    public void dispense(int amount){
        if(amount>=denomination){
            int notesRequired = Math.min(amount/denomination, this.numNotes);
            int remainingAmount = amount - (denomination * notesRequired);

            if(notesRequired>0){
                this.numNotes-=notesRequired;
                System.out.println("fetching " + notesRequired + "" + amount);
            }

            if(remainingAmount>0 && this.nextChain!=null){
                this.nextChain.dispense(remainingAmount);
            }    
        }else if(this.nextChain!=null){
            this.nextChain.dispense(amount);
        }
    }

    public synchronized boolean canDispense(int amount) {
        if (amount < 0) return false;
        if (amount == 0) return true;

        int numToUse = Math.min(amount / denomination, this.numNotes);
        int remainingAmount = amount - (numToUse * denomination);

        if (remainingAmount == 0) return true;
        if (this.nextChain != null) {
            return this.nextChain.canDispense(remainingAmount);
        }
        return false;
    }
}