package com.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {

    //邮件服务器的登录验证  
    private MailAuthenticator authenticator;  
    //邮箱发送会话  
    private Session session;  
  
    public MailSender(String username, String password) {  
    	
    	authenticator = new MailAuthenticator(username,password);  
    	
    	//根据发件人账号解析出smtp服务器 
        String smtpHost = "smtp." + username.split("@")[1];  
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.host", smtpHost);  
        
        session = Session.getInstance(props, authenticator);  
    }  
    
    /**
     * @param toList 收件人
     * @param subject 主题
     * @param content 正文  
     * @throws MessagingException 
     * @throws AddressException 
     */
    public void sendSimple(List<String> toList, 
    		String subject, String content) throws AddressException, MessagingException  {  
        
        MimeMessage message = new MimeMessage(session); 
        //发件人  
        message.setFrom(new InternetAddress(authenticator.getUsername()));  
        
        InternetAddress[] toAddress = new InternetAddress[toList.size()];  
        for(int i=0; i< toAddress.length; i++) {  
        	toAddress[i] = new InternetAddress(toList.get(i));  
        }  
        //收件人   
        message.setRecipients(RecipientType.TO, toAddress);  
          
        //主题  
        message.setSubject(subject);  
        //正文  
        message.setContent(content, "text/html;charset=utf-8");  
        
        Transport.send(message);  
    }  
    
    /**
     * @param toList 收件人
     * @param ccList 抄送人
     * @param subject 主题
     * @param content 正文 
     * @param attachList 附件(文件路径)
     * @param fileNameList 文件名
     * @throws MessagingException 
     * @throws AddressException 
     */
    public void send(List<String> toList, List<String> ccList,
    		String subject, String content,
    		List<String> attachList, List<String> fileNameList ) throws AddressException, MessagingException  {  
        
        MimeMessage message = new MimeMessage(session);  
        //发件人  
        message.setFrom(new InternetAddress(authenticator.getUsername()));  
        
        InternetAddress[] toAddress = new InternetAddress[toList.size()];  
        for(int i=0; i< toAddress.length; i++) {  
        	toAddress[i] = new InternetAddress(toList.get(i));  
        }  
        //收件人   
        message.setRecipients(RecipientType.TO, toAddress);  
        
  		if ( ccList != null ) {
  			InternetAddress[] ccAddress = new InternetAddress[ccList.size()];
			for (int i = 0; i < ccAddress.length; i++) {
				ccAddress[i] = new InternetAddress(ccList.get(i));
			}
			//抄送人
			message.setRecipients(RecipientType.CC, ccAddress);
  		}
          
        //主题  
        message.setSubject(subject);  
        
        //正文  
        BodyPart contentPart = new MimeBodyPart();  
        contentPart.setContent(content, "text/html;charset=utf-8"); 
        
        Multipart multipart = new MimeMultipart();   
        multipart.addBodyPart(contentPart);  
        
        //附件  
        if ( attachList != null ){
	        for(int i = 0; i < attachList.size(); i++) {  
	        	String attach = attachList.get(i);
	            BodyPart attachmentPart = new MimeBodyPart();  
	            File attachFile = new File(attach);
	            attachmentPart.setDataHandler( new DataHandler( new FileDataSource(attachFile) ) ); 
	            
	            //
	            String fileName = attachFile.getName();
	            if ( fileNameList != null ){
	            	String suffix = fileName.substring( fileName.lastIndexOf(".") );
	            	fileName = fileNameList.get(i) + suffix;
	            }
	            System.out.println(fileName);
	            attachmentPart.setFileName(fileName);
	            
	            multipart.addBodyPart(attachmentPart);  
	        }   
        }
        
        message.setContent(multipart, "text/html;charset=utf-8");  
        
        Transport.send(message);   
    }  
    
    public static void main(String[] args) throws AddressException, MessagingException  {
		String username = "victor_shnu@163.com";
		String password = "******";
		MailSender sender = new MailSender(username, password);
		
		String subject = "sub";
		String content = "content";
		List<String> toList = Arrays.asList("781561470@qq.com");
		
		//sender.sendSimple(toList, subject, content);
		
		List<String> ccList = Arrays.asList("2018707377@qq.com");
		List<String> attachList = Arrays.asList( "D:/WorkSpace/Test2015/src/main/java/mail/邮件附件.txt" );
		List<String> fileNameList =  Arrays.asList("邮件附件");
		sender.send(toList, ccList, subject, content, attachList, fileNameList);
	}
}
