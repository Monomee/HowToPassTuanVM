import java.util.ArrayList;

public class CheckData {
    static boolean checkIdUniqueFruit(ArrayList<Fruit> fruitList, Fruit newFruit) {
        boolean isNotUnique = false;

        // Loop each fruit in fruitList
        for (Fruit fruit : fruitList) {
            // Check if same fruit id
            if (newFruit.getFruitId().equalsIgnoreCase(fruit.getFruitId())) {
                // Check if not same name
                if (!newFruit.getFruitName().equalsIgnoreCase(fruit.getFruitName())) {
                    System.out.println("Id is unique. Please re-enter!");
                    isNotUnique = true;
                    break;
                }
            }
        }

        return isNotUnique;
    }

    static boolean checkDuplicatedFruit(ArrayList<Fruit> fruitList, Fruit newFruit) {
        boolean isDuplicate = false;

        // Loop each fruit in fruitList
        for (Fruit fruit : fruitList) {
            // Check if same id
            if (newFruit.getFruitId().equalsIgnoreCase(fruit.getFruitId())) {
                // Check if same origin
                if (newFruit.getOrigin().equalsIgnoreCase(fruit.getOrigin())) {
                    // Check if same price
                    if (newFruit.getPrice() == fruit.getPrice()) {
                        // Check if same quantity
                        if (newFruit.getQuantity() == fruit.getQuantity()) {
                            System.out.println("Duplicated fruit. Please re-enter!");
                            isDuplicate = true;
                            break;
                        }
                    }
                }
            }
        }

        return isDuplicate;
    }
}
