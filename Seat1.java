/*
*
* Seat1.java
* Guo Jianing
* 2018-Jan-5th
*
*/

package ticketingsystem;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Seat1 {
    private final int seatId;
    private AtomicInteger stateOfPeace = null;
    
    public Seat1(final int seatId, final int countOfPeace) {
        
        this.seatId = seatId;
        
        // If there are more than 33 stations need to be operated,
        // we will used "stateOfMuchPeace" instead "stateOfPeace",
        // instead of operations to "stateOfPeace" are faster.
        this.stateOfPeace = new AtomicInteger(0);
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
    