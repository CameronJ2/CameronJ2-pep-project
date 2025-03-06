package Service;

import DAO.SMDAO;
import Model.Account;
import Model.Message;
import java.util.*;

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

    public Account login(Account account){
        return SMDAO.checkValidAccount(account);
    }


    public Message createMessage(Message message){
        if (message.message_text.equals("") || message.message_text.length() > 255 || SMDAO.getAccountById(message.getPosted_by()) == null){
            return null;
        }
        return SMDAO.insertMessage(message);
    }

    public List<Message> getAllMessages(){
        return SMDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return SMDAO.getMessageById(id);
    }



}



