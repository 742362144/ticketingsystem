/*
*
* Coach.java
* Guo Jianing
* 2018-Jan-5th
*
*/

package ticketingsystem;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class CoachIdAndSeatId {
    public int coachId;
    public int seatId;
    
    public CoachIdAndSeatId(int _caochId, int _seatId) {
        
        this.coachId = _caochId;
        this.seatId = _seatId;
    }
}

public class Coach {
    private final boolean muchPeace;
    private final int coachId;
    private final int countOfSeat;
    private Seat[] allSeat;
    
    // private volatile int countOfVisitor = 0;
    
    public Coach(final int coachId, final int countOfStation, final int countOfSeat) {
        
        // If there are more than 33 stations need to be operated,
        // we will used "Seat.stateOfMuchPeace" instead "Seat.stateOfPeace",
        // instead of operations to "Seat.stateOfPeace" are faster.
        this.muchPeace = (countOfStation > 33);
        
        this.coachId = coachId;
        this.countOfSeat = countOfSeat;
        this.allSeat = new Seat[countOfSeat];
        
        for (int i = 0; i < this.countOfSeat; i++) {
            this.allSeat[i] = new Seat(i + 1, countOfStation - 1);
        }
    }
    
    public int checkFreeSeat(final int departure, final int arrival) {
        
        int freeCount = 0;
        if (this.muchPeace) {
            for (int i = 0; i < this.countOfSeat; i++) {
                if (this.allSeat[i].checkState(departure, arrival)) {
                    freeCount++;
                }
            }
        } else {
            for (int i = 0; i < this.countOfSeat; i++) {
                if (this.allSeat[i].checkState1(departure, arrival)) {
                    freeCount++;
                }
            }
        }
        return freeCount;
    }
    
    public CoachIdAndSeatId trySeal(final int departure, final int arrival) {
        
        int _seatId = -1;
        CoachIdAndSeatId result = null;   

        int i = 0;
        // int j = this.countOfVisitor;
        // this.countOfVisitor = (this.countOfVisitor + 1) % this.countOfSeat;
        int j = ThreadLocalRandom.current().nextInt(this.countOfSeat);
        if (this.muchPeace) {
            while (i < this.countOfSeat) {
                _seatId = this.allSeat[j].trySealTick(departure, arrival);
                if (_seatId > 0) {
                    result = new CoachIdAndSeatId(this.coachId, _seatId);
                    break;
                }
                
                i++;
                j = (j + 1) % this.countOfSeat;
            }
        } else {
            while (i < this.countOfSeat) {
                _seatId = this.allSeat[j].trySealTick1(departure, arrival);
                if (_seatId > 0) {
                    result = new CoachIdAndSeatId(this.coachId, _seatId);
                    break;
                }
                
                i++;
                j = (j + 1) % this.countOfSeat;
            }
        }
        
        return result;
    }
    
    public boolean tryRefund(final int departure, final int arrival, final int seatId) {
        boolean result = false;
        if (this.muchPeace) {
            result = this.allSeat[seatId - 1].tryRefundTick(departure, arrival);
        } else {
            result = this.allSeat[seatId - 1].tryRefundTick1(departure, arrival);
        }
        return result;
    }
}