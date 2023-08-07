import java.util.*;


public class Sort {

    public static void quicksort(int[] nums,int l,int r){
        if(l>=r)
            return;
        int a=l;
        int b=r;
        int temp=nums[l];
        //调整，小于放左边，大于放右边，等于不操作
        while(a<b){
            //从右边开始找第一个小于temp的值
            while(a<b&&temp<=nums[b]){
                b--;
            }
            //小于temp的值放左边
            nums[a]=nums[b];
            //从左边开始找第一个大于temp的值
            while(a<b&&nums[a]<=temp){
                a++;
            }
            //大于temp的值放右边
            nums[b]=nums[a];
        }
        //中间位置放temp
        nums[a]=temp;
        //递归调整
        quicksort(nums,l,a-1);
        quicksort(nums,a+1,r);
    }

    public static void heapsort(int[] nums){
        //从下往上调整成大顶堆
        for(int i=nums.length/2-1;i>=0;i--){
            adjust(nums,i,nums.length);
        }
        //不断移除堆顶元素放入数组末尾
        for(int i=nums.length-1;i>=0;i--){
            //交换堆顶和堆底元素
            int temp=nums[0];
            nums[0]=nums[i];
            nums[i]=temp;
            //移除堆底元素并调整为大顶堆
            adjust(nums,0,i);
        }
    }

    /**
     * 向下调整为大顶堆，需要左右子堆均为大顶堆
     * @param nums 堆数组
     * @param i 堆顶下标
     * @param j 堆边界
     */
    private static void adjust(int[] nums,int i,int j){
        //左子堆
        int l=2*i+1;
        //左右子堆已被移除
        if(l>=j)
            return;
        //取左右子堆较大的
        if(l+1<j&&nums[l]<nums[l+1]){
            l++;
        }
        //已经是大顶堆
        if(nums[i]>=nums[l])
            return;
        //交换
        int temp=nums[i];
        nums[i]=nums[l];
        nums[l]=temp;
        //继续向下调整
        adjust(nums,l,j);
    }

}

