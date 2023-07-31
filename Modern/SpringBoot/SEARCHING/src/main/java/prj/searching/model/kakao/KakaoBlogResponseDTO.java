package prj.searching.model.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoBlogResponseDTO {
    private KakaoBlogMetaDTO meta;
    private List<KakaoBlogDocumentDTO> documents;
}
