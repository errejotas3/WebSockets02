package br.edu.ifpb.casa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/serverendpoint")
public class ServerEndPoint {
	String escolhida  = escolhe();
	
	@OnOpen
	public void handleOpen(Session ses) {
		
		System.out.println("client is now connected...");
		System.out.println(escolhida);
		int tamanho = escolhida.length();
		System.out.println(tamanho);
		ArrayList <String> palavra = new ArrayList <String>(); 
		
		
		for(int i=0; i< tamanho; i++) {
			palavra.add("-");
		}
		
		try {
			ses.getBasicRemote().sendText("Bem vindo ao jogo da forca: " + palavra);
		} catch (IOException ex) {
			ex.getStackTrace();
		}
		
	}
	
	@OnMessage
	
	public String handleMessage(String message) {
		System.out.println("receive from client: " + message);
		String[] separada = escolhida.split("");
		boolean contains = false;
		for (String s : separada) {
			if(s == message) {
				contains = true;
				break;
			}
			
		}
		
		
		String replyMessage = "echo " + message;
		System.out.println("send to client: " +replyMessage);
		return replyMessage;
	}
	
	@OnClose
	public void handleClose() {
		System.out.println("client is now disconnected...");
	}
	
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
	
	public String escolhe() {
		String[] palavras  = {"abece", "deehefe", "geagaih"};
		int quantidade = palavras.length;
		Random rand = new Random();
		int value = rand.nextInt(quantidade);
		String escolhida = palavras[value];
		
		return escolhida;
		
	}
}