package com.example.otp.restcontroller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.otp.model.MessageDetails;
import com.example.otp.model.MessageObject;
import com.example.otp.repository.OTPRespository;
import com.example.otp.utility.OtpUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*import com.example.otp.services.OtpServices;*/

@RestController
@RequestMapping("/api")
public class OtpRest {

	/*
	 * HttpResponse<String> response = Unirest.post(
	 * "http://control.msg91.com/api/sendotp.php?template=&otp_length=&authkey=&message=&sender=&mobile=&otp=&otp_expiry=&email=")
	 * 2 .asString();
	 */
	@Autowired
	private OTPRespository otpRepository;

	@Value("${otp.url}")
	private String optUrl;

	@Value("${otp.length}")
	private String length;

	@Value("${otp.authentication}")
	private String authenticactionKey;

	@Value("${otp.message}")
	private String message;

//	private Map<String, OtpUtil> otp_data = new HashMap<>();

	/*
	 * @Autowired private OtpServices otpServices;
	 */

	@PostMapping("/mobilenumbers/{mobilenumber}")
	public MessageDetails otpGenerate(@PathVariable("mobilenumber") String mobilenumber) {
		RestTemplate restTemp = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON
		// }));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		Random random = new Random();
		String otp = String.format("%04d", random.nextInt(10000));
		System.out.println(otp);
		String messageData = message + otp;
		System.out.println(messageData);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(optUrl)
				.queryParam("authkey", authenticactionKey).queryParam("otp_length", length)
				.queryParam("message", messageData).queryParam("sender", "OTPSMS").queryParam("mobile", mobilenumber)
				.queryParam("otp", otp);

		ResponseEntity<String> response = restTemp.exchange(uriBuilder.toUriString(), HttpMethod.POST, entity,String.class);
		String body = response.getBody();
		System.out.println(body);
		ObjectMapper mapper = new ObjectMapper();
		MessageDetails readValue = null;
		try {
			readValue = mapper.readValue(body, MessageDetails.class);
		} catch (JsonParseException e) {
						
				e.printStackTrace();
		} catch (JsonMappingException e) {
				
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (readValue.getType().equalsIgnoreCase("success")) {
			OtpUtil otputilsDetails = otpRepository.findByMobilenumber(mobilenumber);
			if (otputilsDetails != null) {
				otputilsDetails.setOtp(otp);
				otputilsDetails.setExpirytime(System.currentTimeMillis() + 600000);
				otpRepository.save(otputilsDetails);
			} else {
				OtpUtil otpSave = new OtpUtil();
				otpSave.setMobilenumber(mobilenumber);
				otpSave.setOtp(otp);
				otpSave.setExpirytime(System.currentTimeMillis() + 600000);
				otpRepository.save(otpSave);
			}
		}
		/* ResponseEntity<String> response =
		*/
		// restTemp.exchange("http://2factor.in/API/V1/e159d868-1a59-11e9-9ee8-0200cd936042/SMS/{mobilenumber}/4499",HttpMethod.GET,entity,String.class);
		return readValue;

	}
	
	@GetMapping("/mobilenumbers/{mobilenumber}/{otp}")
	public ResponseEntity<Object> verfyOtp(@PathVariable("mobilenumber") String mobilenumber, @PathVariable("otp") String otp){
		
		
		if(mobilenumber !=null) {
			
			if(otp!=null) {
				
				OtpUtil otputilsDetails = otpRepository.findByMobilenumber(mobilenumber);
				
				if(otputilsDetails!=null)
				{
					if(otputilsDetails.getExpirytime() >= System.currentTimeMillis()) {
						if(otputilsDetails.getOtp().equals(otp)) { 
							//System.out.print(otpRepository.delete(otpRetrieve.getOtp()));
							return new ResponseEntity<Object>(new MessageObject(true, "Success"), HttpStatus.ACCEPTED);
						}
						else {
							   return new ResponseEntity<Object>(new MessageObject(false,"Invalid otp"),HttpStatus.NOT_FOUND);
						    }
					    }
				    else
				    	{
				    	
				    	   return new ResponseEntity<Object>(new MessageObject(false,"otp expired resend"),HttpStatus.NOT_FOUND);
				    	}
			    }
		       else	{
		    	   return new ResponseEntity<Object>(new MessageObject(false,"mobile number not found"), HttpStatus.NOT_FOUND);
		       }
		 }
	      else {
	    	  return new ResponseEntity<Object>(new MessageObject(false,"please provide the otp"), HttpStatus.BAD_REQUEST);
	      }
		
	   }
		else {
			return new ResponseEntity<Object>(new MessageObject(false,"please provide mobile number"),HttpStatus.BAD_REQUEST);
		}
	  }
			
	}

