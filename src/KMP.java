import java.util.*;


public class KMP {

    public static int kmpOne(String s,String t){
        int m=s.length();
        int n=t.length();
        int[] next=getNext(s);
        int i=0,j=0;
        while(i<n&&j<m){
            while(j!=-1&&t.charAt(i)!=s.charAt(j)){
                j=next[j];
            }
            i++;
            j++;
        }
        //j等于m则匹配完成，否则匹配失败
        return j==m?i-m:-1;
    }

    public static List<Integer> kmpAll(String s, String t){
        List<Integer> list=new ArrayList<>();
        int m=s.length();
        int n=t.length();
        int[] next=getNext(s);
        int i=0,j=0;
        while(i<n){
            while(j!=-1&&t.charAt(i)!=s.charAt(j)){
                j=next[j];
            }
            i++;
            j++;
            //匹配完成，继续下一次匹配
            if(j==m){
                j=next[j];
                list.add(i-m);
            }
        }
        return list;
    }
    
    //模式串next数组存储匹配失败后重新匹配的起点，s.substring(0,next[i])与s.substring(i-next[i],i)相同
    private static int[] getNext(String s){
        int m=s.length();
        int[] next=new int[m+1];
        next[0]=-1;
        for(int i=1;i<=m;i++){
            int j=next[i-1];
            while(j!=-1&&s.charAt(i-1)!=s.charAt(j)){
                j=next[j];
            }
            next[i]=j+1;
        }
        return next;
    }

}