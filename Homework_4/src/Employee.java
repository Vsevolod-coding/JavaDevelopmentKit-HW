/*
Табельный номер
Номер телефона
Имя
Стаж
*/

public class Employee {
    private static int lastAssignedId = 0;
    private final int id;
    private final String name;
    private long phoneNumber;
    private int experience;

    /**
     * Создает нового сотрудника с уникальным ID, именем, номером телефона и опытом.
     * ID присваивается автоматически и увеличивается на 1 с каждым новым сотрудником.
     *
     * @param name имя сотрудника
     * @param phoneNumber номер телефона сотрудника в формате Long
     * @param experience опыт работы сотрудника в годах
     */
    public Employee(String name, long phoneNumber, int experience) {
        if (phoneNumber < 0) {
            throw new IllegalArgumentException("Номер телефона не может быть отрицательным.");
        }
		if (experience < 0) {
            throw new IllegalArgumentException("Стаж не может быть отрицательным.");
        }
        this.id = ++lastAssignedId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Устанавливает номер телефона сотрудника.
     * Проверяется, что номер телефона не является отрицательным числом.
     * Если номер телефона меньше 0, выбрасывается исключение {@link IllegalArgumentException}.
     *
     * @param phoneNumber номер телефона сотрудника в формате {@code long}, должен быть неотрицательным
     * @throws IllegalArgumentException если номер телефона меньше 0
     */
    public void setPhoneNumber(long phoneNumber) {
        if (phoneNumber < 0) {
            throw new IllegalArgumentException("Номер телефона не может быть отрицательным.");
        }
        this.phoneNumber = phoneNumber;
    }

    public int getExperience() {
        return experience;
    }

    /**
     * Устанавливает стаж сотрудника.
     * Проверяется, что стаж не является отрицательным числом.
     * Если стаж меньше 0, выбрасывается исключение {@link IllegalArgumentException}.
     *
     * @param experience стаж сотрудника в годах, должен быть неотрицательным
     * @throws IllegalArgumentException если стаж меньше 0
     */
    public void setExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Стаж не может быть отрицательным.");
        }
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "{Name: " + name + ", phone number: " + phoneNumber + ", experience: " + experience + " years}";
    }
}
