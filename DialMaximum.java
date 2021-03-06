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
    // greedy approach will fail once two (src+dest)/wt are very close
    private static int dialGreedy(ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts, int money){
        int prevEdgeIndex = -1;
        int noDials = 10;
        int [][] dialArr = new int[money+1][noDials];
        BooleanHolder  flip = new BooleanHolder(false);
        return greedyDialMax(wts, money,prevEdgeIndex,dialArr, flip);
    }
    private static int dpDialMax(ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts, int money, int prevEdgeIndex, int [][] arr, BooleanHolder flip){
        
        for(int i = 0 ; i < money+1; i++){
            for ( int j =0; j< 10; j++){
                // Money is i and current chosen dial is j. We assume that we already chose the j index.
                // Be sure to add j in the final arr, since first choosing of j is costless.
               for(int k = 0; k<wts.size(); k++){
                    flip.value = false;
                    if(i-wts.get(k).getValue() >=0 && checkEdge(j, wts.get(k).getKey(), flip)){
                        if(flip.value){
                            int residualValue= arr[i-wts.get(k).getValue()][wts.get(k).getKey().getKey()];
                            if(arr[i][j] < wts.get(k).getKey().getKey() + residualValue){
                                arr[i][j] = wts.get(k).getKey().getKey() + residualValue;
                            }
                        }
                        else{
                            int residualValue= arr[i-wts.get(k).getValue()][wts.get(k).getKey().getValue()];
                            if(arr[i][j] < wts.get(k).getKey().getValue() + residualValue){
                                arr[i][j] = wts.get(k).getKey().getValue() + residualValue;
                            }
                        }
                    }
                }
            }
        }
        for (int count = 0; count <10; count++)
            arr[money][count]+=count; 
        //System.out.println(Arrays.toString(arr[money]));
        return Arrays.stream(arr[money]).max().getAsInt();  
    }

    private static int greedyDialMax(ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts, int money, int prevEdgeIndex, int [][] arr, BooleanHolder flip){
        int finalvalue = 0;
        int wt = 0;// Highest weight's index
        while(true)
        {
            flip.value = false;
            if(prevEdgeIndex ==-1){

                if (money>=0)
                {
                    finalvalue += wts.get(wt).getKey().getValue();
                }
                prevEdgeIndex = wts.get(wt).getKey().getValue();
            }
            else if (money-wts.get(wt).getValue() >=0 && checkEdge(prevEdgeIndex, wts.get(wt).getKey(), flip)){
                if(flip.value){
                    money -= wts.get(wt).getValue();
                    finalvalue += wts.get(wt).getKey().getKey();
                    prevEdgeIndex= wts.get(wt).getKey().getKey();
                }
                else{
                    money -= wts.get(wt).getValue();
                    finalvalue += wts.get(wt).getKey().getValue();
                    prevEdgeIndex= wts.get(wt).getKey().getValue();
                }

            }
            else{
                wt++;
            }
            if(money == 0 || wt ==wts.size())
                break;
        }
 
        return finalvalue;
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

    private static int dialDP(ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts, int money){
        int prevEdgeIndex = -1;
        int noDials = 10;
        int [][] dialArr = new int[money+1][noDials];
        BooleanHolder  flip = new BooleanHolder(false);
        return dpDialMax(wts, money,prevEdgeIndex,dialArr, flip);
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
        int money = 10000;
        int x , y ,w;
        ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts = new ArrayList<>(12);
        wts.add(new Pair<>(new  Pair<>(1,2),1));
        wts.add(new Pair<>(new  Pair<>(1,4),5793));
        wts.add(new Pair<>(new  Pair<>(4,7),37));
        wts.add(new Pair<>(new  Pair<>(2,3),8));
        wts.add(new Pair<>(new  Pair<>(2,5),6));
        wts.add(new Pair<>(new  Pair<>(5,8),11));
        wts.add(new Pair<>(new  Pair<>(3,6),31));
        wts.add(new Pair<>(new  Pair<>(6,9),10));
        wts.add(new Pair<>(new  Pair<>(4,5),3));
        wts.add(new Pair<>(new  Pair<>(5,6),5));
        wts.add(new Pair<>(new  Pair<>(7,8),7979));
        wts.add(new Pair<>(new  Pair<>(8,9),22));
        /*
        int money = 15;
        int x , y ,w;
        ArrayList<Pair<Pair <Integer, Integer>,Integer>> wts = new ArrayList<>(12);
        wts.add(new Pair<>(new  Pair<>(1,2),1));
        wts.add(new Pair<>(new  Pair<>(1,4),1));
        wts.add(new Pair<>(new  Pair<>(4,7),1));
        wts.add(new Pair<>(new  Pair<>(2,3),1));
        wts.add(new Pair<>(new  Pair<>(2,5),1));
        wts.add(new Pair<>(new  Pair<>(5,8),1));
        wts.add(new Pair<>(new  Pair<>(3,6),1));
        wts.add(new Pair<>(new  Pair<>(6,9),1));
        wts.add(new Pair<>(new  Pair<>(4,5),1));
        wts.add(new Pair<>(new  Pair<>(5,6),1));
        wts.add(new Pair<>(new  Pair<>(7,8),1));
        wts.add(new Pair<>(new  Pair<>(8,9),1));
        */
        // Sorting the wts only matter for greedy problem. Inconsequential for memoization and DP
        //Sort the wts in deceasing order of sum (src+dest) to take care of boundary cases when ratio is same.Longer edges preferred
        Collections.sort(wts, (first,second) -> second.getKey().getKey() + second.getKey().getValue() - first.getKey().getKey() - first.getKey().getValue());
        // sorting the wts in decreasing order of (src+dest)/w to be used for greedy approach
        Collections.sort(wts, (first,second) -> Float.compare((float)(second.getKey().getKey() + second.getKey().getValue())/second.getValue(), (float)(first.getKey().getKey() + first.getKey().getValue())/first.getValue()));
        
        System.out.println(wts);
        Instant startInstant1 = Instant.now();
        //Memoization can throw stack over flow despite memoization in top down approach. Java doesnt allow more than 1000's stack calls.
		int maximum1 = dialMaximum(wts, money); 
        Instant stopInstant1 = Instant.now();
        Instant startInstant2 = Instant.now();
        int maximum2 = dialDP(wts, money);
        Instant stopInstant2 = Instant.now();
        Instant startInstant3 = Instant.now();
        int maximum3 = dialGreedy(wts, money); 
        Instant stopInstant3 = Instant.now();
        System.out.println("Maximum: "+maximum1+ " Time taken By Memoization: " + Duration.between( startInstant1 , stopInstant1 ).toMillis()/1000d + "  seconds / " +Duration.between( startInstant1 , stopInstant1 ).toNanos()+" ns");
        System.out.println("Maximum: "+maximum2+ " Time taken By DP: " + Duration.between( startInstant2 , stopInstant2 ).toMillis()/1000d + "  seconds / " +Duration.between( startInstant2 , stopInstant2 ).toNanos()+" ns");
        System.out.println("Maximum: "+maximum2+ " Time taken By Greedy: " + Duration.between( startInstant2 , stopInstant2 ).toMillis()/1000d + "  seconds / " +Duration.between( startInstant2 , stopInstant2 ).toNanos()+" ns");
    }
}
