import java.util.*;

// Reservation (reuse)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Queue (reuse)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Allocation Service
class RoomAllocationService {

    // Prevent duplicate room IDs
    private Set<String> allocatedRooms = new HashSet<>();

    // Track per type
    private Map<String, Set<String>> roomMap = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        // Check availability
        if (inventory.getAvailability(type) <= 0) {
            System.out.println("No rooms available for " + r.getGuestName());
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(type);

        // Store globally
        allocatedRooms.add(roomId);

        // Store per type
        roomMap.putIfAbsent(type, new HashSet<>());
        roomMap.get(type).add(roomId);

        // Update inventory
        inventory.reduceAvailability(type);

        // Confirm booking
        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName() + ", Room ID: " + roomId);
    }

    // Generate unique ID
    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 1) + "-" + (allocatedRooms.size() + 1);
        } while (allocatedRooms.contains(id));
        return id;
    }
}

// Main class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Setup
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Requests (FIFO)
        queue.addRequest(new Reservation("Adhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Double"));
        queue.addRequest(new Reservation("Vanathi", "Suite"));

        // Process queue
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            service.allocateRoom(r, inventory);
        }
    }
}