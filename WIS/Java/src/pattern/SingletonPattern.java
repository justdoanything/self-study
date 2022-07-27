package pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingletonPattern {
    
    // 아래 2개는 static이 없어도 singleton을 보장한다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final SingletonPattern instance = new SingletonPattern();

    // 기본 생성자는 다른 곳에서 생성할 수 없도록 막아야 한다.
    private SingletonPattern() {}

    public static SingletonPattern getInstance() {
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        // store 보호
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
