```java
import java.util.HashSet;

// array에서 중복 제거 후 사용
// HashSet, HashMap 사용
// Stream 사용
class Solution {
    public void solution() {
        int[] array = {2, 2, 1, 3, 4};
        int answer;
        
        HashSet<Integer> hashSet = new HashSet<>();
        for(int i =0; i<nums.length;i++)
            hs.add(nums[i]);
        if(hs.size()>nums.length/2)
            answer = nums.length/2;
        else
            answer = hs.size();

        HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0; i < nums.length; i++)
            map.put(nums[i], 1);
        answer = map.size() > nums.length / 2 ? nums.length / 2 : map.size();

        answer = Arrays.stream(nums)
                .boxed()
                .collect(Collectors.collectingAndThen(Collectors.toSet(),
                        phonekemons -> Integer.min(phonekemons.size(), nums.length / 2)));
    }
}
```