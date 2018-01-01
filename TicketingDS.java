package ticketingsystem;

import java.util.*;

public class TicketingDS implements TicketingSystem {
		
	// private Route[] allRoutes;
    private final List<Route> allRoutes;
    
    public TicketingDS(int countOfRoute,
        int countOfCoach,
        int countOfSeat,
        int countOfStation,
        int countOfThread) {
        
        // this.allRoutes = new Route[countOfRoute];
        this.allRoutes = new ArrayList<Route>();
        for (int i = 0; i < countOfRoute; i++) {
            // this.allRoutes[i] = new Route(i + 1, countOfStation, countOfCoach, countOfSeat);
            allRoutes.add(new Route(i + 1, countOfStation, countOfCoach, countOfSeat));
        }
    }
    
    public Ticket buyTicket(String name, int route, int departure, int arrival) {
        
        // return this.allRoutes[route - 1].trySeal(name, departure, arrival);
        return this.allRoutes.get(route - 1).trySeal(name, departure, arrival);
    }
    
    public int inquiry(int route, int departure, int arrival) {
        
        // return this.allRoutes[route - 1].checkFreeSeat(departure, arrival);
        return this.allRoutes.get(route - 1).checkFreeSeat(departure, arrival);
    }
    
    public boolean refundTicket(Ticket ticket) {
        
        final int route = ticket.route;
        final int coachId = ticket.coach;
        final int seatId = ticket.seat;
        final int departure = ticket.departure;
        final int arrival = ticket.arrival;
        
        // return this.allRoutes[route - 1].tryRefund(coachId, seatId, departure, arrival);
        return this.allRoutes.get(route - 1).tryRefund(coachId, seatId, departure, arrival);
    }

}
