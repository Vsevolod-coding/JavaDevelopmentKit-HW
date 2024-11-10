/*
Создать справочник сотрудников
Необходимо:
Создать класс справочник сотрудников, который содержит внутри
коллекцию сотрудников - каждый сотрудник должен иметь следующие атрибуты:
Табельный номер
Номер телефона
Имя
Стаж
Добавить метод, который ищет сотрудника по стажу (может быть список)
Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
Добавить метод, который ищет сотрудника по табельному номеру
Добавить метод добавления нового сотрудника в справочник
 */

public class Main {
    public static void main(String[] args) {
        EmployeeDirectory employeeDirectory = new EmployeeDirectory();
        employeeDirectory.add(new Employee("Всеволод", 89209990000L, 4));
        employeeDirectory.add(new Employee("Никита", 89991239181L, 2));
        employeeDirectory.add(new Employee("Влад", 89156783121L, 11));
        employeeDirectory.add(new Employee("Никита", 89990808009L, 7));
        employeeDirectory.add(new Employee("Влад", 89559768856L, 0));
        employeeDirectory.add(new Employee("Стас", 89209876534L, 14));
        employeeDirectory.add(new Employee("Ольга", 89203278833L, 1));
        employeeDirectory.add(new Employee("Влад", 89919009090L, 10));
        employeeDirectory.add(new Employee("Мария", 89123456798L, 11));

        System.out.println(employeeDirectory.getEmployeeByXpInYears(10)); // [{Name: Влад, phone number: 89919009090, experience: 10 years}]
        System.out.println(employeeDirectory.getEmployeeById(2)); // {Name: Никита, phone number: 89991239181, experience: 2 years}
        System.out.println(employeeDirectory.getPhoneNumberByName("Никита")); // [89991239181, 89990808009]

        // {Name: Всеволод, phone number: 89209990000, experience: 4 years}
        System.out.println(employeeDirectory.getEmployeeByName("Всеволод"));

        System.out.println(employeeDirectory.getEmployeeById(0)); // null
        System.out.println(employeeDirectory);
        // {Name: Всеволод, phone number: 89209990000, experience: 4 years}
        // {Name: Никита, phone number: 89991239181, experience: 2 years}
        // {Name: Влад, phone number: 89156783121, experience: 11 years}
        // etc.
    }
}