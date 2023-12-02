package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
public class ForgotPasswordController {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtOTP;

    int random;

    @FXML
    private AnchorPane pwRoot;

    @FXML
    void btnSendOTPOnAction(ActionEvent event) {
        try {

            Random rand = new Random();
            random = rand.nextInt(999999);
            String host = "smtp.gmail.com";
            String user = "sarasinishala@gmail.com";
            String pass = "raux mwck ustp ceus";
            String to = txtEmail.getText();
            String subject = "Resetting code";
            String message = "Your reset code is " + random;
            boolean sessionDebug = false;
            Properties pros = System.getProperties();
            pros.put("mail.smtp.starttls.enable", "true");
            pros.put("mail.smtp.host", "host");
            pros.put("mail.smtp.port", "587");
            pros.put("mail.smtp.auth", "true");
            pros.put("mail.smtp.ssl.protocols", "TLSv1.2");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(pros, null);
            mailSession.setDebug(sessionDebug);


            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(user));
            InternetAddress[] addresses = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setSubject(subject);
            msg.setText(message);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            new Alert(Alert.AlertType.INFORMATION, "Code has been send to the email").show();

        } catch (MessagingException | HeadlessException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
    @FXML
    void chekOTPOnAction(ActionEvent event) throws IOException {
        if (random == Integer.parseInt(txtOTP.getText())){

            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/ForgotPasswordNext.fxml"));

            Scene scene = new Scene(rootNode);
            Stage stage = (Stage) this.pwRoot.getScene().getWindow();

            stage.setTitle("Reset Password");
            stage.setScene(scene);
            stage.centerOnScreen();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Wrong Code").show();
            txtOTP.setText("");
        }
    }

}
