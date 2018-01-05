/*
*
* Route1.java
* Guo Jianing
* 2018-Jan-5th
*
*/
package ticketingsystem;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.*;

public class Route1 implements Train {
    private final int routeId;
    private final int countOfStation;
    private final int countOfSeat;
    private final int countOfCoach;
    
    // private Coach[] allCoach;
    private List<Coach1> allCoach;
    
    private volatile int countOfSoldTicket;
    
    private Queue<Integer> queueOfSoldTicket 
        = new ConcurrentLinkedQueue<Integer>();
    
    // private volatile int countOfVisitor = 0;
    
    public Route1(final int routeId,
        final int countOfStation,
        final int countOfCoach,
        final int countOfSeat) {
        
        this.routeId = routeId;
        this.countOfStation = countOfStation;
        this.countOfSeat = countOfSeat;
        this.countOfCoach = countOfCoach;
        
        this.countOfSoldTicket = 0;
        
        // this.allCoach = new Coach[countOfCoach];
        this.allCoach = new ArrayList<Coach1>();
        for (int i = 0; i < countOfCoach; i++) {
            // this.allCoach[i] = new Coach(i + 1, countOfStation, countOfSeat);
            this.allCoach.add(new Coach1(i + 1, countOfStation, countOfSeat));
        }
    }
    
    public int checkFreeSeat(final int departure, final int arrival) {
        
        int freeCount = 0;
        for (int i = 0; i < countOfCoach; i++) {
            // freeCount += this.allCoach[i].checkFreeSeat(departure, arrival);
            freeCount += this.allCoach.get(i).checkFreeSeat1(departure, arrival);
        }
        return freeCount;
    }
    
    public Ticket trySeal(final String name, final int departure, final int arrival) {
        
        Ticket ticket = null;
        CoachIdAndSeatId result = null;
        
        int i = 0;
        int j = ThreadLocalRandom.current().nextInt(this.countOfCoach);
        // int j = this.countOfVisitor;
        // this.countOfVisitor = (this.countOfVisitor + 1) % this.countOfCoach;
        while (i < this.countOfCoach) {
            // result = this.allCoach[i].trySeal(departure, arrival);
            result = this.allCoach.get(j).trySeal1(departure, arrival);
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
                
                Integer hashCodeOfTicket = new Integer(ticket.hashCode());
                this.queueOfSoldTicket.add(hashCodeOfTicket);
                // System.out.println("Bought: tid: " + ticket.tid + " hashCode: " + hashCodeOfTicket.intValue());
                break;
            }
            
            i++;
            j = (j + 1) % this.countOfCoach;
        }
                
        return ticket;
    }
    
    public boolean tryRefund(final Ticket ticket) {
        Integer hashCodeOfTicket = new Integer(ticket.hashCode());
        // System.out.println("Refund: tid: " + ticket.tid + " hashCode: " + hashCodeOfTicket.intValue());
        if (!this.queueOfSoldTicket.contains(hashCodeOfTicket)) {
            return false;
        } else {
            this.queueOfSoldTicket.remove(hashCodeOfTicket);
            final int coachId = ticket.coach;
            final int seatId = ticket.seat;
            final int departure = ticket.departure;
            final int arrival = ticket.arrival;
            return this.allCoach.get(coachId - 1).tryRefund1(departure, arrival, seatId);
        }
    }
}