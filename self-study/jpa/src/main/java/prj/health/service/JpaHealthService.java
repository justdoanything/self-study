package prj.health.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Service
public class JpaHealthService {
    
    @Value("${yongwoo.test}")
    private String profile;

    public void test(){
        System.out.println("################ ==> " + profile);
    }
}
