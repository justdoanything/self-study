package modern;

public class Apple {
    
    public Apple(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
    public Apple(String name, int weight, int price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }
    private String name;
    private int weight;
    private int price;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }
    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString() {
        return "Apple [name=" + name + ", weight=" + weight + "]";
    }

    
}
