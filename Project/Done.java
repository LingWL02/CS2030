class Done extends Event {

    public Done(Customer customer, Server server, double time) {
        super(customer, server, time);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Event next(ImList<Server> servers) {
        return new Rest(new Customer(0, 0.0, () -> 0.0),
            servers.get(this.getServer().getID() - 1),
            this.getTime());
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(
                "%s done serving by %s\n",
                this.getCustomer().toString(), this.getServer().toString());
    }

}