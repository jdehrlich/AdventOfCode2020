package aoc.week3;

import aoc.utils.AbstractDay;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * --- Day 16: Ticket Translation ---
 * As you're walking to yet another connecting flight, you realize that one of the legs of your re-routed trip coming up is on a high-speed train. However, the train ticket you were given is in a language you don't understand. You should probably figure out what it says before you get to the train station after the next flight.
 *
 * Unfortunately, you can't actually read the words on the ticket. You can, however, read the numbers, and so you figure out the fields these tickets must have and the valid ranges for values in those fields.
 *
 * You collect the rules for ticket fields, the numbers on your ticket, and the numbers on other nearby tickets for the same train service (via the airport security cameras) together into a single document you can reference (your puzzle input).
 *
 * The rules for ticket fields specify a list of fields that exist somewhere on the ticket and the valid ranges of values for each field. For example, a rule like class: 1-3 or 5-7 means that one of the fields in every ticket is named class and can be any value in the ranges 1-3 or 5-7 (inclusive, such that 3 and 5 are both valid in this field, but 4 is not).
 *
 * Each ticket is represented by a single line of comma-separated values. The values are the numbers on the ticket in the order they appear; every ticket has the same format. For example, consider this ticket:
 *
 * .--------------------------------------------------------.
 * | ????: 101    ?????: 102   ??????????: 103     ???: 104 |
 * |                                                        |
 * | ??: 301  ??: 302             ???????: 303      ??????? |
 * | ??: 401  ??: 402           ???? ????: 403    ????????? |
 * '--------------------------------------------------------'
 * Here, ? represents text in a language you don't understand. This ticket might be represented as 101,102,103,104,301,302,303,401,402,403; of course, the actual train tickets you're looking at are much more complicated. In any case, you've extracted just the numbers in such a way that the first number is always the same specific field, the second number is always a different specific field, and so on - you just don't know what each position actually means!
 *
 * Start by determining which tickets are completely invalid; these are tickets that contain values which aren't valid for any field. Ignore your ticket for now.
 *
 * For example, suppose you have the following notes:
 *
 * class: 1-3 or 5-7
 * row: 6-11 or 33-44
 * seat: 13-40 or 45-50
 *
 * your ticket:
 * 7,1,14
 *
 * nearby tickets:
 * 7,3,47
 * 40,4,50
 * 55,2,20
 * 38,6,12
 * It doesn't matter which position corresponds to which field; you can identify invalid nearby tickets by considering only whether tickets contain values that are not valid for any field. In this example, the values on the first nearby ticket are all valid for at least one field. This is not true of the other three nearby tickets: the values 4, 55, and 12 are are not valid for any field. Adding together all of the invalid values produces your ticket scanning error rate: 4 + 55 + 12 = 71.
 *
 * Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?
 *
 * Your puzzle answer was 27898.
 *
 * --- Part Two ---
 * Now that you've identified which tickets contain invalid values, discard those tickets entirely. Use the remaining valid tickets to determine which field is which.
 *
 * Using the valid ranges for each field, determine what order the fields appear on the tickets. The order is consistent between all tickets: if seat is the third field, it is the third field on every ticket, including your ticket.
 *
 * For example, suppose you have the following notes:
 *
 * class: 0-1 or 4-19
 * row: 0-5 or 8-19
 * seat: 0-13 or 16-19
 *
 * your ticket:
 * 11,12,13
 *
 * nearby tickets:
 * 3,9,18
 * 15,1,5
 * 5,14,9
 * Based on the nearby tickets in the above example, the first position must be row, the second position must be class, and the third position must be seat; you can conclude that in your ticket, class is 12, row is 11, and seat is 13.
 *
 * Once you work out which field is which, look for the six fields on your ticket that start with the word departure. What do you get if you multiply those six values together?
 *
 * Your puzzle answer was 2766491048287.
 */
public class Day16 extends AbstractDay {


    private Map<String, boolean[]> rules;
    private int[] myTicket;
    private List<int[]> validTickets;
    private List<int[]> invalidTickets;

