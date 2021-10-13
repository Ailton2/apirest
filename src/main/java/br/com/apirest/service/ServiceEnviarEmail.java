package br.com.apirest.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class ServiceEnviarEmail {
	
	private String userName = "marcelonasci149@gmail.com";
	private String senha = "025043025043";
	
	public void enviarEmail (String assunto, String emailDestino, String mensagem) throws Exception {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust","*");//Autorizaçao
		properties.put("mail.smtp.auth", true);//Autorizaçao
		properties.put("mail.smtp.starttls", true); //Autenticaçao
		properties.put("mail.smtp.host", "smtp.gmail.com"); //Servidor Google
		properties.put("mail.smtp.port","465");//Porta servidor
		properties.put("mail.smtp.socketFactory.port", "465"); // Expecifica porta socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");// classe conexao socket
		
		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
			
				return new PasswordAuthentication(userName.trim(), senha);
			}
		});
		
		Address[] toUser = InternetAddress.parse(emailDestino);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName)); // quem envia
		message.setRecipients(Message.RecipientType.TO, toUser);//quem recebe
		message.setSubject(assunto);
		message.setText(mensagem);
		
		Transport.send(message);
		
	}

}
