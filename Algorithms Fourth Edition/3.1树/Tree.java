import java.util.LinkedList;
import java.util.Stack;

/**
 * @author
 * @date /12/02
 */
public class Tree {
    private static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public void preTraverse(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.println(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public void inTraverse(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.println(root.val);
                root = root.right;
            }
        }
    }

    public void postTraverse(TreeNode root){
        Stack<TreeNode> nodes = new Stack<>();
        Stack<Integer> values = new Stack<>();
        nodes.push(root);
        while(!nodes.isEmpty()){
            TreeNode node = nodes.pop();
            values.push(node.val);
            if(node.left!=null){
                nodes.push(node.left);
            }
            if(node.right!=null){
                nodes.push(node.right);
            }
        }
        while(!values.isEmpty()){
            System.out.println(values.pop());
        }
    }

    public void levelTravese(TreeNode root){
        LinkedList<TreeNode> lists = new LinkedList<>();
        lists.add(root);
        while (!lists.isEmpty()){
            TreeNode node = lists.pop();
            System.out.println(node.val);
            if(node.left!=null){
                lists.add(node.left);
            }
            if(node.right!=null){
                lists.add(node.right);
            }
        }
    }

    /**
     * 判断是否是平衡二叉树
     * */
    private static class ResultData{
        private boolean isBalance;
        private int height;
        public ResultData(boolean isBalance, int height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }
    public boolean isBalanceTree(TreeNode root){
        return process(root).isBalance;
    }
    private ResultData process(TreeNode node){
        if(node == null){
           return new ResultData(true,0);
        }
        ResultData leftResultData = process(node.left);
        if(!leftResultData.isBalance){
            return leftResultData;
        }
        ResultData rightResultData = process(node.right);
        if(!rightResultData.isBalance){
            return rightResultData;
        }
        if(Math.abs(leftResultData.height-rightResultData.height)>1){
            return new ResultData(false,0);
        }
        return new ResultData(true,Math.max(leftResultData.height,rightResultData.height)+1);
    }
    /**
     * 判断是否是一棵搜索树
     * */
    public boolean isSearchTree(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        int pre = Integer.MIN_VALUE;
        while(!stack.isEmpty()||root!=null){
            if(root!=null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if(root.val<pre){
                    return false;
                }
                pre = root.val;
                root = root.right;
            }
        }
        return true;
    }
    /**
     * 是否是完全二叉树
     * */
    public boolean isCompleteTree(TreeNode root){
        LinkedList<TreeNode> lists = new LinkedList<>();
        boolean isleaf=false;
        lists.add(root);
        TreeNode left = null;
        TreeNode right = null;
        while(!lists.isEmpty()){
            TreeNode node = lists.pop();
           left = node.left;
           right = node.right;
           if((left==null&&right!=null)||(isleaf&&(left!=null||right!=null))){
               return false;
           }
           if(left!=null){
               lists.add(left);
           }
           if(right!=null){
               lists.add(right);
           }else {
               isleaf=true;
           }
        }
        return true;
    }
}
