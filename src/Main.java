import java.util.Scanner;

class Node {
    Student data;
    Node prev;
    Node next;

    public Node(Student data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class Student {
    private String name;
    private char gender;
    private boolean dormitory;

    public Student(String name, char gender, boolean dormitory) {
        this.name = name;
        this.gender = gender;
        this.dormitory = dormitory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public boolean isDormitory() {
        return dormitory;
    }

    public void setDormitory(boolean dormitory) {
        this.dormitory = dormitory;
    }

    @Override
    public String toString() {
        String genderStr = (gender == 'Ч') ? "чоловік" : "жінка";
        String dormitoryStr = (dormitory) ? "Проживає у гуртожитку" : "Не проживає у гуртожитку";
        return String.format("%-10s | %-7s | %s", name, genderStr, dormitoryStr);
    }
}

class DoublyLinkedList {
    Node head;
    Node tail;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void add(Student data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void remove(int index) {
        if (head == null || index < 0) return;
        Node current = head;
        int counter = 0;
        while (current != null && counter != index) {
            current = current.next;
            counter++;
        }
        if (current != null) {
            if (current.prev != null) current.prev.next = current.next;
            if (current.next != null) current.next.prev = current.prev;
            if (current == head) head = current.next;
            if (current == tail) tail = current.prev;
        }
    }

    public Student get(int index) {
        if (head == null || index < 0) throw new IndexOutOfBoundsException("Index out of bounds");
        Node current = head;
        for (int i = 0; i < index; i++) {
            if (current == null) throw new IndexOutOfBoundsException("Index out of bounds");
            current = current.next;
        }
        if (current == null) throw new IndexOutOfBoundsException("Index out of bounds");
        return current.data;
    }

    public int length() {
        int length = 0;
        Node current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    public void iterateBackward() {
        Node current = tail;
        while (current != null) {
            System.out.println(current.data);
            current = current.prev;
        }
    }

    public void sort() {
        if (head == null) return;
        Node current = head;
        while (current != null) {
            Node index = current.next;
            while (index != null) {
                if (current.data.getName().compareTo(index.data.getName()) > 0) {
                    Student temp = current.data;
                    current.data = index.data;
                    index.data = temp;
                }
                index = index.next;
            }
            current = current.next;
        }
    }

    public void searchMaleDormitory() {
        Node current = head;
        while (current != null) {
            if (current.data.getGender() == 'Ч' && current.data.isDormitory()) {
                System.out.println(current.data);
            }
            current = current.next;
        }
    }

    public void displayForward() {
        System.out.printf("%-10s | %-7s | %s\n", "Ім'я", "Стать", "Проживання");
        System.out.println("------------------------------------------");
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.println("------------------------------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoublyLinkedList list = new DoublyLinkedList();

        boolean exit = false;
        while (!exit) {
            System.out.println("\nОберіть опцію:");
            System.out.println("1. Додати студента");
            System.out.println("2. Видалити студента за індексом");
            System.out.println("3. Вивести список студентів у прямому порядку");
            System.out.println("4. Вивести список студентів у зворотному порядку");
            System.out.println("5. Сортувати список за іменем студента");
            System.out.println("6. Знайти та вивести студентів чоловічої статі, що проживають в гуртожитку");
            System.out.println("7. Вийти з програми");

            int option = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    option = Integer.parseInt(scanner.next());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Неправильний вибір. Спробуйте ще раз.");
                    scanner.nextLine(); // Clear the buffer
                }
            }

            switch (option) {
                case 1:
                    System.out.println("Введіть ім'я студента:");
                    scanner.nextLine();  // Clear the buffer
                    String name = scanner.nextLine();

                    char gender;
                    while (true) {
                        System.out.println("Введіть стать студента (Ч/Ж):");
                        gender = scanner.next().charAt(0);
                        if (gender == 'Ч' || gender == 'Ж') {
                            break;
                        } else {
                            System.out.println("Помилка: Стать студента має бути вказана як 'Ч' або 'Ж'.");
                        }
                    }

                    boolean dormitory = false;
                    while (true) {
                        System.out.println("Проживає в гуртожитку? (Так/Ні):");
                        String dormitoryInput = scanner.next();
                        if (dormitoryInput.equalsIgnoreCase("Так")) {
                            dormitory = true;
                            break;
                        } else if (dormitoryInput.equalsIgnoreCase("Ні")) {
                            dormitory = false;
                            break;
                        } else {
                            System.out.println("Помилка: Введено некоректне значення. Використовуйте 'Так' або 'Ні'.");
                        }
                    }

                    list.add(new Student(name, gender, dormitory));
                    System.out.println("Студент успішно доданий до списку.");
                    list.displayForward(); // Display the list after addition
                    break;
                case 2:
                    System.out.println("Введіть індекс(и) студентів, яких потрібно видалити (через пробіл):");
                    scanner.nextLine();  // Clear the buffer
                    String[] indices = scanner.nextLine().split("\\s+");
                    for (String indexStr : indices) {
                        try {
                            int index = Integer.parseInt(indexStr);
                            if (index >= 0 && index < list.length()) {
                                list.remove(index);
                                System.out.println("Студент з індексом " + index + " успішно видалений зі списку.");
                            } else {
                                System.out.println("Помилка: Невірний індекс " + index + ". Спробуйте ще раз.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Помилка: Невірний індекс " + indexStr + ". Спробуйте ще раз.");
                        }
                    }
                    list.displayForward(); // Display the list after removal
                    break;
                case 3:
                    System.out.println("Список студентів у прямому порядку:");
                    list.displayForward();
                    break;
                case 4:
                    System.out.println("Список студентів у зворотному порядку:");
                    list.iterateBackward();
                    break;
                case 5:
                    list.sort();
                    System.out.println("Список відсортовано за іменем студента.");
                    list.displayForward(); // Display the list after sorting
                    break;
                case 6:
                    System.out.println("Список студентів чоловічої статі, що проживають в гуртожитку:");
                    list.searchMaleDormitory();
                    break;
                case 7:
                    exit = true;
                    System.out.println("Програма завершена.");
                    break;
                default:
                    System.out.println("Неправильний вибір. Спробуйте ще раз.");
                    break;
            }
        }
        scanner.close();
    }
}
