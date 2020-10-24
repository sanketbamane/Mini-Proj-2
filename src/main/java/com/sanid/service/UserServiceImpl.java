package com.sanid.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanid.entity.CityEntity;
import com.sanid.entity.CountryEntity;
import com.sanid.entity.StateEntity;
import com.sanid.entity.UserAccEntity;
import com.sanid.pojo.UserAccount;
import com.sanid.repository.CityRepository;
import com.sanid.repository.CountryRepository;
import com.sanid.repository.StateRepository;
import com.sanid.repository.UserAccRepository;
import com.sanid.util.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserAccRepository userAccRepo;	
	@Autowired
	private StateRepository stateRepo;	
	@Autowired
	private CountryRepository countryRepo;	
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String loginCheck(String email, String pwd) {
		// TODO Auto-generated method stub
		UserAccEntity entity = userAccRepo.findByUserEmailAndUserPazzword(email, pwd);
		if(entity == null) {
			return "Invalid credentials";
		}else if(entity.getAccStatus().equals("LOCKED")){
			return "Your account is locked";
		}else {
			return "VALID";
		}
	}

	@Override
	public Map<Integer, String> loadCountries() {
		// TODO Auto-generated method stub
		
		Map<Integer,String> countryMap = new HashMap<>();
		
		List<CountryEntity> entitiesList = countryRepo.findAll();
		
		entitiesList.forEach(entity -> {
			countryMap.put(entity.getCid(), entity.getCname());
		});
		
		return countryMap;
	}

	@Override
	public Map<Integer, String> loadStatesByCountryId(Integer countryId) {
		// TODO Auto-generated method stub
		
		Map<Integer, String> stateMap = new HashMap<>();
		List<StateEntity> entitiesList = stateRepo.findBycid(countryId);
		
		entitiesList.forEach(entity -> {
			stateMap.put(entity.getSid(), entity.getSname());
		});
		
		return stateMap;
	}

	@Override
	public Map<Integer, String> loadCitiesByStateId(Integer stateId) {
		// TODO Auto-generated method stub
		Map<Integer,String> citiesMap = new HashMap<>();
		
		List<CityEntity> entitiesList = cityRepo.findBysid(stateId);
		entitiesList.forEach(entity -> {
			citiesMap.put(entity.getCid(), entity.getCname());
		});
		return citiesMap;
	}

	@Override
	public boolean isUniqueEmail(String email) {
		// TODO Auto-generated method stub
		
		UserAccEntity userAccEntity = userAccRepo.findByUserEmail(email);
		/*if(userAccEntity!=null){
		 * return false;
		 * 
		 * }else{
		 * return true;
		 * }
		 */
		return userAccEntity !=null ? false:true ;
		
	}

	@Override
	public String generateTempPwd() {
		// TODO Auto-generated method stub

        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(6); 
  
        for (int i = 0; i < 6; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
		
		
	}

	@Override
	public boolean saveUserAccount(UserAccount userAccount) {
		// TODO Auto-generated method stub
		userAccount.setAccStatus("LOCKED");
		userAccount.setUserPazzword(generateTempPwd());
		
		UserAccEntity entity = new UserAccEntity();
		BeanUtils.copyProperties(userAccount, entity);
		
		UserAccEntity savedEntity = userAccRepo.save(entity);
		
		Integer userId = savedEntity.getUid();
		
		if(userId !=null) {
			String to= userAccount.getUserEmail();
			String subject="Registration Successful";
			String body = getRegSuccMailBody(userAccount);
			sendRegSuccEmail(to, subject, body);
			return true;
		}
		return false;
		
		//can also use ternary operator above for method if
	
	}

	@Override
	public String getRegSuccMailBody(UserAccount user) {
	String fileName = "UNLOCK-ACC-EMAIL-BODYTEMPLATE.txt";
	List<String> replacedLines = null;
	String mailBody = null;
	try {
	Path path = Paths.get(fileName,"");
	Stream<String> lines = Files.lines(path);
	replacedLines = lines.map(line -> line.replace("{FNAME}", user.getFname())
										  .replace("{LNAME}", user.getLname())
										  .replace("{TEMP-PWD}", user.getUserPazzword())
										  .replace("{EMAIL}", user.getUserEmail()))
										  .collect(Collectors.toList());
	mailBody = String.join("", replacedLines);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return mailBody;
	}


	@Override
	public boolean sendRegSuccEmail(String to, String subject, String body) {
		// TODO Auto-generated method stub
		return emailUtils.sendEmail(to, subject, body);
	}

	@Override
	public boolean isTempPwdValid(String email, String tempPwd) {
		// TODO Auto-generated method stub
		UserAccEntity entity = userAccRepo.findByUserEmailAndUserPazzword(email, tempPwd);
		return entity!= null ? true : false;
	}

	@Override
	public boolean unlockAccount(String email, String password) {
		// TODO Auto-generated method stub
		UserAccEntity entity = userAccRepo.findByUserEmail(email);
		entity.setAccStatus("unlocked");
		entity.setUserPazzword(password);
		UserAccEntity savedEntity = userAccRepo.save(entity);
		return savedEntity.getUid() != null ? true : false;
	}

	@Override
	public String recoverPassword(String email) {
		// TODO Auto-generated method stub
		UserAccEntity entity = userAccRepo.findByUserEmail(email);
		if(entity!=null) {
			UserAccount acc= new UserAccount();
			BeanUtils.copyProperties(entity, acc);
			String body = getRecoverPwdEmailBody(acc);
			String to = acc.getUserEmail();
			String subject = "Password Recovery | Ashok IT";
			return sendPwdToEmail(to, subject, body);
			
		}else {
			return "FAIL";
		}
		
	}

	@Override
	public String getRecoverPwdEmailBody(UserAccount user) {
		// TODO Auto-generated method stub

		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		List<String> replacedLines = null;
		String mailBody = null;
		try {
		Path path = Paths.get(fileName,"");
		Stream<String> lines = Files.lines(path);
		replacedLines = lines.map(line -> line.replace("{FNAME}", user.getFname())
											  .replace("{LNAME}", user.getLname())
											  .replace("{PWD}", user.getUserPazzword()))
											  .collect(Collectors.toList());
		mailBody = String.join("", replacedLines);
		} catch (Exception e) {
		e.printStackTrace();
		}
		return mailBody;
	}

	@Override
	public String sendPwdToEmail(String to, String subject, String body) {
		// TODO Auto-generated method stub
		boolean isSent = emailUtils.sendEmail(to, subject, body);
		if(isSent) {
			return "SUCCESS";
		}
		
		return "FAILED";
	}
}