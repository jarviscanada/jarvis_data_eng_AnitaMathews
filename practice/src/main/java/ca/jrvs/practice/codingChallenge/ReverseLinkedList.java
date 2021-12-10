package ca.jrvs.practice.codingChallenge;

// solution works with online judge
// write test cases with JUnit

public class ReverseLinkedList {
    /**
     * Definition for singly-linked list.
     */
     public class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }
      }

    /**
     * Big-O: O(n) because each Node is visited only once
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        return reverseListRecursive(head, null);
        // return reverseListIterative(head, null);

    }

    private ListNode reverseListRecursive(ListNode currNode, ListNode prevNode) {
        if (currNode == null) { return prevNode; }
        else {
            ListNode nextNode = currNode.next;
            currNode.next = prevNode;
            return reverseListRecursive(nextNode, currNode);
        }
    }

    private ListNode reverseListIterative(ListNode currNode, ListNode prevNode) {
        while (currNode != null) {
            ListNode nextNode = currNode.next;
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = nextNode;
        }
        return prevNode;
    }

}
