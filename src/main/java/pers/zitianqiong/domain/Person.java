package pers.zitianqiong.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zitianqiong
 */
@Component
@ConfigurationProperties(prefix = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	private int id;
	private String name;
	private List<String> hobby;
	private String[] family;
	private Map<String,String> map;
	private Pet pet;
}
