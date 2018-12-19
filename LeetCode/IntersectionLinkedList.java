package Code;

/**
 * @author
 * @date /12/19
 * @des
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 */
public class IntersectionLinkedList {
    static class ListNode{
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
    /***
     * 获取两条链表的长度差后，通过移动长链表的游标。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB){
          int lengthA = length(headA);
          int lengthB = length(headB);
          while(lengthA>lengthB){
              headA = headA.next;
              lengthA--;
          }
          while(lengthA<lengthB){
              headB = headB.next;
              lengthB--;
          }

          while (headA!=headB){
              headA = headA.next;
              headB = headB.next;
          }
          return headA;
    }

    private int length(ListNode node){
        int length = 0;
        while(node!=null){
            node = node.next;
            length++;
        }
        return length;
    }

   /**
    * 通过一次迭代来消除两条链表之间的差距
    * */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        ListNode A = headA;
        ListNode B = headB;
        while(A!=B){
            A = A==null?headB:A.next;
            B = B==null?headA:B.next;
        }
        return A;
    }
}
