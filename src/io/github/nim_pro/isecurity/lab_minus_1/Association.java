package io.github.nim_pro.isecurity.lab_minus_1;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Association<K, V> {
    private HashMap<K, V> assocs;
    Association() {
        assocs = new HashMap<>();
    }
    Association(Association<K, V> association) {
        assocs = new HashMap<>();
        copy(association);
    }
    public void put(K l, V r) {
        assocs.put(l, r);
    }
    public boolean has(K l) {
        return assocs.containsKey(l);
    }
    public V get(K l) {
        return assocs.get(l);
    }
    public void copy(Association<K, V> association) {
        for (Entry<K, V> entry : association.assocs.entrySet())
            put(entry.getKey(), entry.getValue());
    }
    private static String scanWord(Scanner s) throws NoSuchElementException, IllegalStateException {
        return s.next();
    }
    private static void scanArrow(Scanner s) throws NoSuchElementException, IllegalStateException, UnexpectedInputException {
        if (!scanWord(s).equals("=>"))
            throw new UnexpectedInputException("Unexpected input. Expected \"=>\" token after first word.");
    }
    public static Association<Character, Character> scanAssoc(Scanner s) throws NoSuchElementException, IllegalStateException, UnexpectedInputException {
        String left = scanWord(s);
        scanArrow(s);
        String right = scanWord(s);
        Association<Character, Character> result = new Association<>();
        int l = left.length();
        if (l != right.length())
            throw new UnexpectedInputException("Unexpected input. Expected two equals size words.");
        for (int i = 0; i < l; i++)
            result.put(left.charAt(i), right.charAt(i));
        return result;
    }
}
