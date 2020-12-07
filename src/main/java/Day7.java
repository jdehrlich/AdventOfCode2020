import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    private Day7() {
        super("day7");
    }

    private Map<String, Map<String, Integer>> parseRules() {
        Pattern p = Pattern.compile("([^0-9]+) bags contain (.*)");
        Pattern p2 = Pattern.compile("([0-9]+) ([^0-9]+) bag");
        return lines.stream().map(s -> {
            Map<String, Integer> map = new HashMap<>();
            Matcher m = p.matcher(s);
            if (m.matches()) {
                String bagType  = m.group(1);
                String innerBags = m.group(2);
                Matcher m2 = p2.matcher(innerBags);
                while (m2.find()) {
                    String num = m2.group(1);
                    String innerBag = m2.group(2);
                    map.put(innerBag, Integer.parseInt(num));
                }
                return new AbstractMap.SimpleImmutableEntry<>(bagType, map);
            } else {
                System.out.println("ERROR: " + s);
                return new AbstractMap.SimpleImmutableEntry<String, Map<String, Integer>>(s, new HashMap<>());
            }

            }
        ).collect(Collectors.toMap(e -> e.getKey(), e-> e.getValue()));
    }

    private void addToMap(Map<String, Map<String, Integer>> m, String k1, String k2, int v) {
        Map<String, Integer> nm = m.getOrDefault(k1, new HashMap<>());
        nm.put(k2, v);
        m.put(k1, nm);
    }

    private Map<String, Map<String, Integer>> inverted() {
        Pattern p = Pattern.compile("([^0-9]+) bags contain (.*)");
        Pattern p2 = Pattern.compile("([0-9]+) ([^0-9]+) bag");
        Map<String, Map<String, Integer>> res = new HashMap<>();
        lines.stream().forEach(s -> {
                    Matcher m = p.matcher(s);
                    if (m.matches()) {
                        String bagType = m.group(1);
                        String innerBags = m.group(2);
                        Matcher m2 = p2.matcher(innerBags);
                        while (m2.find()) {
                            String num = m2.group(1);
                            String innerBag = m2.group(2);
                            addToMap(res, innerBag, bagType, Integer.parseInt(num));
                        }
                    } else {
                        System.out.println("ERROR: " + s);
                    }

                }
        );
        return res;
    }

    @Override
    public String solve1() {
        Set<String> outerbags = new HashSet<>();
        String start = "shiny gold";
        Map<String, Map<String, Integer>> bagMap = inverted();
        Queue<String> q = new LinkedList<>();
        q.add(start);
        outerbags.add(start);
        while (!q.isEmpty()) {
            String bag = q.poll();
            Set<String> bags = bagMap.getOrDefault(bag, new HashMap<>()).keySet();
            for (String b : bags) {
                if (!outerbags.contains(b)) {
                    outerbags.add(b);
                    q.add(b);
                }
            }
        }
        outerbags.remove(start);
        return "" + outerbags.size();
    }

    private Integer recurse(Map<String, Map<String, Integer>> m, Map<String, Integer> memo, String current) {
        if (memo.containsKey(current)) {
            return memo.get(current);
        }
        Map<String, Integer> children = m.get(current);
        if (children.isEmpty()) {
            memo.put(current, 0);
            return 0;
        }
        Integer res = children.entrySet().stream().mapToInt(e -> e.getValue() * (recurse(m, memo, e.getKey()) + 1)).sum();
        memo.put(current, res);
        return res;
    }

    @Override
    public String solve2() {
        Map<String, Map<String, Integer>> rules = parseRules();
        String start = "shiny gold";
        Queue<String> q = new LinkedList<>();
        q.add(start);

        Map<String, Integer> colorToSize = new HashMap<>();
        Integer res = recurse(rules, colorToSize, start);

        return start +" "  + colorToSize.get(start);
    }

    public static void main(String args[]) {
        new Day7().main();
    }
}
