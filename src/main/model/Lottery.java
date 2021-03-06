package main.model;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Lottery {
    private int numbers;
    private int selected;
    private final ArrayList<Integer> selNums = new ArrayList<>();
    private final ArrayList<Integer> winNums = new ArrayList<>();
    private final Random rand = new Random();

    public Lottery(int numbers, int selected) {
        setNumbers(numbers);
        setSelected(selected);
    }

    public Lottery() {}

    public void select(int @NotNull ... numbers) {
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
        boolean[] ret = new boolean[winNums.size()];
        for (int i = 0; i < winNums.size(); ++i) {
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
        for (Integer winNum : winNums) {
            for (int num : selNums) {
                if (winNum == num) {
                    ret.add(num);
                    break;
                }
            }
        }
        return ret;
    }

    private static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Value must be positive");
        }

        BigInteger ret = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            ret = ret.multiply(BigInteger.valueOf(i));
        }

        return ret;
    }

    @Override
    public String toString() {
        return "Your number" + (selNums.size() > 1 ? "s" : "") + ": " + selNums.toString().replace("[", "").replace("]", "") +
             "\nWinner number" + (winNums.size() > 1 ? "s" : "") + ": " + winNums.toString().replace("[", "").replace("]", "") +
             "\nThe number of numbers you got right: " + num() + " out of " + selected + (num() > 0 ? " (the number" + (num() > 1 ? "s" : "") + ": " + right().toString().replace("[", "").replace("]", "") + ")" : "") +
             "\nThe number of combinations possible: " + (factorial(numbers).divide((factorial(selected).multiply(factorial(numbers - selected)))));
    }


    public void setNumbers(int numbers) throws IllegalArgumentException{
        try {
            if (numbers < selected && selected != 0) {
                throw new IllegalArgumentException("Number cannot be smaller than the number of numbers can be selected (" + selected + ")!");
            } else if (numbers < 1) {
                throw new IllegalArgumentException("Number cannot be smaller than 1!");
            } else {
                this.numbers = numbers;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid parameter!");
        }
    }

    public void setSelected(int selected) throws IllegalArgumentException{
        try {
            if (numbers < selected && numbers != 0) {
                throw new IllegalArgumentException("Number cannot be bigger than the maximum number can be selected (" + numbers + ")!");
            } else if (selected < 1) {
                throw new IllegalArgumentException("Number cannot be smaller than 1!");
            } else {
                this.selected = selected;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid parameter!");
        }
    }

    public int getNumbers() {
        return numbers;
    }

    public int getSelected() {
        return selected;
    }
}
