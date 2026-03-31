import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Warehouse Inventory Management System
 * Single File Version - All classes in one file
 */
public class Warehouse {

    // Inner Item Class
    static class Item {
        private String itemId;
        private String itemName;
        private int quantity;
        private double price;

        public Item(String itemId, String itemName, int quantity, double price) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = Math.max(0, quantity);
            this.price = Math.max(0, price);
        }

        // Getters and Setters
        public String getItemId() { return itemId; }
        public String getItemName() { return itemName; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }

        public void setQuantity(int quantity) {
            this.quantity = Math.max(0, quantity);
        }

        public void updateQuantity(int amount) {
            this.quantity = Math.max(0, this.quantity + amount);
        }

        @Override
        public String toString() {
            return String.format("Item ID   : %s\n" +
                               "Name      : %s\n" +
                               "Quantity  : %d\n" +
                               "Price     : $%.2f\n" +
                               "-----------------------------",
                               itemId, itemName, quantity, price);
        }
    }

    // Inner Inventory Class
    static class Inventory {
        private List<Item> items;

        public Inventory() {
            this.items = new ArrayList<>();
        }

        public boolean addItem(Item item) {
            for (Item existing : items) {
                if (existing.getItemId().equals(item.getItemId())) {
                    System.out.println("Error: Item with ID " + item.getItemId() + " already exists!");
                    return false;
                }
            }
            items.add(item);
            System.out.println("Item added successfully: " + item.getItemName());
            return true;
        }

        public boolean removeItem(String itemId) {
            for (Item item : items) {
                if (item.getItemId().equals(itemId)) {
                    items.remove(item);
                    System.out.println("Item removed successfully: " + item.getItemName());
                    return true;
                }
            }
            System.out.println("Error: Item with ID " + itemId + " not found!");
            return false;
        }

        public boolean updateQuantity(String itemId, int amount) {
            for (Item item : items) {
                if (item.getItemId().equals(itemId)) {
                    item.updateQuantity(amount);
                    System.out.println("Quantity updated for " + item.getItemName() + ". New quantity: " + item.getQuantity());
                    return true;
                }
            }
            System.out.println("Error: Item with ID " + itemId + " not found!");
            return false;
        }

        public Item searchById(String itemId) {
            for (Item item : items) {
                if (item.getItemId().equals(itemId)) {
                    return item;
                }
            }
            return null;
        }

        public List<Item> searchByName(String name) {
            List<Item> results = new ArrayList<>();
            for (Item item : items) {
                if (item.getItemName().toLowerCase().contains(name.toLowerCase())) {
                    results.add(item);
                }
            }
            return results;
        }

        public void displayAllItems() {
            if (items.isEmpty()) {
                System.out.println("Inventory is empty!");
                return;
            }
            System.out.println("\n=== Current Inventory ===");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println("Total items: " + items.size());
        }
    }

    // Main Warehouse Class
    private Inventory inventory;
    private Scanner scanner;

    public Warehouse() {
        this.inventory = new Inventory();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=====================================");
        System.out.println("  WAREHOUSE INVENTORY MANAGEMENT SYSTEM");
        System.out.println("=====================================");

        int choice;
        do {
            displayMenu();
            choice = getValidIntegerInput("Enter your choice: ");

            switch (choice) {
                case 1: addItem(); break;
                case 2: removeItem(); break;
                case 3: updateStock(); break;
                case 4: searchItem(); break;
                case 5: inventory.displayAllItems(); break;
                case 6: 
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;
                default: 
                    System.out.println("Invalid choice! Please try again.");
            }
            System.out.println();
        } while (choice != 6);
    }

    private void displayMenu() {
        System.out.println("1. Add a new item");
        System.out.println("2. Remove an existing item");
        System.out.println("3. Update item quantity");
        System.out.println("4. Search for an item");
        System.out.println("5. View all inventory");
        System.out.println("6. Exit");
    }

    private void addItem() {
        System.out.print("Enter Item ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine().trim();

        int quantity = getValidIntegerInput("Enter Quantity: ");
        double price = getValidDoubleInput("Enter Price: ");

        Item newItem = new Item(id, name, quantity, price);
        inventory.addItem(newItem);
    }

    private void removeItem() {
        System.out.print("Enter Item ID to remove: ");
        String id = scanner.nextLine().trim();
        inventory.removeItem(id);
    }

    private void updateStock() {
        System.out.print("Enter Item ID: ");
        String id = scanner.nextLine().trim();
        int amount = getValidIntegerInput("Enter quantity to add/subtract (negative to reduce): ");
        inventory.updateQuantity(id, amount);
    }

    private void searchItem() {
        System.out.println("Search by:");
        System.out.println("1. Item ID");
        System.out.println("2. Item Name");
        int option = getValidIntegerInput("Choose option: ");

        if (option == 1) {
            System.out.print("Enter Item ID: ");
            String id = scanner.nextLine().trim();
            Item item = inventory.searchById(id);
            if (item != null) {
                System.out.println("\nItem Found:\n" + item);
            } else {
                System.out.println("Item not found!");
            }
        } else if (option == 2) {
            System.out.print("Enter Item Name (or part of name): ");
            String name = scanner.nextLine().trim();
            List<Item> results = inventory.searchByName(name);
            if (results.isEmpty()) {
                System.out.println("No items found!");
            } else {
                System.out.println("\nSearch Results:");
                for (Item item : results) {
                    System.out.println(item);
                }
            }
        } else {
            System.out.println("Invalid option!");
        }
    }

    // Input validation helpers
    private int getValidIntegerInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private double getValidDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.start();
    }
}