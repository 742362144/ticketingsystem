package ticketingsystem;

public class Route {
    private final int routeId;
    private final int countOfStation;
    private final int countOfSeat;
    private final int countOfCoach;
    
    private Coach[] allCoach;
    
    private volatile int countOfSoldTicket;
    
    private volatile int countOfVisitor;
    
    public Route(final int routeId,
        final int countOfStation,
        final int countOfCoach,
        final int countOfSeat) {
        
        this.routeId = routeId;
        this.countOfStation = countOfStation;
        this.countOfSeat = countOfSeat;
        this.countOfCoach = countOfCoach;
        
        this.countOfSoldTicket = 0;
        this.countOfVisitor = 0;
        
        this.allCoach = new Coach[countOfCoach];
        for (int i = 0; i < countOfCoach; i++) {
            this.allCoach[i] = new Coach(i + 1, countOfStation, countOfSeat);
        }
    }
    
    public int checkFreeSeat(final int departure, final int arrival) {
        
        int freeCount = 0;
        for (int i = 0; i < countOfCoach; i++) {
            freeCount += this.allCoach[i].checkFreeSeat(departure, arrival);
        }
        return freeCount;
    }
    
    public Ticket trySeal(final String name, final int departure, final int arrival) {
        
        Ticket ticket = null;
        CoachIdAndSeatId result = null;
        
<<<<<<< HEAD
        // We will let visitors go to different coachs.
        int i = 0;
        int j = countOfVisitor % this.countOfCoach;
        this.countOfVisitor++;
        while (i < countOfCoach) {
            result = this.allCoach[j].tryModifySeatState(departure, arrival, 0, 0);
            
=======
        int i = 0;
        while (i < countOfCoach) {
            result = this.allCoach[i].tryModifySeatState(departure, arrival, 0, 0);
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
            if (result != null) {
                this.countOfSoldTicket += 1;
                // Create a ticket.
                ticket = new Ticket();
                ticket.tid = this.routeId * 10000 + this.countOfSoldTicket;
                ticket.passenger = name;
                ticket.route = this.routeId;
                ticket.coach = result.coachId;
                ticket.seat = result.seatId;
                ticket.departure = departure;
                ticket.arrival = arrival;
                break;
            }
            
            i++;
<<<<<<< HEAD
            j = (j + 1) % this.countOfCoach;
=======
>>>>>>> cb1b5ccd3e277e177043d1872d463557debd8eb3
        }
                
        return ticket;
    }
    
    public boolean tryRefund(final int coachId, final int seatId, final int departure, final int arrival) {
        
        this.allCoach[coachId - 1].tryModifySeatState(departure, arrival, 1, seatId);
        return true;
    }
}