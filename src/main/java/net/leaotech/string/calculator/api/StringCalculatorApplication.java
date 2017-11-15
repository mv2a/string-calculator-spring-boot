package net.leaotech.string.calculator.api;

import net.leaotech.string.calculator.api.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class StringCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(StringCalculatorApplication.class, args);
    }
}
