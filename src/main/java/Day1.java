import java.util.List;
import java.util.stream.Collectors;

public class Day1 extends AbstractDay {

    private Day1() {
        super("day1");
    }

    public String solve1() {
        List<Integer> expenses = lines.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; i < expenses.size(); ++i) {
            int first = expenses.get(i);
            for (int j = i+1; j < expenses.size(); ++j) {
                int second = expenses.get(j);
                if (first + second == 2020) {
                    return (first * second) + "; First: " + first + "; Second: " + second;
                }
            }
        }
        return "";
    }

    public String solve2() {
        List<Integer> expenses = lines.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; i < expenses.size(); ++i) {
            int first = expenses.get(i);
            for (int j = i+1; j < expenses.size(); ++j) {
                int second = expenses.get(j);
                for (int k = j+1; k < expenses.size(); ++k) {
                    int third = expenses.get(k);
                    if (first + second + third == 2020) {
                        return (first * second * third) + "; First: " + first + "; Second: " + second + "; Third: " + third;
                    }
                }
            }
        }
        return "";
    }

    public static void main (String args[]) {
        new Day1().main();
    }
}
