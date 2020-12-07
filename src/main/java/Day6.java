import java.io.SyncFailedException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Day6 extends AbstractDay {
    private Day6() {
        super("day6");
    }

    @Override
    public String solve1() {
        if (true) return "skip";
        List<String> groups = new LinkedList<>();
        String answers = "";
        for (String line : lines) {
            if (line == null || line.equals("")) {
                groups.add(answers);
                answers = "";
            }
            answers += line;
        }
        groups.add(answers);
        return  "" + groups.stream()
                .mapToInt(s -> chars(s)
                        .stream()
                        .collect(Collectors.toSet())
                        .size())
                .sum();
    }

    @Override
    public String solve2() {
        List<Set<Character>> groups = new LinkedList<>();
        final Set<Character> answers = new HashSet<>();
        AtomicBoolean isFirst = new AtomicBoolean(true);
        lines.stream().forEach(line -> {
            if (line == null || line.trim().equals("")) {
                Set<Character> cs = new HashSet<>(answers);
                groups.add(cs);
                answers.clear();
                isFirst.set(true);
            } else if (isFirst.get()) {
                answers.addAll(chars(line));
                isFirst.set(false);
            } else {
                answers.retainAll(chars(line));
            }
        });
        groups.add(answers);
        return  "" + groups.stream().mapToInt(Set::size).sum();
    }

    private static Set<Character> chars(String s) {
        LinkedList<Character> c = new LinkedList<>();
        for (char letter : s.toCharArray()) {
            c.add(letter);
        }
        return new HashSet<>(c);
    }

    public static void main(String args[]) {
        new Day6().main();
    }
}
