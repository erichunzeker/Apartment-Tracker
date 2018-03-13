import java.text.NumberFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Car{

    private Scanner scanner = new Scanner(System.in);
    NumberFormat formatter = NumberFormat.getCurrencyInstance();

    //class attributes

    private String VIN, make, model, color;
    private int size;
    protected double price, mileage; //price per month & square footage
    private Car[] carPricePQ, carMileagePQ;

    public Car()
    {
        // default constructor
        carPricePQ = new Car[25];
        carMileagePQ = new Car[25];
        size = 0;
    }

    public Car(String VIN, String make, String model, double price, double mileage, String color)
    {
        this.VIN = VIN;
        this.make = make;
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        this.color = color;
    }

    public void addCar()
    {
        boolean valid;
        do {
            valid = true;
            try {

                System.out.println("Enter the VIN");
                VIN = scanner.nextLine();

                System.out.println("Enter the make");
                make = scanner.nextLine();

                System.out.println("Enter the model");
                model = scanner.nextLine();

                System.out.println("Enter the price");
                price = scanner.nextDouble();

                System.out.println("Enter the mileage");
                mileage = scanner.nextDouble();

                System.out.println("Enter the color");
                scanner.nextLine();
                color = scanner.nextLine();


                Car car = new Car(VIN, make, model, price, mileage, color);

                if (size < carPricePQ.length) {
                    carPricePQ[size] = car;
                    carMileagePQ[size] = car;

                    size++;
                } else {
                    resize();
                    carPricePQ[size] = car;
                    carMileagePQ[size] = car;

                    size++;
                }

                if (size > 1) {
                    PriceHeap.sort(carPricePQ, size);
                    MileageHeap.sort(carMileagePQ, size);
                }

            } catch (InputMismatchException i) {
                System.out.println("Unable to add car");
                scanner.nextLine();
                valid = false;
            }
        }while(!valid);
    }

    public void updateCar()
    {
        try {
            System.out.println("Enter the VIN");
            VIN = scanner.nextLine();

            int p = -1, m = -1;

            for (int i = 0; i < size; i++)
            {
                if (carPricePQ[i].VIN.equalsIgnoreCase(VIN))
                    p = i;
                if (carMileagePQ[i].VIN.equalsIgnoreCase(VIN))
                    m = i;
            }

            displayCar(carPricePQ[p]);

            int answer;

            do {
                System.out.println("Would you like to update the " + "\n" +
                        "\t1.) price " + "\n" +
                        "\t2.) mileage" + "\n" +
                        "\t3.) color" + "\n" +
                        "\t4.) cancel");
                answer = scanner.nextInt();
            } while (answer < 1 || answer > 4);

            if (answer == 1) {
                System.out.println("Enter the new price");
                price = scanner.nextDouble();
                scanner.nextLine();

                carPricePQ[p].price = price;
                carMileagePQ[m].price = price;

                PriceHeap.sort(carPricePQ, size);
                MileageHeap.sort(carMileagePQ, size);
            }

            else if (answer == 2) {
                System.out.println("Enter the new mileage");
                mileage = scanner.nextDouble();
                scanner.nextLine();

                carPricePQ[p].mileage = mileage;
                carMileagePQ[m].mileage = mileage;

                PriceHeap.sort(carPricePQ, size);
                MileageHeap.sort(carMileagePQ, size);
            }

            else if (answer == 3) {
                System.out.println("Enter the new color");
                scanner.nextLine();
                color = scanner.nextLine();

                carPricePQ[p].color = color;
                carMileagePQ[p].color = color;
            }
        }
        catch(ArrayIndexOutOfBoundsException | InputMismatchException e)
        {
            System.out.println("Error in updating price");
        }

    }

    public void removeCar()
    {
        try {
            System.out.println("Enter the VIN");
            VIN = scanner.nextLine();

            int p = -1, m = -1;

            for (int i = 0; i < size; i++)
            {
                if (carPricePQ[i].VIN.equalsIgnoreCase(VIN))
                    p = i;
                if (carMileagePQ[i].VIN.equalsIgnoreCase(VIN))
                    m = i;
            }

            displayCar(carPricePQ[p]);

            delete(p, m);
        }

        catch(ArrayIndexOutOfBoundsException | InputMismatchException e)
        {
            System.out.println("Sorry, no car was found");
        }

    }

    public void getCarByPriceLowToHigh()
    {
        try {
            System.out.println("Lowest Price: ");
            displayCar(carPricePQ[0]);
        }
        catch(NullPointerException e) {
            System.out.println("Car list is empty");
        }
    }

    public void getCarByMileageHighToLow()
    {
        try {
            System.out.println("Lowest Mileage: ");
            displayCar(carMileagePQ[0]);
        }
        catch(NullPointerException e) {
            System.out.println("Car list is empty");
        }
    }

    public void getCarByMakeModelByPriceLowToHigh()
    {
        try {
            System.out.println("Enter the make");
            make = scanner.nextLine();

            System.out.println("Enter the model");
            model = scanner.nextLine();


            int p = -1;
            double price = -1;

            for (int i = 0; i < size; i++) {
                if (carPricePQ[i].make.equalsIgnoreCase(make) && carPricePQ[i].model.equalsIgnoreCase(model)) {
                    if (price == -1 || price < carPricePQ[p].price) {
                        price = carPricePQ[i].price;
                        p = i;
                    }
                }
            }


            System.out.println("Lowest Price: " + carPricePQ[p].model);
            displayCar(carPricePQ[p]);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Sorry, no car was found");
        }

    }

    public void getCarByMakeModelByMileageLowToHigh()
    {
        try {
            System.out.println("Enter the make");
            make = scanner.nextLine();

            System.out.println("Enter the model");
            model = scanner.nextLine();

            int m = -1;
            double mileage = -1;

            for (int i = 0; i < size; i++) {
                if (carMileagePQ[i].make.equalsIgnoreCase(make) && carMileagePQ[i].model.equalsIgnoreCase(model)) {
                    if (mileage == -1 || mileage < carMileagePQ[m].mileage) {
                        mileage = carMileagePQ[i].mileage;
                        m = i;
                    }
                }
            }

            System.out.println("Lowest Mileage: " + carMileagePQ[m].model);
            displayCar(carMileagePQ[m]);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Sorry, no car was found");
        }

    }


    private void displayCar(Car a)
    {
        System.out.println(
                "\nVIN: " + a.VIN + "\n" +
                        "Make: " + a.make + "\n" +
                        "Model: " + a.model + "\n" +
                        "Price: " + formatter.format(a.price) + "\n" +
                        "Mileage: " + a.mileage + " miles" + "\n" +
                        "Color: " + a.color + "\n");
    }

    private void resize()
    {
        Car[] newCarPricePQ = new Car[carPricePQ.length * 2];
        Car[] newCarMileagePQ = new Car[carMileagePQ.length * 2];

        for(int i = 0; i < carPricePQ.length; i++)
            newCarPricePQ[i] = carPricePQ[i];
        for(int i = 0; i < carMileagePQ.length; i++)
            newCarMileagePQ[i] = carMileagePQ[i];

        carPricePQ = newCarPricePQ;
        carMileagePQ = newCarMileagePQ;
    }

    private void delete(int p , int m)
    {
        size--;

        if(size == 0)
        {
            carPricePQ = new Car[25];
            carMileagePQ = new Car[25];
        }
        else {
            for (int i = p; i < size; i++)
                carPricePQ[i] = carPricePQ[i + 1];
            for (int i = m; i < size; i++)
                carMileagePQ[i] = carMileagePQ[i + 1];
        }
    }
}
