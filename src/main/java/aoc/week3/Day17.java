package aoc.week3;

import aoc.utils.AbstractDay;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * --- Day 17: Conway Cubes ---
 * As your flight slowly drifts through the sky, the Elves at the Mythical Information Bureau at the North Pole contact you. They'd like some help debugging a malfunctioning experimental energy source aboard one of their super-secret imaging satellites.
 *
 * The experimental energy source is based on cutting-edge technology: a set of Conway Cubes contained in a pocket dimension! When you hear it's having problems, you can't help but agree to take a look.
 *
 * The pocket dimension contains an infinite 3-dimensional grid. At every integer 3-dimensional coordinate (x,y,z), there exists a single cube which is either active or inactive.
 *
 * In the initial state of the pocket dimension, almost all cubes start inactive. The only exception to this is a small flat region of cubes (your puzzle input); the cubes in this region start in the specified active (#) or inactive (.) state.
 *
 * The energy source then proceeds to boot up by executing six cycles.
 *
 * Each cube only ever considers its neighbors: any of the 26 other cubes where any of their coordinates differ by at most 1. For example, given the cube at x=1,y=2,z=3, its neighbors include the cube at x=2,y=2,z=2, the cube at x=0,y=2,z=3, and so on.
 *
 * During a cycle, all cubes simultaneously change their state according to the following rules:
 *
 * If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
 * If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
 * The engineers responsible for this experimental energy source would like you to simulate the pocket dimension and determine what the configuration of cubes should be at the end of the six-cycle boot process.
 *
 * For example, consider the following initial state:
 *
 * .#.
 * ..#
 * ###
 * Even though the pocket dimension is 3-dimensional, this initial state represents a small 2-dimensional slice of it. (In particular, this initial state defines a 3x3x1 region of the 3-dimensional space.)
 *
 * Simulating a few cycles from this initial state produces the following configurations, where the result of each cycle is shown layer-by-layer at each given z coordinate (and the frame of view follows the active cells in each cycle):
 *
 * Before any cycles:
 *
 * z=0
 * .#.
 * ..#
 * ###
 *
 *
 * After 1 cycle:
 *
 * z=-1
 * #..
 * ..#
 * .#.
 *
 * z=0
 * #.#
 * .##
 * .#.
 *
 * z=1
 * #..
 * ..#
 * .#.
 *
 *
 * After 2 cycles:
 *
 * z=-2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-1
 * ..#..
 * .#..#
 * ....#
 * .#...
 * .....
 *
 * z=0
 * ##...
 * ##...
 * #....
 * ....#
 * .###.
 *
 * z=1
 * ..#..
 * .#..#
 * ....#
 * .#...
 * .....
 *
 * z=2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 *
 * After 3 cycles:
 *
 * z=-2
 * .......
 * .......
 * ..##...
 * ..###..
 * .......
 * .......
 * .......
 *
 * z=-1
 * ..#....
 * ...#...
 * #......
 * .....##
 * .#...#.
 * ..#.#..
 * ...#...
 *
 * z=0
 * ...#...
 * .......
 * #......
 * .......
 * .....##
 * .##.#..
 * ...#...
 *
 * z=1
 * ..#....
 * ...#...
 * #......
 * .....##
 * .#...#.
 * ..#.#..
 * ...#...
 *
 * z=2
 * .......
 * .......
 * ..##...
 * ..###..
 * .......
 * .......
 * .......
 * After the full six-cycle boot process completes, 112 cubes are left in the active state.
 *
 * Starting with your given initial configuration, simulate six cycles. How many cubes are left in the active state after the sixth cycle?
 *
 * Your puzzle answer was 448.
 *
 * --- Part Two ---
 * For some reason, your simulated results don't match what the experimental energy source engineers expected. Apparently, the pocket dimension actually has four spatial dimensions, not three.
 *
 * The pocket dimension contains an infinite 4-dimensional grid. At every integer 4-dimensional coordinate (x,y,z,w), there exists a single cube (really, a hypercube) which is still either active or inactive.
 *
 * Each cube only ever considers its neighbors: any of the 80 other cubes where any of their coordinates differ by at most 1. For example, given the cube at x=1,y=2,z=3,w=4, its neighbors include the cube at x=2,y=2,z=3,w=3, the cube at x=0,y=2,z=3,w=4, and so on.
 *
 * The initial state of the pocket dimension still consists of a small flat region of cubes. Furthermore, the same rules for cycle updating still apply: during each cycle, consider the number of active neighbors of each cube.
 *
 * For example, consider the same initial state as in the example above. Even though the pocket dimension is 4-dimensional, this initial state represents a small 2-dimensional slice of it. (In particular, this initial state defines a 3x3x1x1 region of the 4-dimensional space.)
 *
 * Simulating a few cycles from this initial state produces the following configurations, where the result of each cycle is shown layer-by-layer at each given z and w coordinate:
 *
 * Before any cycles:
 *
 * z=0, w=0
 * .#.
 * ..#
 * ###
 *
 *
 * After 1 cycle:
 *
 * z=-1, w=-1
 * #..
 * ..#
 * .#.
 *
 * z=0, w=-1
 * #..
 * ..#
 * .#.
 *
 * z=1, w=-1
 * #..
 * ..#
 * .#.
 *
 * z=-1, w=0
 * #..
 * ..#
 * .#.
 *
 * z=0, w=0
 * #.#
 * .##
 * .#.
 *
 * z=1, w=0
 * #..
 * ..#
 * .#.
 *
 * z=-1, w=1
 * #..
 * ..#
 * .#.
 *
 * z=0, w=1
 * #..
 * ..#
 * .#.
 *
 * z=1, w=1
 * #..
 * ..#
 * .#.
 *
 *
 * After 2 cycles:
 *
 * z=-2, w=-2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-1, w=-2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=-2
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=1, w=-2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=-2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-2, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-1, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=1, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-2, w=0
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=-1, w=0
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=0
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=1, w=0
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=0
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=-2, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-1, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=1, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-2, w=2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-1, w=2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=2
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=1, w=2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 * After the full six-cycle boot process completes, 848 cubes are left in the active state.
 *
 * Starting with your given initial configuration, simulate six cycles in a 4-dimensional space. How many cubes are left in the active state after the sixth cycle?
 *
 * Your puzzle answer was 2400.
 */
public class Day17 extends AbstractDay {

    private class Point {
        int x, y, z, w;

        public Point(int x, int y, int z, int w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y &&
                    z == point.z &&
                    w == point.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, w);
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Point{");
            sb.append("x=").append(x);
            sb.append(", y=").append(y);
            sb.append(", z=").append(z);
            sb.append(", w=").append(w);
            sb.append('}');
            return sb.toString();
        }
    }

    private Day17() {
        super(Day17.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
    }

    @Override
    public String solve1() {
        return getActiveCount(3);
    }

    private String getActiveCount(int dimensions) {
        Set<Point> cur = getPoints();
        Set<Point> next = new HashSet<>();
        for (int n = 0; n < 6; n++) {
            for (int i = getmin(cur, p -> p.x); i <= getmax(cur, p -> p.x); i++) {
                for (int j = getmin(cur, p -> p.y); j <=  getmax(cur, p -> p.y); j++) {
                    for (int k = getmin(cur, p -> p.z); k <= getmax(cur, p -> p.z); k++) {
                        for (int l = getmin(cur, p -> p.w); l <= getmax(cur, p -> p.w); l++) {
                            Point p = new Point(i, j, k, dimensions == 4 ? l : 0);
                            if (activate(cur, p, dimensions)) {
                                next.add(p);
                            }
                        }
                    }
                }
            }
            cur = next;
            next = new HashSet<>();
        }
        return "" + cur.size();
    }

    private int getmin(Set<Point> cur, Function<Point, Integer> fn) {
        return cur.stream().mapToInt(fn::apply).min().getAsInt() - 1;
    }


    private int getmax(Set<Point> cur, Function<Point, Integer> fn) {
        return cur.stream().mapToInt(fn::apply).max().getAsInt() + 1;
    }

    private Set<Point> getPoints() {
        AtomicInteger x = new AtomicInteger(0);
        Set<Point> cur = new HashSet<>();
        lines.forEach(s -> {
            AtomicInteger y = new AtomicInteger(0);
            s.chars().forEach(ch -> {
                if (ch == '#') {
                    cur.add(new Point(x.get(), y.get(), 0, 0));
                }
                y.incrementAndGet();
            });
            x.incrementAndGet();
        });
        return cur;
    }

    private boolean activate(Set<Point> points, Point p, int dimensions) {
        int count = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    for (int w = -1; w <= 1; w++) {
                        if (dimensions != 4 && w != 0) continue;
                        Point p2 = new Point(p.x + x, p.y + y, p.z + z,  dimensions == 4 ? p.w + w : 0);
                        if (!p.equals(p2) && points.contains(p2)) count++;
                    }
                }
            }
        }
        return (points.contains(p) && count == 2) || count == 3;

    }

    @Override
    public String solve2() {
        return getActiveCount(4);
    }

    public static void main(String args[]) {
        new Day17().main();
    }
}
