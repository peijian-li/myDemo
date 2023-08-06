import java.util.*;


public class Dijkstra{

    public static int[] dijkstra(int n,int k,int[][] edges){
        //数组存储每个节点到k的最短距离
        int[] distance=new int[n];
        //初始化为最大值，便于比较
        Arrays.fill(distance,Integer.MAX_VALUE);
        //迪杰斯特拉为贪心BFS,利用优先队列存储更新过距离的节点，节点每次更新距离会被加入队列，旧值采用懒删除策略
        PriorityQueue<int[]> pq=new PriorityQueue<>((o1, o2)->o1[1]-o2[1]);
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
        //k到k的最短距离为0，k加入优先队列
        distance[k]=0;
        pq.add(new int[]{k,0});
        //BFS
        while(pq.size()>0){
            //节点一旦被移除，说明节点最短距离已求出，后续不会再被更新距离加入队列
            int[] node=pq.remove();
            //判断距离是否为最新值，是旧值则跳过
            if(distance[node[0]]==node[1]){
                //遍历相邻的边
                for(int[] edge:map.get(node[0])){
                    //更新相邻点距离，如果更新成功则加入优先队列
                    if(edge[1]+distance[node[0]]<distance[edge[0]]){
                        distance[edge[0]]=edge[1]+distance[node[0]];
                        pq.add(new int[]{edge[0],distance[edge[0]]});
                    }
                }
            }
        }
        return distance;
    }

}