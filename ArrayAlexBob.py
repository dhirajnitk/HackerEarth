'''
# Sample code to perform I/O:

name = input()                  # Reading input from STDIN
print('Hi, %s.' % name)         # Writing output to STDOUT

# Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
'''

# Write your code here

count = int(input())
arr = list(map(int,input().split()))
alex = 0
bob = 0
no_trial = len(arr)//2
for trial in range(no_trial):
    if(len(arr)%2):
        index = len(arr)//2 + 1
        alex+=arr[index-1]
        arr.pop(index-1)
        index = len(arr)//2
        if(arr[index-1] > arr[index]):
            bob+=arr[index-1]
            arr.pop(index-1)
            
            
        else:
            bob+=arr[index]
            arr.pop(index)
            
    else:
        index = len(arr)//2
        if(arr[index-1] > arr[index]):
            alex+=arr[index-1]
            arr.pop(index-1)
            index = len(arr)//2 + 1
            bob+=arr[index-1]
            arr.pop(index-1)
            
        else:
            alex+=arr[index]
            arr.pop(index)
            index = len(arr)//2 + 1
            bob+=arr[index-1]
            arr.pop(index-1)
if(len(arr)):
    alex+=arr[0]
if(alex>=bob):
    print("Alex",alex-bob)
else:
    print("Bob", bob-alex)

            