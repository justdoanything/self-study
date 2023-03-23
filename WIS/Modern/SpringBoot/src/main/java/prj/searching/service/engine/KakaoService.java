package prj.searching.service.engine;

import prj.searching.model.kakao.KakaoBlogRequestDTO;
import prj.searching.model.kakao.KakaoBlogResponseDTO;

public interface KakaoService {
    KakaoBlogResponseDTO getKakaoBlog(KakaoBlogRequestDTO kakaoBlogRequestDTO);
}
