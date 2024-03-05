import java.util.ArrayList;
import java.util.List;

enum PassengerType {
    STANDARD, GOLD, PREMIUM
}

class TravelPackage {
    private String name;
    private int passengerCapacity;
    private Itinerary itinerary;
    private List<Passenger> passengers;

    public TravelPackage(String name, int passengerCapacity, Itinerary itinerary) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.itinerary = itinerary;
        this.passengers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    // Enroll a passenger for an activity at a destination
    public boolean enrollPassenger(Passenger passenger, Activity activity, Destination destination) {
        if (passenger != null && activity != null && destination != null &&
            passengers.size() < passengerCapacity && activity.getCapacity() > 0) {

            double cost = activity.getCost();
            PassengerType passengerType = passenger.getType();

            switch (passengerType) {
                case GOLD:
                    double discount = 0.10;
                    double discountedAmount = cost * discount;
                    passenger.setBalance(passenger.getBalance() - (cost - discountedAmount));
                    break;

                case STANDARD:
                    passenger.setBalance(passenger.getBalance() - cost);
                    break;

                case PREMIUM:
                    // Premium passengers sign up for activities for free
                    break;

                default:
                    // Handle unknown passenger type
                    return false;
            }

            Enrollment enrollment = new Enrollment(activity, destination, cost, passengerType);
            passenger.addEnrollment(enrollment);
            activity.addPassenger(passenger);

            // Add passenger to the TravelPackage
            if(!passengers.contains(passenger))passengers.add(passenger);
            return true;
        } else {
            return false;
        }
    }


    // Print details of an individual passenger
    public void printPassengerDetails(Passenger passenger) {
        if (passengers.contains(passenger)) {
            passenger.printDetails();
        } else {
            System.out.println("Passenger not found in this travel package.");
        }
    }

    // Print itinerary details
    public void printItineraryDetails() {
        System.out.println("Travel Package: " + name);
        itinerary.printItineraryDetails();
    }

    // Print passenger list details
    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("Name: " + passenger.getName());
        }
    }
}

class Itinerary {
    private List<Destination> destinations;

    public Itinerary(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    // Print itinerary details
    public void printItineraryDetails() {
        for (Destination destination : destinations) {
            destination.printDetails();
        }
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name, List<Activity> activities) {
        this.name = name;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    // Print destination details
    public void printDetails() {
        System.out.println("Destination: " + name);
        for (Activity activity : activities) {
            activity.printDetails();
        }
    }
}

class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private List<Passenger> enrolledPassengers;
    private Destination destination;

    public Activity(String name, String description, double cost, int capacity, Destination destination) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.enrolledPassengers = new ArrayList<>();
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getCost(){
        return cost;
    }

    // Add a passenger to the enrolled list
    public boolean addPassenger(Passenger passenger) {
        if (!enrolledPassengers.contains(passenger) && enrolledPassengers.size() < capacity) {
            enrolledPassengers.add(passenger);
            return true;
        }
        return false;
    }

    // Print activity details
    public void printDetails() {
        System.out.println("Activity: " + name);
        System.out.println("Description: " + description);
        System.out.println("Cost: " + cost);
        System.out.println("Capacity: " + capacity);
    }

    // Get the destination of the activity
    public Destination getDestination() {
        return destination;
    }
}

class Passenger {
    private String name;
    private int passengerNumber;
    private PassengerType type;
    private double balance;
    private List<Enrollment> enrollments;

    public Passenger(String name, int passengerNumber, PassengerType type, double balance) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.balance = balance;
        this.enrollments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public PassengerType getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Add an enrollment to the list
    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    // print passenger details
    public void printDetails() {
        System.out.println("Passenger Details: ");
        System.out.println("Name: " + name);
        System.out.println("Passenger Number: " + passengerNumber);
        System.out.println("Type: " + type);
        System.out.println("Balance: " + balance);

        if (!enrollments.isEmpty()) {
            System.out.println("Enrollments: ");
            for (Enrollment enrollment : enrollments) {
                System.out.println("Activity: " + enrollment.getActivity().getName() +
                        ", Destination: " + enrollment.getDestination().getName() +
                        ", Price: " + enrollment.getPrice() +
                        ", Passenger Type: " + enrollment.getPassengerType());
            }
        }
    }
}

class Enrollment {
    private Activity activity;
    private Destination destination;
    private double price;
    private PassengerType passengerType;

