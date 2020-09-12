package com.vsite;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.mail.*;
import javax.mail.internet.*;

public class Main {

    public static void main(String[] args) throws Exception{
        String path ="C:\\TEST2";
        File folder = new File(path);
        File [] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for(File file:listOfFiles)
            {
                display(file);
            }
        }

    }

    public static void display(File emlFile) throws Exception{

        InputStream source = new FileInputStream(emlFile);
        Message message = new MimeMessage(Session.getInstance(System.getProperties()),source);

        System.out.println("Subject : " + message.getSubject());
        System.out.println("From : " + message.getFrom()[0]);
        System.out.println("--------------");
        //System.out.println("Body : " +  message.getContent());
    }
}
