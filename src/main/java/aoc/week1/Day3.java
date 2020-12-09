package aoc.week1;

import aoc.utils.AbstractDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Day 3: Toboggan Trajectory ---
 * With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan might be easy, it's certainly not safe: there's very minimal steering and the area is covered in trees. You'll need to see which angles will take you near the fewest trees.
 *
 * Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:
 *
 * ..##.......
 * #...#...#..
 * .#....#..#.
 * ..#.#...#.#
 * .#...##..#.
 * ..#.##.....
 * .#.#.#....#
 * .#........#
 * #.##...#...
 * #...##....#
 * .#..#...#.#
 * These aren't the only trees, though; due to something you read about once involving arboreal genetics and biome stability, the same pattern repeats to the right many times:
 *
 * ..##.........##.........##.........##.........##.........##.......  --->
 * #...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
 * .#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
 * ..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
 * .#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
 * ..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
 * .#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
 * .#........#.#........#.#........#.#........#.#........#.#........#
 * #.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
 * #...##....##...##....##...##....##...##....##...##....##...##....#
 * .#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
 * You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).
 *
 * The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:
 *
 * From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.
 *
 * The locations you'd check in the above example are marked here with O where there was an open square and X where there was a tree:
 *
 * ..##.........##.........##.........##.........##.........##.......  --->
 * #..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
 * .#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
 * ..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
 * .#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
 * ..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
 * .#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
 * .#........#.#........X.#........#.#........#.#........#.#........#
 * #.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
 * #...##....##...##....##...#X....##...##....##...##....##...##....#
 * .#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
 * In this example, traversing the map using this slope would cause you to encounter 7 trees.
 *
 * Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?
 *
 * Your puzzle answer was 191.
 *
 * --- Part Two ---
 * Time to check the rest of the slopes - you need to minimize the probability of a sudden arboreal stop, after all.
 *
 * Determine the number of trees you would encounter if, for each of the following slopes, you start at the top-left corner and traverse the map all the way to the bottom:
 *
 * Right 1, down 1.
 * Right 3, down 1. (This is the slope you already checked.)
 * Right 5, down 1.
 * Right 7, down 1.
 * Right 1, down 2.
 * In the above example, these slopes would find 2, 7, 3, 4, and 2 tree(s) respectively; multiplied together, these produce the answer 336.
 *
 * What do you get if you multiply together the number of trees encountered on each of the listed slopes?
 *
 * Your puzzle answer was 1478615040.
 */
public class Day3 extends AbstractDay {
    List<List<Boolean>> map;

    protected Day3() {
        super(Day3.class.getCanonicalName().toLowerCase().replaceAll("\\.", "/"));
        map = lines.stream().map(s -> {
            List<Boolean> b = new ArrayList<>(s.length());
            for (int i = 0; i < s.length(); ++i) {
                b.add(s.charAt(i) == '#');
            }
            return b;
        }).collect(Collectors.toList());

    }


    private int solve(int right, int down) {
        int res = 0;
        int i =0, j =0;
        int len = map.get(0).size();
        while (i < map.size()) {
            if (map.get(i).get(j)) {
                res++;
            }
            j = (j + right) % len;
            i = i + down;
        }
        return res;
    }


    @Override
    public String solve1() {
        return "Solution 1: " + solve(3, 1);
    }

    @Override
    public String solve2() {
        int [][] slopes = new int[][]{{1,1},{3,1},{5,1},{7,1},{1,2}};
        long res = 1L;
        for (int[] slope : slopes) {
            int t = solve(slope[0], slope[1]);
            System.out.println(Arrays.toString(slope) + " " + t);
            res *= t;
        }
        return "Solution 2: " + res;
    }

    public static void main(String args[]) {
        new Day3().main();
    }
}
