import java.util.List;

public final class TaskPrinter {

    private TaskPrinter() {
        throw new UnsupportedOperationException();
    }

    public static void printMenu() {
        System.out.println("""
                    1. Добавить задачу
                    2. Показать все задачи
                    3. Удалить задачу (по ID)
                    4. Отметить задачу как выполненную
                    0. Выход
                    Введите номер операции:""");
    }

    public static void printAllTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Задач нет");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.isStatus()) {
                System.out.println(task.getId() + ". [x] " + task.getName());
            } else {
                System.out.println(task.getId() + ". [ ] " + task.getName());
            }
        }
    }
}
