package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.payload.request.EmailDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Service
public class EmailService {

    TemplateEngine templateEngine; //convert thành giao diện

    JavaMailSender javaMailSender;



    public void sendEmail(EmailDetail emailDetail) {


        try{
            Context context = new Context();
            context.setVariable("name", emailDetail.getReceiver().getEmail());
            context.setVariable("button", "Go to home page");
            context.setVariable("link", emailDetail.getLink());

            String template = templateEngine.process("email-template", context);

            // Tạo mail
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);


            helper.setFrom("bidkoi@gmail.com");
            helper.setTo(emailDetail.getReceiver().getEmail());
            helper.setText(template, true);
            helper.setSubject(emailDetail.getSubject());


            javaMailSender.send(mimeMessage);

        }
        catch (MessagingException e){
            System.out.println("ERROR SEND EMAIL!!!");
        }


    }
}
