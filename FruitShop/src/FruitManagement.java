import java.util.Scanner;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class FruitManagement {
    static Scanner sc = new Scanner(System.in);

    void createFruit(ArrayList<Fruit> fruitList) {
        ArrayList<Fruit> addedFruitList = new ArrayList<>();
        String fruitId;
        String fruitName;
        float price;
        int quantity;
        String origin;

        boolean isContinue = false;
        boolean isDuplicate = false;
        boolean isNotUnique = false;

        do {
            System.out.println();

            // id
            fruitId = GetData.getValidString("Enter fruit id: ").trim();
            // name
            fruitName = GetData.getValidString("Enter fruit name: ").trim();
            // price
            price = GetData.getValidPrice();
            // quantity
            quantity = GetData.getValidQuantity();
            // origin
            origin = GetData.getValidString("Enter fruit origin: ").trim();

            Fruit newFruit = new Fruit(fruitId, fruitName, price, quantity, origin);

            // function check id unique
            isNotUnique = CheckData.checkIdUniqueFruit(fruitList, newFruit);

            // Check if fruitId is not unique
            if (isNotUnique) {
                continue;
            } else {
                // function check duplicate
                isDuplicate = CheckData.checkDuplicatedFruit(fruitList, newFruit);

                // Check if duplicated
                if (isDuplicate) {
                    continue;
                }
            }

            fruitList.add(newFruit);
            addedFruitList.add(newFruit);

            String confirmInput;
            System.out.println("Do you want to continue (Y/N)? Choose Y to continue, N to return main screen.");
            confirmInput = GetData.getValidConfirm();

            // check if confirm is yes
            if (confirmInput.equalsIgnoreCase("Y")) {
                isContinue = true;
            } else {
                isContinue = false;
                System.out.printf("|%-8s|%-30s|%-30s|%-30s|%-30s|\n", "Fruit id", "Fruit name", "Price", "Quantity", "Origin");

                // loop each fruit in addedFruitList
                for (Fruit fruit : addedFruitList) {
                    System.out.println(fruit.toString());
                }
            }
        } // check if continue
        while (isContinue);

        addedFruitList.clear();
    }

    void viewOrder(Hashtable<Customer, Order> orderList) {
        // check if list is empty
        if (orderList.isEmpty()) {
            System.out.println("There no orders here!");
            return;
        }

        String fruitName;
        int quantity;
        float price;
        float amount;
        float total;
        int count = 0;

        // loop each customer and their orders in orderList
        for (Map.Entry<Customer, Order> entry : orderList.entrySet()) {
            Customer customer = entry.getKey();
            Order orders = entry.getValue();

            count = 0;
            total = 0;

            System.out.println("Customer: " + customer.getCustomerName());
            System.out.printf("%-5s|%-20s|%-20s|%-20s|%-20s\n", "No", "Product", "Quantity", "Price", "Amount");

            // loop each fruit in order
            for (Fruit order : orders.getFruits()) {
                count++;
                fruitName = order.getFruitName();
                quantity = order.getQuantity();
                price = order.getPrice();

                amount = price * quantity;
                total += amount;

                String priceStr = String.format("%.2f", price) + "$";
                String amountStr = String.format("%.2f", amount) + "$";

                System.out.printf("%-5d. %-20s|%-20d|%-20s|%-20s\n", count, fruitName, quantity, priceStr, amountStr);
            }

            System.out.println("Total: " + total + "$");
        }
    }

    void shoppingForBuyer(ArrayList<Fruit> fruitList, Hashtable<Customer, Order> orderList) {
        // check if fruit list is empty
        if (fruitList.isEmpty()) {
            System.out.println("There no products (fruits) to order!");
            return;
        }

        ArrayList<Fruit> orderedFruits = new ArrayList<>();
        Hashtable<Integer, Fruit> fruitDisplayList = new Hashtable<>();

        boolean isContinue = false;
        int choice;
        int maxChoice;
        String confirmInput;

        String customerName;
        String id;
        String fruitName;
        float price;
        int quantity;
        String origin;

        int count = 0;

        // loop each fruit in fruitList to add into fruitDisplayList
        for (Fruit fruit : fruitList) {
            count++;
            fruitDisplayList.put(count, fruit);
        }

        maxChoice = fruitDisplayList.size();

        do {
            System.out.println("List of Fruits: ");
            System.out.println("| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |");

            // loop each fruit in fruitDisplayList by key(stt) to print
            for (int i = 1; i <= fruitDisplayList.size(); i++) {
                Fruit fruit = fruitDisplayList.get(i);
                String priceStr = String.format("%.2f", fruit.getPrice()) + "$";
                System.out.printf("%-14d%-20s%-16s%-15s", i, fruit.getFruitName(), fruit.getOrigin(), priceStr);

                // check if this fruit is sold out (quantity = 0)
                if (fruit.getQuantity() == 0) {
                    System.out.println("(Sold out!)");
                } else {
                    System.out.println();
                }
            }

            choice = GetData.getValidChoice("Enter your choice: ", "(choice must be from 1 to " + maxChoice + ". Please re-enter)", 1, maxChoice);
            Fruit selectedFruit = fruitDisplayList.get(choice);

            System.out.println("You selected: " + selectedFruit.getFruitName());

            // check if selectedFruit is not sold out (quantity != 0)
            if (selectedFruit.getQuantity() != 0) {
                quantity = GetData.getValidChoice("Please input quantity: ", ("Must not choose quantity greater than " + selectedFruit.getFruitName() + "’s current quantity: " + selectedFruit.getQuantity() + " or less than 1. Please re-enter!"), 0, selectedFruit.getQuantity());
                selectedFruit.setQuantity(selectedFruit.getQuantity()-quantity);
                id = selectedFruit.getFruitId();
                fruitName = selectedFruit.getFruitName();
                price = selectedFruit.getPrice();
                origin = selectedFruit.getOrigin();

                Fruit newFruit = new Fruit(id, fruitName, price, quantity, origin);
                orderedFruits.add(newFruit);

                System.out.print("Do you want to order now (Y/N). If select N, continue ordering. If select Y, list of order will be displayed: ");
                confirmInput = GetData.getValidConfirm();

                // check if confirm is no (N)
                if (confirmInput.equalsIgnoreCase("N")) {
                    isContinue = true;
                } else {
                    isContinue = false;
                }

            } else {
                System.out.println(selectedFruit.getFruitName() + " is sold out. Please choose again!");
                continue;
            }
        } //loop if continue
        while (isContinue);

        Customer orderCustomer = null;
        boolean isCustomerExisted = false;
        customerName = GetData.getValidString("Input your name: ");

        // loop each element in orderList
        for (Map.Entry<Customer, Order> entry : orderList.entrySet()) {
            Customer customer = entry.getKey();

            // check if same customer
            if (customer.getCustomerName().equalsIgnoreCase(customerName)) {
                orderCustomer = customer;
                isCustomerExisted = true;
                break;
            }
        }

        // check if customer is existed
        if (isCustomerExisted) {
            // loop each fruit in orderedFruits
            for (Fruit orderFruit : orderedFruits) {
                int sizeOrderList = orderList.get(orderCustomer).getFruits().size();
                boolean isFruitExisted = false;

                // loop each fruit in orderList (bought before) by index
                for (int i = 0; i < sizeOrderList; i++) {
                    Fruit boughtFruit = orderList.get(orderCustomer).getFruits().get(i);

                    // check if fruit is bought before
                    if (orderFruit.getFruitId().equalsIgnoreCase(boughtFruit.getFruitId())) {
                        boughtFruit.setQuantity(boughtFruit.getQuantity() + orderFruit.getQuantity());
                        isFruitExisted = true;
                    }
                }

                // check if fruit is not existed
                if (!isFruitExisted) {
                    orderList.get(orderCustomer).getFruits().add(orderFruit);
                }
            }

        } else {
            Customer customer = new Customer(customerName);
            orderList.put(customer, new Order(customer, orderedFruits));
        }

        float amount;
        float total = 0;

        System.out.printf("%-20s|%-20s|%-20s|%-20s\n", "Product", "Quantity", "Price", "Amount");

        // loop each element in orderedFruits
        for (Fruit order : orderedFruits) {
            fruitName = order.getFruitName();
            quantity = order.getQuantity();
            price = order.getPrice();

            amount = price * quantity;
            total += amount;

            String priceStr = String.format("%.2f", price) + "$";
            String amountStr = String.format("%.2f", amount) + "$";

            System.out.printf("%-20s|%-20d|%-20s|%-20s\n", fruitName, quantity, priceStr, amountStr);
        }
        System.out.println("Total: " + total + "$");
    }
}
