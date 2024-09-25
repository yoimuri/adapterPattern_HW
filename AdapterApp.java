package adapterPattern;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AdapterApp {

    private static Map<String, Boolean> deviceStatus = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Laptop laptop = new Laptop();
        Refrigerator refrigerator = new Refrigerator();
        SmartphoneCharger smartphoneCharger = new SmartphoneCharger();

        PowerOutlet laptopAdapter = new LaptopAdapter(laptop);
        PowerOutlet refrigeratorAdapter = new RefrigeratorAdapter(refrigerator);
        PowerOutlet smartphoneAdapter = new SmartphoneAdapter(smartphoneCharger);

        deviceStatus.put("Laptop", false);
        deviceStatus.put("Refrigerator", false);
        deviceStatus.put("Smartphone", false);

        while (true) {
            System.out.println("\nDevice Charging Menu:");
            System.out.println("1. Laptop (Status: " + (deviceStatus.get("Laptop") ? "Charging" : "Not Charging") + ")");
            System.out.println("2. Refrigerator (Status: " + (deviceStatus.get("Refrigerator") ? "Cooling" : "Not Cooling") + ")");
            System.out.println("3. Smartphone (Status: " + (deviceStatus.get("Smartphone") ? "Charging" : "Not Charging") + ")");
            System.out.println("4. Exit");
            System.out.print("Select a device to plug in or unplug (1-3) or Exit (4): ");
            int choice = scanner.nextInt();

            if (choice == 4) {
                System.out.println("Exiting the application...");
                break;
            }

            switch (choice) {
                case 1:
                    handleDevice(scanner, "Laptop", laptopAdapter);
                    break;
                case 2:
                    handleDevice(scanner, "Refrigerator", refrigeratorAdapter);
                    break;
                case 3:
                    handleDevice(scanner, "Smartphone", smartphoneAdapter);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }


    private static void handleDevice(Scanner scanner, String deviceName, PowerOutlet adapter) throws InterruptedException {
        boolean isCharging = deviceStatus.get(deviceName);
        System.out.print(deviceName + " is currently " + (isCharging ? "charging" : "not charging") + ". Do you want to change the status? (y/n): ");
        String response = scanner.next();

        if (response.equalsIgnoreCase("y")) {
            if (isCharging) {
                // Stop charging
                System.out.println("Unplugging " + deviceName + "...");
                simulateLoading();
                deviceStatus.put(deviceName, false);
                System.out.println(deviceName + " is now not charging.");
            } else {
                // Start charging
                System.out.println("Plugging in " + deviceName + "...");
                simulateLoading();
                adapter.plugIn();
                deviceStatus.put(deviceName, true);
            }
        } else {
            System.out.println("No changes made to " + deviceName + ".");
        }
    }
    
    private static void handleDevice(Scanner scanner, String deviceName, PowerOutlet adapter, String action) throws InterruptedException {
        boolean isCooling = deviceStatus.get(deviceName);
        System.out.print(deviceName + " is currently " + (isCooling ? "cooling" : "not cooling") + ". Do you want to change the status? (y/n): ");
        String response = scanner.next();

        if (response.equalsIgnoreCase("y")) {
            if (isCooling) {
                // Stop cooling
                System.out.println("Turning off " + deviceName + "...");
                simulateLoading();
                deviceStatus.put(deviceName, false);
                System.out.println(deviceName + " is now not cooling.");
            } else {
                // Start cooling
                System.out.println("Turning on " + deviceName + "...");
                simulateLoading();
                adapter.plugIn();
                deviceStatus.put(deviceName, true);
            }
        } else {
            System.out.println("No changes made to " + deviceName + ".");
        }
    }
    


private static void simulateLoading() throws InterruptedException {
    System.out.print("Processing");
    for (int i = 0; i < 2; i++) {
        TimeUnit.SECONDS.sleep(1);
        System.out.print(".");
    }
    System.out.println();
}
}

