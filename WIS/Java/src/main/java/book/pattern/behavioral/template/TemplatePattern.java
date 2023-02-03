package book.pattern.behavioral.template;

import book.pattern.behavioral.template.template.CreamPasta;
import book.pattern.behavioral.template.template.Pasta;
import book.pattern.behavioral.template.template.TomatoPasta;

public class TemplatePattern {
    public static void main(String[] args) {
        Pasta creamPasta = new CreamPasta();
        creamPasta.cookPasta();

        System.out.println("========================");

        Pasta tomatoPasta = new TomatoPasta();
        tomatoPasta.cookPasta();
    }
}
