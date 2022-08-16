package programmers;

class Liquid {
	protected Integer amount;
	public Liquid() { 
		amount = null;
	}
	public void drink() { 
		return; 
	}
	public int getAmount() { 
		return this.amount; 
	}
}

class ZeroCoke extends Liquid {
	public ZeroCoke(Integer amount) { 
		super.amount = amount;
	}
	public void drink() { 
		this.amount -= 20 ; 
	}
}


class P05_debug {
	static Liquid factory(String name, int amount) { 
		if(name.equals("zero")) {
			return new ZeroCoke(amount);
		}
		return null; 
	}
	public static int solution(String name, int amount) {
    	Liquid zero = factory(name, amount); 
    	if(zero == null) return - 1;
    	zero.drink(); 
    	zero.drink(); 
    	return zero.getAmount();
    }
	
	public static void main(String[] args) {
		System.err.println(solution("zero", 200));
	}
}

/*
다음 문제는 클래스로 작성된 코드중 한 줄을 고치는 문제입니다.
코드를 파악한 뒤, 잘못된 곳 한줄을 고쳐 버그를 고쳐주세요.

UML은 다음과 같습니다.

테스트 1
입력값 〉	"zero", 100
기댓값 〉	60
실행 결과 〉	테스트를 통과하였습니다.

*/