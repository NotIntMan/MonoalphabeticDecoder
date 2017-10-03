package io.github.nim_pro.isecurity.lab_minus_1;

import javafx.util.Pair;

import java.util.*;

public class Main {

    public static final int CONSOLE_WIDTH = 80;

    public static void readAssoc(Scanner s, Association<Character, Character> assoc) {
        System.out.print("Введите предположение: ");
        try {
            assoc.copy(Association.scanAssoc(s));
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    public static Vector<String> splitWithLength(final String input, final int length) throws IllegalArgumentException {
        if (length <= 0)
            throw new IllegalArgumentException();
        Vector<String> result = new Vector<>();
        final int L = input.length();
        for (int i = 0; i < L; i += length)
            result.add(input.substring(i, Math.min(L, i + length)));
        return result;
    }

    public static Vector<Character> findTopSymbols(final String coded, Association<Character, Character> assoc) {
        HashMap<Character, Integer> counts = new HashMap<>();
        final int L = coded.length();
        for (int i = 0; i < L; i++) {
            char c = coded.charAt(i);
            if (!assoc.has(c) && !Character.isWhitespace(c)) {
                int count = 0;
                if (counts.containsKey(c))
                    count = counts.get(c);
                counts.put(c, count + 1);
            }
        }
        Vector<HashMap.Entry<Character, Integer>> entries = new Vector<>(counts.entrySet());
        entries.sort((l, r) -> r.getValue() - l.getValue());
        Vector<Character> result = new Vector<>(entries.size());
        for (HashMap.Entry<Character, Integer> entry : entries)
            result.add(entry.getKey());
        return result;
    }

    public static void printWithAssoc(final String coded, Association<Character, Character> assoc) throws Exception {
        StringBuilder processed_builder = new StringBuilder();
        StringBuilder decoded_builder = new StringBuilder();
        for (int i = 0; i < coded.length(); i++) {
            Character key = coded.charAt(i);
            if (assoc.has(key)) {
                processed_builder.append('|');
                decoded_builder.append(assoc.get(key));
            } else {
                processed_builder.append(' ');
                decoded_builder.append(key);
            }
        }
        Vector<String> processed_vec = splitWithLength(processed_builder.toString(), CONSOLE_WIDTH);
        Vector<String> decoded_vec = splitWithLength(decoded_builder.toString(), CONSOLE_WIDTH);
        Vector<String> coded_vec = splitWithLength(coded, CONSOLE_WIDTH);
        final int L = decoded_vec.size();
        if (!(
                (processed_vec.size() == L) && (coded_vec.size() == L)
        ))
            throw new Exception("Unexpected behaviour: equal size input for splitWithLength produces different size output");
        for (int i = 0; i < L; i++) {
            System.out.println(coded_vec.get(i));
            System.out.println(processed_vec.get(i));
            System.out.println(decoded_vec.get(i));
            System.out.println(' ');
        }
        System.out.print("Top of undefined symbols: ");
        Vector<Character> symbols = findTopSymbols(coded, assoc);
        final int sL = symbols.size();
        for (int i = 0; i < sL; i++) {
            if (i > 0)
                System.out.print(", ");
            System.out.print(symbols.get(i));
        }
        System.out.println("");
    }

    public static void main(String[] args) {
	    Scanner stdin = new Scanner(System.in);
	    Association<Character, Character> accos = new Association<>();
        System.out.print("Введите зашифрованное сообщение: ");
        String coded_message = stdin.nextLine();
        try {
            while(true) {
                System.out.print("Введите предположение: ");
                String input = stdin.nextLine().trim();
                if (input.length() == 0)
                    break;
                readAssoc(new Scanner(input), accos);
                System.out.println("Попытка расшифровать: ");
                printWithAssoc(coded_message, accos);
            }
            System.out.println("Завершение работы.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
