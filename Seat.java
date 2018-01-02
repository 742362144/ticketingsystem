package ticketingsystem;

import java.util.Arrays;

public class Seat {
    private final int seatId;
    private boolean[] stateOfPeace;
    
    private volatile int countOfSold;
    // private volatile boolean isBusy = false;
    
    private Object lockState = new Object();
    
    public Seat(final int seatId, final int countOfPeace) {
        
        this.seatId = seatId;
        this.stateOfPeace = new boolean[countOfPeace];
        this.countOfSold = 0;
        // Java will initialize the defaule value (false) of "stateOfPeace",
        // but we will do it (true) in the right direction.
        for (int i = 0; i < countOfPeace; i++) {
            this.stateOfPeace[i] = true;
        }
    }
    
    public boolean checkState(final int departure, final int arrival) {
        
        boolean result = true;
        // The result may not be right,
        // cause "tryModifyState()" could change the states of this seat
        // when we are running "checkState()".
        for (int i = departure - 1; i < arrival - 1; i++) {
            result = result && this.stateOfPeace[i];
        }
        return result;
    }
    
    // public synchronized int trySealTick(final int departure, final int arrival) {
    public int trySealTick(final int departure, final int arrival) {
        
        synchronized(lockState) {
            boolean result = true;
            for (int i = departure - 1; i < arrival - 1; i++) {
                result = result && this.stateOfPeace[i];
            }
            if (result == true) {
                for (int i = departure - 1; i < arrival - 1; i++) {
                    this.stateOfPeace[i] = false;
                }

                return this.seatId;
            } else {

                return -1;
            }
        }
    }
    
    public boolean tryRefundTick(final int departure, final int arrival) {
        // We don't need lock the state when refund tickets,
        // cause when we could sell a ticket without refunding,
        // we can make it after refunding also.
        for (int i = departure - 1; i < arrival - 1; i++) {
            this.stateOfPeace[i] = true;
        }
        return true;
    }
}
    