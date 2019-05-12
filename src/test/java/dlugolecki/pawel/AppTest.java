package dlugolecki.pawel;

import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.model.Car;
import dlugolecki.pawel.model.enums.Colour;
import dlugolecki.pawel.model.enums.SortingType;
import dlugolecki.pawel.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private CarService carService;

    @BeforeEach
    public void beforeEach() {
        carService = new CarService("test_cars");
    }

    @Test
    @DisplayName("Check cars with price between correct price range")
    public void test1() {

        // WHEN
        final BigDecimal minPrice = new BigDecimal("90000");
        final BigDecimal maxPrice = new BigDecimal("100000");
        List<Car> cars = carService.carsWithPriceBetween(minPrice, maxPrice);
        System.out.println(cars);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());
        // THEN
        Assertions.assertEquals(2, cars.size(), "TEST 1 CARS WITH PRICE BETWEEN CORRECT PRICE RANGE FAILED");
        Assertions.assertIterableEquals(Arrays.asList("AUDI", "MAZDA"), models, "TEST 2 CARS WITH PRICE BETWEEN CORRECT PRICE RANGE FAILED");
    }

    @Test
    @DisplayName("Check cars with price between incorrect price range")
    public void test2() {

        // GIVEN + THEN
        final BigDecimal minPrice = new BigDecimal("100000");
        final BigDecimal maxPrice = new BigDecimal("90000");
        MyException e = Assertions.assertThrows(
                MyException.class,
                () -> carService.carsWithPriceBetween(minPrice, maxPrice));
        System.out.println(e.getExceptionInfo().getMessage());
        Assertions.assertEquals(ExceptionCode.INPUT_DATA, e.getExceptionInfo().getExceptionCode());
        Assertions.assertEquals("Min price can not be greater than the max price", e.getExceptionInfo().getMessage());
    }

    @Test
    @DisplayName("Check cars with biggest price - correct")
    public void test3() {

        // WHEN
        List<Car> cars = carService.carsWithBiggestPrice();
        System.out.println(cars);
        List<String> model = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertEquals(1, cars.size(), "TEST 3 CARS WITH BIGGEST PRICE");
        Assertions.assertIterableEquals(Arrays.asList("HONDA"), model, "TEST 3 CARS WITH BIGGEST PRICE");

    }

    @org.junit.jupiter.api.Test
    @DisplayName("Check cars with biggest price - incorrect")
    public void test4() {

        // WHEN
        final int maxMileage = 9500;
        List<Car> cars = carService.carsWithMileageGreaterThan(maxMileage);
        System.out.println(cars);
        List<String> model = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertEquals(3, cars.size(), "TEST 4 CARS WITH MILEAGE GREATER THAN");
        Assertions.assertIterableEquals(Arrays.asList("HONDA", "AUDI", "MAZDA"), model, "TEST 4 CARS WITH MILEAGE GREATER THAN");
    }

    @Test
    @DisplayName("Check if sort by model desc works")
    public void test5() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.MODEL, true);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(models, Arrays.asList("MAZDA", "HONDA", "AUDI"));
    }

    @Test
    @DisplayName("Check if sort by model asc works")
    public void test6() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.MODEL, false);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(models, Arrays.asList("AUDI", "HONDA", "MAZDA"));
    }

    @Test
    @DisplayName("Check if sort by price desc works")
    public void test7() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.PRICE, true);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(models, Arrays.asList("HONDA", "AUDI", "MAZDA"));
    }

    @Test
    @DisplayName("Check if sort by price asc works")
    public void test8() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.PRICE, false);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(models, Arrays.asList("MAZDA", "AUDI", "HONDA"));
    }

    @Test
    @DisplayName("Check if sort by mileage desc works")
    public void test9() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.MILEAGE, true);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(models, Arrays.asList("HONDA", "MAZDA", "AUDI"));
    }

    @Test
    @DisplayName("Check if sort by mileage desc works")
    public void test10() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.MILEAGE, true);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(models, Arrays.asList("HONDA", "MAZDA", "AUDI"));
    }

    @Test
    @DisplayName("Check if sort by mileage asc works")
    public void test11() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.MILEAGE, false);
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(models, Arrays.asList("AUDI", "MAZDA", "HONDA"));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Check if sort by color desc works")
    public void test16() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.COLOUR, false);
        List<String> colors = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(colors, Arrays.asList("AUDI", "MAZDA", "HONDA"));
    }

    @Test
    @DisplayName("Check if sort by color asc works")
    public void test17() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.COLOUR, true);
        List<String> colors = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(colors, Arrays.asList("HONDA", "MAZDA", "AUDI"));
    }

    @Test
    @DisplayName("Check if sort by components desc works")
    public void test18() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.COMPONENTS, false);
        List<String> components = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(components, Arrays.asList("AUDI", "MAZDA", "HONDA"));
    }

    @Test
    @DisplayName("Check if sort by components asc works")
    public void test19() {

        // WHEN
        List<Car> cars = carService.sort(SortingType.COMPONENTS, true);
        List<String> components = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertLinesMatch(components, Arrays.asList("HONDA", "MAZDA", "AUDI"));
    }


    @Test
    @DisplayName("Check if getting map of colors and numbers works")
    public void test12() {

        // WHEN
        Map<Colour, Long> cars = carService.groupedByColors();

        // THEN
        Assertions.assertTrue(cars.containsKey(Colour.WHITE) && cars.get(Colour.WHITE) == 1);
        Assertions.assertTrue(cars.containsKey(Colour.SILVER) && cars.get(Colour.SILVER) == 1);
        Assertions.assertTrue(cars.containsKey(Colour.BLUE) && cars.get(Colour.BLUE) == 1);
    }

    @Test
    @DisplayName("Check if getting map of model and the most expensive car")
    public void test13() {

        //WHEN
        Map<String, Car> cars = carService.getTheMostExpensiveCarModel();

        // THEN
        Car car1 = Car
                .builder()
                .model("HONDA")
                .price(BigDecimal.valueOf(120000))
                .mileage(50000)
                .colour(Colour.WHITE)
                .equipment(Arrays.asList("ABS", "ESR", "REFLEKTOR LED", "LAKIER", "HAK"))
                .build();

        Car car2 = Car
                .builder()
                .model("AUDI")
                .price(BigDecimal.valueOf(97000))
                .mileage(10000)
                .colour(Colour.BLUE)
                .equipment(Arrays.asList("ABS", "AUDI SYSTEM SOUND"))
                .build();

        Car car3 = Car
                .builder()
                .model("MAZDA")
                .price(BigDecimal.valueOf(91500))
                .mileage(30000)
                .colour(Colour.SILVER)
                .equipment(Arrays.asList("ABS", "LAKIER METALIK", "AUDI SYSTEM SOUND"))
                .build();

        Map<String, Car> carMap = new HashMap<>();
        carMap.put("HONDA", car1);
        carMap.put("AUDI", car2);
        carMap.put("MAZDA", car3);

        Assertions.assertEquals(cars, carMap);
    }

    @Test
    @DisplayName("Check if sorting components lists works")
    public void test14() {

        // WHEN
        List<Car> cars = carService.sortedAlphabeticComponents();

        List<List<String>> modelsWithComponents = cars.stream().map(Car::getEquipment).collect(Collectors.toList());
        List<List<String>> sortedModels = Arrays.asList(
                Arrays.asList("ABS", "ESR", "HAK", "LAKIER", "REFLEKTOR LED"),
                Arrays.asList("ABS", "AUDI SYSTEM SOUND"),
                Arrays.asList("ABS", "AUDI SYSTEM SOUND", "LAKIER METALIK"));

        // THEN
        Assertions.assertEquals(modelsWithComponents, sortedModels);
    }

    @Test
    @DisplayName("Check if getting component map with car lists works")
    public void test15() {

        // WHEN
        Map<String, List<Car>> mapCars = carService.carsWithComponent();

        Car car1 = Car
                .builder()
                .model("HONDA")
                .price(BigDecimal.valueOf(120000))
                .mileage(50000)
                .colour(Colour.WHITE)
                .equipment(Arrays.asList("ABS", "ESR", "REFLEKTOR LED", "LAKIER", "HAK"))
                .build();

        Car car2 = Car
                .builder()
                .model("AUDI")
                .price(BigDecimal.valueOf(97000))
                .mileage(10000)
                .colour(Colour.BLUE)
                .equipment(Arrays.asList("ABS", "AUDI SYSTEM SOUND"))
                .build();

        Car car3 = Car
                .builder()
                .model("MAZDA")
                .price(BigDecimal.valueOf(91500))
                .mileage(30000)
                .colour(Colour.SILVER)
                .equipment(Arrays.asList("ABS", "LAKIER METALIK", "AUDI SYSTEM SOUND"))
                .build();

        Map<String, List<Car>> expectedMap = new HashMap<>();

        expectedMap.put("ABS", Arrays.asList(car1, car2, car3));
        expectedMap.put("ESR", Arrays.asList(car1));
        expectedMap.put("REFLEKTOR LED", Arrays.asList(car1));
        expectedMap.put("LAKIER", Arrays.asList(car1));
        expectedMap.put("HAK", Arrays.asList(car1));
        expectedMap.put("AUDI SYSTEM SOUND", Arrays.asList(car2, car3));
        expectedMap.put("LAKIER METALIK", Arrays.asList(car3));

        // THEN
        Assertions.assertEquals(mapCars, expectedMap);
    }

    @Test
    @DisplayName("Check get cars from json")
    public void test20() {

        // WHEN
        List<Car> cars = carService.getCarsFromJson("test_cars");
        List<String> models = cars.stream().map(Car::getModel).collect(Collectors.toList());

        // THEN
        Assertions.assertEquals(models, Arrays.asList("HONDA", "AUDI", "MAZDA"));
    }
}
