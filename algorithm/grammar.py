## Type of Value
#import keyword
#print("keyword.kwlist : " + keyword.kwlist.__str__)
# int
# String
stringValue = "String/Value/Test"
print(type(stringValue))
print(stringValue)

# String - substring
print("#### substring")
print(stringValue[:6])
print(stringValue[7:12])
print(stringValue[13:])
print(stringValue * 3)

# String - replace
print("#### replace")
def replaceRight(original, old, new, count_right):
    repeat=0
    text=original
    old_len=len(old)

    count_find = original.count(old)
    if(count_right > count_find):
        repeat = count_find
    else:
        repeat = count_right
    while(repeat) :
        find_index = text.rfind(old)
        text = text[:find_index] + new +text[find_index+old_len:]

        repeat -=1
    return text

stringValue = "100,000,000,000"
print(stringValue.replace(",",""))
print(stringValue.replace(",","",2))
print(replaceRight(stringValue, ",", "", 2))
print(stringValue.replace(",","",100))

# String -> Int
print("### String -> Int")
intValue = 100.001
stringValue = "100.001"
print(float(intValue))      # 100.001
print(float(stringValue))   # 100.001
stringValue = "001"
print(int(stringValue))     # 1

# Int -> String
print("#### Int -> String")
print(repr(stringValue))    # '100.001'
print(str(intValue))        # 100.001



# List
# Map

#if
#while
#switch
#for
#third-if