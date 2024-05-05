class Serve extends Event {

    public Serve(Customer customer, Server server, double time) {
        super(customer.served(time), server.serve(customer, time), time);
    }

    @Override
    public boolean hasUpdateCustomer() {
        return true;
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
        return new Done(this.getCustomer(), this.getServer(),
            this.getServer().doneTime());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                "%s serves by %s\n", this.getCustomer().toString(), this.getServer().toString());
    }

}