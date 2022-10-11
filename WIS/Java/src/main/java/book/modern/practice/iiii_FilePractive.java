package modern.practice;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class iiii_FilePractive {
    public static void main(String[] args) {
        try(Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir") + "/self-study/Java/data"), Charset.defaultCharset())){
            lines.flatMap(line -> Arrays.stream(line.split((" ")))).distinct().forEach(s -> System.out.print(s + " "));
            System.out.println();
            lines.flatMap(line -> Arrays.stream(line.split(("")))).distinct().forEach(s -> System.out.print(s + " "));
        }catch(Exception e){

        }
    }   
}
