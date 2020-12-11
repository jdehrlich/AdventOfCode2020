package aoc;

import aoc.utils.AbstractDay;

import java.util.Arrays;

/**
 * --- Day 11: Seating System ---
 * Your plane lands with plenty of time to spare. The final leg of your journey is a ferry that goes directly to the tropical island where you can finally start your vacation. As you reach the waiting area to board the ferry, you realize you're so early, nobody else has even arrived yet!
 *
 * By modeling the process people use to choose (or abandon) their seat in the waiting area, you're pretty sure you can predict the best place to sit. You make a quick map of the seat layout (your puzzle input).
 *
 * The seat layout fits neatly on a grid. Each position is either floor (.), an empty seat (L), or an occupied seat (#). For example, the initial seat layout might look like this:
 *
 * L.LL.LL.LL
 * LLLLLLL.LL
 * L.L.L..L..
 * LLLL.LL.LL
 * L.LL.LL.LL
 * L.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLLL
 * L.LLLLLL.L
 * L.LLLLL.LL
 * Now, you just need to model the people who will be arriving shortly. Fortunately, people are entirely predictable and always follow a simple set of rules. All decisions are based on the number of occupied seats adjacent to a given seat (one of the eight positions immediately up, down, left, right, or diagonal from the seat). The following rules are applied to every seat simultaneously:
 *
 * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
 * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
 * Otherwise, the seat's state does not change.
 * Floor (.) never changes; seats don't move, and nobody sits on the floor.
 *
 * After one round of these rules, every seat in the example layout becomes occupied:
 *
 * #.##.##.##
 * #######.##
 * #.#.#..#..
 * ####.##.##
 * #.##.##.##
 * #.#####.##
 * ..#.#.....
 * ##########
 * #.######.#
 * #.#####.##
 * After a second round, the seats with four or more occupied adjacent seats become empty again:
 *
 * #.LL.L#.##
 * #LLLLLL.L#
 * L.L.L..L..
 * #LLL.LL.L#
 * #.LL.LL.LL
 * #.LLLL#.##
 * ..L.L.....
 * #LLLLLLLL#
 * #.LLLLLL.L
 * #.#LLLL.##
 * This process continues for three more rounds:
 *
 * #.##.L#.##
 * #L###LL.L#
 * L.#.#..#..
 * #L##.##.L#
 * #.##.LL.LL
 * #.###L#.##
 * ..#.#.....
 * #L######L#
 * #.LL###L.L
 * #.#L###.##
 * #.#L.L#.##
 * #LLL#LL.L#
 * L.L.L..#..
 * #LLL.##.L#
 * #.LL.LL.LL
 * #.LL#L#.##
 * ..L.L.....
 * #L#LLLL#L#
 * #.LLLLLL.L
 * #.#L#L#.##
 * #.#L.L#.##
 * #LLL#LL.L#
 * L.#.L..#..
 * #L##.##.L#
 * #.#L.LL.LL
 * #.#L#L#.##
 * ..L.L.....
 * #L#L##L#L#
 * #.LLLLLL.L
 * #.#L#L#.##
 * At this point, something interesting happens: the chaos stabilizes and further applications of these rules cause no seats to change state! Once people stop moving around, you count 37 occupied seats.
 *
 * Simulate your seating area by applying the seating rules repeatedly until no seats change state. How many seats end up occupied?
 *
 * Your puzzle answer was 2418.
 *
 * --- Part Two ---
 * As soon as people start to arrive, you realize your mistake. People don't just care about adjacent seats - they care about the first seat they can see in each of those eight directions!
 *
 * Now, instead of considering just the eight immediately adjacent seats, consider the first seat in each of those eight directions. For example, the empty seat below would see eight occupied seats:
 *
 * .......#.
 * ...#.....
 * .#.......
 * .........
 * ..#L....#
 * ....#....
 * .........
 * #........
 * ...#.....
 * The leftmost empty seat below would only see one empty seat, but cannot see any of the occupied ones:
 *
 * .............
 * .L.L.#.#.#.#.
 * .............
 * The empty seat below would see no occupied seats:
 *
 * .##.##.
 * #.#.#.#
 * ##...##
 * ...L...
 * ##...##
 * #.#.#.#
 * .##.##.
 * Also, people seem to be more tolerant than you expected: it now takes five or more visible occupied seats for an occupied seat to become empty (rather than four or more from the previous rules). The other rules still apply: empty seats that see no occupied seats become occupied, seats matching no rule don't change, and floor never changes.
 *
 * Given the same starting layout as above, these new rules cause the seating area to shift around as follows:
 *
 * L.LL.LL.LL
 * LLLLLLL.LL
 * L.L.L..L..
 * LLLL.LL.LL
 * L.LL.LL.LL
 * L.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLLL
 * L.LLLLLL.L
 * L.LLLLL.LL
 * #.##.##.##
 * #######.##
 * #.#.#..#..
 * ####.##.##
 * #.##.##.##
 * #.#####.##
 * ..#.#.....
 * ##########
 * #.######.#
 * #.#####.##
 * #.LL.LL.L#
 * #LLLLLL.LL
 * L.L.L..L..
 * LLLL.LL.LL
 * L.LL.LL.LL
 * L.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLL#
 * #.LLLLLL.L
 * #.LLLLL.L#
 * #.L#.##.L#
 * #L#####.LL
 * L.#.#..#..
 * ##L#.##.##
 * #.##.#L.##
 * #.#####.#L
 * ..#.#.....
 * LLL####LL#
 * #.L#####.L
 * #.L####.L#
 * #.L#.L#.L#
 * #LLLLLL.LL
 * L.L.L..#..
 * ##LL.LL.L#
 * L.LL.LL.L#
 * #.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLL#
 * #.LLLLL#.L
 * #.L#LL#.L#
 * #.L#.L#.L#
 * #LLLLLL.LL
 * L.L.L..#..
 * ##L#.#L.L#
 * L.L#.#L.L#
 * #.L####.LL
 * ..#.#.....
 * LLL###LLL#
 * #.LLLLL#.L
 * #.L#LL#.L#
 * #.L#.L#.L#
 * #LLLLLL.LL
 * L.L.L..#..
 * ##L#.#L.L#
 * L.L#.LL.L#
 * #.LLLL#.LL
 * ..#.L.....
 * LLL###LLL#
 * #.LLLLL#.L
 * #.L#LL#.L#
 * Again, at this point, people stop shifting around and the seating area reaches equilibrium. Once this occurs, you count 26 occupied seats.
 *
 * Given the new visibility method and the rule change for occupied seats becoming empty, once equilibrium is reached, how many seats end up occupied?
 *
 * Your puzzle answer was 2144.
 */
