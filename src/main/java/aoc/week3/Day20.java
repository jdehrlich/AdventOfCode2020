package aoc.week3;

import aoc.utils.AbstractDay;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * --- Day 20: Jurassic Jigsaw ---
 * The high-speed train leaves the forest and quickly carries you south. You can even see a desert in the distance! Since you have some spare time, you might as well see if there was anything interesting in the image the Mythical Information Bureau satellite captured.
 *
 * After decoding the satellite messages, you discover that the data actually contains many small images created by the satellite's camera array. The camera array consists of many cameras; rather than produce a single square image, they produce many smaller square image tiles that need to be reassembled back into a single image.
 *
 * Each camera in the camera array returns a single monochrome image tile with a random unique ID number. The tiles (your puzzle input) arrived in a random order.
 *
 * Worse yet, the camera array appears to be malfunctioning: each image tile has been rotated and flipped to a random orientation. Your first task is to reassemble the original image by orienting the tiles so they fit together.
 *
 * To show how the tiles should be reassembled, each tile's image data includes a border that should line up exactly with its adjacent tiles. All tiles have this border, and the border lines up exactly when the tiles are both oriented correctly. Tiles at the edge of the image also have this border, but the outermost edges won't line up with any other tiles.
 *
 * For example, suppose you have the following nine tiles:
 *
 * Tile 2311:
 * ..##.#..#.
 * ##..#.....
 * #...##..#.
 * ####.#...#
 * ##.##.###.
 * ##...#.###
 * .#.#.#..##
 * ..#....#..
 * ###...#.#.
 * ..###..###
 *
 * Tile 1951:
 * #.##...##.
 * #.####...#
 * .....#..##
 * #...######
 * .##.#....#
 * .###.#####
 * ###.##.##.
 * .###....#.
 * ..#.#..#.#
 * #...##.#..
 *
 * Tile 1171:
 * ####...##.
 * #..##.#..#
 * ##.#..#.#.
 * .###.####.
 * ..###.####
 * .##....##.
 * .#...####.
 * #.##.####.
 * ####..#...
 * .....##...
 *
 * Tile 1427:
 * ###.##.#..
 * .#..#.##..
 * .#.##.#..#
 * #.#.#.##.#
 * ....#...##
 * ...##..##.
 * ...#.#####
 * .#.####.#.
 * ..#..###.#
 * ..##.#..#.
 *
 * Tile 1489:
 * ##.#.#....
 * ..##...#..
 * .##..##...
 * ..#...#...
 * #####...#.
 * #..#.#.#.#
 * ...#.#.#..
 * ##.#...##.
 * ..##.##.##
 * ###.##.#..
 *
 * Tile 2473:
 * #....####.
 * #..#.##...
 * #.##..#...
 * ######.#.#
 * .#...#.#.#
 * .#########
 * .###.#..#.
 * ########.#
 * ##...##.#.
 * ..###.#.#.
 *
 * Tile 2971:
 * ..#.#....#
 * #...###...
 * #.#.###...
 * ##.##..#..
 * .#####..##
 * .#..####.#
 * #..#.#..#.
 * ..####.###
 * ..#.#.###.
 * ...#.#.#.#
 *
 * Tile 2729:
 * ...#.#.#.#
 * ####.#....
 * ..#.#.....
 * ....#..#.#
 * .##..##.#.
 * .#.####...
 * ####.#.#..
 * ##.####...
 * ##..#.##..
 * #.##...##.
 *
 * Tile 3079:
 * #.#.#####.
 * .#..######
 * ..#.......
 * ######....
 * ####.#..#.
 * .#...#.##.
 * #.#####.##
 * ..#.###...
 * ..#.......
 * ..#.###...
 * By rotating, flipping, and rearranging them, you can find a square arrangement that causes all adjacent borders to line up:
 *
 * #...##.#.. ..###..### #.#.#####.
 * ..#.#..#.# ###...#.#. .#..######
 * .###....#. ..#....#.. ..#.......
 * ###.##.##. .#.#.#..## ######....
 * .###.##### ##...#.### ####.#..#.
 * .##.#....# ##.##.###. .#...#.##.
 * #...###### ####.#...# #.#####.##
 * .....#..## #...##..#. ..#.###...
 * #.####...# ##..#..... ..#.......
 * #.##...##. ..##.#..#. ..#.###...
 *
 * #.##...##. ..##.#..#. ..#.###...
 * ##..#.##.. ..#..###.# ##.##....#
 * ##.####... .#.####.#. ..#.###..#
 * ####.#.#.. ...#.##### ###.#..###
 * .#.####... ...##..##. .######.##
 * .##..##.#. ....#...## #.#.#.#...
 * ....#..#.# #.#.#.##.# #.###.###.
 * ..#.#..... .#.##.#..# #.###.##..
 * ####.#.... .#..#.##.. .######...
 * ...#.#.#.# ###.##.#.. .##...####
 *
 * ...#.#.#.# ###.##.#.. .##...####
 * ..#.#.###. ..##.##.## #..#.##..#
 * ..####.### ##.#...##. .#.#..#.##
 * #..#.#..#. ...#.#.#.. .####.###.
 * .#..####.# #..#.#.#.# ####.###..
 * .#####..## #####...#. .##....##.
 * ##.##..#.. ..#...#... .####...#.
 * #.#.###... .##..##... .####.##.#
 * #...###... ..##...#.. ...#..####
 * ..#.#....# ##.#.#.... ...##.....
 * For reference, the IDs of the above tiles are:
 *
 * 1951    2311    3079
 * 2729    1427    2473
 * 2971    1489    1171
 * To check that you've assembled the image correctly, multiply the IDs of the four corner tiles together. If you do this with the assembled tiles from the example above, you get 1951 * 3079 * 2971 * 1171 = 20899048083289.
 *
 * Assemble the tiles into an image. What do you get if you multiply together the IDs of the four corner tiles?
 *
 * Your puzzle answer was 18262194216271.
 *
 * --- Part Two ---
 * Now, you're ready to check the image for sea monsters.
 *
 * The borders of each tile are not part of the actual image; start by removing them.
 *
 * In the example above, the tiles become:
 *
 * .#.#..#. ##...#.# #..#####
 * ###....# .#....#. .#......
 * ##.##.## #.#.#..# #####...
 * ###.#### #...#.## ###.#..#
 * ##.#.... #.##.### #...#.##
 * ...##### ###.#... .#####.#
 * ....#..# ...##..# .#.###..
 * .####... #..#.... .#......
 *
 * #..#.##. .#..###. #.##....
 * #.####.. #.####.# .#.###..
 * ###.#.#. ..#.#### ##.#..##
 * #.####.. ..##..## ######.#
 * ##..##.# ...#...# .#.#.#..
 * ...#..#. .#.#.##. .###.###
 * .#.#.... #.##.#.. .###.##.
 * ###.#... #..#.##. ######..
 *
 * .#.#.### .##.##.# ..#.##..
 * .####.## #.#...## #.#..#.#
 * ..#.#..# ..#.#.#. ####.###
 * #..####. ..#.#.#. ###.###.
 * #####..# ####...# ##....##
 * #.##..#. .#...#.. ####...#
 * .#.###.. ##..##.. ####.##.
 * ...###.. .##...#. ..#..###
 * Remove the gaps to form the actual image:
 *
 * .#.#..#.##...#.##..#####
 * ###....#.#....#..#......
 * ##.##.###.#.#..######...
 * ###.#####...#.#####.#..#
 * ##.#....#.##.####...#.##
 * ...########.#....#####.#
 * ....#..#...##..#.#.###..
 * .####...#..#.....#......
 * #..#.##..#..###.#.##....
 * #.####..#.####.#.#.###..
 * ###.#.#...#.######.#..##
 * #.####....##..########.#
 * ##..##.#...#...#.#.#.#..
 * ...#..#..#.#.##..###.###
 * .#.#....#.##.#...###.##.
 * ###.#...#..#.##.######..
 * .#.#.###.##.##.#..#.##..
 * .####.###.#...###.#..#.#
 * ..#.#..#..#.#.#.####.###
 * #..####...#.#.#.###.###.
 * #####..#####...###....##
 * #.##..#..#...#..####...#
 * .#.###..##..##..####.##.
 * ...###...##...#...#..###
 * Now, you're ready to search for sea monsters! Because your image is monochrome, a sea monster will look like this:
 *
 *                   #
 * #    ##    ##    ###
 *  #  #  #  #  #  #
 * When looking for this pattern in the image, the spaces can be anything; only the # need to match. Also, you might need to rotate or flip your image before it's oriented correctly to find sea monsters. In the above image, after flipping and rotating it to the appropriate orientation, there are two sea monsters (marked with O):
 *
 * .####...#####..#...###..
 * #####..#..#.#.####..#.#.
 * .#.#...#.###...#.##.O#..
 * #.O.##.OO#.#.OO.##.OOO##
 * ..#O.#O#.O##O..O.#O##.##
 * ...#.#..##.##...#..#..##
 * #.##.#..#.#..#..##.#.#..
 * .###.##.....#...###.#...
 * #.####.#.#....##.#..#.#.
 * ##...#..#....#..#...####
 * ..#.##...###..#.#####..#
 * ....#.##.#.#####....#...
 * ..##.##.###.....#.##..#.
 * #...#...###..####....##.
 * .#.##...#.##.#.#.###...#
 * #.###.#..####...##..#...
 * #.###...#.##...#.##O###.
 * .O##.#OO.###OO##..OOO##.
 * ..O#.O..O..O.#O##O##.###
 * #.#..##.########..#..##.
 * #.#####..#.#...##..#....
 * #....##..#.#########..##
 * #...#.....#..##...###.##
 * #..###....##.#...##.##.#
 * Determine how rough the waters are in the sea monsters' habitat by counting the number of # that are not part of a sea monster. In the above example, the habitat's water roughness is 273.
 *
 * How many # are not part of a sea monster?
 *
 * Your puzzle answer was 2023.
 */
