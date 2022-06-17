
public class Apple {
    
    public Apple(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
    private String name;
    private int weight;
    /**
     * @return the name
     */
    public String getName() {
        return name;
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
