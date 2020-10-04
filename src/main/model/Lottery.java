package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Lottery {
    private int numbers;
    private int selected;
    private final ArrayList<Integer> selNums = new ArrayList<>();
    private final ArrayList<Integer> winNums = new ArrayList<>();
    Random rand = new Random();

    public Lottery(int numbers, int selected) {
        this.numbers = numbers;
        this.selected = selected;
    }

    public void select(int... numbers) {
        selNums.clear();
        for (int num : numbers) {
            selNums.add(num);

        }
    }

    public void random() {
        int randNum;
        winNums.clear();
        for (int i = 0; i < selected; i++) {
            do {
                randNum = rand.nextInt(numbers)+1;
                if (!(winNums.contains(randNum))) {
                    winNums.add(randNum);
                    break;
                }
            } while (winNums.contains(randNum));
        }
    }

    public boolean[] won() {
        boolean[] ret = new boolean[winNums.toArray().length];
        for (int i = 0; i < winNums.toArray().length; ++i) {
            ret[i] = false;
            for (int num : selNums) {
                if (winNums.get(i) == num) {
                    ret[i] = true;
                    break;
                }
            }
        }
        return ret;
    }

    public int num() {
        int ret = 0;
        for (boolean bool : won()) {
            if (bool) {
                ++ret;
            }
        }
        return ret;
    }

    public ArrayList<Integer> right() {
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 0; i < winNums.toArray().length; ++i) {
            for (int num : selNums) {
                if (winNums.get(i) == num) {
                    ret.add(num);
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Your numbers: " + Arrays.toString(selNums.toArray()).replace("[", "").replace("]", "") +
                "\nWinner numbers: " + Arrays.toString(winNums.toArray()).replace("[", "").replace("]", "") +
                "\nThe number of numbers you got right: " + num() + (num() > 0 ? " (the number" + (num() > 1 ? "s" : "") + ": " + right().toString().replace("[", "").replace("]", "") + ")" : "");
    }


    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getNumbers() {
        return numbers;
    }

    public int getSelected() {
        return selected;
    }
}
