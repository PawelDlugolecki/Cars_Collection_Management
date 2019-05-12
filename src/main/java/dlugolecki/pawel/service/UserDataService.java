package dlugolecki.pawel.service;

import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.model.enums.SortingType;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserDataService {

    private Scanner sc = new Scanner(System.in);

        public int getInt(String message) {
            System.out.println(message);
            String text  = sc.nextLine();

            if (!text.matches("\\d+")) {
                throw new MyException(ExceptionCode.VALIDATION, "Value is not digit: " + text);
            }
            return Integer.parseInt(text);
        }

    public boolean isOrderDescending() {
        System.out.println("Sort cars descending ?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        String text = sc.nextLine();
        if (!text.matches("[1-2]")) {
            throw new MyException(ExceptionCode.VALIDATION, "Sort type option number is not valid: " + text);
        }
        return text.equals("1");
    }

    public SortingType getSortingType() {
        System.out.println("Choose sorting type:");
        System.out.println("1 - model");
        System.out.println("2 - price");
        System.out.println("3 - mileage");
        System.out.println("4 - colour");
        System.out.println("5 - components");

        String text = sc.nextLine();
        if (!text.matches("[1-4]")) {
            throw new MyException(ExceptionCode.INPUT_DATA, "Sorting type option is not valid: " + text);
        }
        return SortingType.values()[Integer.parseInt(text) - 1];
    }


    public int getIntMileage(String message) {
        System.out.println(message);
        String text = sc.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.INPUT_DATA, "Value is not a number: " + text);
        }
        return Integer.parseInt(text);
    }

    public BigDecimal getBigDecimal(String message) {
        System.out.println(message);
        String text = sc.nextLine();

        if (!text.matches("(\\d+\\.)*\\d+")) {
            throw new MyException(ExceptionCode.INPUT_DATA, "Value is not a decimal: " + text);
        }

        return new BigDecimal(text);
    }

    public void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }
}
