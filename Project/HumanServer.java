import java.lang.IllegalArgumentException;
import java.util.function.Supplier;

class HumanServer extends Server {

    private final Supplier<Double> restTime;

    public HumanServer(int id, int maxQueueSize, Supplier<Double> restTime) {
        super(id, maxQueueSize);
        this.restTime = restTime;
    }

    private HumanServer(int id, double nextAvailableTime, ImList<Customer> queue,
        int maxQueueSize, Supplier<Double> restTime) {
        super(id, maxQueueSize, nextAvailableTime, queue);
        this.restTime = restTime;
    }
    
    @Override
    public HumanServer serve(Customer customer, double time) {
        if (this.canServe(customer, time)) {
            if (this.getQueue().isEmpty()) {
                return new HumanServer(this.getID(), time + customer.getServiceTime(),
                    this.getQueue(), this.getMaxQueueSize(), this.restTime);
            } else {
                return new HumanServer(this.getID(), time + customer.getServiceTime(),
                    this.getQueue().remove(0), this.getMaxQueueSize(), this.restTime);
            }
        } else {
            throw new IllegalArgumentException("HumanServer unavailable to serve Customer.");
        }
    }

    @Override
    public HumanServer addToQueue(Customer customer) {
        if (this.canQueue()) {
            return new HumanServer(this.getID(), this.getNextAvailableTime(),
                this.getQueue().add(customer), this.getMaxQueueSize(), this.restTime);
        } else {
            throw new IllegalArgumentException("HumanServer unavailable to add Customer to queue.");
        }
    }

    @Override
    public HumanServer rest() {
        return new HumanServer(this.getID(),
        this.getNextAvailableTime() + this.restTime.get(),
        this.getQueue(), this.getMaxQueueSize(), this.restTime);
    }

}