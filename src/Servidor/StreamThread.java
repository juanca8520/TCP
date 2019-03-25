package Servidor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class StreamThread extends Thread{

	private int id;
	private String rutaVideo;
	private int puerto;

	public StreamThread(int pId, String pRuta, int pPuerto) {
		id = pId;
		rutaVideo = pRuta;
		puerto = pPuerto;
	}

	@Override
	public void run()
	{
		System.out.println("arranco");
		//		try{
		//			BufferedImage img = ImageIO.read(new File(rutaVideo));
		//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//			ImageIO.write(img, "jpg", baos);
		//			baos.flush();
		//			byte[] buffer = baos.toByteArray();
		//			
		//			DatagramSocket clientSocket = new DatagramSocket();
		//			InetAddress IPAddress = InetAddress.getByName("localhost");
		//			System.out.println(buffer.length);
		//			
		//			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, IPAddress, puerto);
		//			
		//			clientSocket.send(packet);
		//		}
		//		catch (Exception e) 
		//		{
		//			
		//		}

		try
		{
			File file = new File(rutaVideo);
			//System.out.println("Entre al try");
			File archivo = new File(rutaVideo);
			//Scanner sc = new Scanner(System.in); 
			DatagramSocket ds = new DatagramSocket(); 

			InetAddress ip = InetAddress.getLocalHost(); 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream fis = new FileInputStream(file);

			byte[] buf = new byte[4096];
			System.out.println("Tamanho de paquetes: " + buf.length);
			int n;
			while (-1 != (n = fis.read(buf)))
				baos.write(buf, 0, n);

			byte[] videoBytes = baos.toByteArray();


			//byte buf[] = null; 
			int i = 0;

			//byte[] array = Files.readAllBytes(new File(rutaVideo).toPath());
			//System.out.println((array.length/64000) + 1);

			String tam ="t:"+videoBytes.length;
			System.out.println(tam);
			byte[]tamanho = tam.getBytes();

			DatagramPacket text = new DatagramPacket(tamanho, tamanho.length, ip, puerto);
			ds.send(text);
			Thread.sleep(50);
			while (i<videoBytes.length) 
			{ 
				byte[] temp = new byte[4096];
				int j = 0;
				for (; i < videoBytes.length && j<temp.length; i++) {
					temp[j] = videoBytes[i];
					j++;
					//System.out.println(array[i]);
					//System.out.println(i);
				}
				//System.out.println(i);
				String inp = "Hola, prueba"; 
				// convert the String input into the byte array. 
				//buf = inp.getBytes(); 

				// Step 2 : Create the datagramPacket for sending 
				// the data. 
				DatagramPacket DpSend = new DatagramPacket(temp, temp.length, ip, puerto); 
				// Step 3 : invoke the send call to actually send 
				// the data. 
				ds.send(DpSend); 
				//Thread.sleep(1);
				Thread.sleep(0, 1);

				// break the loop if user enters "bye" 
				if (inp.equals("bye")) 
					break; 
			} 
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
