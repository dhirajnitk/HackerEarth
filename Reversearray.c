#include <stdio.h>
#include <string.h>
#include <stdlib.h>
// 3 operation Xor Swap
void swap ( char* a, char* b){
	/*
	char c =*a;
	*a = *b;
	*b = c;
   */
	// fails  if a and b point to same memory
    if(a!=b)
	*a ^= *b ^=(*a ^= *b);
}
// Reverse C String  with offset in place. 
void reverse_chars(char *arr, size_t start, size_t end)
{
    size_t n = end- start;
	//End contains blankspace, '\0'. Excluded 
    if(n>0){
    		swap(&arr[start],&arr[end-1]);
    		if(arr[start] >='a' && arr[start]<='z')
        		arr[start] -= 'a' -'A';
        }
    for (size_t i=1; i < n/2; ++i) {
        swap(&arr[start+i],&arr[end-i-1]);
    }
}


// Reverse words without  trimming inplace
void reverse_words(char * arr) {
	size_t length = strlen(arr);
	size_t src_start = 0, src_end = 0;
	size_t dest_start = 0, dest_end = 0;
	for (size_t i = 0; i <length; i++){
		if(arr[i] != ' ')
			src_end+=1;
		else if(arr[i] == ' ')
			{	
				if(src_start!=src_end)
						reverse_chars(arr, src_start, src_end);
				src_end+=1;
				src_start = src_end;

			}	
	}
    if(src_start!=src_end)
						reverse_chars(arr, src_start, src_end);
}


int main() {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT */ 
    int T;
    scanf("%d", &T);
    char arr[200];
    for(int i = 0; i <T; i++) {
        scanf (" %[^\n]%*c", arr);
        //scanf (" %[^\n]", arr);
        //fgets(arr, 200, stdin);
        reverse_words(arr);
        printf("%s\n", arr);
    }
    return 0;
}
