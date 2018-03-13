import java.util.InputMismatchException;
import java.util.Scanner;

public class CarTracker {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Car car = new Car();

        String input;
        int choice;
        boolean run = true;

        do {
            System.out.println("Would you like to start the car application (yes/no)");
            input = scanner.nextLine();
        }while(!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));

        if(input.equalsIgnoreCase("no"))
            run = false;

        while(run) {
            do {
                try {
                    System.out.println("Enter your choice: "
                            + "\n" + "1.) Add Car"
                            + "\n" + "2.) Update Existing Car"
                            + "\n" + "3.) Remove Existing Car"
                            + "\n" + "4.) Search By Lowest Price"
                            + "\n" + "5.) Search By Lowest Mileage"
                            + "\n" + "6.) Search By Lowest Price By Make & Model"
                            + "\n" + "7.) Search By Lowest Mileage By Make & Model");

                    choice = scanner.nextInt();
                }
                catch (InputMismatchException i) {
                    System.out.println("Error in selecting choice, try again");
                    scanner.nextLine();
                    choice = 0;
                }

            }while(choice < 1 || choice > 7);

            if(choice == 1)
                car.addCar();

            else if(choice == 2)
                car.updateCar();

            else if(choice == 3)
                car.removeCar();

            else if(choice == 4)
                car.getCarByPriceLowToHigh();

            else if(choice == 5)
                car.getCarByMileageHighToLow();

            else if(choice == 6)
                car.getCarByMakeModelByPriceLowToHigh();

            else
                car.getCarByMakeModelByMileageLowToHigh();

            do
            {
                scanner.nextLine();
                System.out.println("Would you like to continue with the car application (yes/no)");
                input = scanner.nextLine();
            }while(!input.equalsIgnoreCase("yes") &&!input.equalsIgnoreCase("no"));

            if(input.equalsIgnoreCase("no"))
                run = false;

        }

        System.out.println("goodbye");
    }
}
