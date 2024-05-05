abstract class Event {

    private final Customer customer;
    private final Server server;
    private final double time;

    protected Event(Customer customer, Server server, double time) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Server getServer() {
        return this.server;
    }

    public boolean hasUpdateCustomer() {
        return false;
    }

    public boolean hasUpdateServer() {
        return false;
    }

    public boolean hasNext() {
        return false;
    }

    public Customer updateCustomer() {
        if (this.hasUpdateCustomer()) {
            return this.customer;
        } else {
            throw new IllegalStateException("Event does not update Customer.");
        }
    }

    public Server updateServer() {
        if (this.hasUpdateServer()) {
            return this.server;
        } else {
            throw new IllegalStateException("Event does not update Server.");
        }
    }

    public Event next(ImList<Server> servers) {
        throw new IllegalStateException("Event has no next Event.");
    }

    @Override
    public String toString() {
        return String.format("%.3f ", this.time);
    }

}
