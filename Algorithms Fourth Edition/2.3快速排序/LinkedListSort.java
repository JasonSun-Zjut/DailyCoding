/**
 * @author
 * @date /10/28
 */
public class LinkedListSort {
    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    //链表的快速排序
    public static ListNode QuickSortList(ListNode head) {
        QuickSortList(head, null);
        return head;
    }

    private static ListNode QuickSortList(ListNode head, ListNode tail) {
        if (head == tail) {
            return head;
        }
        ListNode partitionNode = partition(head, tail);
        QuickSortList(head, partitionNode);
        QuickSortList(partitionNode.next, tail);
        return head;
    }

    //链表的划分
    private static ListNode partition(ListNode head, ListNode tail) {
        ListNode lo = head;
        ListNode node = head.next;
        int midVal = head.val;
        while (node != tail) {
            if (node.val < midVal) {
                lo = lo.next;
                swap(lo, node);
            }
            node = node.next;
        }
        swap(head, lo);
        return lo;
    }

    private static void swap(ListNode i, ListNode j) {
        int temp = i.val;
        i.val = j.val;
        j.val = temp;
    }

    //链表的归并排序
    public static ListNode MergeSortList(ListNode head) {
        // write your code here
        if (head == null || head.next == null) {
            return head;
        }

        ListNode midNode = findMid(head);
        ListNode right = MergeSortList(midNode.next);
        midNode.next = null;
        ListNode left = MergeSortList(head);
        return merge(left, right);
    }

    //快慢指针寻找中间节点
    private static ListNode findMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //归并操作
    private static ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (left != null && right != null) {
            if (left.val < right.val) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }
        if (left != null) {
            tail.next = left;
        }
        if (right != null) {
            tail.next = right;
        }
        return dummy.next;
    }
}
