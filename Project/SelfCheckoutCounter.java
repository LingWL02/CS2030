import java.lang.IllegalArgumentException;

class SelfCheckoutCounter extends Server {

    public SelfCheckoutCounter(int id, int maxQueueSize) {
        super(id, maxQueueSize);
    }

    private SelfCheckoutCounter(int id, int maxQueueSize, double nextAvailableTime) {
        super(id, maxQueueSize, nextAvailableTime, new ImList<Customer>());
    }

    @Override
    public boolean canQueue() {
        return false;
    }

    @Override
    public SelfCheckoutCounter serve(Customer customer, double time) {
        if (this.canServe(customer, time)) {
            return new SelfCheckoutCounter(this.getID(), this.getMaxQueueSize(),
                time + customer.getServiceTime());
        } else {
            throw new IllegalArgumentException(
                "SelfCheckoutCounter unavailable to serve Customer.");
        }
    }

    @Override
    public SelfCheckoutCounter addToQueue(Customer customer) {
        throw new IllegalArgumentException(
            "SelfCheckoutCounter unavailable to add Customer to queue.");
    }

    @Override
    public String toString() {
        return String.format("self-check %s", this.getID());
    }
    
}