package neverstop.manager.adaptor.requester;

import java.util.ArrayList;
import java.util.List;

/**
 * RequestMessage
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 10/09/2018
 */
public class RequestMessage {

    private String method;
    private List<String> params;

    public RequestMessage() {
        this.params = new ArrayList<String>();
    }

    public RequestMessage(String method) {
        this();
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<String> getParams() {
        return params;
    }
}
