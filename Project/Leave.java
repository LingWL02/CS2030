class Leave extends Event {

    public Leave(Customer customer, double time) {
        super(customer, new HumanServer(0, 0, () -> 0.0), time);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%s leaves\n", super.getCustomer().toString());
    }
    
}