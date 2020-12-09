package aoc;

import aoc.utils.AbstractDay;

public class Day extends AbstractDay {


    private Day() {
        super(Day.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
    }

    @Override
    public String solve1() {
        return "1";
    }

    @Override
    public String solve2() {
        return "2";
    }

    public static void main(String args[]) {
        new Day().main();
    }
}
