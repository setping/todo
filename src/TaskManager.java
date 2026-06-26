import java.util.Scanner;

public class TaskManager {
    static Scanner scanner = new Scanner(System.in);
    static String[] tasks = new String[100];
    static boolean[] completed = new boolean[100];
    static int taskCount = 0;

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();

            if (!scanner.hasNextInt()) {
                System.out.println("Нужно ввести число");
                scanner.next();
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> addTask();
                case 2 -> showAllTasks();
                case 3 -> deleteTask();
                case 4 -> completeTask();
                case 0 -> {
                    running = false;
                    System.out.println("Пока!");
                }
                default -> System.out.println("Введите корректный номер операции из перечисленных ниже");
            }
        }
    }

    static void printMenu() {
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Удалить задачу (по номеру)");
        System.out.println("4. Отметить задачу как выполненную");
        System.out.println("0. Выход");
        System.out.println("Введите номер операции:");
    }

    static void addTask() {
        if (taskCount >= 100) {
            System.out.println("Список задач переполнен! Удалите одну.");
            return;
        }
        System.out.print("Введите описание задачи: ");
        String taskName = scanner.nextLine();
        tasks[taskCount] = taskName;
        completed[taskCount] = false;
        taskCount++;
        System.out.println("Задача добавлена!");
    }

    static void showAllTasks() {
        if (taskCount == 0) {
            System.out.println("Задач нет");
            return;
        }
        for (int i = 0; i < taskCount; i++) {
            if (completed[i]) {
                System.out.println((i + 1) + ". [x] " + tasks[i]);
            } else {
                System.out.println((i + 1) + ". [ ] " + tasks[i]);
            }
        }
    }

    static void deleteTask() {
        if (taskCount == 0) {
            System.out.println("Нет задач для удаления, добавьте новую");
            return;
        }
        System.out.println("Введите номер задачи для удаления:");
        if (!scanner.hasNextInt()) {
            System.out.println("Нужно ввести число");
            scanner.next();
            return;
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        if (number < 1 || number > taskCount) {
            System.out.println("Этот номер задачи не существует");
            return;
        }

        for (int i = number - 1; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
            completed[i] = completed[i + 1];
        }
        taskCount--;
        tasks[taskCount] = null;
        System.out.println("Задача удалена!");
    }

    static void completeTask() {
        if (taskCount == 0) {
            System.out.println("Нет задач, добавьте новую");
            return;
        }

        System.out.println("Введите номер задачи для отметки:");
        if (!scanner.hasNextInt()) {
            System.out.println("Нужно ввести число");
            scanner.next();
            return;
        }

        int number = scanner.nextInt();
        scanner.nextLine();
        if (number < 1 || number > taskCount) {
            System.out.println("Этот номер задачи не существует");
            return;
        }

        if (completed[number - 1]) {
            System.out.println("Эта задача уже была выполнена");
            return;
        }

        completed[number - 1] = true;
        System.out.println("Задача \"" + tasks[number - 1] + "\" отмечена как выполненная!");
    }
}
