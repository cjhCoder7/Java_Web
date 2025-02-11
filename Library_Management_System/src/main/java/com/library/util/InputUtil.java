package com.library.util;

import java.util.Arrays;
import java.util.List;

import static com.library.Main.scanner;

public class InputUtil {

    public static String inputString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public static String inputString(String prompt, String... defaultValues) {
        List<String> defaultValueList = Arrays.asList(defaultValues);
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (defaultValueList.contains(input)) {
                return input;
            }
            System.out.println("输入不合法，请重新输入");
        }
    }

    public static int inputInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("输入无效，请输入一个整数！");
            }
        }
    }
}
