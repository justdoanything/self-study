package modern.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModernVO {
    private String temp;

    public static void main(String[] args) {
        System.out.println("z");
    }
}