#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <iterator> 
#include <chrono>
using namespace std::chrono;
using namespace std;
namespace std{
	std::ostream& operator<<(ostream& stream, const pair<pair<int,int>,int>& item)
	{
	    stream << item.first.first;
	    stream << " ";
	    stream << item.first.second;
	    stream << " ";
	    stream << item.second;
	    stream <<"\n";
	    return stream;
	}
}

bool check_edge(int src, pair<int,int> &dest, bool &flip){
			if(dest.first == src) {
					//flip = false;
                	return true;
                }
            else if(dest.second == src){
            	flip = true;
            	return true;
            }
            else{
            	//flip = false;
            	return false;
            }
}
// gcc+ doesnt not support VLA( used in c99). Forced to use single pointer
int recurse_find_dial_max(vector<pair<pair<int,int>,int>>& wts, int money, int rows, int cols, int prev_edge_index, int* arr, bool& flip){
	if(money<=0)
		return 0;
	if(prev_edge_index ==-1){
		//Memoization doesnt matter for -1 index.
		  if (*(arr+money*cols+ prev_edge_index+1))
			return *(arr+money*cols+ prev_edge_index+1);
		else{
			for(int j =0 ; j<wts.size();j++){
				int residual_value = recurse_find_dial_max(wts,money,rows, cols, wts[j].first.second,arr,flip);
				if(*(arr+money*cols+ prev_edge_index+1) <wts[j].first.second + residual_value){
						*(arr+money*cols+ prev_edge_index+1)=wts[j].first.second + residual_value;
					}
			}
			return *(arr+money*cols+ prev_edge_index+1);
		}
	}
	else{
		//No Memoization
		{
			for(int j =0 ; j<wts.size();j++){
				flip = false;
				int residual_value;
				if(money-wts[j].second>=0 && check_edge(prev_edge_index, wts[j].first,flip)){
					if(flip){
						residual_value = recurse_find_dial_max(wts,money-wts[j].second, rows, cols, wts[j].first.first,arr,flip);
						if(*(arr+money*cols+ prev_edge_index) <wts[j].first.first + residual_value){
								*(arr+money*cols+ prev_edge_index)=wts[j].first.first + residual_value;
								
							}
					}
					else{
						residual_value = recurse_find_dial_max(wts,money-wts[j].second, rows, cols, wts[j].first.second,arr,flip);
						if(*(arr+money*cols+ prev_edge_index) <wts[j].first.second + residual_value){
								*(arr+money*cols+ prev_edge_index)=wts[j].first.second + residual_value;
								
							}
					}
				}
			}
			return *(arr+money*cols+ prev_edge_index);
		}
	}
}
int dial_maximum(vector<pair<pair<int,int>,int>>& wts, int money){
	int prev_edge_index = -1;
	int no_dials = 10;
	int dial_arr[money+1][no_dials] = {0};
	bool flip = false;
	return recurse_find_dial_max(wts,money, money+1, no_dials, prev_edge_index, (int*)dial_arr,flip);
	
}
int main(){
	
	int money;
	//cin >> money;
	money = 15;
	int x, y , w;
	vector<pair<pair<int,int>,int>> wts;
	/*
	for(int i = 0; i < 12; i ++){
		cin >>x;
		cin >>y;
		cin >>w;

		pair<pair<int,int>,int>wt = make_pair(make_pair(x,y),w);
		wts.emplace_back(wt);
	}
	*/
	wts.emplace_back(make_pair(make_pair(1,2),1));
	wts.emplace_back(make_pair(make_pair(1,4),1));
	wts.emplace_back(make_pair(make_pair(4,7),1));
	wts.emplace_back(make_pair(make_pair(2,3),1));
	wts.emplace_back(make_pair(make_pair(2,5),1));
	wts.emplace_back(make_pair(make_pair(5,8),1));
	wts.emplace_back(make_pair(make_pair(3,6),1));
	wts.emplace_back(make_pair(make_pair(6,9),1));
	wts.emplace_back(make_pair(make_pair(4,5),1));
	wts.emplace_back(make_pair(make_pair(5,6),1));
	wts.emplace_back(make_pair(make_pair(7,8),1));
	wts.emplace_back(make_pair(make_pair(8,9),1));
	//Sorting weights has no effect on memoization problem
	sort(wts.begin(), wts.end(), [](pair<pair<int,int>,int> a, pair<pair<int,int>,int> b) {return a.second < b.second; });
	// Get starting timepoint
    auto start = high_resolution_clock::now();
    int maximum = dial_maximum(wts,money);
    // Get ending timepoint
    auto stop = high_resolution_clock::now();
    cout<<"x "<<"y "<<"w"<<endl;
	std::copy(wts.begin(), wts.end(), std::ostream_iterator<pair<pair<int,int>,int>>(cout, ""));
    cout<<"Maximum dials: "<<maximum<<endl;
    cout << "Time taken: " << duration_cast<duration<float>>(stop - start).count() << " seconds" << endl;
    return 0;    

}