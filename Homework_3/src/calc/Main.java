package calc;
/*

*/

public class Main {
    public static void main(String[] args) {
        System.out.println(Calculator.sum(10,100)); // 110
        System.out.println(Calculator.multiply(10,100)); // 1000
        System.out.println(Calculator.divide(10,100)); // 0.1
        System.out.println(Calculator.subtract(10,100)); // -90
    }

    public static class Calculator {
        public static <T extends Number, U extends Number> Number sum(T firstNum, U secondNum) {
            return firstNum.doubleValue() + secondNum.doubleValue();
        }
        public static <T extends Number, U extends Number> Number multiply(T firstNum, U secondNum) {
            return firstNum.doubleValue() * secondNum.doubleValue();
        }
        public static <T extends Number, U extends Number> Number divide(T firstNum, U divisor) {
            return firstNum.doubleValue() / divisor.doubleValue();
        }
        public static <T extends Number, U extends Number> Number subtract(T firstNum, U subtracted) {
            return firstNum.doubleValue() - subtracted.doubleValue();
        }
    }
}