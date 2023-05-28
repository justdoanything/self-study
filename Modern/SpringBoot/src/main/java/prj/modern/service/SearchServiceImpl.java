package prj.modern.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.modern.constant.SearchConstant.PopularKeywordPeriod;
import prj.modern.entity.SearchKeywordHistoryEntity;
import prj.modern.model.kakao.KakaoBlogRequestDTO;
import prj.modern.model.kakao.KakaoBlogResponseDTO;
import prj.modern.model.naver.NaverBlogRequestDTO;
import prj.modern.model.naver.NaverBlogResponseDTO;
import prj.modern.model.pagination.PaginationResponseVO;
import prj.modern.model.popular.PopularKeywordDTO;
import prj.modern.model.search.SearchKakaoBlogRequestVO;
import prj.modern.model.search.SearchNaverBlogRequestVO;
import prj.modern.model.search.SearchPopularKeywordRequestVO;
import prj.modern.model.search.SearchPopularKeywordResponseVO;
import prj.modern.repository.SearchKeywordHistoryRepository;
import prj.modern.service.engine.KakaoService;
import prj.modern.service.engine.NaverService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchKeywordHistoryRepository searchKeywordHistoryRepository;

    private final KakaoService kakaoService;
    private final NaverService naverService;

    /**
     * @Description Hystrix
     *   1. Java Source에서 설정
     *     - commandKey :  안쓰면 자동으로 메소드 명으로 지정됨
     *     - execution.isolation.thread.timeoutInMilliseconds : 해당 시간 동안 메서드가 끝나지 않으면 circuit open
     *     - metrics.rollingStats.timeInMilliseconds : (circuit open 조건) 해당 시간 동안
     *     - circuitBreaker.errorThresholdPercentage : (circuit open 조건) 해당 에러 퍼센트만큼 실패시 오픈
     *     - circuitBreaker.requestVolumeThreshold : (circuit open 조건) 최소 판단 하기 위해 해당 요청 건수만큼 들어와야 한다.
     *     - circuitBreaker.sleepWindowInMilliseconds : circuit open 시 지속 될 시간
     *
     *     // 10 초 동안 10번 호출 중 20% 실패시(2번 실패시) 10초간 fallback 메소드 호출
     *     // 단, 해당 메소드가 3초 안에 끝나지 않을시 fallback 메소드 호출
     *     @HystrixCommand(
     *       commandKey = "commandKeyExample"
     *       , fallbackMethod = "doFallbackProcess"
     *       , commandProperties = {
     *             @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
     *             @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
     *             @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "20"),
     *             @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
     *             @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
     *         }
     *     )
     *   2. applica.yml에서 설정
     *   hystrix:
     *     command:
     *       commandKeyExample:
     *         execution:
     *           isolation:
     *             thread:
     *               timeoutInMilliseconds: 3000
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetKakaoBlogs")
    public PaginationResponseVO getKakaoBlogs(SearchKakaoBlogRequestVO searchKakaoBlogRequestVO) {
        KakaoBlogRequestDTO kakaoBlogRequestDTO = searchKakaoBlogRequestVO.toKakaoBlogRequestDTO();
        KakaoBlogResponseDTO kakaoBlogResponseDTO = kakaoService.getKakaoBlog(kakaoBlogRequestDTO);
        kakaoBlogResponseDTO.getMeta().setQuery(kakaoBlogRequestDTO.getQuery());
        searchKeywordHistoryRepository.save(SearchKeywordHistoryEntity.builder().keyword(searchKakaoBlogRequestVO.getKeyword()).build());
        return new PaginationResponseVO(kakaoBlogResponseDTO.getMeta(), kakaoBlogResponseDTO.getDocuments());
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetNaverBlogs")
    public PaginationResponseVO getNaverBlogs(SearchNaverBlogRequestVO searchNaverBlogRequestVO) {
        NaverBlogRequestDTO naverBlogRequestDTO = searchNaverBlogRequestVO.toNaverBlogRequestDTO();
        NaverBlogResponseDTO naverBlogResponseDTO = naverService.getNaverBlog(naverBlogRequestDTO);
        searchKeywordHistoryRepository.save(SearchKeywordHistoryEntity.builder().keyword(searchNaverBlogRequestVO.getKeyword()).build());
        return new PaginationResponseVO(naverBlogResponseDTO.getMeta(naverBlogRequestDTO.getQuery()), naverBlogResponseDTO.getItems());
    }

    @Override
    public List<SearchPopularKeywordResponseVO> getPopularKeywords(SearchPopularKeywordRequestVO searchPopularKeywordRequestVO) {
        PopularKeywordPeriod period = PopularKeywordPeriod.valueOf(searchPopularKeywordRequestVO.getPeriod());
        int limitSize = searchPopularKeywordRequestVO.getSize();

        LocalDateTime startDate = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.now();
        switch (period) {
            case MIN:
                startDate = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
                break;
            case HOUR:
                startDate = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
                break;
            case DAY:
                startDate = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
                break;
            case WEEK:
                startDate = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
                break;
            case MONTH:
                startDate = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
                break;
            case YEAR:
                startDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
                break;
            case ALL:
                break;
        }
        List<Object[]> historyObjects = searchKeywordHistoryRepository.findTopKeywordByPeriod(limitSize, startDate, endDate);
        List<PopularKeywordDTO> topKeywords = historyObjects.stream().map(PopularKeywordDTO::new).collect(Collectors.toList());

        /**
         *
         * Object[]를 쓰지 않고 Query 결과를 Mapping 하는 방법
         *
         * @PersistenceContext
         *     private EntityManager em;
         *
         * @SqlResultSetMapping(
         *         name = "MappingPopularKeyword",
         *         classes = {
         *                 @ConstructorResult(
         *                         targetClass = PopularKeywordDTO.class,
         *                         columns = {
         *                                 @ColumnResult(name = "keyword", type=String.class),
         *                                 @ColumnResult(name = "keywordCount", type = BigInteger.class)
         *                         }
         *                 )
         *         }
         * )
         * @NamedNativeQuery(
         *         name = "findTopKeywordByPeriod",
         *         query =  "SELECT keyword as keyword, COUNT(*) as keywordCount " +
         *                 "FROM search_keyword_history " +
         *                 "WHERE created_datetime BETWEEN :startDate AND :endDate " +
         *                 "GROUP BY keyword " +
         *                 "ORDER BY keywordCount DESC " +
         *                 "LIMIT :limitSize",
         *         resultSetMapping = "MappingPopularKeyword"
         * )
         *
         * List<PopularKeywordDTO> historyObjects = em.createNamedQuery("findTopKeywordByPeriod")
         *                 .setParameter("limitSize",limitSize)
         *                 .setParameter("startDate",startDate)
         *                 .setParameter("endDate",endDate)
         *                 .getResultList();
         */
        return topKeywords.stream().map(SearchPopularKeywordResponseVO::new).collect(Collectors.toList());
    }

    public PaginationResponseVO fallbackGetKakaoBlogs(SearchKakaoBlogRequestVO searchKakaoBlogRequestVO) {
        return getNaverBlogs(searchKakaoBlogRequestVO.toSearchNaverBlogRequestVO());
    }

    public PaginationResponseVO fallbackGetNaverBlogs(SearchNaverBlogRequestVO searchNaverBlogRequestVO) {
        return getKakaoBlogs(searchNaverBlogRequestVO.toSearchKakaoBlogRequestVO());
    }
}
