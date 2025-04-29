import java.util.Scanner;

public class Utilitaire {
    private static final Scanner scanner = new Scanner(System.in);

    public static String lire(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
