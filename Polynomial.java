import java.io.*;
import java.util.ArrayList;

public class Polynomial {
    private double[] coefficients;  // Stores non-zero coefficients
    private int[] exponents;        // Stores corresponding exponents

    // Constructor to initialize polynomial to zero
    public Polynomial() {
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    // Constructor to initialize polynomial with arrays for coefficients and exponents
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // Constructor to initialize polynomial from a file
    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        ArrayList<Double> coeffList = new ArrayList<>();
        ArrayList<Integer> expList = new ArrayList<>();

        // Parsing the file content into coefficients and exponents
        String[] terms = line.split("(?=\\+|-)");
        for (String term : terms) {
            String[] parts = term.split("x\\^?");
            double coefficient = Double.parseDouble(parts[0]);
            int exponent = (parts.length == 1) ? 0 : Integer.parseInt(parts[1]);
            coeffList.add(coefficient);
            expList.add(exponent);
        }

        this.coefficients = coeffList.stream().mapToDouble(Double::doubleValue).toArray();
        this.exponents = expList.stream().mapToInt(Integer::intValue).toArray();
        reader.close();
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        ArrayList<Double> resultCoeffs = new ArrayList<>();
        ArrayList<Integer> resultExps = new ArrayList<>();

        int i = 0, j = 0;
        while (i < this.coefficients.length || j < other.coefficients.length) {
            if (i < this.coefficients.length && (j >= other.coefficients.length || this.exponents[i] < other.exponents[j])) {
                resultCoeffs.add(this.coefficients[i]);
                resultExps.add(this.exponents[i]);
                i++;
            } else if (j < other.coefficients.length && (i >= this.coefficients.length || other.exponents[j] < this.exponents[i])) {
                resultCoeffs.add(other.coefficients[j]);
                resultExps.add(other.exponents[j]);
                j++;
            } else {
                resultCoeffs.add(this.coefficients[i] + other.coefficients[j]);
                resultExps.add(this.exponents[i]);
                i++;
                j++;
            }
        }

        return new Polynomial(
            resultCoeffs.stream().mapToDouble(Double::doubleValue).toArray(),
            resultExps.stream().mapToInt(Integer::intValue).toArray()
        );
    }

    // Method to multiply two polynomials
    public Polynomial multiply(Polynomial other) {
        ArrayList<Double> resultCoeffs = new ArrayList<>();
        ArrayList<Integer> resultExps = new ArrayList<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double newCoeff = this.coefficients[i] * other.coefficients[j];
                int newExp = this.exponents[i] + other.exponents[j];

                int index = resultExps.indexOf(newExp);
                if (index != -1) {
                    resultCoeffs.set(index, resultCoeffs.get(index) + newCoeff);
                } else {
                    resultCoeffs.add(newCoeff);
                    resultExps.add(newExp);
                }
            }
        }

        return new Polynomial(
            resultCoeffs.stream().mapToDouble(Double::doubleValue).toArray(),
            resultExps.stream().mapToInt(Integer::intValue).toArray()
        );
    }

    // Method to evaluate the polynomial for a given value of x
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    // Method to check if the given value is a root
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Method to save the polynomial to a file
    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] > 0 && sb.length() > 0) sb.append("+");
            if (exponents[i] == 0) sb.append(coefficients[i]);
            else if (exponents[i] == 1) sb.append(coefficients[i]).append("x");
            else sb.append(coefficients[i]).append("x^").append(exponents[i]);
        }
        writer.write(sb.toString());
        writer.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] > 0 && sb.length() > 0) sb.append("+");
            if (exponents[i] == 0) sb.append(coefficients[i]);
            else if (exponents[i] == 1) sb.append(coefficients[i]).append("x");
            else sb.append(coefficients[i]).append("x^").append(exponents[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Polynomial p1 = new Polynomial(new double[]{6, -2, 5}, new int[]{0, 1, 3});
        Polynomial p2 = new Polynomial(new double[]{5, 3}, new int[]{2, 4});

        Polynomial sum = p1.add(p2);
        Polynomial product = p1.multiply(p2);

        System.out.println("Sum: " + sum);
        System.out.println("Product: " + product);
        System.out.println("Evaluate p1 at x = 2: " + p1.evaluate(2));

        if (p1.hasRoot(1)) {
            System.out.println("1 is a root of p1");
        } else {
            System.out.println("1 is not a root of p1");
        }

        // Save the polynomial to a file
        p1.saveToFile("polynomial.txt");

        // Load polynomial from a file
        Polynomial pFromFile = new Polynomial(new File("polynomial.txt"));
        System.out.println("Loaded from file: " + pFromFile);
    }
}
