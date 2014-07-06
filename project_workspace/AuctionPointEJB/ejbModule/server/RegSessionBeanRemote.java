package server;

import javax.ejb.Remote;

@Remote
public interface RegSessionBeanRemote {

	public void setUname(String uname);
	
	public void setEmail(String email);
	
	public void setPword(String pword);
}
