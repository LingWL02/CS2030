class Arrive extends Event {
    
    public Arrive(Customer customer, double time) {
        super(customer, new HumanServer(0, 0, () -> 0.0), time);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Event next(ImList<Server> servers) {
        for (Server server : servers) {
            if (server.canServe(this.getCustomer(), this.getTime())) {
                return new Serve(this.getCustomer(), server, this.getTime());
            } else {
                continue;
            }
        }

        for (Server server : servers) {
            if (server.canQueue()) {
                return new Wait(this.getCustomer(), server, this.getTime());
            } else {
                continue;
            }
        }
        
        return new Leave(this.getCustomer(), this.getTime());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            "%s arrives\n", this.getCustomer().toString());
    }

}