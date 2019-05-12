package dlugolecki.pawel.app;

import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.service.CarService;
import dlugolecki.pawel.service.UserDataService;

public class MenuService {

    private final CarService carService;
    private final UserDataService userDataService;

    public MenuService(String filename) {
        carService = new CarService(filename);
        userDataService = new UserDataService();
    }

    public void menu() {

        while (true) {
            try {
                printMenu();
                int fromUser = userDataService.getInt("Choose option");

                switch (fromUser) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        option4();
                        break;
                    case 5:
                        option5();
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8();
                        break;
                    case 9:
                        option9();
                        break;
                    case 10:
                        option10();
                        break;
                    case 11:
                        option11();
                        break;
                }
            } catch (MyException e) {
                System.out.println("\n****************************** EXCEPTIONS *************************************");
                System.out.println(e.getExceptionInfo().getMessage());
                System.out.println(e.getExceptionInfo().getExceptionCode());
                System.out.println(e.getExceptionInfo().getTimeOfException());
                System.out.println("*******************************************************************************\n");
            }
        }
    }

    private void printMenu() {

        System.out.println("1  - Show cars");
        System.out.println("2  - Sorted cars");
        System.out.println("3  - Show cars with greater mileage");
        System.out.println("4  - Show numbers of cars with particular colour");
        System.out.println("5  - Show the most expensive car for each model");
        System.out.println("6  - Show statistics");
        System.out.println("7  - Show the most expensive car/cars");
        System.out.println("8  - Show cars with particular component");
        System.out.println("9  - Sort cars by price range");
        System.out.println("10 - Show cars with sorted components");
        System.out.println("11 - Exit");
    }

    private void option1() {
        System.out.println(carService);
        System.out.println("--------------------------");
    }

    private void option2() {
        carService
                .sort(userDataService.getSortingType(), userDataService.isOrderDescending())
                .forEach(System.out::println);
        System.out.println("--------------------------");
    }

    private void option3() {
        carService
                .carsWithMileageGreaterThan(userDataService.getIntMileage("Enter the mileage"))
                .forEach(System.out::println);
        System.out.println("--------------------------");
    }

    private void option4() {
        carService
                .groupedByColors()
                .forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("--------------------------");
    }

    private void option5() {
        carService
                .getTheMostExpensiveCarModel()
                .forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("--------------------------");
    }

    private void option6() {
        carService
                .statistics();
        System.out.println("--------------------------");
    }

    private void option7() {
        carService
                .carsWithBiggestPrice()
                .forEach(s -> System.out.println(s));
        System.out.println("--------------------------");
    }

    private void option8() {
        carService
                .carsWithComponent()
                .forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("--------------------------");
    }

    private void option9() {
        carService
                .carsWithPriceBetween(userDataService.getBigDecimal("Enter min price"), userDataService.getBigDecimal("Enter max price"))
                .forEach(c -> System.out.println(c));
        System.out.println("--------------------------");
    }

    private void option10() {
        carService
                .sortedAlphabeticComponents()
                .forEach(s -> System.out.println(s));
        System.out.println("--------------------------");
    }

    private void option11() {
        userDataService
                .close();
    }
}
