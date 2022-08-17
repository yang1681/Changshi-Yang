package week3.hw1;

import java.util.Map;

public class ValueComparator <String>{
    Map<String,Integer> s;


   public ValueComparator(Map<String,Integer> s){
        this.s=s;
    }

    public int compare(String a,String b){
        if(s.get(a)>=s.get(b)){
            return -1;
        }else{
            return 1;
        }
    }

}
