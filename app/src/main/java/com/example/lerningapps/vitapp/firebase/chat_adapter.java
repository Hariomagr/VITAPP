package com.example.lerningapps.vitapp.firebase;

public class chat_adapter {
    String reg_no;
    String chat;
    String chk;
    String clas;

    public chat_adapter(String reg_no, String chat, String chk, String clas) {
        this.reg_no = reg_no;
        this.chat = chat;
        this.chk = chk;
        this.clas = clas;
    }


    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
        this.chk = chk;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

}
