package book.effective;

public class ImplementEquals {
    private final short areaCode, prefix, lineNum;
    
    public ImplementEquals(int areaCode, int prefix, int lineNum){
        this.areaCode = rangeCheck(areaCode, 999, "locale code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNum = rangeCheck(lineNum, 9999, "number");
    }

    private static short rangeCheck(int val, int max, String arg){
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }   

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof ImplementEquals))
            return false;
        ImplementEquals ie = (ImplementEquals) o;
        return ie.lineNum == lineNum && ie.prefix == prefix && ie.areaCode == areaCode;
    }

    @Override
    public ImplementEquals clone(){
        try{
            return (ImplementEquals) super.clone();
        }catch(CloneNotSupportedException e){
            throw new AssertionError(); // 일어날 수 없는 일이다.
        }
    }
}
