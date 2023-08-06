import java.util.*;


public class RedBlackTree {

    private static class RedBlackTreeNode{
        int val;
        boolean color;
        RedBlackTreeNode left;
        RedBlackTreeNode right;
        RedBlackTreeNode parent;

        RedBlackTreeNode(int val){
            this.val=val;
        }
    }

    private static final boolean RED=false;
    private static final boolean BLACK=true;
    private RedBlackTreeNode root;

    public void add(int val){
        if(root==null){
            root=new RedBlackTreeNode(val);
            root.color=BLACK;
            return;
        }
        //寻找插入节点父节点
        RedBlackTreeNode node=root;
        RedBlackTreeNode parent=root;
        while (node!=null){
            if(node.val==val)
                return;
            if(node.val<val) {
                parent=node;
                node = node.right;
            }
            else {
                parent=node;
                node = node.left;
            }
        }
        //插入节点
        RedBlackTreeNode insertNode=new RedBlackTreeNode(val);
        insertNode.parent=parent;
        if(parent.val<val)
            parent.right=insertNode;
        else
            parent.left=insertNode;
        //调整
        fixAfterInsert(insertNode);
    }

    public void remove(int val) {
        //寻找删除节点
        RedBlackTreeNode deleteNode = root;
        while (deleteNode != null) {
            if (deleteNode.val == val)
                break;
            if (deleteNode.val < val)
                deleteNode = deleteNode.right;
            else
                deleteNode = deleteNode.left;
        }
        //节点不存在
        if (deleteNode == null)
            return;
        //有两个子节点，找到后继节点作为删除节点，后继节点最多一个子节点
        if (deleteNode.left != null && deleteNode.right != null) {
            RedBlackTreeNode next = deleteNode.right;
            while (next.left != null) {
                next = next.left;
            }
            deleteNode.val = next.val;
            deleteNode = next;
        }
        //子节点作为代替节点
        RedBlackTreeNode replace = deleteNode.left == null ? deleteNode.right : deleteNode.left;
        //一个子节点，用子节点代替节点，子节点设置为黑
        if (replace != null) {
            replace.parent = deleteNode.parent;
            if (deleteNode.parent == null)
                root = replace;
            else if (deleteNode == deleteNode.parent.left)
                deleteNode.parent.left = replace;
            else
                deleteNode.parent.right = replace;
            replace.color = BLACK;
        }
        //没有子节点
        else {
            //调整
            fixBeforeDelete(deleteNode);
            //删除节点
            if(deleteNode.parent==null)
                root=null;
            if(deleteNode==deleteNode.parent.left)
                deleteNode.parent.left=null;
            else
                deleteNode.parent.right=null;
        }
    }

    public boolean contains(int target){
        RedBlackTreeNode node=root;
        while(node!=null){
            if(node.val==target)
                return true;
            if(node.val<target)
                node=node.right;
            else
                node=node.left;
        }
        return false;
    }

