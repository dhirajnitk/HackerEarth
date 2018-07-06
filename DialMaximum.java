import java.util.*;
import javafx.util.Pair;
import java.time.*;
class BooleanHolder {
    public BooleanHolder(boolean flip){
        this.value = flip;
    }
   public boolean value;
}
class DialMaximum{
    private static boolean checkEdge(int src, Pair<Integer, Integer> dest, BooleanHolder flip){
            if(dest.getKey() == src) {
                    return true;
                }
            else if(dest.getValue()== src){
                flip.value = true;
                return true;
            }
            else{
                return false;
            }
        }
    private static int recurseFindDialMax(ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts, int money, int prevEdgeIndex, int [][] arr, BooleanHolder flip){
        if(money<0)
            return 0;
        if(prevEdgeIndex == -1){
            if(arr[money][prevEdgeIndex+1] >0)
                return arr[money][prevEdgeIndex+1];
            else{
                for(int j = 0; j<wts.size(); j++){
                    int residualValue = recurseFindDialMax(wts, money, wts.get(j).getKey().getValue(), arr, flip);
                    if(arr[money][prevEdgeIndex+1] < wts.get(j).getKey().getValue() + residualValue)
                        arr[money][prevEdgeIndex+1] = wts.get(j).getKey().getValue() + residualValue;
                }
                return arr[money][prevEdgeIndex+1];
            }
        }
        else{
            //Memoization
            if(arr[money][prevEdgeIndex]>0)
                return arr[money][prevEdgeIndex];
            else{
                for(int j = 0; j<wts.size(); j++){
                    flip.value = false;
                    int residualValue;
                    if(money-wts.get(j).getValue() >=0 && checkEdge(prevEdgeIndex, wts.get(j).getKey(), flip)){
                        if(flip.value){
                            residualValue= recurseFindDialMax(wts, money-wts.get(j).getValue() ,  wts.get(j).getKey().getKey(), arr, flip);
                            if(arr[money][prevEdgeIndex] < wts.get(j).getKey().getKey() + residualValue){
                                arr[money][prevEdgeIndex] = wts.get(j).getKey().getKey() + residualValue;
                            }
                        }
                        else{
                            residualValue= recurseFindDialMax(wts, money-wts.get(j).getValue() ,  wts.get(j).getKey().getValue(), arr, flip);
                            if(arr[money][prevEdgeIndex] < wts.get(j).getKey().getValue() + residualValue){
                                arr[money][prevEdgeIndex] = wts.get(j).getKey().getValue() + residualValue;
                            }
                        }
                    }
                }
                System.out.println(arr[money][prevEdgeIndex]);
                return arr[money][prevEdgeIndex];
            }

        }
    } 
    private static int dialMaximum(ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts, int money){
        int prevEdgeIndex = -1;
        int noDials = 10;
        int [][] dialArr = new int[money+1][noDials];
        BooleanHolder  flip = new BooleanHolder(false);
        return recurseFindDialMax(wts, money,prevEdgeIndex,dialArr, flip);
    }
    public static void main( String [] args){
        /*
        Scanner s = new Scanner(System.in);
        int money = s.nextInt();
        int x , y ,w;
        ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts = new ArrayList<>(12);
        for (int i = 0;i<12; i++){
            x = s.nextInt();
            y = s.nextInt();
            w = s.nextInt();
            Pair<Pair <Integer, Integer>,Integer> wt = new Pair<>(new  Pair<>(x,y),w);
            wts.add(wt);
        }
    	*/
        int money = 0;
        int x , y ,w;
        ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts = new ArrayList<>(12);
        wts.add(new Pair<>(new  Pair<>(1,2),2));
        wts.add(new Pair<>(new  Pair<>(1,4),10));
        wts.add(new Pair<>(new  Pair<>(4,7),10));
        wts.add(new Pair<>(new  Pair<>(2,3),10));
        wts.add(new Pair<>(new  Pair<>(2,5),10));
        wts.add(new Pair<>(new  Pair<>(5,8),10));
        wts.add(new Pair<>(new  Pair<>(3,6),10));
        wts.add(new Pair<>(new  Pair<>(6,9),10));
        wts.add(new Pair<>(new  Pair<>(4,5),10));
        wts.add(new Pair<>(new  Pair<>(5,6),10));
        wts.add(new Pair<>(new  Pair<>(7,8),10));
        wts.add(new Pair<>(new  Pair<>(8,9),10));
	    // Displaying ArrayList 
        //System.out.println(wts);
        // sorting the wts by w
        Collections.sort(wts, (first,second) -> first.getValue() - second.getValue());
        //System.out.println(wts);
        Instant startInstant = Instant.now();
		int maximum = dialMaximum(wts, money); 
        Instant stopInstant = Instant.now();
		System.out.println(maximum);
        System.out.println("Time taken: " + Duration.between( startInstant , stopInstant ).toMillis()/1000d + "  seconds / " +Duration.between( startInstant , stopInstant ).toNanos()+" ns");
    }
}
