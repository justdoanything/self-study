package prj.modern.service.engine;

import prj.modern.model.kakao.KakaoBlogRequestDTO;
import prj.modern.model.kakao.KakaoBlogResponseDTO;

public interface KakaoService {
    KakaoBlogResponseDTO getKakaoBlog(KakaoBlogRequestDTO kakaoBlogRequestDTO);
}
