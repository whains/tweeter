package edu.byu.cs.tweeter.model.net.response;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse extends Response {

    public boolean hasMorePages;

    PagedResponse(boolean success, boolean hasMorePages) {
        super(success);
        this.hasMorePages = hasMorePages;
    }

    PagedResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message);
        this.hasMorePages = hasMorePages;
    }

    public boolean getHasMorePages() {
        return hasMorePages;
    }
}
