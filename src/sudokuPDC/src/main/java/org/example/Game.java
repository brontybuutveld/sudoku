package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Game {
    private final List<Integer> solution;
    private final ColumnNode head;
    private final Node[] matrix;

    public Game(List<Integer> solution, ColumnNode head, Node[] matrix) {
        this.solution = solution;
        this.head = head;
        this.matrix = matrix;
    }

    private char chooseMovement() {
        char movement = 0, save = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose movement:\n1. (u)p (d)own (l)eft (r)ight\n2. WASD\n3. hjkl (vim)");
        while (movement != '1' && movement != '2' && movement != '3') {
            movement = scanner.nextLine().charAt(0);
            if (movement != '1' && movement != '2' && movement != '3')
                System.out.println("Invalid input");
        }
        System.out.println("Save preference? (y/n)");
        while (save != 'y' && save != 'n') {
            save = scanner.nextLine().charAt(0);
            if (save != 'y' && save != 'n')
                System.out.println("Invalid input");
        }
        if (save == 'y') {
            try {
                File myObj = new File("src/main/java/org/example/config.txt");
                myObj.createNewFile();
                Formatter myWriter = new Formatter(myObj);
                myWriter.format("%c", movement);
                myWriter.close();
            } catch (FileNotFoundException ej) {
                System.out.println("An error occurred.");
                ej.printStackTrace();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return movement;
    }

    private void printBoard(int x, int y, int[][] printout, int[][] mask) {
        System.out.println("┌─────────┬─────────┬─────────┐");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == y && j == x) {
                    if (j == 0 || j == 3 || j == 6)
                        System.out.print("│");
                    System.out.print("→");
                } else {
                    if (j == 0 || j == 3 || j == 6)
                        System.out.print("│ ");
                    if (j == 1 || j == 2 || j == 4 || j == 5 || j == 7 || j == 8)
                        System.out.print(" ");
                }
                if (printout[i][j] != 0) {
                    System.out.print(printout[i][j]);
                    if (mask[i][j] == AlgorithmX.en.USER.ordinal())
                        System.out.print("*");
                    else if (mask[i][j] == AlgorithmX.en.CONSTRAINT.ordinal())
                        System.out.print("#");
                    else if (mask[i][j] == AlgorithmX.en.DEFAULT_CONSTRAINT.ordinal())
                        System.out.print("!");
                    else
                        System.out.print(" ");
                } else
                    System.out.print(". ");
            }
            System.out.println("│");
            if (i == 2 || i == 5)
                System.out.println("├─────────┼─────────┼─────────┤");
        }
        System.out.println("└─────────┴─────────┴─────────┘");
        //System.out.println("Solutions found: " + ++count);
    }
    public void game() {
        Scanner scanner = new Scanner(System.in);
        int x = 0, y = 0, size = 0;
        int[][] printout = new int[9][9];
        int[][] mask = new int[9][9];
        char movement;

        for (int node : solution) {
            printout[(node / 4) / 81][(node / 4) / 9 % 9] = ((node / 4) % 9) + 1;
            mask[(node / 4) / 81][(node / 4) / 9 % 9] = AlgorithmX.en.DEFAULT.ordinal();
        }
        try {
            File myObj = new File("src/main/java/org/example/config.txt");
            Scanner myReader = new Scanner(myObj).useDelimiter("");
            movement = myReader.next().charAt(0);
            if (movement != '1' && movement != '2' && movement != '3')
                movement = chooseMovement();
            myReader.close();
        } catch (FileNotFoundException | NoSuchElementException e) {
            movement = chooseMovement();
        }

        while (true) {
            printBoard(x, y, printout, mask);

            if (solution.size() + size == 81) {
                boolean solved = true;
                for (int i = 0; i < 9 && solved; i++) {
                    for (int j = 0; j < 9 && solved; j++) {
                        if (mask[i][j] == AlgorithmX.en.CONSTRAINT.ordinal() || mask[i][j] == AlgorithmX.en.DEFAULT_CONSTRAINT.ordinal())
                            solved = false;
                    }
                }
                if (solved) {
                    System.out.println("Sudoku solved!");
                    break;
                }
            }


            if (movement == '1')
                System.out.print("(u) ↑, (d) ↓, (l) ←, (r) →,");
            else if (movement == '2')
                System.out.print("(w) ↑, (a) ←, (s) ↓, (d) →,");
            else if (movement == '3')
                System.out.print("(h) ←, (j) ↓, (k) ↑, (l) →,");

            System.out.println(" (solve), (#)input, (q)uit");
            System.out.println("(123) *edited, #constraint, !constraint (can't change), (0) to delete");
            System.out.println(" ││└─ input (optional)");
            System.out.println(" │└── row");
            System.out.println(" └─── column");

            String input = scanner.nextLine();
            if (input.equals("q"))
                break;
            if (input.equals("solve")) {
                AlgorithmX ax = new AlgorithmX(head, matrix, new Stack<>(), solution);
                final long startTime = System.currentTimeMillis();
                ax.search(0, false, true, false, false, false);
                final long endTime = System.currentTimeMillis();
                System.out.println("Total execution time: " + (endTime - startTime));
                break;
            }
            if (movement == '1' && input.equals("l") ||
                    movement == '2' && input.equals("a") ||
                    movement == '3' && input.equals("h")) {
                if (x > 0)
                    x--;
                else
                    x = 8;
            } else if (movement == '1' && input.equals("r") ||
                    movement == '2' && input.equals("d") ||
                    movement == '3' && input.equals("l")) {
                if (x < 8)
                    x++;
                else
                    x = 0;
            } else if (movement == '1' && input.equals("u") ||
                    movement == '2' && input.equals("w") ||
                    movement == '3' && input.equals("k")) {
                if (y > 0)
                    y--;
                else
                    y = 8;
            } else if (movement == '1' && input.equals("d") ||
                    movement == '2' && input.equals("s") ||
                    movement == '3' && input.equals("j")) {
                if (y < 8)
                    y++;
                else
                    y = 0;
            } else if (input.length() == 1) {
                try {
                    if (mask[y][x] == AlgorithmX.en.DEFAULT.ordinal() || mask[y][x] == AlgorithmX.en.DEFAULT_CONSTRAINT.ordinal()) {
                        System.out.println("Cheater!");
                    } else {
                        int in = Integer.parseInt(input);
                        size = inputNumber(in, x, y, printout, mask, size);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                }
            } else if (input.length() == 2) {
                try {
                    x = Integer.parseInt(input.substring(0, 1)) - 1;
                    y = Integer.parseInt(input.substring(1, 2)) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                }
            } else if (input.length() == 3) {
                try {
                    x = Integer.parseInt(input.substring(0, 1)) - 1;
                    y = Integer.parseInt(input.substring(1, 2)) - 1;
                    if (mask[y][x] == AlgorithmX.en.DEFAULT.ordinal() || mask[y][x] == AlgorithmX.en.DEFAULT_CONSTRAINT.ordinal()) {
                        System.out.println("Cheater!");
                    } else {
                        int in = Integer.parseInt(input.substring(2, 3));
                        size = inputNumber(in, x, y, printout, mask, size);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                }
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    private int inputNumber(int in, int x, int y, int[][] printout, int[][] mask, int size) {
        int prev = printout[y][x];
        // delete
        for (int i = 0; i < 9; i++) {
            if (prev != 0 && prev == printout[y][i] && i != x)
                helper1(i, y, mask);
            if (prev != 0 && prev == printout[i][x] && i != y)
                helper1(x, i, mask);
            if (prev != 0 && printout[(y / 3) * 3 + i % 3][(x / 3) * 3 + i / 3] == prev)
                helper1((x / 3) * 3 + i / 3, (y / 3) * 3 + i % 3, mask);
        }
        if (in != 0) {
            // add
            for (int i = 0; i < 9; i++) {
                if (printout[i][x] == in) {
                    helper2(x, i, mask);
                    mask[y][x] = AlgorithmX.en.CONSTRAINT.ordinal();
                }
                if (printout[y][i] == in) {
                    helper2(i, y, mask);
                    mask[y][x] = AlgorithmX.en.CONSTRAINT.ordinal();
                }
                if (printout[(y/3) * 3 + i%3][(x/3) * 3 + i/3] == in) {
                    helper2((x / 3) * 3 + i / 3, (y / 3) * 3 + i % 3, mask);
                    mask[y][x] = AlgorithmX.en.CONSTRAINT.ordinal();
                }
            }
        } else {
            mask[y][x] = AlgorithmX.en.UNSET.ordinal();
        }
        if (mask[y][x] != AlgorithmX.en.CONSTRAINT.ordinal())
            mask[y][x] = AlgorithmX.en.USER.ordinal();
        if (in != 0 && prev == 0)
            size++;
        else if (in == 0 && prev != 0)
            size--;
        printout[y][x] = in;
        return size;
    }

    private void helper1(int x, int y, int[][] mask) {
        if (mask[y][x] == AlgorithmX.en.DEFAULT_CONSTRAINT.ordinal() || mask[y][x] == AlgorithmX.en.DEFAULT.ordinal())
            mask[y][x] = AlgorithmX.en.DEFAULT.ordinal();
        else
            mask[y][x] = AlgorithmX.en.USER.ordinal();
    }

    private void helper2(int x, int y, int[][] mask) {
        if (mask[y][x] == AlgorithmX.en.DEFAULT_CONSTRAINT.ordinal() || mask[y][x] == AlgorithmX.en.DEFAULT.ordinal())
            mask[y][x] = AlgorithmX.en.DEFAULT_CONSTRAINT.ordinal();
        else
            mask[y][x] = AlgorithmX.en.CONSTRAINT.ordinal();
    }
}
