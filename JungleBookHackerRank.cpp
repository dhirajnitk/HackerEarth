#include <iostream>
#include <algorithm>
#include <vector>
#include <unordered_map>
using namespace std;
 class Graph {
   public:class Node;
   unordered_map<int,Node*> animal_map;
   vector<Node*>tree;
   class Node {
      public:
       Node(int id) {
        this->value  =id;
       }
       Node * parent;
       int value;
       //int height;
       vector<Node*> children;
   };
   Node* getNode(int id) {
      return animal_map[id];
    }
   // 1 Full traversal of tree will calculate the height. No need to store height in Node
   int createTreeHeightLessMem(Node * tree) {
      if(tree->children.size() == 0)
        return 0;
      else{
        int ht_tree=0;
        for ( int i =0;i <tree->children.size();i++){
          ht_tree=max(ht_tree,createTreeHeightLessMem(tree->children[i]));
        }
        return(1+ht_tree);
      }
      
   }
  
   Node* createNode(vector<Node*>& tree, vector<int> predators, int index){  
           if(predators[index] == -1){
              if(!animal_map.count(index)){
                Node * first = new Node(index);
                tree.push_back(first);
                //animal_map[index] = first;
                // uses pair's converting move constructor
                animal_map.emplace(std::make_pair(index,first));
                 // uses pair's template constructor
                // animal_map.emplace(index,first);
                //animal_map.insert(std::make_pair(index,first));
                return first;
              }
              else{
                //printf("Index is:%d\n",index);
                return animal_map[index];
              }
          }
          else{
              int father_index = predators[index];
              if(!animal_map.count(index)){
                  Node * first = new Node(index);
                  first->parent = createNode(tree, predators, father_index);
                  first->parent->children.push_back(first);
                  //animal_map[index] = first;
                  animal_map.emplace(std::make_pair(index,first));
                  //animal_map.emplace(index,first);
                  //animal_map.insert(std::make_pair(index,first));
                  return first;
              }
              else{
                  return animal_map[index];
              }
              
          }
   }
   

   int maximumGroups(vector<int>&predators){
  
      for(int i = 0; i < predators.size();i++){
        createNode(tree,predators,i);
      }
      vector<int> heights;
      for ( int i = 0; i < tree.size(); i ++)
        heights.push_back(createTreeHeightLessMem(tree[i]));     
      cout <<*max_element(heights.begin(),heights.end())+1;
   }
};
 int main(){
  Graph g;
  //int predators_count =10;
  vector<int>predators={-1, 8, 6, 0, 7, 3, 8, 9, -1, 6};
  //vector<int>predators={-1, 0, 1};
  int res = g.maximumGroups(predators);
    
} 
