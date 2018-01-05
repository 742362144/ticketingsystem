/*
*
* Seat.java
* Guo Jianing
* 2018-Jan-5th
*
*/

package ticketingsystem;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Seat {
    private final int seatId;
    private boolean[] stateOfMuchPeace = null;
    
    public Seat(final int seatId, final int countOfPeace) {
        
        this.seatId = seatId;
        
        // If there are more than 33 stations need to be operated,
        // we will used "stateOfMuchPeace" instead "stateOfPeace",
        // instead of operations to "stateOfPeace" are faster.
        this.stateOfMuchPeace = new boolean[countOfPeace];
        for (int i = 0; i < countOfPeace; i++) {
            this.stateOfMuchPeace[i] = true;
        }
    }
    
    public boolean checkState(final int departure, final int arrival) {
        
        boolean result = true;
        // The result may not be right,
        // cause "tryModifyState()" could change the states of this seat
        // when we are running "checkState()".
        for (int i = departure - 1; i < arrival - 1; i++) {
            result = result && this.stateOfMuchPeace[i];
        }
        return result;
    }
    
    public synchronized int trySealTick(final int departure, final int arrival) {
        
        boolean result = true;
        for (int i = departure - 1; i < arrival - 1; i++) {
            result = result && this.stateOfMuchPeace[i];
        }
        if (result == true) {
            for (int i = departure - 1; i < arrival - 1; i++) {
                this.stateOfMuchPeace[i] = false;
            }
            return this.seatId;
        } else {
            return -1;
        }
    }
    
    public synchronized boolean tryRefundTick(final int departure, final int arrival) {
        
        // We don't need lock the state when refund tickets,
        // cause when we could sell a ticket without refunding,
        // we can make it after refunding also.
        for (int i = departure - 1; i < arrival - 1; i++) {
            this.stateOfMuchPeace[i] = true;
        }
        return true;
    }
}
    