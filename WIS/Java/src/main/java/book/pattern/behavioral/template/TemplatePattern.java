package book.pattern.behavioral.template;

import book.pattern.behavioral.template.templ.CreamPasta;
import book.pattern.behavioral.template.templ.Pasta;
import book.pattern.behavioral.template.templ.TomatoPasta;

public class TemplatePattern {
    public static void main(String[] args) {
        Pasta creamPasta = new CreamPasta();
        creamPasta.cookPasta();

        System.out.println("========================");

        Pasta tomatoPasta = new TomatoPasta();
        tomatoPasta.cookPasta();
    }
}
