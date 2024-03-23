package gb.java_core.UI;

import gb.java_core.Model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

import static gb.java_core.Model.Student.specialityList;


public class MainMenu extends Menu {

    public MainMenu() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(this::showTask1, "Задача 1"));
        menuItems.add(new MenuItem(this::showTask2, "Задача 2"));
        menuItems.add(new MenuItem(this::showTask3, "Задача 3"));
        menuItems.add(new MenuItem(this::quit, "Выйти"));
    }

    public void quit() {
        ConsoleUi.close();
    }

    public void showTask1() {
        ConsoleUi.print("Написать прототип компаратора - метод внутри класса сотрудника,\n" +
                "сравнивающий две даты, представленные в виде трёх чисел гггг-мм-дд \n", Colors.MAGENTA);

        List<Employee> employees = EmployeesData.EmployeeList();
        ConsoleUi.println(employees.toString().replace("[", "").replace("]", ""), Colors.YELLOW);
        ConsoleUi.println("Разница дат рождения: " + employees.get(0).compareTo(employees.get(1)), Colors.YELLOW);
    }

    public void showTask2() {
        ConsoleUi.print("Опишите класс руководителя, наследник от сотрудника. Перенесите статический метод повышения зарплаты в класс руководителя,\n" +
                "модифицируйте метод таким образом, чтобы он мог поднять заработную плату всем, кроме руководителей.\n" +
                "В основной программе создайте руководителя и поместите его в общий массив сотрудников.\n" +
                "Повысьте зарплату всем сотрудникам и проследите, чтобы зарплата руководителя не повысилась.\n", Colors.MAGENTA);

        List<Employee> employees = EmployeesData.EmployeeList();
        ConsoleUi.print(employees.toString().replace("[", "").replace("]", ""), Colors.YELLOW);

        double raiseSalary= 0.00;
        boolean flag;
            flag = true;
            while (flag) {
                ConsoleUi.print("Введите зарплату: ", Colors.BLUE);
                if (ConsoleUi.scanner.hasNextDouble()) {
                    raiseSalary = ConsoleUi.scanner.nextDouble();
                    flag = false;
                } else {
                    System.out.println("Допускается ввод только цифр и запятой! ");
                    flag = true;
                }
            }
        ConsoleUi.println("Результат: ",Colors.YELLOW);
        Director.upSalaryForEmployee(employees,raiseSalary);
        ConsoleUi.println(employees.toString().replace("[","").replace("]",""),Colors.YELLOW);
    }


    public void showTask3() {
        ConsoleUi.print("Имеется список студентов. Каждый студент имеет имя, список оценок и специальность.\n" +
                "Найдите первых 5 студентов специальности \"Информатика\" с средним баллом выше 4.5, отсортированных по убыванию среднего балла.\n" +
                "В решении не использовать циклы! Только StreamAPI.\n", Colors.MAGENTA);

        List<Student> students = StudentData.StudentList();
        students.stream()
                .map(Student::toString)
                .forEach(System.out::println);

        List<Student> result = students.stream()
                .filter(s -> s.getSpecialty().equals(specialityList[2]))
                .filter(s -> s.getAverageGrade() > 4.5)
                .sorted(Comparator.comparingDouble(s -> -s.getAverageGrade()))
                .limit(15).toList();
        ConsoleUi.println("Результат: ", Colors.YELLOW);
        result.stream()
                .map(a -> a.getName() + ": " + a.getAverageGrade())
                .forEach(System.out::println);

    }
}
