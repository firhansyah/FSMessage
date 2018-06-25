package id.co.firhansyah.fsmessage.models;

public class Sms{
    private String ID;
    private String PhoneNumber;
    private String Message;
    private String ReadFlag;
    private String Date;

    public String getId(){
        return ID;
    }
    public String getPhoneNumber(){
        return PhoneNumber;
    }
    public String getMessage(){
        return Message;
    }
    public String getReadFlag(){
        return ReadFlag;
    }
    public String getDate(){
        return Date;
    }


    public void setId(String id){
        ID = id;
    }
    public void setPhoneNumber(String phoneNumber){
        PhoneNumber = phoneNumber;
    }
    public void setMessage(String message){
        Message = message;
    }
    public void setReadFlag(String readFlag){
        ReadFlag = readFlag;
    }
    public void setDate(String date){
        Date = date;
    }
}
