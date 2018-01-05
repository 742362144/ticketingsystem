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
    private AtomicInteger stateOfPeace = null;
    private boolean[] stateOfMuchPeace = null;
    
    public Seat(final int seatId, final int countOfPeace) {
        
        this.seatId = seatId;
        
        // If there are more than 33 stations need to be operated,
        // we will used "stateOfMuchPeace" instead "stateOfPeace",
        // instead of operations to "stateOfPeace" are faster.
        if (countOfPeace > 32) {
            this.stateOfMuchPeace = new boolean[countOfPeace];
        } else {
            this.stateOfPeace = new AtomicInteger(0);
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
    
    public boolean checkState1(final int departure, final int arrival) {
        int expect = this.stateOfPeace.get();
        int temp = 0;
        for (int i = departure - 1; i < arrival - 1; i++) {
            int pow = 1;
            pow = pow << i;
            temp += pow;
        }
        int result = temp & expect;
        if (result != 0) {
            /* System.out.println("checkState1: dep:" + 
                departure + " arr: " + 
                arrival + " result: " + 
                Integer.toBinaryString(result) + " stateOfPeace: " +
                Integer.toBinaryString(expect)); */
            return false;
        } else {
            /* System.out.println("checkState1: dep:" + 
                departure + " arr: " + 
                arrival + " result: " + 
                Integer.toBinaryString(result) + " stateOfPeace: " +
                Integer.toBinaryString(expect)); */
            return true;
        }
    }
    
    public int trySealTick1(final int departure, final int arrival) {
        
        int expect = 0;
        int update = 0;
        do {
            expect = this.stateOfPeace.get();
            int temp = 0;
            for (int i = departure - 1; i < arrival - 1; i++) {
                int pow = 1;
                pow = pow << i;
                temp += pow;
            }
            int result = temp & expect;
            if (result != 0) {
                return -1;
            } else {
                update = temp | expect;
            }
        } while (!this.stateOfPeace.compareAndSet(expect, update));
        return this.seatId;
    }
    
    public boolean tryRefundTick1(final int departure, final int arrival) {
        
        int expect = 0;
        int update = 0;
        do {
            expect = this.stateOfPeace.get();
            int temp = 0;
            for (int i = departure - 1; i < arrival - 1; i++) {
                int pow = 1;
                pow = pow << i;
                temp += pow;
            }
            update = (~temp) & expect;
        } while (!this.stateOfPeace.compareAndSet(expect, update));
        return true;
    }
}
    