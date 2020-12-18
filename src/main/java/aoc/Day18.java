package aoc;

import aoc.utils.AbstractDay;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 18: Operation Order ---
 * As you look out the window and notice a heavily-forested continent slowly appear over the horizon, you are interrupted by the child sitting next to you. They're curious if you could help them with their math homework.
 *
 * Unfortunately, it seems like this "math" follows different rules than you remember.
 *
 * The homework (your puzzle input) consists of a series of expressions that consist of addition (+), multiplication (*), and parentheses ((...)). Just like normal math, parentheses indicate that the expression inside must be evaluated before it can be used by the surrounding expression. Addition still finds the sum of the numbers on both sides of the operator, and multiplication still finds the product.
 *
 * However, the rules of operator precedence have changed. Rather than evaluating multiplication before addition, the operators have the same precedence, and are evaluated left-to-right regardless of the order in which they appear.
 *
 * For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are as follows:
 *
 * 1 + 2 * 3 + 4 * 5 + 6
 *   3   * 3 + 4 * 5 + 6
 *       9   + 4 * 5 + 6
 *          13   * 5 + 6
 *              65   + 6
 *                  71
 * Parentheses can override this order; for example, here is what happens if parentheses are added to form 1 + (2 * 3) + (4 * (5 + 6)):
 *
 * 1 + (2 * 3) + (4 * (5 + 6))
 * 1 +    6    + (4 * (5 + 6))
 *      7      + (4 * (5 + 6))
 *      7      + (4 *   11   )
 *      7      +     44
 *             51
 * Here are a few more examples:
 *
 * 2 * 3 + (4 * 5) becomes 26.
 * 5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 437.
 * 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 12240.
 * ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 13632.
 * Before you can help with the homework, you need to understand it yourself. Evaluate the expression on each line of the homework; what is the sum of the resulting values?
 *
 * Your puzzle answer was 8298263963837.
 *
 * --- Part Two ---
 * You manage to answer the child's questions and they finish part 1 of their homework, but get stuck when they reach the next section: advanced math.
 *
 * Now, addition and multiplication have different precedence levels, but they're not the ones you're familiar with. Instead, addition is evaluated before multiplication.
 *
 * For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are now as follows:
 *
 * 1 + 2 * 3 + 4 * 5 + 6
 *   3   * 3 + 4 * 5 + 6
 *   3   *   7   * 5 + 6
 *   3   *   7   *  11
 *      21       *  11
 *          231
 * Here are the other examples from above:
 *
 * 1 + (2 * 3) + (4 * (5 + 6)) still becomes 51.
 * 2 * 3 + (4 * 5) becomes 46.
 * 5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 1445.
 * 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 669060.
 * ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 23340.
 * What do you get if you add up the results of evaluating the homework problems using these new rules?
 *
 * Your puzzle answer was 145575710203332.
 */
public class Day18 extends AbstractDay {

    Pattern p = Pattern.compile("\\([^\\(\\)]+\\)");
    AtomicBoolean useInvertedOrder = new AtomicBoolean(false);

    private Day18() {
        super(Day18.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
    }

    private long eval(String line) {
        Matcher m = p.matcher(line);
        if (m.find()) {
            String subEx = m.group();
            String repr = m.replaceFirst("" + eval(subEx.substring(1, subEx.length()-1)));
            return eval(repr);
        }
        String[] mult = useInvertedOrder.get() ? line.split("\\*") : new String[0];
        if (mult.length > 1) {
            return Arrays.stream(mult).mapToLong(this::eval).reduce(1, (a,b) -> a*b);
        }
        String [] tokens = line.trim().split("\\s+");
        long current = Integer.parseInt(tokens[0]);
        for (int i = 1; i < tokens.length; ++i) {
            if (tokens[i].equals("*")) {
                current *= Integer.parseInt(tokens[i+1]);
            } else if (tokens[i].equals("+")) {
                current += Integer.parseInt(tokens[i+1]);
            }
        }
        return current;
    }

    @Override
    public String solve1() {
        useInvertedOrder.set(false);
        return "" + lines.stream().mapToLong(this::eval).sum();
    }

    @Override
    public String solve2() {
        useInvertedOrder.set(true);
        return "" + lines.stream().mapToLong(this::eval).sum();
    }

    public static void main(String args[]) {
        new Day18().main();
    }
}
