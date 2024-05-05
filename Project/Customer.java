import java.util.function.Supplier;

class Customer {

    private final int id;
    private final double arrivalTime;
    private final double serveTime;
    private final Supplier<Double> serviceTime;
    private final boolean isServed;

    public Customer(int id, double arrivalTime, Supplier<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serveTime = 0;
        this.serviceTime = serviceTime;
        this.isServed = false;
    }

    private Customer(int id, double arrivalTime, double serveTime,
        Supplier<Double> serviceTime, boolean isServed) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serveTime = serveTime;
        this.serviceTime = serviceTime;
        this.isServed = isServed;
    }

    public int getID() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServeTime() {
        return this.serveTime;
    }

    public double getServiceTime() {
        return this.serviceTime.get();
    }

    public double getWaitTime() {
        return this.serveTime - this.arrivalTime;
    }

    public boolean getIsServed() {
        return this.isServed;
    }

    public Customer served(double time) {
        return new Customer(this.id, this.arrivalTime, time, this.serviceTime, true);
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }

}