import java.util.Scanner;

public class todos {
    static String [] zadachi = new String[100];
    static boolean [] vipolneno = new boolean[100];
    static int kolichestvoZadach = 0;


    static void glavnoeMenu () {
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Удалить задачу (по номеру)");
        System.out.println("4. Отметить задачу как выполненную");
        System.out.println("0. Выход");
        System.out.println("Введите номер операции:");
    }

    static void dobavitZadachu(Scanner scanner) {
        if (kolichestvoZadach >= 100) {
            System.out.println("Количество задач - переполнено. Удалите одну.");
        } else {
            System.out.print("Введите название задачи:");
            String nasvanieZadachi = scanner.nextLine();
            zadachi[kolichestvoZadach] = nasvanieZadachi;
            vipolneno[kolichestvoZadach] = false;
            kolichestvoZadach++;
            System.out.println("Задача добавлена");
        }
    }

    static void pokazatVseZadachi() {
        if (kolichestvoZadach == 0) {
            System.out.println("Задач нет");
        } else {
            for (int i = 0; i < kolichestvoZadach; i++) {
                if (vipolneno[i] == false) {
                    System.out.println((i + 1) + ". " + "[ ] " + zadachi[i]);
                } else {
                    System.out.println((i + 1) + ". " + "[x] " + zadachi[i]);
                }
            }
        }
    }

    static void udalitZadachu(Scanner scanner) {
        if (kolichestvoZadach == 0) {
            System.out.println("Нету задач для удаления, добавьте новую");
        } else {
            System.out.println("Введите номер задачи для удаления:");

            if (scanner.hasNextInt()) {
                int nomerzadachi = scanner.nextInt();

                if (nomerzadachi > kolichestvoZadach ||  nomerzadachi <= 0) {
                    System.out.println("Этот номер задачи не существует");
                } else {
                    for (int index = nomerzadachi - 1; index < kolichestvoZadach - 1; index++) {
                            zadachi[index] = zadachi[index + 1];
                            vipolneno[index] = vipolneno[index + 1];
                    }
                    zadachi[kolichestvoZadach - 1] = null;
                    kolichestvoZadach--;
                }
            } else {
                System.out.println("Введите числовое значение");
                scanner.next();
            }
        }
    }

    static void vipolnenieZadachi(Scanner scanner) {
        System.out.println("Введите номер задачи для выполнения:");

        if (scanner.hasNextInt()) {
            int nomerVipolneniya = scanner.nextInt();

            if (nomerVipolneniya > kolichestvoZadach || nomerVipolneniya <= 0) {
                System.out.println("Этот номер задачи не существует");
            } else {
                if (vipolneno[nomerVipolneniya - 1] == true) {
                    System.out.println("Эта задача уже была выполнена");
                } else {
                    vipolneno[nomerVipolneniya - 1] = true;
                    System.out.println("Задача " + zadachi[nomerVipolneniya - 1] + " отмечена как выполненная");
                }
            }
        } else {
            System.out.println("Введите числовое значение");
            scanner.next();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 100; i++) {
            zadachi[i] = "Задача " + (i + 1);
        } kolichestvoZadach = 100;

        boolean rabotaem = true;

        while (rabotaem) {
            glavnoeMenu();

            if (scanner.hasNextInt()) {
                int operaciya = scanner.nextInt();

                switch (operaciya) {
                    case 1 -> dobavitZadachu(scanner);
                    case 2 -> pokazatVseZadachi();
                    case 3 -> udalitZadachu(scanner);
                    case 4 -> vipolnenieZadachi(scanner);
                    case 0 -> {
                        rabotaem = false;
                        System.out.println("Пока");
                    }
                    default -> System.out.println("Введите корректный номер операции из перечисленных ниже");
                }
            } else {
                System.out.println("Нужно ввести число");
                scanner.next();
            }
        }
    }
}



/* показываем меню с командами
если выбрали "Добавить задачу", то добавляем одну новую задачу - спрашивая название задачи
если выбрали "Показать все задачи", то показываем все доступные задачи с порядковыми номерами, если список задач пустой - то пишем сообщение - "Задач нет"
если выбрали "Удалить задачу (по номеру)", то справшиваем номер задачи и потом удаляем задачу. Удаляя задачу перепоставляем номера для задач
если выбрали "Отметить задачу как выполненную", то спрашиваем номер задачи и отмечаем как выполненую. Повторно выполнять нельзя - должно выходит сообщение - "Уже выполнено"
если выбрали "Выход", то завершаем работу
после каждой успешной выполненой операции должно быть переход в главное меню.
 */