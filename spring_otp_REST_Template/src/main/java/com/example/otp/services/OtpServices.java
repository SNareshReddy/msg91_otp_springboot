package com.example.otp.services;

import java.util.Random;

import org.springframework.stereotype.Service;


@Service
public class OtpServices {


	

	public String sendOtp(String mobilenumber) {
		
		Random random = new Random();
		String otp = String.format("%04d", random.nextInt(10000));
		String template="Naresh";
		long otp_length=4;
		String message="your one time password  is"+otp;
		String auth_key="253985ACLNlclK5c261982";
		String senderID="OTPSMS";
		long opt_expiry=10;
		String email=null;
		String urlParameters= "template="+template+
							"&otp_length="+otp_length+
							"&uthkey="+auth_key+
							"&message="+message+
							"&sender="+senderID+
							"&mobile="+mobilenumber+
							"&otp="+otp+
							"&otp_expiry="+opt_expiry+
							"&email="+email;
	
		
		return urlParameters;
		
	}
}
