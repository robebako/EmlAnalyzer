package com.vsite;

import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Main {

    public static void main(String[] args) throws Exception{
        String readPath ="C:\\TEST2";
        File folder = new File(readPath);
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

        if(hasAttachments(message))
            System.out.println("Has attachment!");

        String savePath ="C:\\TEST3";
        new File(savePath).mkdir();
        String name = message.getSubject().replaceAll("[:\\\\/*?|<> \"]", "_");
        savePath += "\\" + name+".eml";
        OutputStream dest = new FileOutputStream(new File(savePath));
        try {
            message.writeTo(dest);
        }
        finally {
            if (dest != null) { dest.flush(); dest.close(); }
        }
    }
    public static boolean hasAttachments(Message msg) throws MessagingException, IOException {
        if (msg.isMimeType("multipart/mixed")) {
            Multipart mp = (Multipart)msg.getContent();
            return mp.getCount() > 1;
        }
        return false;
    }
}
