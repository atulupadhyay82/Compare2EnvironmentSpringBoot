package com.thomsonreuters

import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import java.util.concurrent.CancellationException
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

def sendMail(host, sender, receivers, subject) {

    Properties props = System.getProperties()
    props.put("mail.smtp.host", host)
    Session session = Session.getDefaultInstance(props, null)

    MimeMessage message = new MimeMessage(session)
    message.setFrom(new InternetAddress(sender))
    message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(receivers));
    message.setSubject(subject)



    def filePath ='${env.WORKSPACE}/ExtentReports/ExtentReportResults.html'
    def filePath2 ='$WORKSPACE}/ExtentReports/ExtentReportResults.html'
    println filePath
    println filePath2

    def file= new File(filePath)
    def htmlContent = filePath.text
    println htmlContent


    Multipart multipart = new MimeMultipart("alternative");

    MimeBodyPart htmlPart = new MimeBodyPart();
    htmlPart.setContent(htmlContent, "text/html");
    multipart.addBodyPart(htmlPart);
    message.setContent(multipart);
    //message.setText(multipart)
    println 'Sending mail to ' + receivers + '.'
    Transport.send(message)
    println 'Mail sent.'

}

sendMail('relay.cust.services', 'Atul.Upadhyay@thomsonreuters.com','Atul.Upadhyay@thomsonreuters.com','QA Daily FST Automated Test Result')