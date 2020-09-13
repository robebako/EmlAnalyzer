package com.vsite;

import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmlAnalyzer {
    private static File [] listOfFiles;
    private static File sourceFolder;
    private static File destinationFolder;

    public static void analyze() throws Exception {
        setListOfFiles(sourceFolder.listFiles());
        if (getListOfFiles() != null) {
            for(File file: getListOfFiles())
            {
                display(file);
            }
        }
    }

    public static void display(File emlFile) throws Exception{
        int cntr=0;
        InputStream source = new FileInputStream(emlFile);
        Message message = new MimeMessage(Session.getInstance(System.getProperties()),source);

        System.out.println("Subject : " + message.getSubject());
        System.out.println("From : " + message.getFrom()[0]);
        System.out.println("--------------");
        //System.out.println("Body : " +  message.getContent());

        if(hasAttachments(message)){
            cntr++;
            System.out.println("Has attachment!");
        }
        String name = message.getSubject().replaceAll("[:\\\\/*?|<> \"]", "_");
        OutputStream dest = new FileOutputStream(new File(String.valueOf(getDestinationFolder())+"\\"+name+".eml"));
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

    public static File[] getListOfFiles() {
        return listOfFiles;
    }

    public static void setListOfFiles(File[] listOfFiles) {
        EmlAnalyzer.listOfFiles = listOfFiles;
    }

    public static void setSourceFolder(File sourceFolder) {
        EmlAnalyzer.sourceFolder = sourceFolder;
    }

    public static File getDestinationFolder() {
        return destinationFolder;
    }

    public static void setDestinationFolder(File destinationFolder) {
        EmlAnalyzer.destinationFolder = destinationFolder;
    }
}
