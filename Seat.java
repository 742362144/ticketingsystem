package ticketingsystem;

import java.util.Arrays;

public class Seat {
    private final int seatId;
    private boolean[] stateOfPeace;
    
    private final Object lockState;
    // private volatile int countOfSold;
    public boolean isBusy = false;
    
    public Seat(final int seatId, final int countOfPeace) {
        
        this.seatId = seatId;
        this.stateOfPeace = new boolean[countOfPeace];
        this.lockState = new Object();
<<<<<<< HEAD
        
        this.countOfSold = 0;
=======
        // this.countOfSold = 0;
        
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
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
    
    /*
    private int trySealTick1(final int departure, final int arrival) {
        while (true) {
            boolean[] state = null;
            int countOfSoldBk = 0;
            
            synchronized(lockState) {
                state = Arrays.copyOf(this.stateOfPeace, this.stateOfPeace.length);
                countOfSoldBk = this.countOfSold;
            }
            
            boolean result = true;
            for (int i = departure - 1; i < arrival - 1; i++) {
                result = result && state[i];
            }
            if (result == true) {
                for (int i = departure - 1; i < arrival - 1; i++) {
                    state[i] = false;
                }
            
                synchronized(lockState) {
                    if (countOfSoldBk == this.countOfSold) {
                        this.stateOfPeace = state;
                        countOfSold += 1;
                        return this.seatId;
                    }
                }
            } else {
                return -1;
            }
        }
    }
    
    private synchronized int trySealTick2(final int departure, final int arrival) {
        
        for (int i = departure - 1; i < arrival - 1; i++) {
            if (this.stateOfPeace[i] == false) {
                return -1;
            }
        }
        for (int i = departure - 1; i < arrival - 1; i++) {
            this.stateOfPeace[i] = false;
        }
        return this.seatId;
    } */
    
    private synchronized int trySealTick(final int departure, final int arrival) {
        
        boolean result = true;
        int _seatId = -1;
<<<<<<< HEAD
        
        for (int i = departure - 1; i < arrival - 1; i++) {
            result = result && this.stateOfPeace[i];
        }
        if (result == true) {
=======
        try {
            this.isBusy = true;
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
            for (int i = departure - 1; i < arrival - 1; i++) {
                this.stateOfPeace[i] = false;
            }
<<<<<<< HEAD
        }
        
        return result ? this.seatId : _seatId;
=======
        } finally {
            this.isBusy = false;
            return result ? this.seatId : _seatId;
        }
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
    }
    
    private int tryRefundTick(final int departure, final int arrival) {
        // We don't need lock the state when refund tickets,
        // cause when we could sell a ticket without refunding,
        // we can make it after refunding also.
        for (int i = departure - 1; i < arrival - 1; i++) {
            this.stateOfPeace[i] = true;
        }
        return 0;
    }
    
    public int tryModifyState(final int departure, final int arrival, final int SEAL_REFUND) {
        
        return (SEAL_REFUND == 0) ? trySealTick(departure, arrival) : tryRefundTick(departure, arrival);
    }
}
    