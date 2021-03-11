package pe.cortzotinnus.security.web.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SkipPathsExceptOneRequestMatcher implements RequestMatcher {

    private final OrRequestMatcher ignorePathsMatcher;
    private final RequestMatcher processingPathMatcher;

    public static SkipPathsExceptOneRequestMatcher create(final List<String> pathsToSkip, final String processingPath) {
        if (StringUtils.isEmpty(processingPath)) {
            throw new IllegalArgumentException("Processing path can't be empty or null");
        }
        List<RequestMatcher> requestMatchersToSkip = pathsToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        return new SkipPathsExceptOneRequestMatcher(new OrRequestMatcher(requestMatchersToSkip), new AntPathRequestMatcher(processingPath));
    }

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        if (this.ignorePathsMatcher.matches(httpServletRequest)) {
            return false;
        }
        return this.processingPathMatcher.matches(httpServletRequest);
    }
}
