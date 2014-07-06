package server;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 * Session Bean implementation class RegSessionBean
 */
@Stateless
public class RegSessionBean implements RegSessionBeanRemote {
	 //@PersistenceContext(unitName="AuctionPointEJB")
	   private EntityManager emgr;
    /**
     * Default constructor. 
     */
	 
    public RegSessionBean() {
        // TODO Auto-generated constructor stub
	}

	@Override
	public void setUname(String uname) {
		// TODO Auto-generated method stub
		emgr.createQuery(arg0)
	}

	@Override
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		System.out.println(email);
	}

	@Override
	public void setPword(String pword) {
		// TODO Auto-generated method stub
		System.out.println(pword);
	}

}
