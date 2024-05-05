abstract class Server {
    
    private final int id;
    private final int maxQueueSize;
    private final double nextAvailableTime;
    private final ImList<Customer> queue;

    protected Server(int id, int maxQueueSize) {
        this.id = id;
        this.maxQueueSize = maxQueueSize;
        this.nextAvailableTime = 0;
        this.queue = new ImList<Customer>();
    }

    protected Server(int id, int maxQueueSize,
        double nextAvailableTime, ImList<Customer> queue) {
        this.id = id;
        this.maxQueueSize = maxQueueSize;
        this.nextAvailableTime = nextAvailableTime;
        this.queue = queue;
    }

    public int getID() {
        return this.id;
    }

    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    public ImList<Customer> getQueue() {
        return this.queue;
    }

    public double doneTime() {
        return this.nextAvailableTime;
    }

    public boolean canServe(Customer customer, double time) {
        if (this.queue.isEmpty() && this.nextAvailableTime <= time) {
            return true;
        } else if (!this.queue.isEmpty() && this.nextAvailableTime <= time) {
            return this.queue.get(0).getID() == customer.getID();
        } else {
            return false;
        }
    }

    public boolean canQueue() {
        return this.getQueue().size() < this.maxQueueSize;
    }

    public abstract Server serve(Customer customer, double time);

    public abstract Server addToQueue(Customer customer);

    public Server rest() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }

}
