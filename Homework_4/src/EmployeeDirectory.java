/*
Создать класс справочник сотрудников, который содержит внутри
коллекцию сотрудников

Добавить метод, который ищет сотрудника по стажу (может быть список)
Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
Добавить метод, который ищет сотрудника по табельному номеру
Добавить метод добавления нового сотрудника в справочник
*/

import java.util.ArrayList;
import java.util.List;

public class EmployeeDirectory {
    private final List<Employee> employees;

    public EmployeeDirectory() {
        employees = new ArrayList<>();
    }

    /**
     * Ищет сотрудников с заданным количеством лет опыта.
     *
     * @param xp количество лет опыта, по которому производится поиск
     * @return список сотрудников, чья продолжительность опыта соответствует указанному значению
     */
    public ArrayList<Employee> getEmployeeByXpInYears(int xp) {
        ArrayList<Employee> employeesXp = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getExperience() == xp) {
                employeesXp.add(employee);
            }
        }
        return employeesXp;
    }

    /**
     * Ищет номера телефонов сотрудников по указанному имени.
     *
     * @param employeeName имя сотрудника, по которому производится поиск
     * @return список номеров телефонов сотрудников с данным именем
     */
    public ArrayList<Long> getPhoneNumberByName(String employeeName) {
        ArrayList<Long> employeePhoneNumbers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getName().equalsIgnoreCase(employeeName)) {
                employeePhoneNumbers.add(employee.getPhoneNumber());
            }
        }
        return employeePhoneNumbers;
    }

    /**
     * Ищет сотрудника по уникальному идентификатору.
     *
     * @param id уникальный идентификатор сотрудника
     * @return объект сотрудника, если найден, или null, если сотрудник не найден
     */
    public Employee getEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Ищет сотрудника по имени.
     *
     * @param name имя сотрудника, по которому производится поиск
     * @return объект сотрудника, если найден, или null, если сотрудник не найден
     */
    public Employee getEmployeeByName(String name) {
        for (Employee employee : employees) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Добавляет сотрудника в коллекцию сотрудников.
     *
     * @param employee объект сотрудника, который нужно добавить
     */
    public void add(Employee employee) {
        employees.add(employee);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EmployeeDirectory:");
        for (Employee employee : employees) {
            sb.append(System.lineSeparator()).append(employee);
        }
        return sb.toString();
    }
}
