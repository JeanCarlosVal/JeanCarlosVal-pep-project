package Service;

import DAO.SocialMediaDAOImpl;
import Model.Account;

public class SocialMediaService {

    SocialMediaDAOImpl socialMediaDAOImpl;

    public SocialMediaService(){
        socialMediaDAOImpl = new SocialMediaDAOImpl();
    }

    public SocialMediaService(SocialMediaDAOImpl socialMediaDAOImpl){
        this.socialMediaDAOImpl = socialMediaDAOImpl;
    }

    public Account registerAccount(Account account){
        Account newAccount = socialMediaDAOImpl.registerUser(account);

        return newAccount;
    }

    
    
}
