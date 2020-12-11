package aoc.week2;

import aoc.utils.AbstractDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 extends AbstractDay {


    private Day10() {
        super(Day10.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
    }

    @Override
    public String solve1() {
        List<Integer> nums = lines.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        int cur = 0;
        int three = 0;
        int one = 0;
        for (int num : nums) {
            if (num - cur == 1) {
                one++;
            } else if (num - cur == 3) {
                three++;
            } else {
                System.out.println("" + (num - cur));
            }
            cur = num;
        }
        return "" + (one*(three+1));
    }

    @Override
    public String solve2() {
        List<Integer> nums = new ArrayList();
        nums.add(0);
        lines.stream().map(Integer::parseInt).sorted().forEach(x -> nums.add(x));
        nums.add(nums.get(nums.size()-1) + 3);
        System.out.println(nums);
        long [] solutions = new long[nums.size()];
        solutions[0] = 1;
        for (int i = 1; i < nums.size(); ++i) {
            solutions[i] = solutions[i-1];
            for (int j = 2; j <= i; ++j) {
                if ((nums.get(i) - nums.get(i - j) <= 3)) {
                    solutions[i] += solutions[i - j];
                } else {
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(solutions));
        return "" + solutions[solutions.length-1];
    }

    public static void main(String args[]) {
        new Day10().main();
    }
}
