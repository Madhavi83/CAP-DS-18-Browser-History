import java.util.*;

public class BrowserManager {

    private TabLinkedList tabs;
    private Stack<String> backStack;
    private Stack<String> forwardStack;
    private Deque<String> closedTabs;
    private HashMap<String, Integer> visitCount;

    private String currentPage;

    public BrowserManager() {
        tabs = new TabLinkedList();
        backStack = new Stack<>();
        forwardStack = new Stack<>();
        closedTabs = new ArrayDeque<>();
        visitCount = new HashMap<>();
        currentPage = null;
    }

    // Visit new page (open new tab)
    public void visit(String url) {

        if (currentPage != null) {
            backStack.push(currentPage);
        }

        tabs.addTab(url);
        currentPage = url;
        forwardStack.clear();

        visitCount.put(url, visitCount.getOrDefault(url, 0) + 1);

        System.out.println("Visited: " + url);
    }

    // Go Back
    public void goBack() {

        if (backStack.isEmpty()) {
            System.out.println("No pages to go back.");
            return;
        }

        forwardStack.push(currentPage);
        currentPage = backStack.pop();

        System.out.println("Current Page: " + currentPage);
    }

    // Go Forward
    public void goForward() {

        if (forwardStack.isEmpty()) {
            System.out.println("No pages to go forward.");
            return;
        }

        backStack.push(currentPage);
        currentPage = forwardStack.pop();

        System.out.println("Current Page: " + currentPage);
    }

    // Close current tab
    public void closeTab() {

        if (currentPage == null) {
            System.out.println("No tab open.");
            return;
        }

        tabs.closeTab(currentPage);
        closedTabs.push(currentPage);

        // After closing, set current to last open tab
        currentPage = tabs.getCurrentTab();

        System.out.println("Current Page: " +
                (currentPage == null ? "None" : currentPage));
    }

    // Reopen last closed tab
    public void reopenClosedTab() {

        if (closedTabs.isEmpty()) {
            System.out.println("No recently closed tabs.");
            return;
        }

        String reopened = closedTabs.pop();
        tabs.addTab(reopened);
        currentPage = reopened;

        System.out.println("Reopened: " + reopened);
    }

    // Show current page
    public void showCurrentPage() {

        if (currentPage == null) {
            System.out.println("No page currently open.");
        } else {
            System.out.println("Current Page: " + currentPage);
        }
    }

    // Show all open tabs
    public void showAllTabs() {
        tabs.displayTabs();
    }

    // Show visit statistics
    public void showVisitStats() {

        if (visitCount.isEmpty()) {
            System.out.println("No pages visited yet.");
            return;
        }

        System.out.println("Visit Statistics:");
        for (Map.Entry<String, Integer> entry : visitCount.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " visits");
        }
    }
}