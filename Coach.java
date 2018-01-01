package ticketingsystem;

import java.util.*;

class CoachIdAndSeatId {
    public int coachId;
    public int seatId;
    
    public CoachIdAndSeatId(int _caochId, int _seatId) {
        
        this.coachId = _caochId;
        this.seatId = _seatId;
    }
}

public class Coach {
    private final int coachId;
    private final int countOfSeat;
    private Seat[] allSeat;
    
    // private volatile int countOfVisitor = 0;
    private Random rand = new Random();
    
    public Coach(final int coachId, final int countOfStation, final int countOfSeat) {
        
        this.coachId = coachId;
        this.countOfSeat = countOfSeat;
        this.allSeat = new Seat[countOfSeat];
        
        for (int i = 0; i < this.countOfSeat; i++) {
            this.allSeat[i] = new Seat(i + 1, countOfStation - 1);
        }
    }
    
    public int checkFreeSeat(final int departure, final int arrival) {
        
        int freeCount = 0;
        for (int i = 0; i < this.countOfSeat; i++) {
            if (this.allSeat[i].checkState(departure, arrival)) {
                freeCount++;
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
        int j = this.rand.nextInt(this.countOfSeat);
        while (i < this.countOfSeat) {
            _seatId = this.allSeat[j].trySealTick(departure, arrival);
            if (_seatId > 0) {
                result = new CoachIdAndSeatId(this.coachId, _seatId);
                break;
            }
            
            i++;
            j = (j + 1) % this.countOfSeat;
        }
        
        return result;
    }
    
    public boolean tryRefund(final int departure, final int arrival, final int seatId) {
        
        return this.allSeat[seatId - 1].tryRefundTick(departure, arrival);
    }
}