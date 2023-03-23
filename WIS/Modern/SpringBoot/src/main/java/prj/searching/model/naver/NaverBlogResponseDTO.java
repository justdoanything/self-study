package prj.searching.model.naver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverBlogResponseDTO {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverBlogItemDTO> items;

    public Map getMeta(String query) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("lastBuildDate", lastBuildDate);
        meta.put("total", total);
        meta.put("start", start);
        meta.put("display", display);
        meta.put("engine", "NAVER");
        meta.put("query", query);
        return meta;
    }
}