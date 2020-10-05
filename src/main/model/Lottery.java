package main.model;

import java.math.BigInteger;
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
        setNumbers(numbers);
        setSelected(selected);
    }

    public Lottery() {}

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

    private static BigInteger factorial(int value) {
        if(value < 0){
            throw new IllegalArgumentException("Value must be positive");
        }

        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= value; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }

    @Override
    public String toString() {
        return "Your number" + (selNums.size() > 1 ? "s" : "") + ": " + Arrays.toString(selNums.toArray()).replace("[", "").replace("]", "") +
             "\nWinner number" + (winNums.size() > 1 ? "s" : "") + ": " + Arrays.toString(winNums.toArray()).replace("[", "").replace("]", "") +
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
