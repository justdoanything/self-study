conti = int(input())
multiRow = list(input() for _ in range(conti))

result = ""

for index in range(0, len(multiRow)):
    input = multiRow[index].split()
    if(len(input) != 1):
        number = int(input[0])
        alpabet = input[1]
        for i in range(0, len(alpabet)):
            for j in range(0, number):
                result += alpabet[i]
        print(result)
    result+="\n"
    result = ""


# 여러 줄 입력받기 : list(input() for _ in range(n))