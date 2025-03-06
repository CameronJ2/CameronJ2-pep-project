package Service;

import DAO.SMDAO;
import Model.Account;

public class SMService {
    SMDAO SMDAO;


    public SMService(){
        SMDAO = new SMDAO();
    }

    public SMService(SMDAO SMDAO){
        this.SMDAO = SMDAO;
    }


    public Account register(Account account){
        if (account.getUsername().equals("")){
            return null;
        }
        if (account.getPassword().length() < 4){
            return null;
        }
        if (SMDAO.getAccountByUsername(account.getUsername()) != null){
            return null;
        }
        return SMDAO.insertAccount(account);
    }

}



