input = list(input())

mapping = { }

for i in range(0, len(input)):
    if input[i].upper() in mapping:
        mapping[input[i].upper()] = int(mapping[input[i].upper()]) + 1
    else:
        mapping[input[i].upper()] = 0

maxKey = ""
maxValue = -1
for key in mapping.keys():
    if mapping[key] > maxValue:
        maxKey = key
        maxValue = mapping[key]

if list(mapping.values()).count(maxValue) > 1:
    print("?")
else:
    print(maxKey.upper())