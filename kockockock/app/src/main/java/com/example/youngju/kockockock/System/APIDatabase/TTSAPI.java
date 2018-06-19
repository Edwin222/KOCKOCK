import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javazoom.jl.player.Player;

public class TTSAPI extends Thread {
	
	public static final String KOREA = "mijin";
	public static final String CHINA = "meimei";
	public static final String JAPAN = "yuri";
	public static final String ENGLISH = "clara";
	public static final String SPANISH = "carmen";
	
	private String text;
	private String language;
	
	public TTSAPI(String text, String language) {
		this.text = text;
		this.language = language;
	}
	
	@Override
	public void start() {
		textToString();
	}
	
	public void textToString() {
		
		try {
			URL url = new URL("https://naveropenapi.apigw.ntruss.com/voice/v1/tts");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientID());
			connection.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret());
			
			String postParams = "speaker="+ language + "&speed=0&text=" + URLEncoder.encode(text, "UTF-8");
			connection.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			
			int responseCode = connection.getResponseCode();
			if(responseCode == 200) {
				InputStream is = connection.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                
                File f = new File(this.getName() + ".mp3");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
                
                Player playMp3 = new Player(new FileInputStream(this.getName() + ".mp3"));
                playMp3.play();
			}
			else {  // 에러 발생
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
             
                String inputLine;
                
                StringBuffer response = new StringBuffer();
                
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String clientID() {
		return "4jy9t24pnj";
	}
	
	private String clientSecret() {
		return "lSwC8yvcvvK1EmhYzqLCVPvAXMpws4WSWoSLbXv9";
	}

}