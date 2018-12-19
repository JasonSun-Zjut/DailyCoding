package Code;


/**
 * @author SJ
 * @date 2018/12/19
 * @des
 * Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
 * Given the sorted linked list: [-10,-3,0,5,9],
 * One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 */
public class ConvertSortedListtoBinarySearchTree {
    static class ListNode{
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
    public TreeNode sortedListToBST(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode mid = findMidNode(head);
        TreeNode root = new TreeNode(mid.val);
        if(head==mid){
            return root;
        }
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(mid.next);
        return root;
    }
    /**
     * 寻找中间节点，由于需要递归队两侧的链表进行构造，所以需要进行断链.
     * */
    private static ListNode findMidNode(ListNode node){
        ListNode slow = node;
        ListNode fast = node;
        ListNode midPre = null;
        while(fast!=null&&fast.next!=null){
            midPre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        if(midPre!=null){
            midPre.next = null;
        }
        return slow;
    }
}
