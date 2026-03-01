import java.util.*;

public class BrowserManager {

    private TabLinkedList tabs;
    private Stack<String> backStack;
    private Stack<String> forwardStack;
    private Stack<Action> undoStack;
    private Stack<Action> redoStack;
    private Deque<String> closedTabs;
    private HashMap<String, Integer> visitCount;

    private String currentPage;

    public BrowserManager() {
        tabs = new TabLinkedList();
        backStack = new Stack<>();
        forwardStack = new Stack<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        closedTabs = new ArrayDeque<>();
        visitCount = new HashMap<>();
        currentPage = null;
    }

    // Visit new page
    public void visit(String url) {

        String previous = currentPage;

        if (currentPage != null) {
            backStack.push(currentPage);
        }

        tabs.addTab(url);
        currentPage = url;
        forwardStack.clear();

        visitCount.put(url, visitCount.getOrDefault(url, 0) + 1);

        undoStack.push(new Action("VISIT", url, previous));
        redoStack.clear();

        System.out.println("Visited: " + url);
    }

    // Go Back
    public void goBack() {

        if (backStack.isEmpty()) {
            System.out.println("No pages to go back.");
            return;
        }

        forwardStack.push(currentPage);
        String previous = currentPage;
        currentPage = backStack.pop();

        undoStack.push(new Action("BACK", currentPage, previous));
        redoStack.clear();

        System.out.println("Current Page: " + currentPage);
    }

    // Go Forward
    public void goForward() {

        if (forwardStack.isEmpty()) {
            System.out.println("No pages to go forward.");
            return;
        }

        backStack.push(currentPage);
        String previous = currentPage;
        currentPage = forwardStack.pop();

        undoStack.push(new Action("FORWARD", currentPage, previous));
        redoStack.clear();

        System.out.println("Current Page: " + currentPage);
    }

    // Close current tab
    public void closeTab() {

        if (currentPage == null) {
            System.out.println("No tab open.");
            return;
        }

        String closing = currentPage;

        tabs.closeTab(closing);
        closedTabs.push(closing);

        currentPage = tabs.getCurrentTab();

        undoStack.push(new Action("CLOSE", closing, currentPage));
        redoStack.clear();

        System.out.println("Tab closed: " + closing);
    }

    // Reopen closed tab
    public void reopenClosedTab() {

        if (closedTabs.isEmpty()) {
            System.out.println("No recently closed tabs.");
            return;
        }

        String reopened = closedTabs.pop();
        tabs.addTab(reopened);

        String previous = currentPage;
        currentPage = reopened;

        undoStack.push(new Action("REOPEN", reopened, previous));
        redoStack.clear();

        System.out.println("Reopened: " + reopened);
    }

    // Undo
    public void undo() {

        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Action action = undoStack.pop();

        switch (action.type) {

            case "VISIT":
                tabs.closeTab(action.url);
                currentPage = action.previousPage;
                break;

            case "CLOSE":
                tabs.addTab(action.url);
                currentPage = action.url;
                break;

            case "BACK":
            case "FORWARD":
                currentPage = action.previousPage;
                break;

            case "REOPEN":
                tabs.closeTab(action.url);
                currentPage = action.previousPage;
                break;
        }

        redoStack.push(action);
        System.out.println("Undo performed.");
    }

    // Redo
    public void redo() {

        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }

        Action action = redoStack.pop();

        switch (action.type) {

            case "VISIT":
                tabs.addTab(action.url);
                currentPage = action.url;
                break;

            case "CLOSE":
                tabs.closeTab(action.url);
                currentPage = tabs.getCurrentTab();
                break;

            case "BACK":
            case "FORWARD":
                currentPage = action.url;
                break;

            case "REOPEN":
                tabs.addTab(action.url);
                currentPage = action.url;
                break;
        }

        undoStack.push(action);
        System.out.println("Redo performed.");
    }

    public void showCurrentPage() {
        if (currentPage == null)
            System.out.println("No page currently open.");
        else
            System.out.println("Current Page: " + currentPage);
    }

    public void showAllTabs() {
        tabs.displayTabs();
    }

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