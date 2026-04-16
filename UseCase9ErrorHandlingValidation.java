import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public boolean isValidRoomType(String type) {
        return availability.containsKey(type);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// Validator
class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}

// Queue (reuse)
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

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("Booking Validation:");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue queue = new BookingRequestQueue();

        try {
            // Input
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validate
            validator.validate(name, roomType, inventory);

            // If valid → add request
            queue.addRequest(new Reservation(name, roomType));

            System.out.println("Booking request added successfully.");

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}