//package com.example.managementapp;
//
//
//
//import java.util.Date;
//
//public class Message {
//    private int id;
//    private String content;
//    private String created;
//    private Boolean sent;
//    private String email;
//    private String date;
//    public Message(String content, String date, String sender, String email){
//        this.content = content;
//        this.date = date;
//        this.created = sender;
//        this.email = email;
//    }
//    public Message(int id, String content, String created, Boolean sent, String date, String email) {
//        this.id = id;
//        this.content = content;
//        this.created = created;
//        this.sent = sent;
//        this.date = date;
//        this.email = email;
//    }
//
//    public Message() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getCreated() {
//        return created;
//    }
//
//    public void setCreated(String created) {
//        this.created = created;
//    }
//
//    public Boolean getSent() {
//        return false;
//    }
//
//    public void setSent(Boolean sent) {
//        this.sent = sent;
//    }
//
//    @Override
//    public String toString() {
//        return "Message{" +
//                "id=" + id +
//                ", content='" + content + '\'' +
//                ", created='" + created + '\'' +
//                ", sent=" + sent +
//                '}';
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//}
//



package com.example.managementapp;



import java.util.Date;

public class Message {
    private String content;
    private String date;
    private String sender;
    private Boolean sent;

    public Message(String content, String date, String sender, Boolean sent) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.sent = sent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }
}