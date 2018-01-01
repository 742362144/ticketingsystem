package ticketingsystem;

public class Seat {
    private final int seatId;
    private boolean[] stateOfPeace;
    
    public Seat(final int seatId, final int countOfPeace) {
        
        this.seatId = seatId;
        this.stateOfPeace = new boolean[countOfPeace];
        // Java will initialize the defaule value (false) of "stateOfPeace",
        // but we will do it (true) in the right direction.
        for (int i = 0; i < countOfPeace; i++) {
            this.stateOfPeace[i] = true;
        }
    }
    
    public boolean checkState(final int departure, final int arrival) {
        
        boolean result = false;
        // The result may not be right,
        // cause "tryModifyState()" could change the states of this seat
        // when we are running "checkState()".
        for (int i = departure - 1; i < arrival - 1; i++) {
            if (this.stateOfPeace[i] == false) {
                result = false;
                break;
            } else {
                result = true;
            }
        }
        return result;
    }
    
    public synchronized int tryModifyState(final int departure, final int arrival, final int SEAL_REFUND) {
        
        if (SEAL_REFUND == 0) {
            boolean result = false;
            int _seatId = -1;
            // Firstly, we will check whether we can seal this ticket.
            for (int i = departure - 1; i < arrival - 1; i++) {
                if (this.stateOfPeace[i] == false) {
                    result = false;
                    break;
                } else {
                    result = true;
                }
            }
            // Then, we will update states of this seat.
            if (result = true) {
                for (int i = departure - 1; i < arrival - 1; i++) {
                    this.stateOfPeace[i] = false;
                }
                _seatId = this.seatId;
            }
            
            return _seatId;
        } else {
            for (int i = departure - 1; i < arrival - 1; i++) {
                this.stateOfPeace[i] = false;
            }
            return 0;
        }
    }
}
    