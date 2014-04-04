import com.lowagie.text.DocumentException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Adam on 11.03.14.
 */
public class MainWindow extends JFrame {

    private static final String titlePlaceholder = "TITLE_FIELD";
    private static final String datePlaceholder = "DATE_FIELD";
    private static final String starterPlaceholder = "STARTER_FIELD";
    private static final String mainOnePlaceholder = "MAINONE_FIELD";
    private static final String mainTwoPlaceholder = "MAINTWO_FIELD";
    private static final String dessertPlaceholder = "DESSERT_FIELD";
    private static final String pricePlaceholder = "PRICE_FIELD";

    private static final String starterAdditionPlaceholder = "STARTER_ADDITION_FIELD";
    private static final String mainOneAdditionPlaceholder = "MAINONE_ADDITION_FIELD";
    private static final String mainTwoAdditionPlaceholder = "MAINTWO_ADDITION_FIELD";
    private static final String dessertAdditionPlaceholder = "DESSERT_ADDITION_FIELD";

    private JCheckBox shareOnlineCheckBox;
    private JTextField titleTextField;
    private JTextField starterTextField;
    private JTextField mainOneTextField;
    private JTextField mainTwoTextField;
    private JTextField dessertTextField;
    private JTextField priceTextField;
    private JButton finishButton;
    private JPanel rootPanel;
    private JTextField starterAdditionTextField;
    private JTextField mainOneAdditionTextField;
    private JTextField mainTwoAdditionTextField;
    private JTextField dessertAdditionTextField;
    private JTextField dateTextField;

    private static final String HOST = "msplins10.bon.at";
    private static final int PORT = 21;

    public MainWindow() throws HeadlessException {
        super("Sonntagskarten Generator");
       /* try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/
        setContentPane(rootPanel);

        Calendar now = Calendar.getInstance();
        int weekday = now.get(Calendar.DAY_OF_WEEK);
        if (weekday != Calendar.SUNDAY)
        {
            // calculate how much to add
            // the 2 is the difference between Saturday and Monday
            int days = (Calendar.SATURDAY - weekday + 1) % 7;
            now.add(Calendar.DAY_OF_YEAR, days);
        }
        // now is the date you want
        Date date = now.getTime();
        String format = new SimpleDateFormat("dd.M.yyyy").format(date);
        dateTextField.setText(format);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        finishButton.addActionListener(finishedButtonPressedListener);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private String PASSWORD = "frvofuJx";
    private String USERNAME = "b976345645@der-wirt.at";
    private ActionListener finishedButtonPressedListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String inputFile = "html-templates/template.html";
                File file = new File(inputFile);
                File outputfile = null;
                List<String> content = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

                StringBuffer buf = new StringBuffer();
                for (String s : content) {
                    if (s.contains(titlePlaceholder)) {
                        String text = titleTextField.getText();
                        s = s.replace(titlePlaceholder, text);
                    } else if (s.contains(datePlaceholder)) {
                        s = s.replace(datePlaceholder, dateTextField.getText());
                    } else if (s.contains(starterPlaceholder)) {
                        s = s.replace(starterPlaceholder, starterTextField.getText());
                    } else if (s.contains(mainOnePlaceholder)) {
                        s = s.replace(mainOnePlaceholder, mainOneTextField.getText());
                    } else if (s.contains(mainTwoPlaceholder)) {
                        s = s.replace(mainTwoPlaceholder, mainTwoTextField.getText());
                    } else if (s.contains(dessertPlaceholder)) {
                        s = s.replace(dessertPlaceholder, dessertTextField.getText());
                    } else if (s.contains(pricePlaceholder)) {
                        s = s.replace(pricePlaceholder, priceTextField.getText());
                    } else if (s.contains(starterAdditionPlaceholder)) {
                        s = s.replace(starterAdditionPlaceholder, starterAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : starterAdditionTextField.getText());
                    } else if (s.contains(mainOneAdditionPlaceholder)) {
                        s = s.replace(mainOneAdditionPlaceholder, mainOneAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : mainOneAdditionTextField.getText());
                    } else if (s.contains(mainTwoAdditionPlaceholder)) {
                        s = s.replace(mainTwoAdditionPlaceholder, mainTwoAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : mainTwoAdditionTextField.getText());
                    } else if (s.contains(dessertAdditionPlaceholder)) {
                        s = s.replace(dessertAdditionPlaceholder, dessertAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : dessertAdditionTextField.getText());
                    }

                    buf.append(s);

                }

                ITextRenderer renderer = new ITextRenderer();
                renderer.getFontResolver().addFont("html-templates/Vivaldi.ttf", true);
                renderer.setDocumentFromString(buf.toString());
                renderer.layout();

                JFileChooser c = new JFileChooser();
                // Demonstrate "Save" dialog:
                int rVal = c.showSaveDialog(MainWindow.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {

                    outputfile = c.getSelectedFile();
                    if (outputfile != null) {
                        try {
                            OutputStream os = new FileOutputStream(outputfile);
                            renderer.createPDF(os);
                            os.close();

                            //Check if checkbox is selected
                            if (shareOnlineCheckBox.isSelected() && outputfile != null) {
                                //Publish online
                                FTPClient client = new FTPClient();
                                client.connect(HOST, PORT);
                                int reply = client.getReplyCode();
                                if (FTPReply.isPositiveCompletion(reply)) {
                                    if (client.login(USERNAME, PASSWORD)) {
                                        InputStream in = new FileInputStream(outputfile);
                                        client.storeFile("/var/www/html/data/sonntagskarte.pdf", in);
                                        in.close();
                                    } else {
                                        client.logout();
                                        client.disconnect();
                                    }
                                } else {
                                    client.disconnect();
                                }
                            }

                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (DocumentException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            } catch (DocumentException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    };


}
