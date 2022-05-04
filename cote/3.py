input = input()
result = "";

for i in range(97, 123):
    temp = chr(i)
    result += str(input.find(chr(i))) + " "
print(result)


# .find : 찾고자하는 문자열이 있으면 첫번째 인덱스를, 없으면 -1을 반환