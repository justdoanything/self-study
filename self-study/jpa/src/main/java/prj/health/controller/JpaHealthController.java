package prj.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prj.health.service.JpaHealthService;

@RestController
public class JpaHealthController {
    
    @Autowired
    JpaHealthService jpaService;
    
    @RequestMapping("/health")
    public ResponseEntity healthCheck(){
        System.out.println("################## ==> test");
        jpaService.test();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
