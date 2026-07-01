import java.util.List;

public final class TaskPrinter {

    private TaskPrinter() {
        throw new UnsupportedOperationException();
    }

    public static void printMenu() {
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Удалить задачу (по номеру)");
        System.out.println("4. Отметить задачу как выполненную");
        System.out.println("0. Выход");
        System.out.println("Введите номер операции:");
    }

    public static void printAllTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Задач нет");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.isStatus()) {
                System.out.println((i + 1) + ". [x] " + task.getName());
            } else {
                System.out.println((i + 1) + ". [ ] " + task.getName());
            }
        }
    }
}
