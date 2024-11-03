package calc;

public class Calculator {
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