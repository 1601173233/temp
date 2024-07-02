package cn.com.toolWeb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author hyj
 */
@EnableTransactionManagement
@MapperScan("cn.com.toolWeb.mapper")
@ComponentScan({"cn.com.toolWeb"})
@SpringBootApplication(scanBasePackages = "cn.com.toolWeb")
public class ToolWebApp {

    public static void main(String[] args) {
        SpringApplication.run(ToolWebApp.class, args);
    }
}
