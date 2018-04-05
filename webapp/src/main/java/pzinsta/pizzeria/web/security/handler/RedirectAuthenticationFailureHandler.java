package pzinsta.pizzeria.web.security.handler;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private String defaultReturnUrl = "/";
    private String returnUrlParameterName = "returnUrl";
    private String queryParam = "loginError";
    private final static Logger LOGGER = LogManager.getLogger(RedirectAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String returnUrl = ObjectUtils.firstNonNull(request.getParameter(returnUrlParameterName), defaultReturnUrl);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(returnUrl);
        uriComponentsBuilder.queryParam(queryParam);
        UriComponents uriComponents = uriComponentsBuilder.build();
        String returnUrlWithQueryParameter = uriComponents.toUriString();
        LOGGER.debug(returnUrlWithQueryParameter);
        redirectStrategy.sendRedirect(request, response, returnUrlWithQueryParameter);
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    public String getDefaultReturnUrl() {
        return defaultReturnUrl;
    }

    public void setDefaultReturnUrl(String defaultReturnUrl) {
        this.defaultReturnUrl = defaultReturnUrl;
    }

    public String getReturnUrlParameterName() {
        return returnUrlParameterName;
    }

    public void setReturnUrlParameterName(String returnUrlParameterName) {
        this.returnUrlParameterName = returnUrlParameterName;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }
}
