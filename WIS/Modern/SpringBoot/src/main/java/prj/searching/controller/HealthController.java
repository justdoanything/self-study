package prj.searching.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prj.searching.aop.NoLogging;
import prj.searching.model.common.CommonResponseVO;
import prj.searching.util.ResponseUtil;

@RestController
@RequestMapping("/health")
@Api(tags = {"Health"})
public class HealthController {
   @ApiOperation(value = "HealthCheck", notes = "ELB나 Discovery Service 등이 Application의 정상 동작 여부를 판단하기 위한 API. ")
   @NoLogging
   @GetMapping
   public ResponseEntity<CommonResponseVO> checkHealth() {
       return ResponseUtil.createSuccessResponse();
   }
}