    private void fixAfterInsert(RedBlackTreeNode node){
        //不为根节点且父节点为红
        while(node!=root&&node.parent.color==RED){
            //父节点为左节点
            if(node.parent==node.parent.parent.left){
                RedBlackTreeNode uncle=node.parent.parent.right;
                //叔叔节点为红，父节点叔叔节点变黑，爷爷节点变红
                if(uncle!=null&&uncle.color==RED){
                    node.parent.color=BLACK;
                    uncle.color=BLACK;
                    uncle.parent.color=RED;
                    node=node.parent.parent;
                }
                //叔叔节点为黑
                else{
                    //当前节点为右节点，左旋父节点，当前节点变为左节点
                    if(node==node.parent.right){
                        node=node.parent;
                        leftRotate(node);
                    }
                    //当前节点为左节点，右旋爷爷节点
                    node.parent.color=BLACK;
                    node.parent.parent.color=RED;
                    rightRotate(node.parent.parent);
                }
            }
            //父节点为右节点
            else{
                RedBlackTreeNode uncle=node.parent.parent.left;
                ///叔叔节点为红，父节点叔叔节点变黑，爷爷节点变红
                if(uncle!=null&&uncle.color==RED){
                    node.parent.color=BLACK;
                    uncle.color=BLACK;
                    uncle.parent.color=RED;
                    node=node.parent.parent;
                }
                //叔叔节点为黑
                else{
                    //当前节点为左节点，右旋父节点，当前节点变为右节点
                    if(node==node.parent.left){
                        node=node.parent;
                        rightRotate(node);
                    }
                    //当前节点为右节点，左旋爷爷节点
                    node.parent.color=BLACK;
                    node.parent.parent.color=RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        //根节点有可能变红，设置为黑
        root.color=BLACK;
    }

    private void fixBeforeDelete(RedBlackTreeNode node){
        //不为根节点且颜色为黑
        while(node!=root&&node.color==BLACK){
            //当前节点为左节点
            if(node==node.parent.left){
                RedBlackTreeNode brother=node.parent.right;
                //兄弟节点为红，左旋父节点，兄弟节点变为黑
                if(brother.color==RED){
                    node.parent.color= RED;
                    brother.color=BLACK;
                    leftRotate(node.parent);
                    brother=node.parent.right;
                }
                //兄弟节点无红色子节点
                if((brother.left==null||brother.left.color==BLACK)&&(brother.right==null||brother.right.color==BLACK)){
                    brother.color=RED;
                    node=node.parent;
                }
                //兄弟节点有红色子节点
                else{
                    //兄弟节点左节点为红,右旋兄弟节点，兄弟节点右节点变红
                    if(brother.right==null||brother.right.color==BLACK){
                        brother.left.color=BLACK;
                        brother.color=RED;
                        rightRotate(brother);
                        brother=node.parent.right;
                    }
                    //兄弟节点右节点为红，左旋父节点
                    brother.color=node.parent.color;
                    brother.right.color=BLACK;
                    node.parent.color=BLACK;
                    leftRotate(node.parent);
                    return;
                }
            }
            //当前节点为右节点
            else{
                RedBlackTreeNode brother=node.parent.left;
                //兄弟节点为红，右旋父节点，兄弟节点变黑
                if(brother.color==RED){
                    node.parent.color= RED;
                    brother.color=BLACK;
                    rightRotate(node.parent);
                    brother=node.parent.left;
                }
                //兄弟节点无红色子节点
                if((brother.left==null||brother.left.color==BLACK)&&(brother.right==null||brother.right.color==BLACK)){
                    brother.color=RED;
                    node=node.parent;
                }
                //兄弟节点有红色子节点
                else{
                    //兄弟节点右节点为红,左旋兄弟节点，兄弟节点左节点变红
                    if(brother.left==null||brother.left.color==BLACK){
                        brother.right.color=BLACK;
                        brother.color=RED;
                        leftRotate(brother);
                        brother=node.parent.left;
                    }
                    //兄弟节点左节点为红，右旋父节点
                    brother.color=node.parent.color;
                    brother.left.color=BLACK;
                    node.parent.color=BLACK;
                    rightRotate(node.parent);
                    return;
                }
            }
        }
        //当前节点设置为黑
        node.color=BLACK;
    }


    private void leftRotate(RedBlackTreeNode node){
        RedBlackTreeNode right=node.right;
        node.right=right.left;
        if(right.left!=null)
            right.left.parent=node;
        right.parent=node.parent;
        if(node.parent==null)
            root = right;
        else if(node.parent.left==node)
            node.parent.left=right;
        else
            node.parent.right=right;
        right.left=node;
        node.parent=right;
    }

    private void rightRotate(RedBlackTreeNode node){
        RedBlackTreeNode left=node.left;
        node.left=left.right;
        if(left.right!=null)
            left.right.parent=node;
        left.parent=node.parent;
        if(node.parent==null)
            root=left;
        else if(node.parent.left==node)
            node.parent.left=left;
        else
            node.parent.right=left;
        left.right=node;
        node.parent=left;
    }

}



