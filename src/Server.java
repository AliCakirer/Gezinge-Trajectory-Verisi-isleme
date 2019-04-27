
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Server {
	public static double[][] sayi=new double[1000][2];
	public static ArrayList<String> gelen_veri = new ArrayList<>();
	public static ArrayList<Double> gelen_veri_bolunmus = new ArrayList<>();
	public static List<Point> gelenveri = new ArrayList<Point>();
	public static int sayac=0;

	private static class Point extends Pair<Double, Double> {
		Point(Double key, Double value) {
			super(key, value);
		}

		@Override
		public String toString() {
			return String.format("(%f, %f)", getKey(), getValue());
		}
	}
	
	public static void server() throws IOException
{
	String clientGelen,deger;
    ServerSocket serverSocket = null;
    Socket clientSocket = null;

    

    try {
         //*Server 7755 portundan Client'ý dinliyor *//
         
        	 serverSocket = new ServerSocket(7755);
        	 
    } catch (Exception e) {
         System.out.println("Port Hatasý!");
    }
    System.out.println("SERVER BAÞLANTI ÝÇÝN HAZIR...");
    
    //* Baðlantý saðlamadan program bir alt satýrdaki kod parçasýna geçmez (accept) *//
  
    	    clientSocket = serverSocket.accept();

    

    //* Client'a veri gönderimi için kullandýðýmýz PrintWriter nesnesi oluþturulur *//
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

    //* Client'dan gelen verileri tutan BufferedReader nesnesi oluþturulur *//
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    BufferedReader data = new BufferedReader(new InputStreamReader(System.in));

    int i=0,j=0;
    
 
    while((clientGelen = in.readLine()) != null) {  //Clientten gelen verileri indirgemek icin double a cevirip ikili-ikili sayi matrisine atýyor.
         
    	
    	System.out.println("Client'dan gelen veri = " + clientGelen);
    	 gelen_veri.add(clientGelen);
    
    	  indirgeme_islemi b=new indirgeme_islemi();
    	 /* if(gelen_veri.size()>2)
    	  {
    		
    		 
    	  }*/
    	  out.println("1");
    	  
    	sayac++;
  	  
    	//out.println(indirgeme_islemi.yollamaList);  

    }
	  
	  for (int j2 = 0; j2 < gelen_veri.size(); j2++) {
			
	    	String sp[]=Server.gelen_veri.get(j2).split(",");
	    	for (int k = 0; k < 1; k++) {
				gelen_veri_bolunmus.add(Double.parseDouble(sp[0]));
				gelen_veri_bolunmus.add(Double.parseDouble(sp[1]));
	           // gelenveri.add(Double.parseDouble(sp[0]),Double.parseDouble(sp[1]));
			}
	    	
		}
    
    
    indirgeme_islemi b=new indirgeme_islemi();

    System.out.println(gelen_veri_bolunmus.size());
	  //b.indirgeme(); 
   
    System.out.println("sa");
    
   // System.out.println(gelen_veri_bolunmus.size());
    //indirgeme_islemi b=new indirgeme_islemi();
    b.indirgeme();
     
      //deger=in.readLine();
	 // out.println(deger);
  
    
   
    out.close();
    in.close();
    clientSocket.close();
    serverSocket.close();

}
}
	
	
