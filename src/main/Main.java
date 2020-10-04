package main;

import main.model.Lottery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

public class Main {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Lottery lottery;
    private static String input;
    private static final ArrayList<Integer> biggerNums = new ArrayList<>();
    private static final ArrayList<Integer> smallerNums = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        switch (menu()) {
            case 0 -> System.exit(0);
            case 1 -> lottery = new Lottery(90, 5);
            case 2 -> lottery = new Lottery(45, 6);
            case 3 -> lottery = new Lottery(35, 7);
            default -> throw new UnexpectedException("Unexpected error!");
        }
        lottery.select(ask());
        lottery.random();
        System.out.println(lottery);
    }

    private static int menu() throws IOException {
        int mode;
        System.out.println("0) Exit");
        System.out.println("1) Ötös Lottó");
        System.out.println("2) Hatos Lottó");
        System.out.println("3) Skandináv Lottó");
        do {
            input = reader.readLine();
            try{
                mode = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                mode = -1;
            }
        } while(
            switch (mode) {
                case 0, 1, 2, 3 -> false;
                default -> {
                    if (!input.equals("")) {
                        System.err.println("Invalid parameter, try again!");
                    }
                    yield true;
                }
            }
        );
        return mode;
    }

    private static int[] ask() throws IOException {
        boolean err;
        int i;
        int[] ret;
        System.out.println("Type your " + lottery.getSelected() + " numbers here (minimum 1, maximum " + lottery.getNumbers() + " and separated by space):");
        do {
            i = 0;
            err = false;
            input = reader.readLine();
            ret = new int[lottery.getSelected()];

            for (String num : input.split(" ")) {
                try {
                    try {
                        ret[i] = Integer.parseInt(num);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Too many arguments, try again!");
                        err = true;
                        break;
                    }
                } catch (NumberFormatException e) {
                    if (!input.equals("")) {
                        System.err.println("Invalid character, try again!");
                    }
                    err = true;
                    break;
                }
                i++;
            }

            if (!err) {
                if (input.split(" ").length < lottery.getSelected() && !input.equals("")){
                    System.err.println("Too little arguments, try again!");
                    err = true;
                }

                biggerNums.clear();
                smallerNums.clear();
                for (int num : ret) {
                    if (num > lottery.getNumbers()) {
                        biggerNums.add(num);
                    } else if (num < 1){
                        smallerNums.add(num);
                    }
                }
                if (biggerNums.size() > 0) {
                    System.err.println("Selected number" + (biggerNums.size() > 1 ? "s" : "") + " (" + biggerNums.toString().replace("[", "").replace("]", "") + ") " + (biggerNums.size() > 1 ? "are" : "is") + " greater than the maximum number possible (" + lottery.getNumbers() + "), try again!");
                    err = true;
                }
                if (smallerNums.size() > 0) {
                    System.err.println("Selected number" + (smallerNums.size() > 1 ? "s" : "") + " (" + smallerNums.toString().replace("[", "").replace("]", "") + ") " + (smallerNums.size() > 1 ? "are" : "is") + " smaller than the minimum number possible (1), try again!");
                    err = true;
                }

                biggerNums.clear();
                for (int j = 0; j < ret.length; j++) {
                    for (int k = j + 1; k < ret.length; k++) {
                        if (ret[j] == ret[k]) {
                            biggerNums.add(ret[j]);
                            err = true;
                        }
                    }
                }
                if (biggerNums.size() > 0) {
                    System.err.println("Duplicated numbers (" + biggerNums.toString().replace("[", "").replace("]", "") + "), try again!");
                }
            }
        } while (err);
        return ret;
    }

}