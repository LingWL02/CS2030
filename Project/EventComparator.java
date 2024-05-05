import java.util.Comparator;

class EventComparator implements Comparator<Event> {

    public int compare(Event e1, Event e2) {
        if (e1.getTime() == e2.getTime()) {
            if (e1.getCustomer().getID() == e2.getCustomer().getID()) {
                return Integer.compare(e1.getServer().getID(), e2.getServer().getID());
            } else {
                return Integer.compare(e1.getCustomer().getID(), e2.getCustomer().getID());
            }
        } else {
            return Double.compare(e1.getTime(), e2.getTime());
        }
    }
}
