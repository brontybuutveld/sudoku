package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Read {
    private final Stack<Integer> input = new Stack<>();

    public Stack<Integer> readtxt() {
        try {
            File myObj = new File("src/main/java/org/example/sudokuPuzzles.txt");
            Scanner myReader = new Scanner(myObj);
            read(myReader);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return input;
    }

    public Stack<Integer> getInput() {
        Scanner sc = new Scanner(System.in);
        read(sc);
        return input;
    }

    private void read(Scanner myReader) {
        int i = 0;
        while (i < 81) {
            String str = myReader.nextLine().replaceAll("[^0-9.]", "");
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) != '.' && str.charAt(j) != '0') {
                    int num = Integer.parseInt(str.substring(j, j + 1));
                    input.push((i / 9) * 81 * 4 //column
                            + (i % 9) * 9 * 4 //row
                            + num * 4 - 1); //number
                }
                i++;
            }
        }
    }
}
