package prj.searching.model.naver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverBlogItemDTO {
    private String title;
    private String link;
    private String description;
    private String bloggerName;
    private String bloggerLink;
    private String postDate;
}