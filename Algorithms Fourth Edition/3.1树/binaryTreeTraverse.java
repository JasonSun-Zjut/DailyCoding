import java.util.LinkedList;
import java.util.Stack;

/**
 * @author
 * @date /11/12
 */
public class binaryTreeTraverse {
    static class TreeNode {
        private int val;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private TreeNode parent;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode() {
        }
    }
    //前序遍历
    private void PreTraverse(TreeNode head) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.println(node.val);
            if (node.rightChild != null) {
                stack.push(node.rightChild);
            }
            if (node.leftChild != null) {
                stack.push(node.leftChild);
            }
        }
    }
    //中序遍历
    private void InTraverse(TreeNode head) {
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.leftChild;
            } else {
                head = stack.pop();
                System.out.println(head.val);
                head = head.rightChild;
            }
        }
    }
    //后序遍历
    private void OffTraverse(TreeNode head) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> vals = new Stack<>();
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            vals.push(node.val);
            if (node.leftChild != null) {
                stack.push(node.leftChild);
            }
            if (node.rightChild != null) {
                stack.push(node.rightChild);
            }
        }
        while (!vals.isEmpty()) {
            System.out.println(vals.pop().intValue());
        }
    }
    //层次遍历
    public void LaywerTraversal(TreeNode head) {
        if (head == null) {
            return;
        }
        LinkedList<TreeNode> nodes = new LinkedList<>();
        TreeNode node = null;
        nodes.add(head);
        while (!nodes.isEmpty()) {
            node = nodes.poll();
            System.out.println(node.val);
            if (node.leftChild != null) {
                nodes.add(node.leftChild);
            } else {
                nodes.add(new TreeNode());
            }
            if (node.rightChild != null) {
                nodes.add(node.rightChild);
            } else {
                nodes.add(new TreeNode());
            }
        }
    }
    //指定节点的后继节点
    public TreeNode FindOffNode(TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.rightChild != null) {
            TreeNode cur = node.rightChild;
            while (cur.leftChild != null) {
                cur = cur.leftChild;
            }
            return cur;
        } else {
            TreeNode parent = node.parent;
            while (parent != null && parent.leftChild != node) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }
    //指定节点的前驱结点
    public TreeNode FindPreNode(TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.leftChild != null) {
            TreeNode cur = node.leftChild;
            while (cur.rightChild != null) {
                cur = cur.rightChild;
            }
            return cur;
        } else {
            TreeNode parent = node.parent;
            while (parent != null && parent.rightChild != node) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }
}
