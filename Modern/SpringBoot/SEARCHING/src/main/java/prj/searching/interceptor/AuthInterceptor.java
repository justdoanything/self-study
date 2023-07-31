package prj.searching.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static  final Map<HttpMethod, List<String>> NO_AUTH_URI = new EnumMap<>(HttpMethod.class);

    static {
        // TODO : 필요시 HttpMethod 별로 Interceptor에서 제외하고 싶은 경로를 각 배열에 추가할 수 있다.
        String[] excludeUrlFromGet = {};
        String[] excludeUrlFromPost = {};
        String[] excludeUrlFromPut = {};
        String[] excludeUrlFromDelete = {};
        String[] excludeUrlFromPatch = {};

        NO_AUTH_URI.put(HttpMethod.GET, new ArrayList<>(Arrays.asList(excludeUrlFromGet)));
        NO_AUTH_URI.put(HttpMethod.POST, new ArrayList<>(Arrays.asList(excludeUrlFromPost)));
        NO_AUTH_URI.put(HttpMethod.PUT, new ArrayList<>(Arrays.asList(excludeUrlFromPut)));
        NO_AUTH_URI.put(HttpMethod.DELETE, new ArrayList<>(Arrays.asList(excludeUrlFromDelete)));
        NO_AUTH_URI.put(HttpMethod.PATCH, new ArrayList<>(Arrays.asList(excludeUrlFromPatch)));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // TODO : 필요시 Auth, Api key 검증 등 Interceptor에서 공통으로 처리할 로직이 있으면 추가할 수 있다.
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        } else if (isExcludePattern(HttpMethod.valueOf(request.getMethod()), request.getRequestURI())) {
            return true;
        }
        return true;
    }

    private boolean isExcludePattern(HttpMethod httpMethod, String requestUrl) {
        List<String> urlList = NO_AUTH_URI.get(httpMethod);
        for (String url : urlList) {
            if (antPathMatcher.match(url, requestUrl)) {
                return true;
            }
        }
        return false;
    }
}
