package net.cnki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("net.cnki.mapper")
public class TkMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TkMybatisApplication.class, args);
	}

}
