import java.util.*;


public class SkipList {

    private static class SkipListNode{

        int val;
        SkipListNode next;
        SkipListNode down;

        SkipListNode(int val,SkipListNode next,SkipListNode down){
            this.val=val;
            this.next=next;
            if(down==null)
                this.down=this;
            else
                this.down=down;
        }

    }

    private static final int MAX_LEVEL=32;
    private final SkipListNode[] head;
    private int level;

    public SkipList(){
        this.head=new SkipListNode[MAX_LEVEL];
        head[0]=new SkipListNode(Integer.MIN_VALUE,null,null);
        for(int i=1;i<MAX_LEVEL;i++){
            head[i]=new SkipListNode(Integer.MIN_VALUE,null,head[i-1]);
        }
        this.level=1;
    }


    public void add(int val){
        SkipListNode[] pre=Arrays.copyOf(head,MAX_LEVEL);
        SkipListNode cur=head[level-1];
        //寻找每层值小于val的最后一个节点
        for(int i=level-1;i>=0;i--){
            //寻找值小于val的最后一个节点
            while(cur.next!=null&&cur.next.val<val){
                cur=cur.next;
            }
            pre[i]=cur;
            //进入下一层
            cur=cur.down;
        }
        //val已存在则返回
        if(cur.next!=null&&cur.next.val==val)
            return;
        int lv=randomLevel();
        //更新层数
        level=Math.max(lv,level);
        //插入节点
        pre[0].next=new SkipListNode(val,pre[0].next,null);
        for(int i=1;i<lv;i++){
            pre[i].next=new SkipListNode(val,pre[i].next,pre[i-1].next);
        }
    }


    public void remove(int val){
        SkipListNode[] pre=Arrays.copyOf(head,MAX_LEVEL);
        SkipListNode cur=head[level-1];
        //寻找每层值小于val的最后一个节点
        for(int i=level-1;i>=0;i--){
            //寻找值小于val的最后一个节点
            while(cur.next!=null&&cur.next.val<val){
                cur=cur.next;
            }
            pre[i]=cur;
            //进入下一层
            cur=cur.down;
        }
        //删除节点
        for(int i=0;pre[i].next!=null&&pre[i].next.val==val;i++){
            pre[i].next=pre[i].next.next;
        }
        //更新层数
        while(level>1&&head[level-1].next==null){
            level--;
        }
    }

    public List<Integer> range(int start,int end){
        List<Integer> res=new ArrayList<>();
        SkipListNode cur=head[level-1];
        //寻找第一层值小于val的最后一个节点
        for(int i=level-1;i>=0;i--){
            while(cur.next!=null&&cur.next.val<start){
                cur=cur.next;
            }
            cur=cur.down;
        }
        //cur.next为第一层值大于等于val的第一个节点
        cur=cur.next;
        //返回满足值小于end的节点
        while(cur!=null&&cur.val<=end){
            res.add(cur.val);
            cur=cur.next;
        }
        return res;
    }

    public boolean contains(int target){
        SkipListNode cur=head[level-1];
        //寻找第一层值小于val的最后一个节点
        for(int i=level-1;i>=0;i--){
            while(cur.next!=null&&cur.next.val<target){
                cur=cur.next;
            }
            cur=cur.down;
        }
        //判断next节点值是不是val
        return cur.next!=null&&cur.next.val==target;
    }


    private int randomLevel(){
        int level=1;
        while(Math.random()<0.25&&level<MAX_LEVEL){
            level++;
        }
        return level;
    }

}
