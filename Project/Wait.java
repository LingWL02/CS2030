class Wait extends Event {

    public Wait(Customer customer, Server server, double time) {
        super(customer, server.addToQueue(customer), time);
    }

    @Override
    public boolean hasUpdateServer() {
        return true;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Event next(ImList<Server> servers) {
        return new Hold(this.getCustomer(), this.getServer(),
            this.getServer().getNextAvailableTime());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                "%s waits at %s\n", this.getCustomer().toString(), this.getServer().toString());
    }

}