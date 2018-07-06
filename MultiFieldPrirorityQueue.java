
import java.util.*;
import java.util.Vector;
import java.util.stream.*;

public class Entry implements Comparable<Entry> {
    private int key;
    private int value;

    public Entry(int key, int value) {
        this.key = key;
        this.value = value;
    }

    // getters
    public int getValue(){
        return this.value;
    }
    @Override
    public String toString() { 
    return "Key: '" + this.key + "', Value: '" + this.value + "'";
    } 

    @Override
    public int compareTo(Entry other) {
        Integer value1= this.getValue();
        Integer value2= other.getValue();
        return value1.compareTo(value2);
    }
    //anonymous class type
   public static Comparator<Entry> ValueComparator 
                          = new Comparator<Entry>() {

        public int compare(Entry first, Entry second) {
            
          Integer value1= first.getValue();
          Integer value2= second.getValue();
          
          //ascending order
          return value1.compareTo(value2);
          
          //descending order
          //return value2.compareTo(value1);
        }

    };
}
// separate class type
class Checker implements Comparator<Entry>
 {
    public int compare(Entry first, Entry second) {
            
          Integer value1= first.getValue();
          Integer value2= second.getValue();
          
          //ascending order
          return value1.compareTo(value2);
          
          //descending order
          //return value2.compareTo(value1);
        }
 }
 

class TestClass {
    public static void main(String args[] ) throws Exception {
    Entry e1 = new Entry(10,5);
    Entry e2 = new Entry(20, 7);
    Entry e3 = new Entry(15, 4);
    Entry e4 = new Entry(5, 2);
    Entry e5 = new Entry(2, 1);
    // Default comparable
    //PriorityQueue<Entry> q = new PriorityQueue<>(5);
    // Anonymous class comparator as  static member inside class
    //PriorityQueue<Entry> q = new PriorityQueue<>(5,Entry.ValueComparator);
    // Lambda function
    //PriorityQueue<Entry> q = new PriorityQueue<>(5,(first,second) -> first.getValue() - second.getValue());
    //Method reference
    //PriorityQueue<Entry> q  = new PriorityQueue<Entry>(5, Comparator.comparing(Entry::getValue));
    // Creating a separate class for comparator
    //PriorityQueue<Entry> q = new PriorityQueue<>(5, new Checker());
    //Anonynous class right here
    PriorityQueue<Entry> q = new PriorityQueue<Entry>(5, new Comparator<Entry> () {
        @Override
        public int compare(Entry first, Entry second) {
             Integer value1= first.getValue();
              Integer value2= second.getValue();
              
              //ascending order
              return value1.compareTo(value2);
              
              //descending order
              //return value2.compareTo(value1);
        }});

    q.add(e1);
    q.add(e2);
    q.add(e3);
    q.add(e4);
    q.add(e5);
    while (q.size() != 0)
        {
            System.out.println(q.remove());
        }
    }
}