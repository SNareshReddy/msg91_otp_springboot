package com.example.otp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
*/
@SpringBootApplication
public class SpringOtpRestTemplateApplication {

	/*@Bean
	public RestTemplate getTemplate() {
			
		RestTemplate restTemp = new RestTemplate();
		restTemp.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return restTemp;
	}*/
	public static void main(String[] args) {
		SpringApplication.run(SpringOtpRestTemplateApplication.class, args);
	}

}

