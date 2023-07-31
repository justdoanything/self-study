package prj.searching.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prj.searching.constant.SearchConstant.PopularKeywordPeriod;
import prj.searching.entity.SearchKeywordHistoryEntity;
import prj.searching.model.kakao.KakaoBlogRequestDTO;
import prj.searching.model.kakao.KakaoBlogResponseDTO;
import prj.searching.model.naver.NaverBlogRequestDTO;
import prj.searching.model.naver.NaverBlogResponseDTO;
import prj.searching.model.pagination.PaginationResponseVO;
import prj.searching.model.popular.PopularKeywordDTO;
import prj.searching.model.search.SearchKakaoBlogRequestVO;
import prj.searching.model.search.SearchNaverBlogRequestVO;
import prj.searching.model.search.SearchPopularKeywordRequestVO;
import prj.searching.model.search.SearchPopularKeywordResponseVO;
import prj.searching.repository.SearchKeywordHistoryRepository;
import prj.searching.service.engine.KakaoService;
import prj.searching.service.engine.NaverService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager em;

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

//        List<PopularKeywordDTO> historyObjects = searchKeywordHistoryRepository.findTopKeywordByPeriod(limitSize, startDate, endDate);
//        List<PopularKeywordDTO> topKeywords = historyObjects.stream().map(PopularKeywordDTO::new).collect(Collectors.toList());
        List<PopularKeywordDTO> historyObjects = em.createNamedQuery("findTopKeywordByPeriod")
                .setParameter("limitSize",limitSize)
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .getResultList();
        return historyObjects.stream().map(SearchPopularKeywordResponseVO::new).collect(Collectors.toList());
    }

    public PaginationResponseVO fallbackGetKakaoBlogs(SearchKakaoBlogRequestVO searchKakaoBlogRequestVO) {
        return getNaverBlogs(searchKakaoBlogRequestVO.toSearchNaverBlogRequestVO());
    }

    public PaginationResponseVO fallbackGetNaverBlogs(SearchNaverBlogRequestVO searchNaverBlogRequestVO) {
        return getKakaoBlogs(searchNaverBlogRequestVO.toSearchKakaoBlogRequestVO());
    }
}
