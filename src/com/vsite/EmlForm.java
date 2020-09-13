package com.vsite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EmlForm extends JFrame{
    private JPanel mainPanel;
    private JButton btn_SelectOpenFolder;
    private JButton btn_SelectSaveFolder;
    private JLabel lbl_SelectedSourceFolder;
    private JLabel lbl_countOfFiles;
    private JLabel lbl_SelectedDestinationFolder;
    private JButton btn_Run;

    public EmlForm(String title)  {
        super(title);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(500,300);
        btn_SelectOpenFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(getParent());
                if(option == JFileChooser.APPROVE_OPTION){
                    File folder = fileChooser.getSelectedFile();
                    EmlAnalyzer.setSourceFolder(folder);
                    lbl_SelectedSourceFolder.setText("Folder Selected: " + folder.getAbsolutePath());
                    lbl_countOfFiles.setText("Count of files: " + String.valueOf(folder.listFiles().length));
                }else{
                    lbl_SelectedSourceFolder.setText("Open command canceled");
                    lbl_countOfFiles.setText("Count of files: ");
                }
            }
        });
        btn_SelectSaveFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(getParent());
                if(option == JFileChooser.APPROVE_OPTION){
                    File folder = fileChooser.getSelectedFile();
                    EmlAnalyzer.setDestinationFolder(folder);
                    lbl_SelectedDestinationFolder.setText("Selected save folder: " + folder.getAbsolutePath());
                }else{
                    lbl_SelectedDestinationFolder.setText("No save folder selected");
                }
            }
        });
        btn_Run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    EmlAnalyzer.analyze();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new EmlForm("Eml Analyzer");
        frame.setVisible(true);
    }
}
