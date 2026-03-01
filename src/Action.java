public class Action {

    String type;
    String url;
    String previousPage;

    public Action(String type, String url, String previousPage) {
        this.type = type;
        this.url = url;
        this.previousPage = previousPage;
    }
}