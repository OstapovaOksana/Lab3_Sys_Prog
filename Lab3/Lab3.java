import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;

public class Lab3 {

    private static boolean writeToFile(String content, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(content);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getFileContent(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PatternWithColor[] getPatterns() {
        return new PatternWithColor[] {

            	new PatternWithColor("(\\/\\*(.|\\r\\n)+\\*\\/)", "brown"), // comment
            	new PatternWithColor("(\\/\\/.+)", "brown"), // comment
        		new PatternWithColor("[-+ ]?[0-9]*[.]?[0-9]+([eE][-+]?[0-9]+)?","#1c08ff"), // int + float
        		new PatternWithColor("0x[0-9A-Fa-f]*", "#1c08ff"), // 16numb
        		new PatternWithColor("'[^']+'", "red"), // char const
        		new PatternWithColor("\"(?:\\\\\"|[^\"])*?\"", "red"), // string const
        		new PatternWithColor("#[ ]*(if|else|elif|endif|define|undef|" +
                    	"warning|error|line|region|endregion|pragma|pragma warning|pragma checksum)", "green"), // directives
        		new PatternWithColor(
                    	"\\b(?:abstract|as|base|bool|break|byte|case|catch|char|checked|" +
                       	"class|const|continue|decimal|default|delegate|do|double|else|" +
                       	"enum|event|explicit|extern|false|finally|fixed|float|for|foreach|" +
                       	"goto|if|implicit|in|int|interface|internal|is|lock|long|" +
                       	"namespace|new|null|object|operator|out|override|params|" +
                       	"private|protected|public|readonly|ref|return|sbyte|sealed|"+
                       	"short|sizeof|stackalloc|static|string|struct|switch|this|throw|true|"+
                       	"try|typeof|uint|ulong|unchecked|unsafe|unshort|using|"+
                       	"virtual|volatile|void|while)\\b", "pink"), // reserved
        		new PatternWithColor("==|!=|<=|>=|[+\\-*/<>=]", "yellow"), // operators
        		new PatternWithColor("[\\.;,()\\[\\]{}]", "orange"), // .,;
                new PatternWithColor("[_A-Za-z][0-9A-Za-z_]*", "#00ff80"), // variables       		          
//                new PatternWithColor("\\S+", "black"), // error
        };
    }

    public static String[] colorText(String source) {
        String[] coloredText = new String[source.length()];
        PatternWithColor[] patterns = getPatterns();
        for (PatternWithColor pattern : patterns) {
            Matcher m = pattern.getPattern().matcher(source);
            while (m.find()) {
                boolean canColoring = true;
                for (int i = m.start(); i < m.end(); i++) {
                    if (coloredText[i] != null)
                        canColoring = false;
                }
                if (canColoring) {
                    for (int i = m.start(); i < m.end(); i++) {
                        coloredText[i] = pattern.getColor();
                    }
                }
            }
        }

        return coloredText;
    }

    public static void createHtml(String source) {
        StringBuilder builder = new StringBuilder();
        String[] colors = colorText(source);
        builder.append(
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<style>\n" +
                "\n" +
                "span {\n" +
                "    white-space: pre-wrap;\n" +
                "}\n" +
                "</style>\n" +
                "<body bgcolor=\"white\">\n" +
                "\n"
        );
        for (int i = 0; i < source.length(); i++) {
            builder.append(String.format("<span style='color:%s;'>%c</span>", colors[i], source.charAt(i)));
        }
        builder.append(
                "</body>\n" +
                "</html>"
        );
        writeToFile(builder.toString(), "index.html");
    }

    public static void main(String[] args) {
        String source = getFileContent("Program.cs");
        createHtml(source);

    }
}