public class Day11 extends AbstractDay {


    private Day11() {
        super(Day11.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
    }

    private char[][] readLines(){
        char[][] seats = new char[lines.size()][];
        for (int i = 0; i < lines.size(); ++i) {
            seats[i] = lines.get(i).toCharArray();
        }
        System.out.println(Arrays.deepToString(seats));
        return seats;
    }

    private int count(int i, int j, char [][] seats) {
        int res = 0;
        for (int x = -1; x <=1; ++x) {
            for (int y = -1; y <=1; ++y) {
                if (i+x >= 0 && i+x < seats.length && j+y >=0 && j+y < seats[j].length && (x != 0 || y != 0)) {
                    if (seats[i+x][j+y] == '#') {
                        res++;
                    }
                }
            }
        }
        return res;
    }


    private void set(int i, int j, char [][] prev, char [][] next) {
        if (prev[i][j] == 'L' && count(i, j, prev) == 0) {
            next[i][j] = '#';
        } else  if (prev[i][j] == '#' && count(i,j,prev) >= 4) {
            next[i][j] = 'L';
        } else {
            next[i][j] = prev[i][j];
        }
    }

    @Override
    public String solve1() {
        char[][] seats = readLines();
        char [][] newSeats = new char[seats.length][seats[0].length];
        int count = 0;
        while (!Arrays.deepEquals(seats, newSeats)) {
            for (int i = 0; i < seats.length; ++i) {
                for (int j = 0; j < seats[0].length; ++j) {
                    set(i, j, seats, newSeats);
                }
            }
            char[][] tmp = seats;
            seats = newSeats;
            newSeats = tmp;
            count++;
            System.out.println(count);
        }
        count = 0;
        for (int i = 0; i <newSeats.length; ++i) {
            for (int j = 0; j < newSeats[0].length; ++j) {
                if (newSeats[i][j] == '#') {
                    count++;
                }
            }
        }
        return "count = " + count;
    }

    private void set2(int i, int j, char [][] prev, char [][] next) {
        if (prev[i][j] == 'L' && count2(i, j, prev) == 0) {
            next[i][j] = '#';
        } else  if (prev[i][j] == '#' && count2(i,j,prev) >= 5) {
            next[i][j] = 'L';
        } else {
            next[i][j] = prev[i][j];
        }
    }

    private int count2(int i, int j, char [][] seats) {
        int res = 0;
        for (int x = -1; x <=1; ++x) {
            for (int y = -1; y <=1; ++y) {
                if (x == 0 && y == 0) {
                    continue;
                }
                int z = 1;
                while (i+(x*z) >= 0 && i+(x*z) < seats.length && j+(y*z) >=0 && j+(y*z) < seats[j].length) {
                    if (seats[i+(x*z)][j+(y*z)] == '#') {
                        res++;
                        break;
                    } else if (seats[i+(x*z)][j+(y*z)] == 'L') {
                        break;
                    }
                    z++;
                }
            }
        }
        return res;
    }

    @Override
    public String solve2() {
        char[][] seats = readLines();
        char [][] newSeats = new char[seats.length][seats[0].length];
        int count = 0;
        while (!Arrays.deepEquals(seats, newSeats)) {
            for (int i = 0; i < seats.length; ++i) {
                for (int j = 0; j < seats[0].length; ++j) {
                    set2(i, j, seats, newSeats);
                }
            }
            char[][] tmp = seats;
            seats = newSeats;
            newSeats = tmp;
            count++;
            System.out.println(count);
        }
        count = 0;
        for (int i = 0; i <newSeats.length; ++i) {
            for (int j = 0; j < newSeats[0].length; ++j) {
                if (newSeats[i][j] == '#') {
                    count++;
                }
            }
        }
        return "count = " + count;    }

    public static void main(String args[]) {
        new Day11().main();
    }
}
