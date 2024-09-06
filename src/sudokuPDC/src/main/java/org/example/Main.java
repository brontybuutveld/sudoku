package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println(
                        "     _______. __    __   _______   ______    __  ___  __    __\n" +
                        "    /       ||  |  |  | |   __  \\ /  __  \\  |  |/  / |  |  |  |\n" +
                        "   |   (----`|  |  |  | |  |  |  |  |  |  | |  '  /  |  |  |  |\n" +
                        "    \\   \\    |  |  |  | |  |  |  |  |  |  | |    <   |  |  |  |\n" +
                        ".----)   |   |  `--'  | |  '--'  |  `--'  | |  .  \\  |  `--'  |\n" +
                        "|_______/     \\______/  |_______/ \\______/  |__|\\__\\  \\______/\n\n");
        ColumnNode head = new ColumnNode();
        MakeData md = new MakeData(head);
        Node[] matrix;
        ColumnNode[] columns = md.makeColumns(4 * 9 * 9);
        matrix = md.makeMatrix(columns);
        Read read = new Read();

        final long startTime, endTime;

        System.out.println("1. Solve\n2. Play");
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("$ ");
            choice = sc.nextInt();
        }
        Stack<Integer> input = new Stack<>();
        boolean game;
        if (choice == 1) {
            game = false;
        } else {
            game = true;
        }

        System.out.println("1. Generate\n2. Read file\n3. Paste");
        choice = 0;
        while (choice != 1 && choice != 2 && choice != 3) {
            System.out.print("$ ");
            choice = sc.nextInt();
        }

        boolean print;
        if (choice == 1) {
            input.push(-1);
            print = false;
        } else if (choice == 2) {
            input = read.readtxt();
            System.out.println();
            print = true;
        } else {
            input = read.getInput();
            System.out.println();
            print = true;
        }

        AlgorithmX ax = new AlgorithmX(columns[0], matrix, input, new Stack<>());
        startTime = System.currentTimeMillis();
        ax.search(0, game, print, false, false, false);
        endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.print("Time: ");
        if (time > 60000)
            System.out.println(time / 60000 + " minutes " + (time % 60000) / 1000 + " seconds");
        else if (time > 1000)
            System.out.println(time / 1000 + " seconds " + (time % 1000) + " milliseconds");
        else
            System.out.println(time + " milliseconds");



        /*Read read = new Read();
        ArrayList<Integer> input = read.readtxt();
        AlgorithmX ax = new AlgorithmX(columns, matrix, input);


        final long startTime = System.currentTimeMillis();
        ax.search(0, false);
        final long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime));*/

    }
}