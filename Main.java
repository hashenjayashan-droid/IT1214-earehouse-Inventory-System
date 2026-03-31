import java.util.Scanner;

class Item {
    String name;
    int quantity;

    Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Item[] items = new Item[5];
        int count = 0;

        System.out.println("=== Welcome to Warehouse Inventory System ===");

        while (true) {
            System.out.println("\n------------------------------");
            System.out.println("1. Add Item");
            System.out.println("2. View Items");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                if (count < 5) {
                    System.out.print("Enter item name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();

                    items[count] = new Item(name, qty);
                    count++;

                    System.out.println("✅ Item added successfully!");
                } else {
                    System.out.println("❌ Inventory full!");
                }

            } else if (choice == 2) {
                if (count == 0) {
                    System.out.println("⚠️ No items in inventory!");
                } else {
                    System.out.println("\n📦 Items List:");
                    for (int i = 0; i < count; i++) {
                        System.out.println((i + 1) + ". " + items[i].name + " - " + items[i].quantity);
                    }
                }

            } else if (choice == 3) {
                System.out.println("👋 Thank you! Exiting system...");
                break;

            } else {
                System.out.println("❌ Invalid option! Try again.");
            }
        }
    }
}