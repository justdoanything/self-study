package book.effective;

public class Person {
    private final String name;
    private final int age;
    private final String address;
    
    private final String gender;
    private final String number;
    private final String company;
    private final int family;

    public static class Builder {
        // 필수 매개변수
        private final String name;
        private final int age;
        private final String address;
        
        // 선택 매개변수, 초기화 필요
        private final String gender = "";
        private final String number= "";
        private final String company= "";
        private final int family = 0;
        
        public Builder(String name, int age, String address){
            this.name = name;
            this.age = age;
            this.address = address;
        }

        public Builder gender(String gender){
            gender = gender;
            return this;
        }

        public Builder number(String number){
            number = number;
            return this;
        }

        public Builder company(String company){
            company = company;
            return this;
        }

        public Builder family(int family){
            family = family;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

    private Person(Builder builder){
        name = builder.name;
        age = builder.age;
        address = builder.address;
        
        gender = builder.gender;
        number = builder.number;
        company = builder.company;
        family = builder.family;
    }
}