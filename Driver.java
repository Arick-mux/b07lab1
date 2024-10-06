import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        try {
            // Test default constructor
            Polynomial p = new Polynomial();
            System.out.println("Default Polynomial: " + p);
            System.out.println("p(3) = " + p.evaluate(3));

            // Test constructor with coefficients and exponents
            double[] c1 = {6, 0, 0, 5};  // Polynomial: 6 + 5x^3
            int[] e1 = {0, 1, 2, 3};
            Polynomial p1 = new Polynomial(c1, e1);
            System.out.println("Polynomial p1: " + p1);

            double[] c2 = {0, -2, 0, 0, -9};  // Polynomial: -2x + -9x^4
            int[] e2 = {0, 1, 2, 3, 4};
            Polynomial p2 = new Polynomial(c2, e2);
            System.out.println("Polynomial p2: " + p2);

            // Test addition
            Polynomial sum = p1.add(p2);
            System.out.println("Sum of p1 and p2: " + sum);

            // Test multiplication
            Polynomial product = p1.multiply(p2);
            System.out.println("Product of p1 and p2: " + product);

            // Test evaluation
            System.out.println("p1(0.1) = " + p1.evaluate(0.1));

            // Test if 1 is a root of the polynomial
            if (p1.hasRoot(1)) {
                System.out.println("1 is a root of p1");
            } else {
                System.out.println("1 is not a root of p1");
            }

            // Save polynomial to file and load from file
            p1.saveToFile("polynomial.txt");
            Polynomial pFromFile = new Polynomial(new File("polynomial.txt"));
            System.out.println("Polynomial loaded from file: " + pFromFile);

        } catch (IOException e) {
            System.out.println("Error handling file: " + e.getMessage());
        }
    }
}
