package modern.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import modern.model.CommonResponseVO;
import modern.model.SpringRequestVO;
import modern.model.SpringResponseVO;
import modern.service.SpringService;
import modern.util.ResponseUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Spring"})
@RequestMapping("/v1/spring/health")
public class SpringController {
    private final SpringService springService;

    @ApiOperation(value = "Health Check", httpMethod = "POST", notes = "Health Check")
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CommonResponseVO> healthCheck(
            @RequestBody @Valid SpringRequestVO request) {
        SpringResponseVO vo = springService.checkHealth(request);
        return ResponseUtil.createSuccessResponse(vo);
    }

    @ApiOperation(value = "Health Check", httpMethod = "GET", notes = "Health Check")
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CommonResponseVO> healthCheck(
            @NotNull @PathVariable(name = "id", required = true) Integer id) {
        return ResponseUtil.createSuccessResponse(springService.checkHealth(id));
    }
}
