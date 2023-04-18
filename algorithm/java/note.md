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
```java
// List -> Array
list.stream().mapToInt(i -> i).toArray();
list.toArray(new Integer[0]);
list.toArray(new String[0]);
    
// String -> IntStream
str.chars().filter( e -> 'P'== e).count() == s.chars().filter( e -> 'Y'== e).count();
```
```java
// sort + compare
list.sort((str1, str2) -> {
    if(str1.charAt(n) > str2.charAt(n))
        return 1;
    else if(str1.charAt(n) == str2.charAt(n))
        return str1.compareTo(str2);    // String 비교
    else
        return -1;
});

// string sort
return Stream.of(s.split(""))
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.joining());
```