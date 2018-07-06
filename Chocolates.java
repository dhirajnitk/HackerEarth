/* IMPORTANT: Multiple classes and nested static classes are supported */

/*
 * uncomment this if you want to read input.
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;
*/
import java.util.*;
// Warning: Printing unwanted or ill-formatted data to output will cause the test cases to fail
import java.util.Vector;
import java.util.stream.*;
class TestClass {
    public static void main(String args[] ) throws Exception {
        /* Sample code to perform I/O:
         * Use either of these methods for input

        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();                // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

        //Scanner
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();                 // Reading input from STDIN
        System.out.println("Hi, " + name + ".");    // Writing output to STDOUT

        */

        // Write your code here
        Scanner s = new Scanner(System.in);
        int T = s.nextInt();
        for(int i = 0; i <T; i++){
            int Q = s.nextInt();
            int K = s.nextInt();
            //Vector<Vector<Integer>> Qs = new Vector<Vector<Integer>>();
            int total_kids = 0;
            Vector<LinkedList<Integer>>Qs = new Vector<LinkedList<Integer>>();
            PriorityQueue<Integer> pQueue =new PriorityQueue<Integer>();
            for(int q =0; q<Q; q++){
                LinkedList<Integer> chocolate_demand = new LinkedList<Integer>();
                int no_kids = s.nextInt();
                total_kids+=no_kids;
                for(int kids = 0; kids<no_kids; kids++)
                    chocolate_demand.add(s.nextInt());
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