public class Memo {
  
  public static void main(String[] args) {
    Member member = new Member();
    if(member.getName() == null){
      System.out.println("null");
    }
    member.setName("yongwoo");

    String where = "where a and b";
    where = String.format("%s AND %s.CHML_NO = '%s'", where, "DS", member.getName());
    System.out.println(where);
  }
}

class Member {
  private String name;
  public void setName(String name){
    this.name = name;
  }
  public String getName() {
    return this.name;
  }
}