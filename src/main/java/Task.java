public class Task {
    private int priority;
    private String deadline;
    private String description;
    private String name;
    private int id;
    private boolean status;

    public Task(int priority, String deadline, String description, String name, int id) {
        setPriority(priority);
        setDeadline(deadline);
        setDescription(description);
        setName(name);
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        if (priority < 1 || priority > 5) {
            System.out.println("Приоритет задачи может быть только от 1 до 5");
            return;
        }
        this.priority = priority;
    }

    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        if (deadline == null ||  deadline.isBlank()) {
            System.out.println("Дедлайн не может быть пустым");
            return;
        }
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            System.out.println("Описание задачи не может быть пустым");
            return;
        }
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            System.out.println("Название не может быть пустым");
            return;
        }
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}