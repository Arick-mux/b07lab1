public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        int maxDegree = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxDegree];
        for (int i = 0; i < maxDegree; i++) {
            result[i] = (i < this.coefficients.length ? this.coefficients[i] : 0) 
                      + (i < other.coefficients.length ? other.coefficients[i] : 0);
        }
        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] != 0) {
                if (sb.length() > 0 && coefficients[i] > 0) sb.append("+");
                if (i == 0) sb.append(coefficients[i]);
                else if (i == 1) sb.append(coefficients[i]).append("x");
                else sb.append(coefficients[i]).append("x^").append(i);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(new double[]{6, -2, 0, 5});
        Polynomial p2 = new Polynomial(new double[]{1, 3});
        Polynomial sum = p1.add(p2);
        System.out.println("Sum: " + sum);
        System.out.println("p1 evaluated at x = -1: " + p1.evaluate(-1));
        System.out.println("-1 is a root of p1: " + p1.hasRoot(-1));
    }
}
