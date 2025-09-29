
public class Driver {
    public static void main(String[] args) {
        Polynomial emptyP = new Polynomial();

        int[] redundExp = {1, 1, 1, 2, 5, 2, 9, 8};
        double[] coef = {1, 1, 1.5, 2.5, 5, 2.5, 9, 8};
        Polynomial p = new Polynomial(coef, redundExp);

        int[] m1exp = {1, 3};
        double[] m1coef = {2, 3};
        int[] m2exp = {1, 3};
        double[] m2coef = {3, 4};

        double[] m3coef = {-1, 3};

        Polynomial m1 = new Polynomial(m1coef, m1exp);
        Polynomial m2 = new Polynomial(m2coef, m2exp);
        Polynomial m3 = new Polynomial(m3coef, m2exp);
        Polynomial product = m1.multiply(m3);

        double[] m4coef = {1, 2, 2, 3};
        double[] m5coef = {-1, -2, 2, -3};
        int[] m4exp = {1, 2, 2, 3};
        int[] m5exp = {1, 2, 2, 3};

        Polynomial m4 = new Polynomial(m4coef, m4exp);
        Polynomial m5 = new Polynomial(m5coef, m5exp);
        Polynomial sum = m4.add(m5);

        p.saveToFile("~\\Desktop\\CSCB07\\testPolynomial.txt");
        product.saveToFile("~\\Desktop\\CSCB07\\testPolynomial.txt");
        sum.saveToFile("~\\Desktop\\CSCB07\\testPolynomial.txt");
    }
}