    public Enrollment(Activity activity, Destination destination, double price, PassengerType passengerType) {
        this.activity = activity;
        this.destination = destination;
        this.price = price;
        this.passengerType = passengerType;
    }

    public Activity getActivity() {
        return activity;
    }

    public Destination getDestination() {
        return destination;
    }

    public double getPrice() {
        return price;
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }
}

class TravelPackageManager {
    private List<TravelPackage> travelPackages;

    public TravelPackageManager() {
        this.travelPackages = new ArrayList<>();
    }

    public TravelPackage createTravelPackage(String name, int capacity, Itinerary itinerary) {
        TravelPackage travelPackage = new TravelPackage(name, capacity, itinerary);
        travelPackages.add(travelPackage);
        return travelPackage;
    }
}

public class TravelPackageBooking {
    public static void main(String[] args) {
        // Example
        List<Activity> brahmatalActivities = new ArrayList<>();
        Destination brahmatal = new Destination("Brahmatal", brahmatalActivities);
        brahmatalActivities.add(new Activity("Camping", "Overnight stays with a basic temporary shelter", 1000, 20, brahmatal));
        brahmatalActivities.add(new Activity("Brahmatal Trek", "Hiking in the mountains", 1500.0, 15, brahmatal));
        
        List<Activity> harshilValleyActivities = new ArrayList<>();
        Destination harshilValley = new Destination("Harshil Valley", harshilValleyActivities);
        harshilValleyActivities.add(new Activity("Gangotri Temple", "Located at an altitude of 3,100mts, Gangotri Temple, the highest temple dedicated to Goddess Ganga", 1000.0, 35, harshilValley));
        harshilValleyActivities.add(new Activity("Dharali Village", "Dharali is a picturesque hamlet nestled on the tranquil banks of river Ganges.", 500.0, 35, harshilValley));
        harshilValleyActivities.add(new Activity("pokhari Bugyal Trek", "This 6 kms long trek is a gradual ascend landscaped beautifully with Rhododendrons.", 3000.0, 15, harshilValley));

        List<Destination> destinations = new ArrayList<>();
        destinations.add(brahmatal);
        destinations.add(harshilValley);

        Itinerary itinerary = new Itinerary(destinations);

        TravelPackageManager manager = new TravelPackageManager();
        TravelPackage travelPackage = manager.createTravelPackage("Himalayan Explorers' Club", 100, itinerary);

        // Creating passengers
        Passenger standardPassenger1 = new Passenger("Rahul", 1, PassengerType.STANDARD, 10000.0);
        Passenger standardPassenger2 = new Passenger("Radhika", 2, PassengerType.STANDARD, 15000.0);
        Passenger goldPassenger1 = new Passenger("Sakshi", 5, PassengerType.GOLD, 25000.0);
        Passenger goldPassenger2 = new Passenger("Aman", 6, PassengerType.GOLD, 20000.0);
        Passenger premiumPassenger1 = new Passenger("Tanya", 7, PassengerType.PREMIUM, 30000.0);

        // Enrolling passengers for activities
        travelPackage.enrollPassenger(standardPassenger1, brahmatalActivities.get(0), brahmatal);
        travelPackage.enrollPassenger(standardPassenger1, brahmatalActivities.get(1), brahmatal);
        travelPackage.enrollPassenger(standardPassenger1, harshilValleyActivities.get(0), harshilValley);
        travelPackage.enrollPassenger(standardPassenger2, brahmatalActivities.get(0), brahmatal);
        travelPackage.enrollPassenger(standardPassenger2, harshilValleyActivities.get(2), harshilValley);
        travelPackage.enrollPassenger(goldPassenger1, brahmatalActivities.get(0), brahmatal);
        travelPackage.enrollPassenger(goldPassenger1, brahmatalActivities.get(0), brahmatal);
        travelPackage.enrollPassenger(goldPassenger2, harshilValleyActivities.get(0), harshilValley);
        travelPackage.enrollPassenger(goldPassenger2, harshilValleyActivities.get(1), harshilValley);
        travelPackage.enrollPassenger(premiumPassenger1, brahmatalActivities.get(0), brahmatal);
        travelPackage.enrollPassenger(premiumPassenger1, harshilValleyActivities.get(1), harshilValley);


        // Printing details
        travelPackage.printItineraryDetails();
        travelPackage.printPassengerList();
        travelPackage.printPassengerDetails(standardPassenger1);
        travelPackage.printPassengerDetails(goldPassenger2);
        travelPackage.printPassengerDetails(premiumPassenger1);
    }
}
