#include <iostream>
#include <cstdio>
#include <cstring>

using namespace std;

// 3 operation Xor Swap
void swap ( char& a, char& b){
	/*
	char c =a;
	a = b;
	b = c;
	*/
	// works for all cases. may be slow on modern processesor which are designed for copy
	a ^= b ^= a ^= b;
}
//Reverse C String separated by '\0' 
void reverse_char(char * arr){
	size_t length = strlen(arr);
	for(size_t i = 0; i < length/2; i++){
		swap(arr[i],arr[length-1-i]);
	}

}
// Reverse C String  with offset in place. 
void reverse_chars(char *arr, size_t start, size_t end)
{
	//End contains blanskspace, '\0'. Excluded 
    size_t n = end- start;
    for (size_t i=0; i < n/2; ++i) {
        swap(arr[start+i],arr[end-i-1]);
    }
}
// Reverse C String  with offset and trims it in place.
void reverse_chars(char *arr, size_t start, size_t end, size_t trim_start)
{
    size_t n = end- start;
    for (size_t i=0; i < n/2; ++i) {
        swap(arr[start+i],arr[end-i-1]);
    }
    char word[n];
    // overlapping words require us to use a small temporary memory for a word
    memcpy(word, &arr[start], n);
    //clear the word
    memset(&arr[start], ' ',n);
    memcpy(&arr[trim_start], word, n);
}
// Reverse C String  with offset, Captialize  the first letter, decapitalize last letter and trims it in place.
void reverse_chars_capitalize(char *arr, size_t start, size_t end, size_t trim_start)
{
    size_t  n = end- start;
    if(n>0){
    		if(arr[start] >='A' && arr[start]<='Z')
        		arr[start] += 'a' -'A';
    		swap(arr[start],arr[end-1]);
    		if(arr[start] >='a' && arr[start]<='z')
        		arr[start] -= 'a' -'A';
        }
    for (size_t i=1; i < n/2; ++i) {
        swap(arr[start+i],arr[end-i-1]);
    }
    // overlapping words require us to use a small temporary memory for a word
    char word[n];
    memcpy(word, &arr[start], n);
    memset(&arr[start], ' ',n);
    memcpy(&arr[trim_start], word, n);
}

// Reverse words without  trimming inplace
void reverse_words(char * arr) {
	size_t length = strlen(arr);
	size_t src_start = 0, src_end = 0;
	size_t dest_start = 0, dest_end = 0;
	for (size_t i = 0; i <length+1; i++){
		if(arr[i] != ' ' && arr[i] != '\0')
			src_end+=1;
		else if(arr[i] == ' ')
			{	
				if(src_start!=src_end)
						reverse_chars(arr, src_start, src_end);
				src_end+=1;
				src_start = src_end;

			}
		else{
			if(src_start!=src_end)
						reverse_chars(arr, src_start, src_end);
		}		
	}	
}
// Reverse words,  trimming   more than 1 spaces to  single space inplace
void reverse_words_trim(char * arr) {
	size_t length = strlen(arr);
	size_t src_start = 0, src_end = 0;
	size_t trim_start = 0,trim_end = 0;
	for (size_t i = 0; i <length+1; i++){
		if(arr[i] != ' ' && arr[i] != '\0')
			src_end+=1;
		else if(arr[i] == ' ')
			{	
				if(src_start!=src_end){
						reverse_chars_capitalize(arr, src_start, src_end,trim_start);
						trim_start +=src_end+1-src_start;

					}	
				src_end+=1;
				src_start = src_end;

			}
		else{
			if(src_start!=src_end)
						reverse_chars_capitalize(arr, src_start, src_end,trim_start);
			arr[trim_start+src_end+1-src_start]='\0';		
		}		
	}	
}
int main(){
	
	//char arr[]= "Hello World is old proverb";
	char arr[]= "Hello    World  is old                     proverb";
	// Reverse the sentence
	//reverse_char(arr);
	cout<<arr<<endl;
	// Reverse each word in the setence
	reverse_words_trim(arr);
	// Reverse each world in the setence trimming multiple spaces
	cout<<arr<<endl;
	reverse_words_trim(arr);
	cout<<arr<<endl;
	cout<<strlen(arr);
}