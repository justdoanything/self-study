package prj.modern.service.engine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import prj.modern.constant.StatusCodeMessageConstant;
import prj.modern.exception.ApplicationException;
import prj.modern.model.naver.NaverBlogRequestDTO;
import prj.modern.model.naver.NaverBlogResponseDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverServiceImpl implements NaverService {
    private final RestTemplate restTemplate;

    @Value("${api.naver.client-id}")
    private String naverClientId;

    @Value("${api.naver.client-secret}")
    private String naverClientSecret;

    @Value("${api.naver.host}")
    private String naverHost;

    @Value("${api.naver.url.blog}")
    private String naverUrlBlog;

    public NaverBlogResponseDTO getNaverBlog(NaverBlogRequestDTO naverBlogRequestDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Naver-Client-Id", naverClientId);
            headers.add("X-Naver-Client-Secret", naverClientSecret);

            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(naverHost + naverUrlBlog)
                    .queryParam("sort", naverBlogRequestDTO.getSort())
                    .queryParam("display", naverBlogRequestDTO.getDisplay())
                    .queryParam("start", naverBlogRequestDTO.getStart())
                    .queryParam("query", UriUtils.encode(naverBlogRequestDTO.getQuery(), "UTF-8"));

            log.info("Call RestTemplate : " + uriComponentsBuilder.toUriString());

            HttpEntity<String> naverSearchBlogRequest = new HttpEntity<>(headers);
            ResponseEntity<NaverBlogResponseDTO> naverSearchBlogResponse = restTemplate.exchange(
                    uriComponentsBuilder.build(true).encode().toUri()
                    , HttpMethod.GET
                    , naverSearchBlogRequest
                    , NaverBlogResponseDTO.class
            );

            if (naverSearchBlogResponse.getStatusCode() != HttpStatus.OK)
                throw new ApplicationException("Fail to request Naver API (" + naverUrlBlog + ") : StatusCode is " + naverSearchBlogResponse.getStatusCode(), StatusCodeMessageConstant.FAIL);
            else
                return naverSearchBlogResponse.getBody();
        } catch (Exception e) {
            throw new ApplicationException("Exception in requesting Naver API (" + naverUrlBlog + ")");
        }
    }
}
