import java.util.Scanner;

public class TaskManager {
    private Scanner scanner = new Scanner(System.in);
    private TaskHandler taskHandler;
    private int nextID = 1;

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.start();
    }

    public void start() {
        taskHandler = chooseTaskHandler();

        boolean running = true;
        while (running) {
            TaskPrinter.printMenu();

            if (!scanner.hasNextInt()) {
                System.out.println("Нужно ввести число");
                scanner.next();
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1 -> addTask();
                    case 2 -> TaskPrinter.printAllTasks(taskHandler.getTasks());  // 📚→🖨
                    case 3 -> deleteTask();
                    case 4 -> completeTask();
                    case 0 -> {
                        running = false;
                        System.out.println("Пока!");
                    }
                    default -> System.out.println("Введите корректный номер операции");
                }
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private TaskHandler chooseTaskHandler() {
        System.out.println("Хранить задачи в базе данных? (1 - да, 0 - нет):");
        String answer = scanner.nextLine().trim();
        if (!answer.equals("1")) {
            System.out.println("Задачи хранятся в памяти");
            return new InMemoryTaskHandler();
        }

        String userName = readUserName();
        try {
            UserService userService = new UserService();
            int userId = userService.saveUser(userName);
            System.out.println("Задачи хранятся в базе данных");
            return new DatabaseTaskHandler(userId);
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
            System.out.println("База недоступна, задачи хранятся в памяти");
            return new InMemoryTaskHandler();
        }
    }

    private String readUserName() {
        while (true) {
            System.out.print("Имя пользователя: ");
            String userName = scanner.nextLine().trim();
            if (!userName.isBlank()) {
                return userName;
            }
            System.out.println("Имя не может быть пустым");
        }
    }

    private void addTask() {
        System.out.print("Название задачи: ");
        String name = scanner.nextLine();

        System.out.print("Описание: ");
        String description = scanner.nextLine();

        System.out.print("Приоритет (1-5): ");
        int priority = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Дедлайн (ДД.ММ.ГГГГ: ");
        String deadline = scanner.nextLine();

        Task task = new Task(priority, deadline, description, name, nextID);
        nextID++;
        taskHandler.addTask(task);
        System.out.println("Задача добавлена");
    }

    private void deleteTask() {
        if (taskHandler.getTasks().isEmpty()) {
            System.out.println("Нет задач для удаления, добавьте новую");
            return;
        }

        System.out.println("Введите ID задачи для удаления:");
        if (!scanner.hasNextInt()) {
            System.out.println("Нужно ввести число");
            scanner.next();
            return;
        }

        int id = scanner.nextInt();
        scanner.nextLine();
        if (taskHandler.deleteTask(id)) {
            System.out.println("Задача удалена");
        } else {
            System.out.println("Задача с таким ID не найдена");
        }

    }

    private void completeTask() {
        if (taskHandler.getTasks().isEmpty()) {
            System.out.println("Нет задач, добавьте новую");
            return;
        }

        System.out.println("Введите ID задачи для отметки:");
        if (!scanner.hasNextInt()) {
            System.out.println("Нужно ввести число");
            scanner.next();
            return;
        }

        int id = scanner.nextInt();
        scanner.nextLine();
        if (taskHandler.completeTask(id)) {
            System.out.println("Задача отмечена как выполненная");
        } else {
            System.out.println("Задача с таким ID не найдена");
        }
    }
}
