package helper;

import java.util.Scanner;

public class Validator {
    static Scanner sc = new Scanner(System.in);

    public static int getInt(String prompt, int min, int max) {
        do {
            System.out.print(prompt);
            if (!sc.hasNextInt() || !sc.hasNextLine()) {
                sc.nextLine();
                System.out.println("Invalid input, please try again");
                continue;
            }
            if (sc.hasNextInt() && sc.hasNextLine()) {
                int num = sc.nextInt();
                sc.nextLine();
                if (num >= min && num <= max) {
                    return num;
                }
            }
            if (max == Integer.MAX_VALUE)
                System.out.println(String.format("Invalid input, please enter a number greater than %d", min));
            else
                System.out.println(String.format("Invalid input, please enter a number between %d and %d", min, max));
        } while (true);
    }

    public static double getDouble(String prompt, double min, double max) {
        do {
            System.out.print(prompt);
            if (!sc.hasNextDouble() || !sc.hasNextLine()) {
                sc.nextLine();
                System.out.println("Invalid input, please try again");
                continue;
            }
            if (sc.hasNextDouble() && sc.hasNextLine()) {
                double num = sc.nextDouble();
                sc.nextLine();
                if (num >= min && num <= max) {
                    return num;
                }
            }
            if (max == Double.MAX_VALUE)
                System.out.println(String.format("Invalid input, please enter a number greater than %.2f", min));
            else
                System.out
                        .println(String.format("Invalid input, please enter a number between %.2f and %.2f", min, max));
        } while (true);
    }

    public static char getChar(String prompt, char[] valid) {
        do {
            System.out.print(prompt);
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                char c = input.charAt(0);
                c = Character.toUpperCase(c);
                for (char v : valid) {
                    if (c == v)
                        return c;
                }
            }
            System.out.println("Invalid input, please try again");
        } while (true);
    }

    public static String getString(String prompt) {
        do {
            System.out.print(prompt);
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (!input.isEmpty()) {
                    return input;
                }
            }
            System.out.println("Invalid input, please try again");
        } while (true);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause() {
        System.out.println("Press enter to continue...");
        sc.nextLine();
    }
}
