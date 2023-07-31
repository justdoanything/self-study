package prj.searching.service.engine;

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
import prj.searching.constant.StatusCodeMessageConstant;
import prj.searching.exception.ApplicationException;
import prj.searching.model.kakao.KakaoBlogRequestDTO;
import prj.searching.model.kakao.KakaoBlogResponseDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final RestTemplate restTemplate;

    @Value("${api.kakao.token}")
    private String kakaoToken;

    @Value("${api.kakao.host}")
    private String kakaoHost;

    @Value("${api.kakao.url.blog}")
    private String kakaoUrlBlog;

    @Override
    public KakaoBlogResponseDTO getKakaoBlog(KakaoBlogRequestDTO kakaoBlogRequestDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "KakaoAK " + kakaoToken);

            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(kakaoHost + kakaoUrlBlog)
                    .queryParam("sort", kakaoBlogRequestDTO.getSort())
                    .queryParam("page", kakaoBlogRequestDTO.getPage())
                    .queryParam("size", kakaoBlogRequestDTO.getSize())
                    .queryParam("query", UriUtils.encode(kakaoBlogRequestDTO.getQuery(), "UTF-8"));

            log.info("Call RestTemplate : " + uriComponentsBuilder.toUriString());

            HttpEntity<String> kakaoSearchBlogRequest = new HttpEntity<>(headers);
            ResponseEntity<KakaoBlogResponseDTO> kakaoSearchBlogResponse = restTemplate.exchange(
                    uriComponentsBuilder.build(true).encode().toUri()
                    , HttpMethod.GET
                    , kakaoSearchBlogRequest
                    , KakaoBlogResponseDTO.class
            );

            if (kakaoSearchBlogResponse.getStatusCode() != HttpStatus.OK)
                throw new ApplicationException("Fail to request Kakao API (" + kakaoUrlBlog + ") : StatusCode is " + kakaoSearchBlogResponse.getStatusCode(), StatusCodeMessageConstant.FAIL);
            else
                return kakaoSearchBlogResponse.getBody();
        } catch (Exception e) {
            throw new ApplicationException("Exception in requesting Kakao API (" + kakaoUrlBlog + ")");
        }
    }
}