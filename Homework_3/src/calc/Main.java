package calc;
/*
Написать класс Калькулятор (необобщенный), который содержит обобщенные статические методы: sum(), multiply(), divide(), subtract().
Параметры этих методов – два числа разного типа, над которыми должна быть произведена операция.
Методы должны возвращать результат своей работы.
*/

public class Main {
    public static void main(String[] args) {
        System.out.println(Calculator.sum(10,100)); // 110
        System.out.println(Calculator.multiply(10,100)); // 1000
        System.out.println(Calculator.divide(10,100)); // 0.1
        System.out.println(Calculator.subtract(10,100)); // -90
    }
}
