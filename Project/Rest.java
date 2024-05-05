class Rest extends Event {

    public Rest(Customer customer, Server server, double time) {
        super(customer, server.rest(), time);
    }

    @Override
    public boolean hasUpdateServer() {
        return true;
    }

    @Override
    public String toString() {
        return "";
    }

}