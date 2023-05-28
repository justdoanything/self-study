package prj.yong.modern.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;

public class HttpUrlConstant {

    public static final Map<HttpMethod, List<String>> NO_AUTH_SESSION_HTTP_URI = new EnumMap<>(HttpMethod.class);

    static {
        String[] getExcludeUrl = {};

        String[] postExcludeUrl = {};

        String[] putExcludeUrl = {};

        String[] deleteExcludeUrl = {};

        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.GET, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.POST, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.PUT, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.DELETE, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.PATCH, new ArrayList<>());

        addExcludeHttpPathUrl(HttpMethod.GET, getExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.POST, postExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.PUT, putExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.DELETE, deleteExcludeUrl);
    }

    private static void addExcludeHttpPathUrl(HttpMethod httpMethod, String[] patternList) {
        List<String> urlList = NO_AUTH_SESSION_HTTP_URI.get(httpMethod);
        Collections.addAll(urlList, patternList);
    }
}
