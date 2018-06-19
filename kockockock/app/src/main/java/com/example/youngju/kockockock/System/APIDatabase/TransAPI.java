import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class TransAPI extends Thread {
	
	public final static String KOREA = "ko";
	public final static String ENGLISH = "en";
	public final static String JAPAN = "ja";
	public final static String CHINA = "zh-CN";
	public final static String TAIWAN = "zh-TW";
	
	private String text;
	private String source;
	private String dest;
	
	private NetworkIOCallback callback;
	
	public TransAPI(String text, String source, String dest){
		this.text = text;
		this.source = source;
		this.dest = dest;
	}
	
	public void addNetworkIOEndListener(NetworkIOCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public void start() {
		try {
            String apiURL = "https://naveropenapi.apigw.ntruss.com/smt/v1/translation";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientID());
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret());
            
            String postParams = "source="+ URLEncoder.encode(source, "UTF-8") +"&target=" + URLEncoder.encode(dest, "UTF-8") + "&text=" + URLEncoder.encode(text, "UTF-8");
            con.setDoOutput(true);
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            
            JSONObject obj = new JSONObject(response.toString());
            
            callback.ioCallback(obj.getJSONObject("message").getJSONObject("result").getString("translatedText"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String clientID() {
		return "m6j17dr292";
	}
	
	private String clientSecret() {
		return "E12qgjiN14hVRgK0apw6yfZjeP6Lk4ksVRjdCAhl";
	}
}
