package ticketingsystem;

import java.util.*;

public class Route {
    private final int routeId;
    private final int countOfStation;
    private final int countOfSeat;
    private final int countOfCoach;
    
    private Coach[] allCoach;
    
    private volatile int countOfSoldTicket;
    
    private volatile int countOfVisitor = 0;
    
    public Route(final int routeId,
        final int countOfStation,
        final int countOfCoach,
        final int countOfSeat) {
        
        this.routeId = routeId;
        this.countOfStation = countOfStation;
        this.countOfSeat = countOfSeat;
        this.countOfCoach = countOfCoach;
        
        this.countOfSoldTicket = 0;
        
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
        
        // Random rand = new Random();
        int i = 0;
        // int j = rand.nextInt(this.countOfCoach);
        int j = this.countOfVisitor % this.countOfCoach;
        this.countOfVisitor++;
        while (i < this.countOfCoach) {
            result = this.allCoach[j].trySeal(departure, arrival);
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
            j = (j + 1) % this.countOfCoach;
        }
                
        return ticket;
    }
    
    public boolean tryRefund(final int coachId, final int seatId, final int departure, final int arrival) {
        
        this.allCoach[coachId - 1].tryRefund(departure, arrival, seatId);
        return true;
    }
}