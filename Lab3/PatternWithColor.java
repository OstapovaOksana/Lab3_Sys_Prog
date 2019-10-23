import java.util.regex.Pattern;

public class PatternWithColor {

    private Pattern pattern;
    private String color;

    public PatternWithColor(String pattern, String color) {
        this.pattern = Pattern.compile(pattern);
        this.color = color;
    }

    public Pattern getPattern() { return this.pattern; }

    public String getColor() { return this.color; }
}
