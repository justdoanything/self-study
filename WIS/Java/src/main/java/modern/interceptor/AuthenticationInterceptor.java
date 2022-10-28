package modern.interceptor;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modern.constants.HttpHeaderConstants;
import modern.constants.HttpUrlConstants;
import modern.model.session.SessionVO;
import modern.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String HTTP_METHOD_OPTIONS = "OPTIONS";
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Value("${cloud.token.connect.timeout}")
    private int TOKEN_CONNECT_TIMEOUT;

    @Value("${cloud.token.read.timeout}")
    private int TOKEN_READ_TIMEOUT;

    @Autowired
    SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String sessionId = request.getHeader(HttpHeaderConstants.SESSION_ID);
        if (HTTP_METHOD_OPTIONS.equals(request.getMethod())) return true;
        else if (!ObjectUtils.isEmpty(sessionId)) {
            String authorization = request.getHeader(HttpHeaderConstants.AUTHORIZATION);

            if (ObjectUtils.isEmpty(authorization)) {
                throw new Exception("Authorization is required");
            }

            if (Pattern.matches("^Bearer .*", authorization)) {
                authorization = authorization.replaceAll("^Bearer( )*", "");
            }

            if (!verifyToken(authorization)) {
                throw new Exception("Unauthorized");
            }

            SessionVO sessionUser = sessionService.getSession(sessionId);
            if (sessionUser == null) {
                throw new Exception("Session is expired");
            } else {
                //                SessionScopeUtil.setContextSession(sessionUser);
                return true;
            }
        } else if (isExcludePattern(HttpMethod.valueOf(request.getMethod()), request.getRequestURI())) {
            return true;
        } else {
            throw new Exception("session_id is required");
        }
    }

    private boolean verifyToken(String token) {
        try {
            JWTClaimsSet jwtClaimsSet = configurableJWTProcessor().process(token, null);
            String cognitoPoolUrl = String.format("cognito url", "region", "userPoolId");
            if (!jwtClaimsSet.getIssuer().equals(cognitoPoolUrl)
                    || !jwtClaimsSet.getAudience().get(0).equals("clientId")
                    || !jwtClaimsSet.getClaim("token_use").equals("id")) {
                return false;
            }

            Date now = new Date(System.currentTimeMillis());
            if (!now.before(jwtClaimsSet.getExpirationTime())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
        ResourceRetriever resourceRetriever = new DefaultResourceRetriever(TOKEN_CONNECT_TIMEOUT, TOKEN_READ_TIMEOUT);
        URL url = new URL(String.format("cognito url", "region", "userPoolId"));
        JWKSource jwkSource = new RemoteJWKSet(url, resourceRetriever);
        ConfigurableJWTProcessor configurableJWTProcessor = new DefaultJWTProcessor();
        JWSKeySelector jwsKeySelector = new JWSVerificationKeySelector(JWSAlgorithm.RS256, jwkSource);
        configurableJWTProcessor.setJWSKeySelector(jwsKeySelector);
        return configurableJWTProcessor;
    }

    private boolean isExcludePattern(HttpMethod httpMethod, String requestUri) {
        List<String> uriList = HttpUrlConstants.NO_AUTH_SESSION_HTTP_URI.get(httpMethod);
        for (String uri : uriList) {
            if (antPathMatcher.match(uri, requestUri)) return true;
        }
        return false;
    }
}
