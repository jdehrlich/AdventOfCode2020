package aoc;

import aoc.utils.AbstractDay;

import java.util.*;
import java.util.stream.Collectors;

/**
 * --- Day 24: Lobby Layout ---
 * Your raft makes it to the tropical island; it turns out that the small crab was an excellent navigator. You make your way to the resort.
 *
 * As you enter the lobby, you discover a small problem: the floor is being renovated. You can't even reach the check-in desk until they've finished installing the new tile floor.
 *
 * The tiles are all hexagonal; they need to be arranged in a hex grid with a very specific color pattern. Not in the mood to wait, you offer to help figure out the pattern.
 *
 * The tiles are all white on one side and black on the other. They start with the white side facing up. The lobby is large enough to fit whatever pattern might need to appear there.
 *
 * A member of the renovation crew gives you a list of the tiles that need to be flipped over (your puzzle input). Each line in the list identifies a single tile that needs to be flipped by giving a series of steps starting from a reference tile in the very center of the room. (Every line starts from the same reference tile.)
 *
 * Because the tiles are hexagonal, every tile has six neighbors: east, southeast, southwest, west, northwest, and northeast. These directions are given in your list, respectively, as e, se, sw, w, nw, and ne. A tile is identified by a series of these directions with no delimiters; for example, esenee identifies the tile you land on if you start at the reference tile and then move one tile east, one tile southeast, one tile northeast, and one tile east.
 *
 * Each time a tile is identified, it flips from white to black or from black to white. Tiles might be flipped more than once. For example, a line like esew flips a tile immediately adjacent to the reference tile, and a line like nwwswee flips the reference tile itself.
 *
 * Here is a larger example:
 *
 * sesenwnenenewseeswwswswwnenewsewsw
 * neeenesenwnwwswnenewnwwsewnenwseswesw
 * seswneswswsenwwnwse
 * nwnwneseeswswnenewneswwnewseswneseene
 * swweswneswnenwsewnwneneseenw
 * eesenwseswswnenwswnwnwsewwnwsene
 * sewnenenenesenwsewnenwwwse
 * wenwwweseeeweswwwnwwe
 * wsweesenenewnwwnwsenewsenwwsesesenwne
 * neeswseenwwswnwswswnw
 * nenwswwsewswnenenewsenwsenwnesesenew
 * enewnwewneswsewnwswenweswnenwsenwsw
 * sweneswneswneneenwnewenewwneswswnese
 * swwesenesewenwneswnwwneseswwne
 * enesenwswwswneneswsenwnewswseenwsese
 * wnwnesenesenenwwnenwsewesewsesesew
 * nenewswnwewswnenesenwnesewesw
 * eneswnwswnwsenenwnwnwwseeswneewsenese
 * neswnwewnwnwseenwseesewsenwsweewe
 * wseweeenwnesenwwwswnew
 * In the above example, 10 tiles are flipped once (to black), and 5 more are flipped twice (to black, then back to white). After all of these instructions have been followed, a total of 10 tiles are black.
 *
 * Go through the renovation crew's list and determine which tiles they need to flip. After all of the instructions have been followed, how many tiles are left with the black side up?
 *
 * Your puzzle answer was 375.
 *
 * --- Part Two ---
 * The tile floor in the lobby is meant to be a living art exhibit. Every day, the tiles are all flipped according to the following rules:
 *
 * Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
 * Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
 * Here, tiles immediately adjacent means the six tiles directly touching the tile in question.
 *
 * The rules are applied simultaneously to every tile; put another way, it is first determined which tiles need to be flipped, then they are all flipped at the same time.
 *
 * In the above example, the number of black tiles that are facing up after the given number of days has passed is as follows:
 *
 * Day 1: 15
 * Day 2: 12
 * Day 3: 25
 * Day 4: 14
 * Day 5: 23
 * Day 6: 28
 * Day 7: 41
 * Day 8: 37
 * Day 9: 49
 * Day 10: 37
 *
 * Day 20: 132
 * Day 30: 259
 * Day 40: 406
 * Day 50: 566
 * Day 60: 788
 * Day 70: 1106
 * Day 80: 1373
 * Day 90: 1844
 * Day 100: 2208
 * After executing this process a total of 100 times, there would be 2208 black tiles facing up.
 *
 * How many tiles will be black after 100 days?
 *
 * Your puzzle answer was 3937.
 */
public class Day24 extends AbstractDay {
    static class Coordinate {
        int i, j;

        public Coordinate(int i, int j) {
            this.i = i;
            this.j = j;
        }

        Coordinate move(Dir d) {
            Coordinate res = new Coordinate(i, j);
            res.update(d);
            return res;
        }

        void update(Dir d) {
            switch (d) {
                case nw: i++; break;
                case se: i--; break;
                case ne: j++; break;
                case sw: j--; break;
                case e: j++; i--; break;
                case w: j--; i++; break;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return i == that.i &&
                    j == that.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("coordinate{");
            sb.append("i=").append(i);
            sb.append(", j=").append(j);
            sb.append('}');
            return sb.toString();
        }
    }

    enum Dir{
        e, se, sw, w, nw, ne;

        private static Dir parseFirst(String s) {
            if (s.charAt(0) == 's' || s.charAt(0) == 'n') {
                return Dir.valueOf(s.substring(0,2));
            } else {
                return Dir.valueOf(s.substring(0,1));
            }
        }
    }
    List<Coordinate> tiles;
    Set<Coordinate> unique;

    private Day24() {
        super(Day24.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
        tiles = lines.stream().filter(s -> s != null && !s.isEmpty())
                .map(this::parseLine)
                .map(dirs -> {
                    Coordinate res = new Coordinate(0,0);
                    dirs.forEach(res::update);
                    return res;
                }).collect(Collectors.toList());
        unique = new HashSet<>();
        for (Coordinate tile : tiles) {
            if (unique.contains(tile)) {
                unique.remove(tile);
            } else {
                unique.add(tile);
            }
        }
    }

    private List<Dir> parseLine(String s) {
        List<Dir> res = new LinkedList<>();
        while (!s.isEmpty()) {
            Dir d = Dir.parseFirst(s);
            s = s.substring(d.toString().length());
            res.add(d);
        }
        return res;
    }

    @Override
    public String solve1() {
        return "1: " + unique.size();
    }

    @Override
    public String solve2() {
        Set<Coordinate> current = new HashSet<>(unique);
        for (int i = 0; i < 100; ++i) {
            Set<Coordinate> next = new HashSet<>();
            for (Coordinate tile : current) {
                int b = 0;
                for (Dir d : Dir.values()) {
                    Coordinate nTile = tile.move(d);
                    if (current.contains(nTile)) {
                        b++;
                    } else {
                        if (flipWhite(nTile, current)) {
                            next.add(nTile);
                        }
                    }
                }
                if (b == 0 || b > 2) {
                    // flip
                } else {
                    next.add(tile);
                }
            }
            current = next;
        }
        return "2: " + current.size();
    }

    private boolean flipWhite(Coordinate tile, Set<Coordinate> current) {
        int b = 0;
        for (Dir d : Dir.values()) {
            Coordinate nTile = tile.move(d);
            if (current.contains(nTile)) {
                b++;
            }
        }
        return b == 2;
    }

    public static void main(String args[]) {
        new Day24().main();
    }
}
