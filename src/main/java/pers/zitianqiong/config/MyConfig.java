package pers.zitianqiong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.zitianqiong.domain.Pet;

@Configuration
public class MyConfig {

	@Bean
	public Pet pet01(){
		return new Pet("猫","小白");
	}
}
