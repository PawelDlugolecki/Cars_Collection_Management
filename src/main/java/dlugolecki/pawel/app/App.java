package dlugolecki.pawel.app;

/**
 * @author Pawel Dlugolecki
 * "Cars_Collection_Management"
 */
public class App {
    public static void main(String[] args) {
        String filename = "cars";
        new MenuService(filename).menu();
    }
}
