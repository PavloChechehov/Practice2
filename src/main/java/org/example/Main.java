package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {

        String word = "HELLOWORLD";
        String encrypt = encrypt(word);
        System.out.println("encrypt = " + encrypt);
        String decrypted = decrypt(encrypt);
        System.out.println("decrypted = " + decrypted);
        System.out.println("Is equals these words? = " + word.equals(decrypted));
    }

    private static String decrypt(String word) {
        // 21 32 31 31 35 41
        // 2 1 3
        // 3 1 3

        char[][] alphabetMatrix = buildAlphabetMatrix();

        List<Integer> coordinates = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);

            int column = ALPHABET.indexOf(letter) % 5;
            int row = ALPHABET.indexOf(letter) / 5;

            coordinates.add(row);
            coordinates.add(column);

        }

        //decrypted coordinates the same as a after encrypt

        StringBuilder decryptedWord = new StringBuilder();
        int half = coordinates.size() / 2;
        for (int i = 0; i < half; i++) {

            Integer row = coordinates.get(i);
            Integer column = coordinates.get(half + i);

            char letter = alphabetMatrix[row][column];
            decryptedWord.append(letter);
        }

        return decryptedWord.toString();
    }

    private static String encrypt(String word) {
        // 1. create alphabet - matrix 2x2
        // 2. create for every letter in the word -> coordinate <2.3> or <1.2> based on the alphabet matrix
        // 3. combine all horizontal and vertical numbers
        // 4. convert to new encrypted word based on the new coordinates and matrix alphabet
        char[][] alphabetMatrix = buildAlphabetMatrix();

        List<Pair> coordinates = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);

            int column = ALPHABET.indexOf(letter) % 5;
            int row = ALPHABET.indexOf(letter) / 5;

            coordinates.add(new Pair(row, column));

        }

        List<Integer> rowCoordinates = new ArrayList<>();
        List<Integer> columnCoordinates = new ArrayList<>();
        for (Pair coordinate : coordinates) {

            rowCoordinates.add(coordinate.row());
            columnCoordinates.add(coordinate.column());

        }

        List<Integer> allCoordinates = new ArrayList<>();
        allCoordinates.addAll(rowCoordinates);
        allCoordinates.addAll(columnCoordinates);

        StringBuilder encryptedWord = new StringBuilder();
        for (int i = 0; i < allCoordinates.size(); i = i + 2) {

            int row = allCoordinates.get(i);
            int column = allCoordinates.get(i + 1);
            encryptedWord.append(alphabetMatrix[row][column]);
        }

        return encryptedWord.toString();
    }

    private static char[][] buildAlphabetMatrix() {
        char[] alphabetArr = ALPHABET.toCharArray();
        int columnLength = 5;
        int rowLength = alphabetArr.length / columnLength;

        if (alphabetArr.length % columnLength != 0) {
            rowLength++;
        }

        char[][] arr = new char[rowLength][columnLength];

        for (int i = 0; i < rowLength; i++) {

            for (int j = 0; j < columnLength; j++) {
                int position = columnLength * i + j;
                if (alphabetArr.length > position) {
                    arr[i][j] = alphabetArr[position];
                } else {
                    break;
                }
            }

        }

        return arr;
    }

    record Pair(int row, int column) {
    }
}
