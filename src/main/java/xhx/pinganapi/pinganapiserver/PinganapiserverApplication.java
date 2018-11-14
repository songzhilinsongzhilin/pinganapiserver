package xhx.pinganapi.pinganapiserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("xhx.pinganapi.pinganapiserver")
public class PinganapiserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinganapiserverApplication.class, args);
    }
}
