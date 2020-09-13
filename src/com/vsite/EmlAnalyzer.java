package com.vsite;

import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmlAnalyzer {
    private static File [] listOfFiles;
    private static File sourceFolder;
    private static File destinationFolder;
    private static int numberOfEmlsWithAtt=0;

    public static void iterateEmls() throws Exception {
        setListOfFiles(sourceFolder.listFiles());
        if (getListOfFiles() != null) {
            for(File file: getListOfFiles())
            {
                removeAttachmentsAndSave(file);
            }
        }
    }

    public static void removeAttachmentsAndSave(File emlFile) throws Exception{
        InputStream source = new FileInputStream(emlFile);
        Message message = new MimeMessage(Session.getInstance(System.getProperties()),source);
        if(hasAttachments(message)){
            numberOfEmlsWithAtt++;
            Multipart mp = (Multipart) message.getContent();
            for (int i=mp.getCount()-1; i >=0 ;--i){
                MimeBodyPart body = (MimeBodyPart) mp.getBodyPart(i);
                if(!body.isMimeType("text/plain")){
                    mp.removeBodyPart(body);
                }
                //body.setDataHandler(body.getDataHandler());
            }
            message.saveChanges();
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

    public static File getSourceFolder() {
        return sourceFolder;
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

    public static int getNumberOfEmlsWithAtt() {
        return numberOfEmlsWithAtt;
    }
}
