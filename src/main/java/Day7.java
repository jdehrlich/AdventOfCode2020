import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 7: Handy Haversacks ---
 * You land at the regional airport in time for your next flight. In fact, it looks like you'll even have time to grab some food: all flights are currently delayed due to issues in luggage processing.
 *
 * Due to recent aviation regulations, many rules (your puzzle input) are being enforced about bags and their contents; bags must be color-coded and must contain specific quantities of other color-coded bags. Apparently, nobody responsible for these regulations considered how long they would take to enforce!
 *
 * For example, consider the following rules:
 *
 * light red bags contain 1 bright white bag, 2 muted yellow bags.
 * dark orange bags contain 3 bright white bags, 4 muted yellow bags.
 * bright white bags contain 1 shiny gold bag.
 * muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
 * shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
 * dark olive bags contain 3 faded blue bags, 4 dotted black bags.
 * vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
 * faded blue bags contain no other bags.
 * dotted black bags contain no other bags.
 * These rules specify the required contents for 9 bag types. In this example, every faded blue bag is empty, every vibrant plum bag contains 11 bags (5 faded blue and 6 dotted black), and so on.
 *
 * You have a shiny gold bag. If you wanted to carry it in at least one other bag, how many different bag colors would be valid for the outermost bag? (In other words: how many colors can, eventually, contain at least one shiny gold bag?)
 *
 * In the above rules, the following options would be available to you:
 *
 * A bright white bag, which can hold your shiny gold bag directly.
 * A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
 * A dark orange bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
 * A light red bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
 * So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.
 *
 * How many bag colors can eventually contain at least one shiny gold bag? (The list of rules is quite long; make sure you get all of it.)
 *
 * Your puzzle answer was 115.
 *
 * The first half of this puzzle is complete! It provides one gold star: *
 *
 * --- Part Two ---
 * It's getting pretty expensive to fly these days - not because of ticket prices, but because of the ridiculous number of bags you need to buy!
 *
 * Consider again your shiny gold bag and the rules from the above example:
 *
 * faded blue bags contain 0 other bags.
 * dotted black bags contain 0 other bags.
 * vibrant plum bags contain 11 other bags: 5 faded blue bags and 6 dotted black bags.
 * dark olive bags contain 7 other bags: 3 faded blue bags and 4 dotted black bags.
 * So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!
 *
 * Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count all of the bags, even if the nesting becomes topologically impractical!
 *
 * Here's another example:
 *
 * shiny gold bags contain 2 dark red bags.
 * dark red bags contain 2 dark orange bags.
 * dark orange bags contain 2 dark yellow bags.
 * dark yellow bags contain 2 dark green bags.
 * dark green bags contain 2 dark blue bags.
 * dark blue bags contain 2 dark violet bags.
 * dark violet bags contain no other bags.
 * In this example, a single shiny gold bag must contain 126 other bags.
 *
 * How many individual bags are required inside your single shiny gold bag?
 */
public class Day7 extends AbstractDay {

    private final Map<String, Map<String, Integer>> inverted;
    private final Map<String, Map<String, Integer>> normal;
    private final String START = "shiny gold";

    private Day7() {
        super("day7");
        normal = new HashMap<>();
        inverted = new HashMap<>();
        parseRules();
    }

    private void parseRules() {
        Pattern p = Pattern.compile("([^0-9]+) bags contain (.*)");
        Pattern p2 = Pattern.compile("([0-9]+) ([^0-9]+) bag");
        lines.stream().forEach(s -> {
            Matcher m = p.matcher(s);
            if (m.matches()) {
                String bagType  = m.group(1);
                normal.putIfAbsent(bagType, new HashMap<>());
                Matcher m2 = p2.matcher(m.group(2));
                while (m2.find()) {
                    int num = Integer.parseInt(m2.group(1));
                    String innerBag = m2.group(2);
                    inverted.putIfAbsent(innerBag, new HashMap<>());
                    normal.get(bagType).put(innerBag, num);
                    inverted.get(innerBag).put(bagType, num);
                }
            } else {
                System.out.println("ERROR: " + s);
            }
        });
    }

    @Override
    public String solve1() {
        Set<String> outerbags = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        q.add(START);
        outerbags.add(START);
        while (!q.isEmpty()) {
            String bag = q.poll();
            Set<String> bags = inverted.getOrDefault(bag, new HashMap<>()).keySet();
            for (String b : bags) {
                if (!outerbags.contains(b)) {
                    outerbags.add(b);
                    q.add(b);
                }
            }
        }
        outerbags.remove(START);
        return START + " " + outerbags.size();
    }

    private Integer recurse(Map<String, Integer> memo, String current) {
        if (memo.containsKey(current)) {
            return memo.get(current);
        }
        Map<String, Integer> children = normal.get(current);
        if (children.isEmpty()) {
            memo.put(current, 0);
            return 0;
        }
        Integer res = children.entrySet().stream().mapToInt(e -> e.getValue() * (recurse(memo, e.getKey()) + 1)).sum();
        memo.put(current, res);
        return res;
    }

    @Override
    public String solve2() {
        return START +" "  + recurse(new HashMap<>(), START);
    }

    public static void main(String args[]) {
        new Day7().main();
    }
}
