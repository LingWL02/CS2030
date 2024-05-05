import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

class SelfCheckoutCounterGroup extends Server {

    private final ImList<SelfCheckoutCounter> selfCheckoutCounters;
    private final int currentIndex;

    public SelfCheckoutCounterGroup(int id, int maxQueueSize, int n) {
        super(id, maxQueueSize);
        ImList<SelfCheckoutCounter> temp = new ImList<SelfCheckoutCounter>();
        for (int i = id; i < id + n; i++) {
            temp = temp.add(new SelfCheckoutCounter(i, 0));
        }
        this.selfCheckoutCounters = temp;
        this.currentIndex = 0;
    }

    private SelfCheckoutCounterGroup(int id, int maxQueueSize,
        double nextAvailableTime, ImList<Customer> queue,
        ImList<SelfCheckoutCounter> selfCheckoutCounters, int currentIndex) {
        super(id, maxQueueSize, nextAvailableTime, queue);
        this.selfCheckoutCounters = selfCheckoutCounters;
        this.currentIndex = currentIndex;
    }

    @Override
    public double doneTime() {
        return this.selfCheckoutCounters.get(this.currentIndex).doneTime();
    }

    @Override
    public SelfCheckoutCounterGroup serve(Customer customer, double time) {
        if (this.canServe(customer, time)) {
            for (int i = 0; i < selfCheckoutCounters.size(); i++) {
                SelfCheckoutCounter tempSCC = selfCheckoutCounters.get(i);
                if (tempSCC.canServe(customer, time)) {
                    tempSCC = tempSCC.serve(customer, time);
                    ImList<SelfCheckoutCounter> tempImLSCC =
                        this.selfCheckoutCounters.set(i, tempSCC);
                    double tempNextAvailableTime = tempImLSCC.stream()
                        .mapToDouble(x -> x.getNextAvailableTime())
                        .reduce((x, y) -> x < y ? x : y).getAsDouble();
                    if (this.getQueue().isEmpty()) {
                        return new SelfCheckoutCounterGroup(this.getID(), this.getMaxQueueSize(),
                            tempNextAvailableTime, this.getQueue(),
                            tempImLSCC, i);
                    } else {
                        return new SelfCheckoutCounterGroup(this.getID(),this.getMaxQueueSize(),
                            tempNextAvailableTime, this.getQueue().remove(0),
                            tempImLSCC, i);
                    }
                } else {
                    continue;
                }
            }
        } else {
            throw new IllegalArgumentException(
                "SelfCheckoutCounterGroup unavailable to serve Customer.");
        }
        throw new IllegalStateException("SelfCheckoutCounterGroup logic error.");
    }

    @Override
    public SelfCheckoutCounterGroup addToQueue(Customer customer) {
        if (this.canQueue()) {
            return new SelfCheckoutCounterGroup(this.getID(), this.getMaxQueueSize(),
                this.getNextAvailableTime(), this.getQueue().add(customer),
                this.selfCheckoutCounters, 0);
        } else {
            throw new IllegalArgumentException(
                "SelfCheckoutCounterGroup unavailable to add Customer to queue.");
        }
    }

    @Override
    public String toString() {
        return this.selfCheckoutCounters.get(this.currentIndex).toString();
    }

}