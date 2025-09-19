package JustVibe.com.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JustVibe.com.repository.JustVibeRepository;

@Service
public class UsersManager {
	
	@Autowired
	JustVibeRepository jr;
	
	@Autowired
	JWTManager JM;
	
	public String addUser(Users U)
	{
		if(jr.validateEmail(U.getEmail()) > 0)
		{
			return "401::User Email already exists.";
		}
		
		jr.save(U);
		return "200::User Added Successfully";
	}
	
	public String login(String email, String password)
	{
		if(jr.validateCredentials(email, password) > 0)
		{
			String token = JM.generateToken(email);
			return "200::" + token;
		}
		return "401::Invaild Credentials";
	}
	
	public String getUsername(String token)
	{
		String email=JM.validateToken(token);
	    if(email.compareTo("401")==0)
	    {
	    	return "401::Token Expired";
	      
	    }
	    Users U=jr.findById(email).get();
	    return U.getUsername();
	}
	
	
}
