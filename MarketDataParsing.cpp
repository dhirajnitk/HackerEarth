#include <cstring>
#include <iostream>
using namespace std;
struct trade_info{
	char* symbol;
	int trade_size;
	float trade_price;
};

int read_package(){
    int no_packets, packet_length,no_market_updates;
    int packet_bytes = 4,market_bytes =2;
    char packet_buffer[packet_bytes];
    cin.read (packet_buffer,packet_bytes);
    packet_length = atoi(packet_buffer);
    char packet_market_buffer[market_bytes];
    cin.read (packet_market_buffer,market_bytes);
    no_market_updates = atoi(packet_market_buffer);
    cin.ignore(packet_length -packet_bytes -market_bytes);  
    return no_market_updates;
}
trade_info read_trade_data(bool & flag){
    int packet_bytes = 4,symbol_bytes =5,dynamic_bytes = 22, trade_type =1;
    char packet_buffer[packet_bytes];
    cin.read(packet_buffer,packet_bytes);
    int packet_length = atoi(packet_buffer);
    char *symbol_buffer = new char[symbol_bytes];
    cin.read(symbol_buffer, symbol_bytes);
    char type = cin.get();
    trade_info info;
    if(type == 'Q'){
     	cin.ignore(packet_length - packet_bytes - symbol_bytes - trade_type);
 	  	return info ;
    }
    else{
    	flag = true;
        cin.read (packet_buffer,packet_bytes);
        int trade_size = atoi(packet_buffer);
        cin.read (packet_buffer,packet_bytes);
        float trade_price=(float)atoi(packet_buffer);
        cin.read (packet_buffer,packet_bytes);
        trade_price+=(float)atoi(packet_buffer)*0.001;
        cin.ignore(packet_length -dynamic_bytes);
        info.symbol = symbol_buffer;
        info.trade_size = trade_size;
        info.trade_price = trade_price;
        return info;  
    }
}
int main(){

    int no_market_updates = read_package();
    for(int i =0;i<no_market_updates; i++){
         bool flag = false;
         trade_info info = read_trade_data(flag);
         if(flag)
 	        printf("%d %s @ %.2f", info.trade_size, info.symbol, info.trade_price);
 	     delete(info.symbol);
     }
}
/*
00220100000000000000000025 GOOGT125006140034000
*/