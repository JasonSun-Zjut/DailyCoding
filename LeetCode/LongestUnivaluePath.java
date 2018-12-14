import javax.swing.tree.TreeNode;

/**
 * @author SJ
 * @date 2018/12/14
 *
 *
 *
 * @des
 * Given a binary tree,find the length of the longest path where each node in the path has the same value.
 * This path may or may not pass through the root.
 * The length of path between two nodes is represented by the number of edges between them
 *
 *               5
 *              / \
 *             4   5
 *            / \   \
 *           1   1   5
 *
 *           return 2
 */
public class LongestUnivaluePath {
    int length = 0;
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    public int longestUnivaluePath(TreeNode root) {
        helper(root);
        return length;
    }
    private int helper(TreeNode root){
        if(root == null){
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        left = (root.left!=null&&root.val==root.left.val)?left+1:0;
        right = (root.right!=null&&root.val==root.right.val)?right+1:0;
        length = Math.max(length,left+right);
        return Math.max(left,right);
    }
}
