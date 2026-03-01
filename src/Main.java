import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BrowserManager browser = new BrowserManager();

        while (true) {

            System.out.println("\n===== Browser History Manager =====");
            System.out.println("1. Visit Page");
            System.out.println("2. Go Back");
            System.out.println("3. Go Forward");
            System.out.println("4. Close Current Tab");
            System.out.println("5. Reopen Closed Tab");
            System.out.println("6. Show Current Page");
            System.out.println("7. Show Visit Statistics");
            System.out.println("8. Show All Tabs");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter URL: ");
                    String url = scanner.nextLine();
                    browser.visit(url);
                    break;

                case 2:
                    browser.goBack();
                    break;

                case 3:
                    browser.goForward();
                    break;

                case 4:
                    browser.closeTab();
                    break;

                case 5:
                    browser.reopenClosedTab();
                    break;

                case 6:
                    browser.showCurrentPage();
                    break;

                case 7:
                    browser.showVisitStats();
                    break;

                case 8:
                    browser.showAllTabs();
                    break;

                case 9:
                    System.out.println("Exiting Browser...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}