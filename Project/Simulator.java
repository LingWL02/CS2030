import java.util.function.Supplier;

class Simulator {
    private final int numOfHumanServers;
    private final int numOfSelfCheckoutCounters;
    private final int maxQueueSize;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;

    public Simulator(int numOfHumanServers, int numOfSelfCheckoutCounters,
        int maxQueueSize, ImList<Double> arrivalTimes, 
        Supplier<Double> serviceTimes, Supplier<Double> restTimes) {
        this.numOfHumanServers = numOfHumanServers;
        this.numOfSelfCheckoutCounters = numOfSelfCheckoutCounters;
        this.maxQueueSize = maxQueueSize;
        this.restTimes = restTimes;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
    }

    public String simulate() {
        ImList<Customer> customers = new ImList<Customer>();
        ImList<Server> servers = new ImList<Server>();
        PQ<Event> events = new PQ<Event>(new EventComparator());
        String output = new String("");
        double totalWaitTime = 0;
        int numberOfCustomerServed = 0;
        int numberOfCustomerLeft = 0;

        for (int s = 0; s < numOfHumanServers; s++) {
            servers = servers.add(new HumanServer(s + 1, maxQueueSize, restTimes));
        }

        if (numOfSelfCheckoutCounters > 0) {
            servers = servers.add(new SelfCheckoutCounterGroup(
                numOfHumanServers + 1, maxQueueSize, numOfSelfCheckoutCounters));
        }

        for (int c = 0; c < arrivalTimes.size(); c++) {
            Customer tempCustomer = new Customer(c + 1, arrivalTimes.get(c), serviceTimes);
            customers = customers.add(tempCustomer);
            events = events.add(new Arrive(tempCustomer, tempCustomer.getArrivalTime()));
        }

        while (!events.isEmpty()) {
            Event currentEvent = events.poll().first();
            events = events.poll().second();
            output = output + currentEvent.toString();

            if (currentEvent.hasUpdateCustomer()) {
                Customer updateCustomer = currentEvent.updateCustomer();
                customers = customers.set(updateCustomer.getID() - 1, updateCustomer);
            } 

            if (currentEvent.hasUpdateServer()) {
                Server updateServer = currentEvent.updateServer();
                servers = servers.set(updateServer.getID() - 1, updateServer);
            }

            if (currentEvent.hasNext()) {
                events = events.add(currentEvent.next(servers));
            } 
        }

        for (Customer customer : customers) {
            if (customer.getIsServed()) {
                totalWaitTime = totalWaitTime + customer.getWaitTime();
                numberOfCustomerServed++;
            } else {
                numberOfCustomerLeft++;
            }
        }
        return output + String.format("[%.3f %s %s]", (totalWaitTime / numberOfCustomerServed),
            numberOfCustomerServed, numberOfCustomerLeft);
    }

}