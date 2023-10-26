import java.util.*;


public class Dijkstra{

    public static int[] dijkstra(int n,int k,int[][] edges){
        //数组存储每个节点到k的最短距离,初始化为-1
        int[] distance=new int[n];
        Arrays.fill(distance,-1);
        //迪杰斯特拉为贪心BFS,利用优先队列存储相邻节点和节点到k最短距离
        PriorityQueue<int[]> pq=new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        //邻接表存储图
        Map<Integer, List<int[]>> map=new HashMap<>();
        //初始化邻接表
        for(int i=0;i<n;i++){
            map.put(i,new ArrayList<>());
        }
        for(int[] edge:edges){
            map.get(edge[0]).add(new int[]{edge[1],edge[2]});
            map.get(edge[1]).add(new int[]{edge[0],edge[2]});
        }
        //BFS
        pq.add(new int[]{k,0});
        while(pq.size()>0){
            //相邻节点中到k距离最短的节点
            int[] node=pq.remove();
            //节点到k最短距离已经被求出，懒删除
            if(distance[node[0]]>-1){
                continue;
            }
            //求出节点到k最短距离
            distance[node[0]]=node[1];
            //将相邻节点和节点经过node到k最短距离加入优先队列
            if(map.get(node[0])==null){
                continue;
            }
            for(int[] next:map.get(node)){
                pq.add(new int[]{next[0],next[1]+node[1]});
            }
        }
        return distance;
    }

}