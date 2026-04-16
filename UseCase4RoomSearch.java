import java.util.HashMap;
import java.util.Map;

// Room class (reuse concept)
class Room {
    String type;
    int beds;
    int size;
    double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void display(int available) {
        System.out.println(type + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available: " + available);
        System.out.println();
    }
}

// Inventory (read-only access)
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availability;
    }
}

// Search Service (READ ONLY)
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search\n");

        // Single
        if (availability.get("Single") > 0) {
            singleRoom.display(availability.get("Single"));
        }

        // Double
        if (availability.get("Double") > 0) {
            doubleRoom.display(availability.get("Double"));
        }

        // Suite
        if (availability.get("Suite") > 0) {
            suiteRoom.display(availability.get("Suite"));
        }
    }
}

// Main class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Room objects
        Room single = new Room("Single Room", 1, 250, 1500);
        Room dbl = new Room("Double Room", 2, 400, 2500);
        Room suite = new Room("Suite Room", 3, 750, 5000);

        // Search service
        RoomSearchService service = new RoomSearchService();

        // Perform search
        service.searchAvailableRooms(inventory, single, dbl, suite);
    }
}