public class Day20 extends AbstractDay {

    private static final int DRAGON_HEIGHT = 3-1; //0 indexing
    private static final int DRAGON_WIDTH = 20-1; //0 indexing
    private final Map<Integer, List<List<Boolean>>> tiles;
    private final Map<Integer, Map<Integer, List<Boolean>>> edgeInfo;
    private final Map<Integer, List<Integer>> neighbors;

    private List<Map.Entry<Integer, Integer>> dragonPattern() {
        String[] pattern = new String[] {
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "
        };
        LinkedList<Map.Entry<Integer, Integer>> res = new LinkedList<>();
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[i].length(); j++) {
                if (pattern[i].charAt(j) == '#') {
                    res.add(Map.entry(i,j));
                }
            }
        }
        return res;
    }

    private Day20() {
        super(Day20.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
        tiles = parseInput();
        edgeInfo = getBoundaryMap(tiles);
        neighbors = matchTiles();
    }

    private Map<Integer, List<List<Boolean>>> parseInput() {
        Map<Integer, List<List<Boolean>>> tiles = new HashMap<>();
        List<List<Boolean>> curTile = new LinkedList<>();
        Integer tile = null;
        for (String line : lines) {
            if (line.startsWith("Tile")) {
                if (tile != null) {
                    tiles.put(tile, curTile);
                }
                tile = Integer.parseInt(line.substring(5, line.length()-1));
                curTile = new LinkedList<>();
            } else if (!line.equals("")) {
               curTile.add(line.chars().mapToObj(c -> c == '#').collect(Collectors.toList()));
            }
        }
        tiles.put(tile, curTile);
        return tiles;
    }

    private  Map<Integer, List<Integer>>  matchTiles() {
        return edgeInfo.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, tile -> tile.getValue().values().stream().map(edge -> {
            Integer oTile = findMatchingTile(edgeInfo, tile.getKey(), edge);
            if (oTile == null) {
                // reverse reverse
                oTile = findMatchingTile(edgeInfo, tile.getKey(), reverseList(edge));
            }
            return oTile;
        }).filter(Objects::nonNull).collect(Collectors.toList())));
    }

    @Override
    public String solve1() {
        return "" + neighbors.entrySet().stream().filter(e -> e.getValue().size() == 2).mapToLong(Map.Entry::getKey).reduce(1L, (a,b) -> a*b);
    }

    private Map<Integer, Map<Integer, List<Boolean>>> getBoundaryMap(Map<Integer, List<List<Boolean>>> tiles) {
        return tiles.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e-> {
                Map<Integer, List<Boolean>> boundaries = new HashMap<>();
                boundaries.put(1, e.getValue().get(0));
                List<Boolean> left = new LinkedList<>();
                List<Boolean> right = new LinkedList<>();
                e.getValue().forEach(bs -> {
                    left.add(bs.get(0));
                    right.add(bs.get(bs.size()-1));
                });
                boundaries.put(2, right);
                boundaries.put(4, e.getValue().get(e.getValue().size()-1));
                boundaries.put(8, left);
                return boundaries;
            }));
    }

    private Integer findMatchingTile(Map<Integer, Map<Integer, List<Boolean>>> edgeMap, Integer thisTile, List<Boolean> edgeBs) {
        return edgeMap.entrySet()
                            .stream()
                            .filter(o -> o.getKey() != thisTile)
                            .filter(o -> o.getValue().containsValue(edgeBs))
                            .map(Map.Entry::getKey).findFirst().orElse(null);
    }

    private List<List<Boolean>> arrangeTiles(Map<Integer, List<List<Boolean>>> tiles, int tile, int[][] tileLocations, int i, int j) {
        Map<Integer, Map<Integer, List<Boolean>>> edgeMap = getBoundaryMap(tiles);
        Map<Integer, List<Boolean>> thisEdges = edgeMap.get(tile);
        int top = 0;
        int left = 0;
        if (i > 0) {
            int up = tileLocations[i-1][j];
            top = getMatchedEdge(edgeMap, thisEdges, up);
        } else {
            int down = tileLocations[1][j];
            int bottom = getMatchedEdge(edgeMap, thisEdges, down);
            switch (bottom) {
                case 1: top = 4; break;
                case 2: top = 8; break;
                case 4: top = 1; break;
                case 8: top = 2; break;
            }
        }
        if (j > 0) {
            int leftN = tileLocations[i][j-1];
            left = getMatchedEdge(edgeMap, thisEdges, leftN);
        } else {
            int right = tileLocations[i][1];
            int rightEdge = getMatchedEdge(edgeMap, thisEdges, right);
            switch (rightEdge) {
                case 1: left = 4; break;
                case 2: left = 8; break;
                case 4: left = 1; break;
                case 8: left = 2; break;
            }
        }

        List<List<Boolean>> oImage = tiles.get(tile);
        if (tile == 1951) {
            System.out.println(tile + "\ttop: " + top + "\tleft: " + left + "\n" + convertToImage(bs2Is(oImage)));
            System.out.println("rotate1: " + convertToImage(bs2Is(rotatecc(oImage))));
            System.out.println("rotate2: " + convertToImage(bs2Is(rotatecc(rotatecc(oImage)))));
            System.out.println("rotate2+flip: " + convertToImage(bs2Is(flip(rotatecc(rotatecc(oImage))))));

        }


        if (top == 1) {
            if (left == 2) {
                //System.out.println(tile + " flipped about the vertical axis");
                return (flip(oImage));
            } else {
                //System.out.println(tile + " normal");
                return (oImage);

            }
        } else if (top == 2) {
            if (left == 1) {
                //System.out.println(tile + " rotate counter clockwise");
                return (rotatecc(oImage));

            } else {
                //System.out.println(tile + " rotate counter clockwise and flip");
                return (flip(rotatecc(oImage)));

            }
        } else if (top == 4) {
            if (left == 2) {
                //System.out.println(tile + " rotate 180");
                return (rotatecc(rotatecc(oImage)));

            } else {
                //System.out.println(tile + " rotate 180 and flip");
                return (flip(rotatecc(rotatecc(oImage))));

            }
        } else { // top == 8
            if (left == 1) {
                // rotate clockwise and flip
                //System.out.println(tile + " rotate clockwise and flip");
                return (flip(rotatecc(rotatecc(rotatecc(oImage)))));
            } else {
                // rotate clockwise
                //System.out.println(tile + " rotate clockwise and flip");
                return (rotatecc(rotatecc(rotatecc(oImage))));
            }
        }
    }

    private List<List<Boolean>> formImage(int[][] tileLocations) {
        List<List<Boolean>> fullImage = new LinkedList<>();
        for (int i = 0; i < tileLocations.length; i++) {
            List<List<Boolean>> curImage = new LinkedList<>();
            for (int j = 0; j < tileLocations[i].length; j++) {
                List<List<Boolean>> tileImg = removeBoarder(arrangeTiles(tiles, tileLocations[i][j], tileLocations, i, j));
                if (curImage.isEmpty()) {
                    for (int k = 0; k < tileImg.size(); k++) {
                        curImage.add(new LinkedList<>());
                    }
                }
                for (int k = 0; k <curImage.size(); k++) {
                    curImage.get(k).addAll(tileImg.get(k));
                }
            }
            fullImage.addAll(curImage);
        }
        return fullImage;
    }

    private int[][] placeTiles() {
        int sqSize = (int)Math.round(Math.sqrt(neighbors.size()));
        int[][] tileLocations = new int[sqSize][sqSize];
        Map<Integer, int[]> positions = new HashMap<>();
        int first = neighbors.entrySet().stream().filter(e -> e.getValue().size() == 2).findFirst().get().getKey();
        tileLocations[0][0] = first;
        positions.put(first, new int[]{0,0});
        int cur = neighbors.get(first).get(0);
        tileLocations[0][1] = cur;
        positions.put(cur, new int[]{0,1});
        for (int i = 2; i < sqSize; ++i) {
            int next = neighbors.get(cur).stream()
                    .filter(t -> neighbors.get(t).size() < 4 && !positions.containsKey(t))
                    .findFirst().get();
            tileLocations[0][i] = next;
            positions.put(next, new int[]{0,i});
            cur = next;
        }
        cur = first;
        for (int i = 1; i < sqSize; ++i) {
            int next = neighbors.get(cur).stream()
                    .filter(t -> neighbors.get(t).size() < 4 && !positions.containsKey(t))
                    .findFirst().get();
            tileLocations[i][0] = next;
            positions.put(next, new int[]{i,0});
            cur = next;
        }

        for (int i = 1; i < sqSize; ++i) {
            for (int j = 1; j < sqSize; ++j) {
                int up = tileLocations[i-1][j];
                int left = tileLocations[i][j-1];
                List<Integer> uN = neighbors.get(up);
                List<Integer> lN = neighbors.get(left);

                int next = uN.stream()
                        .filter(x -> !positions.containsKey(x) && lN.contains(x))
                        .findFirst().get();
                tileLocations[i][j] = next;
                positions.put(next, new int[]{i,j});
            }
        }
        return tileLocations;
    }

    private List<List<Integer>> replaceMonsters(List<List<Integer>> sea, boolean flipped, int rotation) {
        List<Map.Entry<Integer, Integer>> pattern = dragonPattern();
        for (int i = 0; i < sea.size() - DRAGON_HEIGHT; ++i) {
            for (int j = 0; j < sea.get(i).size() - DRAGON_WIDTH; ++j) {
                int finalI = i;
                int finalJ = j;
                if (pattern.stream().allMatch(e -> sea.get(finalI + e.getKey()).get(finalJ + e.getValue()) == 1)) {
                    pattern.stream().forEach(e -> sea.get(finalI + e.getKey()).set(finalJ + e.getValue(), 2));
                }
            }
        }
        if (rotation < 4) {
            return replaceMonsters(rotatecc(sea), flipped, rotation+1);
        } else if (!flipped) {
            return replaceMonsters(flip(sea), true, 0);
        } else {
            return sea;
        }

    }

    private int getMatchedEdge(Map<Integer, Map<Integer, List<Boolean>>> edgeMap, Map<Integer, List<Boolean>> thisEdges, int neighbor) {
        int top;
        Collection<List<Boolean>> upEdges = new HashSet<>(edgeMap.get(neighbor).values());
        Set<List<Boolean>> reverseEdges = upEdges.stream().map(Day20::reverseList).collect(Collectors.toSet());
        top = thisEdges.entrySet().stream()
                .filter(e -> upEdges.contains(e.getValue()))
                .mapToInt(Map.Entry::getKey)
                .findFirst().orElseGet(() -> thisEdges.entrySet().stream()
                        .filter(e -> reverseEdges.contains(e.getValue()))
                        .mapToInt(Map.Entry::getKey)
                        .findFirst().getAsInt());
        return top;
    }

    @Override
    public String solve2() {
        int[][] tileLocations = placeTiles();
        List<List<Boolean>> fullImage = formImage(tileLocations);
        return "" + replaceMonsters(bs2Is(fullImage), false, 0).stream().mapToLong(bs -> bs.stream().filter(b -> b == 1).count()).sum();
    }

    public static void main(String args[]) {
        new Day20().main();
    }


    private static  <T> List<List<T>>  flip(List<List<T>> oImage) {
        return oImage.stream().map(Day20::reverseList).collect(Collectors.toList());
    }

    private static <T> List<T> reverseList(List<T> list) {
        List<T> revEdge = new LinkedList<>();
        for (T t : list) {
            revEdge.add(0, t);
        }
        return revEdge;
    }

    private static List<List<Integer>> bs2Is(List<List<Boolean>> fullImage) {
        return fullImage.stream().map(bs -> bs.stream().map(b -> b ? 1 : 0).collect(Collectors.toList())).collect(Collectors.toList());
    }

    private static  <T> List<List<T>>  rotatecc(List<List<T>> oImage) {
        List<List<T>> res = oImage.get(0).stream().map(b -> new LinkedList<T>()).collect(Collectors.toList());
        for (List<T> row : oImage) {
            for (int j = 0; j < row.size(); ++j) {
                res.get(row.size()-1 -j).add(row.get(j));
            }
        }
        return res;
    }

    private static String convertToImage(List<List<Integer>> img) {
        AtomicInteger lineNumber = new AtomicInteger(0);
        return img.stream()
                .map(bs -> lineNumber.getAndIncrement()+"\t" + bs.stream().map(i -> i == 1 ? "#" : ".").collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }

    private static <T> List<List<T>> removeBoarder(List<List<T>> o) {
        return o.stream().skip(1).limit(o.size()-2).map(bs -> bs.subList(1, bs.size()-1)).collect(Collectors.toList());
    }

}
