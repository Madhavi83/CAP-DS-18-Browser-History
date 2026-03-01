public class TabLinkedList {

    class Node {
        String url;
        Node prev;
        Node next;

        Node(String url) {
            this.url = url;
        }
    }

    private Node head;
    private Node tail;

    public void addTab(String url) {
        Node newNode = new Node(url);

        if (head == null) {
            head = tail = newNode;
            return;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    public void closeTab(String url) {
        Node current = head;

        while (current != null) {
            if (current.url.equals(url)) {

                if (current.prev != null)
                    current.prev.next = current.next;
                else
                    head = current.next;

                if (current.next != null)
                    current.next.prev = current.prev;
                else
                    tail = current.prev;

                return;
            }
            current = current.next;
        }
    }

    public void displayTabs() {
        if (head == null) {
            System.out.println("No tabs open.");
            return;
        }

        Node current = head;
        System.out.println("Open Tabs:");
        while (current != null) {
            System.out.println(current.url);
            current = current.next;
        }
    }

    public String getCurrentTab() {
        if (tail == null)
            return null;
        return tail.url;
    }
}