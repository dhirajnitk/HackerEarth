
import java.util.*;
import java.util.Vector;
import java.util.stream.*;

public class Entry {
    private int key;
    private int value;

    public Entry(int key, int value) {
        this.key = key;
        this.value = value;
    }

    // getters
    public int getKey(){
        return this.key;
    }
    public int getValue(){
        return this.value;
    }
    @Override
    public String toString() { 
    return "Key: '" + this.key + "', Value: '" + this.value + "'";
    } 
}

class TestClass {
    public static void main(String args[] ) throws Exception {
        // Write your code here
        Scanner s = new Scanner(System.in);
        int T = s.nextInt();
        for(int i = 0; i <T; i++){
            int Q = s.nextInt();
            int K = s.nextInt();
            //Method reference :) Shortercode than lambda function
            PriorityQueue<Entry> pQueue  = new PriorityQueue<Entry>(Q, Comparator.comparing(Entry::getValue));
            int total_kids = 0;
            Vector<LinkedList<Integer>>Qs = new Vector<LinkedList<Integer>>();
            for(int q =0; q<Q; q++){
                LinkedList<Integer> chocolate_demand = new LinkedList<Integer>();
                int no_kids = s.nextInt();
                total_kids+=no_kids;
                pQueue.add(new Entry(q,s.nextInt()));
                for(int kids = 1; kids<no_kids; kids++){
                    chocolate_demand.add(s.nextInt());
                    
                }
                Qs.addElement(chocolate_demand);
            }
            //K pops from all Qs
            int chocolate = 0;
            if(K>=total_kids){
                for(int q =0; q<Q; q++){
                
                    chocolate+=Qs.get(q).parallelStream().reduce(0, Integer::sum);
                }
            }
            else {
                for ( int iteration = 0; iteration < K; iteration++){
                // slower o(n) find the minimun in Q linked list
                /*    
                    int min = Integer.MAX_VALUE, index = -1;
                    for(int q = 0; q <Q; q++){
                        if(Qs.get(q).size()>0) {
                            if(Qs.get(q).getFirst() <min){
                                index = q;
                                min = Qs.get(q).getFirst();
                            }
                           
                        }
                    }
                    if(index !=-1)
                        chocolate+=Qs.get(index).removeFirst();
                */
                if(pQueue.size()>0){
                    Entry first = pQueue.remove();
                    LinkedList<Integer> q_top = Qs.get(first.getKey());
                    if(q_top.size()>0)
                        pQueue.add(new Entry(first.getKey(),q_top.removeFirst()));
                    chocolate+=first.getValue();
                    }
                }//end
                else{
                    break;
                }
            }
            System.out.println(chocolate);
        }    
        
    }
}
/*
1
3 5
3
1 2 4
2
3 6
1
5
->15
*/
