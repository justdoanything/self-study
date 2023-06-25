package prj.modern.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prj.modern.aop.NoLogging;
import prj.modern.constant.SearchEngineTypeCode;
import prj.modern.model.common.CommonResponseVO;
import prj.modern.model.test.TestEnumCodeVO;
import prj.modern.util.ResponseUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/test/enum")
@Api(tags = {"Test"})
@RequiredArgsConstructor
public class EumTestController {
    @ApiOperation(value = "Converter Test", notes = "Enum 관련한 기능들을 테스트하기 위한 Controller")
    @NoLogging
    @GetMapping(path = "/converter/path-variable/{searchEngineTypeCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> doTestEnumConverterPathVariable(@PathVariable SearchEngineTypeCode searchEngineTypeCode) {
       System.out.println(searchEngineTypeCode);
        return ResponseUtil.createSuccessResponse();
    }

    @ApiOperation(value = "Converter Test", notes = "Enum 관련한 기능들을 테스트하기 위한 Controller")
    @NoLogging
    @GetMapping(path = "/converter/request-param", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> doTestEnumConverterRequestParam(@RequestParam SearchEngineTypeCode searchEngineTypeCode) {
       System.out.println(searchEngineTypeCode);
        return ResponseUtil.createSuccessResponse();
    }

    @ApiOperation(value = "Validator Test", notes = "Enum 관련한 기능들을 테스트하기 위한 Controller")
    @NoLogging
    @GetMapping(path = "/validator", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> doTestEnumValidator() {
        return ResponseUtil.createSuccessResponse();
    }

    @ApiOperation(value = "Deserializer Test", notes = "Enum 관련한 기능들을 테스트하기 위한 Controller")
    @NoLogging
    @GetMapping(path = "/deserializer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> doTestEnumDeserializer(@Valid TestEnumCodeVO testEnumCodeVO) {
        System.out.println(testEnumCodeVO.getSearchEngineTypeCode());
        return ResponseUtil.createSuccessResponse();
    }
}
