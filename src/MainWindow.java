import com.lowagie.text.DocumentException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
    private static final String starterPlaceholder2 = "STARTER2_FIELD";
    private static final String starterPlaceholder3 = "STARTER3_FIELD";
    private static final String mainOnePlaceholder = "MAINONE_FIELD";
    private static final String mainOnePlaceholder2 = "MAINONE2_FIELD";
    private static final String mainOnePlaceholder3 = "MAINONE3_FIELD";
    private static final String mainTwoPlaceholder = "MAINTWO_FIELD";
    private static final String mainTwoPlaceholder2 = "MAINTWO2_FIELD";
    private static final String mainTwoPlaceholder3 = "MAINTWO3_FIELD";
    private static final String dessertPlaceholder = "DESSERT_FIELD";
    private static final String dessertPlaceholder2 = "DESSERT2_FIELD";
    private static final String dessertPlaceholder3 = "DESSERT3_FIELD";
    private static final String pricePlaceholder = "TOTAL_PRICE_FIELD";
    private static final String startPricePlaceholder = "STARTER_PRICE_FIELD";
    private static final String mainOnePricePlaceholder = "MAINONE_PRICE_FIELD";
    private static final String mainTwoPricePlaceholder = "MAINTWO_PRICE_FIELD";
    private static final String dessertPricePlaceholder = "DESSERT_PRICE_FIELD";

    private static final String starterAdditionPlaceholder = "STARTER_ADDITION_FIELD";
    private static final String starterAdditionPlaceholder2 = "STARTER_ADDITION2_FIELD";
    private static final String mainOneAdditionPlaceholder = "MAINONE_ADDITION_FIELD";
    private static final String mainOneAdditionPlaceholder2 = "MAINONE_ADDITION2_FIELD";
    private static final String mainTwoAdditionPlaceholder = "MAINTWO_ADDITION_FIELD";
    private static final String mainTwoAdditionPlaceholder2 = "MAINTWO_ADDITION2_FIELD";
    private static final String dessertAdditionPlaceholder = "DESSERT_ADDITION_FIELD";
    private static final String dessertAdditionPlaceholder2 = "DESSERT_ADDITION2_FIELD";

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
    private JTextField starterPriceField;
    private JTextField mainOnePriceTextField;
    private JTextField mainTwoPriceTextField;
    private JTextField dessertPriceTextField;
    private JTextField starterTextField2;
    private JTextField starterTextField3;
    private JTextField mainOneTextField2;
    private JTextField mainOneTextField3;
    private JTextField mainTwoTextField2;
    private JTextField mainTwoTextField3;
    private JTextField mainTwoAdditionTextField2;
    private JTextField mainOneAdditionTextField2;
    private JTextField starterAdditionTextField2;
    private JTextField dessertTextField2;
    private JTextField dessertTextField3;
    private JTextField dessertAdditionTextField2;

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
        if (weekday != Calendar.SUNDAY) {
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

                System.out.println("File has been read.");

                StringBuffer buf = new StringBuffer();
                for (String s : content) {
                    if (s.contains(titlePlaceholder)) {
                        String text = titleTextField.getText();
                        s = s.replace(titlePlaceholder, text);
                    } else if (s.contains(datePlaceholder)) {
                        s = s.replace(datePlaceholder, dateTextField.getText());
                    } else if (s.contains(starterPlaceholder)) {
                        s = s.replace(starterPlaceholder, starterTextField.getText());
                    } else if (s.contains(starterPlaceholder2)) {
                        s = s.replace(starterPlaceholder2, starterTextField2.getText());
                    } else if (s.contains(starterPlaceholder3)) {
                        s = s.replace(starterPlaceholder3, starterTextField3.getText());
                    } else if (s.contains(mainOnePlaceholder)) {
                        s = s.replace(mainOnePlaceholder, mainOneTextField.getText());
                    } else if (s.contains(mainOnePlaceholder2)) {
                        s = s.replace(mainOnePlaceholder2, mainOneTextField2.getText());
                    } else if (s.contains(mainOnePlaceholder3)) {
                        s = s.replace(mainOnePlaceholder3, mainOneTextField3.getText());
                    } else if (s.contains(mainTwoPlaceholder)) {
                        s = s.replace(mainTwoPlaceholder, mainTwoTextField.getText());
                    } else if (s.contains(mainTwoPlaceholder2)) {
                        s = s.replace(mainTwoPlaceholder2, mainTwoTextField2.getText());
                    } else if (s.contains(mainTwoPlaceholder3)) {
                        s = s.replace(mainTwoPlaceholder3, mainTwoTextField3.getText());
                    } else if (s.contains(dessertPlaceholder)) {
                        s = s.replace(dessertPlaceholder, dessertTextField.getText());
                    } else if (s.contains(dessertPlaceholder2)) {
                        s = s.replace(dessertPlaceholder2, dessertTextField2.getText());
                    } else if (s.contains(dessertPlaceholder3)) {
                        s = s.replace(dessertPlaceholder3, dessertTextField3.getText());
                    } else if (s.contains(pricePlaceholder)) {
                        s = s.replace(pricePlaceholder, priceTextField.getText());
                    } else if (s.contains(starterAdditionPlaceholder)) {
                        s = s.replace(starterAdditionPlaceholder, starterAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : starterAdditionTextField.getText());
                    } else if (s.contains(starterAdditionPlaceholder2)) {
                        s = s.replace(starterAdditionPlaceholder2, starterAdditionTextField2.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : starterAdditionTextField2.getText());
                    } else if (s.contains(mainOneAdditionPlaceholder)) {
                        s = s.replace(mainOneAdditionPlaceholder, mainOneAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : mainOneAdditionTextField.getText());
                    } else if (s.contains(mainOneAdditionPlaceholder2)) {
                        s = s.replace(mainOneAdditionPlaceholder2, mainOneAdditionTextField2.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : mainOneAdditionTextField2.getText());
                    } else if (s.contains(mainTwoAdditionPlaceholder)) {
                        s = s.replace(mainTwoAdditionPlaceholder, mainTwoAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : mainTwoAdditionTextField.getText());
                    } else if (s.contains(mainTwoAdditionPlaceholder2)) {
                        s = s.replace(mainTwoAdditionPlaceholder2, mainTwoAdditionTextField2.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : mainTwoAdditionTextField2.getText());
                    } else if (s.contains(dessertAdditionPlaceholder)) {
                        s = s.replace(dessertAdditionPlaceholder, dessertAdditionTextField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : dessertAdditionTextField.getText());
                    } else if (s.contains(dessertAdditionPlaceholder2)) {
                        s = s.replace(dessertAdditionPlaceholder2, dessertAdditionTextField2.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : dessertAdditionTextField2.getText());
                    } else if (s.contains(startPricePlaceholder)) {
                        s = s.replace(startPricePlaceholder, starterPriceField.getText().isEmpty() ? "﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿ ﻿\n" : starterPriceField.getText());
                    } else if (s.contains(mainOnePricePlaceholder)) {
                        s = s.replace(mainOnePricePlaceholder, mainOnePriceTextField.getText());
                    } else if (s.contains(mainTwoPricePlaceholder)) {
                        s = s.replace(mainTwoPricePlaceholder, mainTwoPriceTextField.getText());
                    } else if (s.contains(dessertPricePlaceholder)) {
                        s = s.replace(dessertPricePlaceholder, dessertPriceTextField.getText());
                    }
                    buf.append(s);

                }


                File workingDirectory = file.getParentFile();
                String workingDir = workingDirectory.toURI().toString();
                boolean isDir = workingDirectory.isDirectory();
                ITextRenderer renderer = new ITextRenderer();
                String newText = buf.toString();
                //Document document = XMLResource.load(new ByteArrayInputStream(newText.getBytes(Charset.forName("UTF-8")))).getDocument();
                Document document = null;

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                try
                {
                    builder = factory.newDocumentBuilder();
                    document = builder.parse( new InputSource( new ByteArrayInputStream( newText.getBytes("UTF-8") ) ) );
                } catch (Exception x) {
                    System.out.println("ERROR WHILE PARSING YO");
                    x.printStackTrace();
                }

               /* renderer.getFontResolver().addFont("html-templates/Vivaldi_mn.ttf", "UTF-8", true);
                renderer.getFontResolver().addFont("html-templates/plategoth.ttf", "UTF-8", true);*/
                renderer.setDocument(document, workingDir);
                renderer.layout();

                System.out.println("Document has been rendered");

                JFileChooser c = new JFileChooser();
                // Demonstrate "Save" dialog:
                int rVal = c.showSaveDialog(MainWindow.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    outputfile = c.getSelectedFile();
                    String outputFileName = outputfile.getAbsolutePath();
                    if(!outputFileName.endsWith(".pdf")){
                        outputFileName+=".pdf";
                    }
                    outputfile = new File(outputFileName);
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
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };


}
