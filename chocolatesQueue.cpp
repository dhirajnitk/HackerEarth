#include <iostream>
#include <algorithm>
#include <queue>
#include <vector>
#include <list>
#include <functional>
#include <numeric>
using namespace std;

template<typename T> void print_queue(T& q) {
    while(!q.empty()) {
        std::cout << q.top().second << " ";
        q.pop();
    }
    std::cout << '\n';
}

template<typename T> void print_list(T& q) {
    while(!q.empty()) {
        std::cout << q.front() << " ";
        q.pop_front();
    }
    std::cout << '\n';
}


int main(){

	int T, Q,K;
	cin >>T;
	for (int i = 0 ; i <T; i ++){
		cin >>Q >>K;
		// Using lambda to compare elements.
    	auto cmp = [](pair<int,int>& left, pair<int,int>& right) { 
    	    if(left.second == right.second)
    	        return(left.first > right.first);
    	    else return (left.second  > right.second);
    	    
    	};
		std::priority_queue<pair<int,int>, std::deque<pair<int,int>>,decltype(cmp)> p_queue(cmp);
		int total_kids = 0;
		int no_kids;
		vector<list<int>>qs;
		for(int q = 0; q<Q; q++){
			list<int>chocolate_demand;
			cin>>no_kids;
			total_kids += no_kids;
			int child_demand;
			cin>>child_demand;
			//cout<<child_demand<<endl;
			p_queue.push(make_pair(q,child_demand));
			for(int kids = 1; kids<no_kids; kids++){
					cin >>child_demand;
                    chocolate_demand.emplace_back(child_demand);
                }
            qs.emplace_back(chocolate_demand);  
		}
		
		//print_queue(p_queue);
		/*
		for(int i = 0; i<qs.size(); i++)
		    print_list(qs.at(i));
		*/

		
		//K pops from each qs
		int chocolate = 0;
		if(K>= total_kids){
			for(int q =0; q<Q; q++){
                    chocolate+=std::accumulate(qs.at(q).begin(), qs.at(q).end(), 0);
                }
		}
		else{
			for ( int iteration = 0; iteration < K; iteration++){
                    if(p_queue.size()>0){
                        pair<int,int> key = p_queue.top();
                        p_queue.pop();
                        list<int> q_top = qs.at(key.first);
                        if(q_top.size()>0){
                            p_queue.push(make_pair(key.first,q_top.front()));
                            q_top.pop_front();
                            // stack variables are not held on heap. Need to be copied back
                            qs.at(key.first) = q_top;
                        }
                        chocolate+=key.second;
                    }
                    else{
                           // Priority queue is empty
                            break;
                    }
                }//end of K iteration
                
        }
        cout<<chocolate<<endl;
	}
}
