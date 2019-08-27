package com.example.otp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.otp.utility.OtpUtil;

@Repository
public interface OTPRespository extends CrudRepository<OtpUtil, Long> {

	public OtpUtil findByMobilenumber(String mobilenumber);

	//public boolean delete(String otp);
	

}
