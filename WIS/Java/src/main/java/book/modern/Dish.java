package book.modern;

public class Dish {
    public enum Type { MEAT, FISH, OTHER }

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;
    /**
     * @param name
     * @param vegetarian
     * @param calories
     * @param type
     */
    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the vegetarian
     */
    public boolean isVegetarian() {
        return vegetarian;
    }
    /**
     * @return the calories
     */
    public int getCalories() {
        return calories;
    }
    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString() {
        return "Dish [calories=" + calories + ", name=" + name + ", type=" + type + ", vegetarian=" + vegetarian + "]";
    }
}
