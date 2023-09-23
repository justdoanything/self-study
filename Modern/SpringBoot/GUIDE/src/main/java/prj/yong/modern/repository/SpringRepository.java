package prj.yong.modern.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import prj.yong.modern.model.spring.SpringRequestVO;
import prj.yong.modern.model.spring.SpringResponseVO;

/**
 * @Descroption
 *  application.yml 파일에 mybatis 태그에서 VO를 맵핑할 패키지의 경로와 sql 파일(xml)을 모아놓는 경로를 명시한다.
 *  map-underscore-to-camel-case: true 옵션으로 VO에선 underscore 패턴을, sql 파일에선 camelcase 패턴을 사용하고 자동으로 매핑해준다.
 */
@Mapper
public interface SpringRepository {
    int insertSpring();

    List<SpringResponseVO> selectSpring(SpringRequestVO springRequestVO);
}
