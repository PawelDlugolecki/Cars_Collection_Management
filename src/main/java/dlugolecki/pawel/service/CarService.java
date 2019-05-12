package dlugolecki.pawel.service;

import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.json.impl.CarsConverter;
import dlugolecki.pawel.model.Car;
import dlugolecki.pawel.model.enums.Colour;
import dlugolecki.pawel.model.enums.SortingType;
import dlugolecki.pawel.validation.CarValidator;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarService {
    private List<Car> cars;

    public CarService(String fileName) {
        this.cars = getCarsFromJson(fileName);
    }

    public List<Car> getCarsFromJson(String filename) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        CarValidator carValidator = new CarValidator();
        return new CarsConverter("src/main/resources/" + filename + ".json")
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CAR SERVICE FROM JSON EXCEPTION"))
                .stream()
                .filter(car -> {

                    Map<String, String> errors = carValidator.validate(car);
                    if (carValidator.hasErrors()) {
                        System.out.println("VALIDATION ERRORS FOR CAR NO. " + atomicInteger.get());
                        errors.forEach((k, v) -> System.out.println(k + " " + v));
                    }
                    atomicInteger.incrementAndGet();
                    return !carValidator.hasErrors();
                }).collect(Collectors.toList());
    }

    public List<Car> sort(SortingType type, boolean descending) {

        Stream<Car> carStream = null;

        switch (type) {
            case MILEAGE -> carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));

            case PRICE -> carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));

            case MODEL -> carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));

            case COLOUR -> carStream = cars.stream().sorted(Comparator.comparing(c -> c.getColour().toString()));

            case COMPONENTS -> carStream = cars.stream().sorted(Comparator.comparingInt(car -> car.getEquipment().size()));
        }

        List<Car> sortedCars = carStream.collect(Collectors.toList());
        if (descending) {
            Collections.reverse(sortedCars);
        }

        return sortedCars;
    }

    public List<Car> carsWithMileageGreaterThan(int mileage) {

        return cars
                .stream()
                .filter(car -> car.getMileage() > mileage)
                .collect(Collectors.toList());
    }

    public Map<Colour, Long> groupedByColors() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getColour, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue(),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Car> getTheMostExpensiveCarModel() {
        Set<String> listOfCars = cars
                .stream()
                .map(Car::getModel)
                .collect(Collectors.toSet());

        return listOfCars
                .stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> cars
                                .stream()
                                .filter(f -> f.getModel().equals(v))
                                .max(Comparator.comparing(Car::getPrice))
                                .orElseThrow(() -> new NoSuchElementException("No max price"))));
    }

    public List<Car> carsWithBiggestPrice() {

        BigDecimal maxPrice = cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(Car::getPrice))
                .getMax();

        return cars
                .stream()
                .filter(car -> car.getPrice().equals(maxPrice))
                .collect(Collectors.toList());
    }

    public void statistics() {

        System.out.println("- - - - PRICE - - - - ");
        BigDecimalSummaryStatistics priceStats = cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(Car::getPrice));
        System.out.println("MAX = " + priceStats.getMax());
        System.out.println("MIN = " + priceStats.getMin());
        System.out.println("AVG = " + priceStats.getAverage());

        System.out.println("- - - - MILEAGE - - - - ");
        DoubleSummaryStatistics mileageStatistics = cars
                .stream()
                .collect(Collectors.summarizingDouble(Car::getMileage));
        System.out.println("MAX = " + mileageStatistics.getMax());
        System.out.println("MIN = " + mileageStatistics.getMin());
        System.out.println("AVG = " + mileageStatistics.getAverage());
    }

    public Map<String, List<Car>> carsWithComponent() {
        return cars
                .stream()
                .flatMap(car -> car.getEquipment()
                        .stream())
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        com -> cars
                                .stream()
                                .filter(car -> car.getEquipment()
                                        .contains(com))
                                .collect(Collectors.toList())
                ));
    }

    public List<Car> sortedAlphabeticComponents() {

        return cars
                .stream()
                .peek(x -> x.setEquipment(
                        x.getEquipment()
                                .stream()
                                .sorted()
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public List<Car> carsWithPriceBetween(BigDecimal min, BigDecimal max) {
        if (min.compareTo(max) >= 0) {
            throw new MyException(ExceptionCode.INPUT_DATA, "Min price can not be greater than the max price");
        }
        return cars
                .stream()
                .filter(f -> f.getPrice().compareTo(min) >= 0 && f.getPrice().compareTo(max) <= 0)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return cars.stream().map(Car::toString).collect(Collectors.joining("\n"));
    }
}
