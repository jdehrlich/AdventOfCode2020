import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDay {
    protected List<String> lines;

    protected AbstractDay(String file) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(file);
        lines = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList());
    }

    public abstract String solve1();
    public abstract String solve2();

    public void main() {
        System.out.println(solve1());
        System.out.println(solve2());
    }
}
