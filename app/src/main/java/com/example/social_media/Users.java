package com.example.social_media;

public class Users {
    String profileimg,username,mail,password,userid,lastmessage;



    public Users() {
    }

    public Users(String profileimg, String username, String mail, String password, String userid, String lastmessage) {
        this.profileimg = profileimg;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userid = userid;
        this.lastmessage = lastmessage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Users(String username, String mail, String password) {

        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    // signup constructor


//    public String getProfileimg() {
//        return profileimg;
//    }
//
//    public void setProfileimg(String profileimg) {
//        this.profileimg = profileimg;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getUserid() {
//        return userid;
//    }
//
//    public void setUserid(String userid) {
//        this.userid = userid;
//    }
//
//    public String getLastmessage() {
//        return lastmessage;
//    }
//
//    public void setLastmessage(String lastmessage) {
//        this.lastmessage = lastmessage;
//    }
}

