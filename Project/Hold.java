class Hold extends Event {

    public Hold(Customer customer, Server server, double time) {
        super(customer, server, time);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Event next(ImList<Server> servers) {
        Server tempServer = servers.get(this.getServer().getID() - 1);
        if (tempServer.canServe(this.getCustomer(), this.getTime())) {
            return new Serve(this.getCustomer(), tempServer, this.getTime());
        } else {
            return new Hold(this.getCustomer(), tempServer, tempServer.getNextAvailableTime());
        }
    }

    @Override
    public String toString() {
        return "";
    }
}