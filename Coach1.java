/*
*
* Coach1.java
* Guo Jianing
* 2018-Jan-5th
*
*/

package ticketingsystem;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Coach1 {
    private final int coachId;
    private final int countOfSeat;
    private Seat1[] allSeat;
    
    // private volatile int countOfVisitor = 0;
    
    public Coach1(final int coachId, final int countOfStation, final int countOfSeat) {
        
        // If there are more than 33 stations need to be operated,
        // we will used "Seat.stateOfMuchPeace" instead "Seat.stateOfPeace",
        // instead of operations to "Seat.stateOfPeace" are faster.
        this.coachId = coachId;
        this.countOfSeat = countOfSeat;
        this.allSeat = new Seat1[countOfSeat];
        
        for (int i = 0; i < this.countOfSeat; i++) {
            this.allSeat[i] = new Seat1(i + 1, countOfStation - 1);
        }
    }
    
    public int checkFreeSeat1(final int departure, final int arrival) {
        
        int freeCount = 0;
        for (int i = 0; i < this.countOfSeat; i++) {
            if (this.allSeat[i].checkState1(departure, arrival)) {
                freeCount++;
            }
        }
        return freeCount;
    }
    
    public CoachIdAndSeatId trySeal1(final int departure, final int arrival) {
        
        int _seatId = -1;
        CoachIdAndSeatId result = null;   

        int i = 0;
        // int j = this.countOfVisitor;
        // this.countOfVisitor = (this.countOfVisitor + 1) % this.countOfSeat;
        int j = ThreadLocalRandom.current().nextInt(this.countOfSeat);

        while (i < this.countOfSeat) {
            _seatId = this.allSeat[j].trySealTick1(departure, arrival);
            if (_seatId > 0) {
                result = new CoachIdAndSeatId(this.coachId, _seatId);
                break;
            }
            
            i++;
            j = (j + 1) % this.countOfSeat;
        }
        
        return result;
    }
    
    public boolean tryRefund1(final int departure, final int arrival, final int seatId) {
        return this.allSeat[seatId - 1].tryRefundTick1(departure, arrival);
    }
}