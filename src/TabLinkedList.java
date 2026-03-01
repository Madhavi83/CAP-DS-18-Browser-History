public class TabLinkedList {

    // Node class
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

    // Add tab at end
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

    // Close specific tab
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

                System.out.println("Tab closed: " + url);
                return;
            }
            current = current.next;
        }

        System.out.println("Tab not found.");
    }

    // Move tab to front
    public void moveTabToFront(String url) {
        Node current = head;

        while (current != null) {
            if (current.url.equals(url)) {

                if (current == head)
                    return;

                // detach
                if (current.prev != null)
                    current.prev.next = current.next;

                if (current.next != null)
                    current.next.prev = current.prev;
                else
                    tail = current.prev;

                // move to front
                current.next = head;
                current.prev = null;
                head.prev = current;
                head = current;

                System.out.println("Tab moved to front: " + url);
                return;
            }
            current = current.next;
        }

        System.out.println("Tab not found.");
    }

    // Display all tabs
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

    // Get current active tab (last one)
    public String getCurrentTab() {
        if (tail == null)
            return null;
        return tail.url;
    }
}