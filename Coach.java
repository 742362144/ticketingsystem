package ticketingsystem;

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
    
<<<<<<< HEAD
    private volatile int countOfVisitor;
=======
    public boolean isBusy = false;
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
    
    public Coach(final int coachId, final int countOfStation, final int countOfSeat) {
        
        this.coachId = coachId;
        this.countOfSeat = countOfSeat;
        this.allSeat = new Seat[countOfSeat];
        
        this.countOfVisitor = 0;
        
        for (int i = 0; i < this.countOfSeat; i++) {
            this.allSeat[i] = new Seat(i + 1, countOfStation - 1);
        }
    }
    
    public int checkFreeSeat(final int departure, final int arrival) {
        
        int freeCount = 0;
        for (int i = 0; i < this.countOfSeat; i++) {
            if (this.allSeat[i].checkState(departure, arrival) == true) {
                freeCount++;
            }
        }
        return freeCount;
    }
    
    public CoachIdAndSeatId tryModifySeatState(final int departure, final int arrival, final int SEAL_REFUND, final int seatId) {
        
        if (SEAL_REFUND == 0) {
            int _seatId = -1;
<<<<<<< HEAD
            CoachIdAndSeatId result = null;
            
            int i = 0;
            int j = this.countOfVisitor % this.countOfSeat;
            this.countOfVisitor++;
            while (i < this.countOfSeat) {
=======
            CoachIdAndSeatId result = null; 

            int i = 0;
            while (i < this.countOfSeat) {
                if (this.allSeat[i].isBusy) {
                    i++;
                    continue;
                }
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
                _seatId = this.allSeat[i].tryModifyState(departure, arrival, 0);
                if (_seatId > 0) {
                    result = new CoachIdAndSeatId(this.coachId, _seatId);
                    break;
                }
<<<<<<< HEAD
                
                i++;
                j = (j + 1) % this.countOfSeat;
=======
                i++;
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
            }
            
            return result;
        } else {
            this.allSeat[seatId - 1].tryModifyState(departure, arrival, 1);
            return null;
        }
    }
}