    private Day16() {
        super(Day16.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
        rules = new HashMap<>();
        List<int[]>tickets = new LinkedList<>();
        int cond = 0;
        for (String line : lines) {
            switch (cond) {
                case 0:
                    if (line.equals("your ticket:")) {
                        cond++;
                    } else {
                        parseRule(rules, line);
                    }
                    break;
                case 1:
                    if (line.equals("nearby tickets:")) {
                        cond++;
                    } else {
                        myTicket = parseTicket(line);
                    }
                    break;
                default:
                    tickets.add(parseTicket(line));
            }
        }
        List<int[]>[] sortedTickets = splitTickets(tickets);
        validTickets = sortedTickets[0];
        invalidTickets = sortedTickets[1];
    }

    private int[] parseTicket(String line) {
        return Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private void parseRule(Map<String, boolean[]> map, String line) {
        Matcher m = Pattern.compile("(\\D+) (\\d+)-(\\d+) or (\\d+)-(\\d+)").matcher(line);
        if (!m.matches()) throw new IllegalArgumentException(line + " does not match");
        int highestBound = Integer.parseInt(m.group(5));
        boolean[] res = new boolean[highestBound+1];
        for (int i = Integer.parseInt(m.group(2)); i <= Integer.parseInt(m.group(3)); ++i){
            res[i] = true;
        }
        for (int i = Integer.parseInt(m.group(4)); i <= highestBound; ++i) {
            res[i] = true;
        }
        map.put(m.group(1), res);
    }


    private List<int[]>[] splitTickets(List<int[]> tickets) {
        boolean[] valid = new boolean[rules.values().stream().mapToInt(a -> a.length).max().getAsInt()];
        rules.values().stream().forEach(a -> {
            for (int i = 0; i < a.length; i++) {
                if(a[i]) {
                    valid[i] = true;
                }
            }
        });
        return new List[]{
                tickets.stream()
                        .filter(a -> Arrays.stream(a).allMatch(i -> i < valid.length && valid[i]))
                        .collect(Collectors.toList()),
                tickets.stream()
                        .map(a -> Arrays.stream(a).filter(i -> i >= valid.length || !valid[i]).toArray())
                        .filter(a -> a.length > 0)
                        .collect(Collectors.toList())
        };
    }

    @Override
    public String solve1() {
        return "" + invalidTickets.stream()
                .mapToInt(a -> Arrays.stream(a).sum())
                .sum();
    }


    @Override
    public String solve2() {
        Map<String, Boolean[]> possibles = rules.keySet().stream().collect(Collectors.toMap(s -> s, s -> new Boolean[myTicket.length]));
        possibles.values().stream().forEach(a -> Arrays.fill(a, true));
        validTickets.forEach(a -> {
                    for (int i = 0; i < a.length; i++) {
                        int val = a[i];
                        for (Map.Entry<String, Boolean[]> p : possibles.entrySet()) {
                            boolean[] valid = rules.get(p.getKey());
                            if (val > valid.length || !valid[val]) {
                                p.getValue()[i] = false;
                            }
                        }
                    }
                });
        Map<String, Set<Integer>> rulePosition = possibles.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                e -> {
                    Set<Integer> s = new HashSet<>();
                    for (int i = 0; i < e.getValue().length; ++i) {
                        if (e.getValue()[i]) {
                            s.add(i);
                        }
                    }
                    return s;
                }));
        return "" + rulePosition.entrySet().stream()
                .sorted(Comparator.comparingInt(x -> x.getValue().size()))
                .map(entry -> {
                    Integer i = rulePosition.get(entry.getKey()).stream().findFirst().get();
                    for (String r : rulePosition.keySet()) {
                        if (!r.equals(entry.getKey())) {
                            rulePosition.get(r).remove(i);
                        }
                    }
                    return Map.entry(entry.getKey(), i);
                })
                .filter(e -> e.getKey().startsWith("departure"))
                .mapToLong(e -> myTicket[e.getValue()])
                .reduce(1, (a,b) -> a*b);
    }

    public static void main(String args[]) {
        new Day16().main();
    }
}
