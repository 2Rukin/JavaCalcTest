
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws InputException {
        System.out.println("Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами");
        System.out.println("Формат ввода должен быть такой : a + b, a - b, a * b, a / b.");
        System.out.println("Формат ввода без пробела  между оператором и операндом не допускается.");
        System.out.println("Калькулятор умеет работать или с арабскими (1,2,3,4,5…), или с римскими (I,II,III,IV,V…) числами < =10.");
        System.out.println("Введите два числа:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        if (regexChek("[+]|[-]|[*]|[/]", input)) {
            String result = calc(input);
            System.out.println(result);
        } else throw new InputException("строка не является математической операцией");

    }

    public static boolean regexChek(String str2Chek, String input) {
        Pattern chekRegex = Pattern.compile(str2Chek);
        Matcher regexMather = chekRegex.matcher(input);
        while (regexMather.find()) {
            if (0 != regexMather.group().length())
                return true;
        }
        return false;

    }

    public static String calc(String input) throws InputException {

        String[] parts = input.split(" ");
        if (parts.length > 3) {
            throw new InputException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }


        String action = parts[1];


        String a = parts[0];
        String b = parts[2];
        boolean Roman = isRomanChek(a, b);
        if (Roman) {

            return ("" + romanResultToString(romanCalc(a, b, action)));
        }
        boolean Arabic = (Integer.parseInt(a) > 0) & (Integer.parseInt(b) > 0);
        if (Arabic) {
            return ("" + arabicCalc(a, b, action));
        } else
            System.exit(1);

        return null;
    }

    private static String romanResultToString(int romanCalc) {

        int n = romanCalc % 10;
        int h = romanCalc % 100 / 10;
        int y = romanCalc / 100;
        int hh = h * 10;
        int yy = y * 100;
        String nnn = "";
        String hhh = "";
        String yyy = "";

        Roman[] romans = Roman.values();
        for (Roman r : romans) {
            if (r.getMean() == n) {
                nnn = r.name();
            }
        }
        for (Roman r : romans) {
            if (r.getMean() == hh) {
                hhh = r.name();
            }
        }
        for (Roman r : romans) {
            if (r.getMean() == yy) {
                yyy = r.name();
            }
        }

        return yyy + hhh + nnn;
    }

    public static boolean isRomanChek(String a, String b) throws InputException {
        boolean isRoman = false;
        boolean isRomanA = false;
        boolean isRomanB = false;
        Roman[] romans = Roman.values();
        for (Roman r : romans) {
            if (a.equals(r.name())) {
                isRomanA = true;
                break;
            }
        }
        for (Roman k : romans) {
            if (b.equals(k.name())) {
                isRomanB = true;
                break;
            }
        }
        if (isRomanA & isRomanB) {
            isRoman = true;
        } else if ((isRomanB & !isRomanA) | (!isRomanB & isRomanA)) {
            throw new InputException("Используются одновременно разные системы счисления");
        }
        return isRoman;

    }

    public static int romanCalc(String a, String b, String action) throws InputException {
        int aa = 0;
        int bb = 0;
        int cc = 0;

        Roman[] romans = Roman.values();
        for (Roman r : romans) {
            if (a.equals(r.name())) {
                aa = r.getMean();
            }
        }
        for (Roman k : romans) {
            if (b.equals(k.name())) {
                bb = k.getMean();
            }
        }
        if (aa > 10 | bb > 10) {
            throw new InputException("Ошибка : Ввод больше X");
        } else if (action.endsWith("+")) {
            cc = aa + bb;
        } else if (action.endsWith("-")) {
            cc = aa - bb;
        } else if (action.endsWith("*")) {
            cc = aa * bb;
        } else if (action.endsWith("/")) {
            cc = aa / bb;
        }

        if (cc < 0) {
            throw new InputException("т.к. в римской системе нет отрицательных чисел");
        }
        return cc;
    }


    public static int arabicCalc(String a, String b, String action) throws InputException {
        int aa = Integer.parseInt(a);
        int bb = Integer.parseInt(b);
        int cc = 0;

        if (aa > 10 | bb > 10) {
            throw new InputException("Ошибка : Ввод больше 10");
        } else if (action.endsWith("+")) {
            cc = aa + bb;
        } else if (action.endsWith("-")) {
            cc = aa - bb;
        } else if (action.endsWith("*")) {
            cc = aa * bb;
        } else if (action.endsWith("/")) {
            cc = aa / bb;
        }
        return cc;
    }

}





