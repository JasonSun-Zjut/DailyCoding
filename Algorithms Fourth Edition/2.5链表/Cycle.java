/**
 * @author SJ
 * @date /11/06
 */
public class Cycle {
    static class ListNode {
        private int val;
        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    //得到两条链表首次相交的节点
    public static ListNode getCorrectNode(ListNode head1, ListNode head2) {
        ListNode c1 = getCorrectNode(head1);//判断node1链表有没有环，有则返回环节点
        ListNode c2 = getCorrectNode(head2);//判断node2链表有没有环，有则返回环节点

        //链条链表都不含有环
        if (c1 == null && c2 == null) {
            return NoLoopCorrectNode(head1, head2);
        }
        //两个链表共享一个环
        if (c1 != null && c2 != null) {
            return BothLoopCorrectNode(head1, head2, c1, c2);
        }
       return null;
    }

    //获取环节点
    public static ListNode getCorrectNode(ListNode head) {
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (fast != slow) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
    //两条无环的链表得到第一个相交点
    public static ListNode NoLoopCorrectNode(ListNode head1, ListNode head2) {
        ListNode node1 = head1;
        ListNode node2 = head2;
        int length = 0;
        while (node1.next != null) {
            length++;
            node1 = node1.next;
        }
        while (node2.next != null) {
            length--;
            node2 = node2.next;
        }
        if (node1 != node2) {
            return null;
        }
        node1 = length > 0 ? head1 : head2;
        node2 = node1 == head1 ? head2 : head1;
        while (length != 0) {
            node1 = node1.next;
            length--;
        }
        while (node1 != node2) {
            node1 = node1.next;
            node2 = node2.next;
        }
        return node1;
    }
    //两个都含有环的链表
    private static ListNode BothLoopCorrectNode(ListNode head1, ListNode head2, ListNode c1, ListNode c2) {
        ListNode node1 = head1;
        ListNode node2 = head2;
        //如果环节点相同
        if (c1 == c2) {
            int length = 0;
            while (node1.next != c1.next) {
                length++;
                node1 = node1.next;
            }
            while (node2.next != c1.next) {
                length--;
                node2 = node2.next;
            }
            if (node1 != node2) {
                return null;
            }
            node1 = length > 0 ? head1 : head2;
            node2 = node1 == head1 ? head2 : head1;
            length=Math.abs(length);
            while (length != 0) {
                node1 = node1.next;
                length--;
            }
            while (node1 != node2) {
                node1 = node1.next;
                node2 = node2.next;
            }
            return node1;
        }
        //环节点不同
        else {
            node1 = c1.next;
            while (node1 != c1) {
                if (node1 == c2) {
                    return c2;
                }
                node1 = node1.next;
            }
            return null;
        }
    }
}
