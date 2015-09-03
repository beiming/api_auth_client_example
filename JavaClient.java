import java.util.Date;
import java.security.MessageDigest;
import java.util.Base64;
import java.security.DigestException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JavaClient
{	
	public static void main(String[] args)
	{
		Boolean COMPARE_MODE = false;
		Long TARGET_TIME_STAMP = 1441278377L; // compare with $target_url
		String TARGET_URL = "http://127.0.0.1:5000/external-api/v1/orgs/1/courses/2014SA01/scores?aid=UirA_RPyTpueOTL4VZZ4CA==&ts=1441278377&t=onE1DK9sXPEG9VTXvwwJhQ==";
		
		String APP_ID = "UirA_RPyTpueOTL4VZZ4CA==";
		String SECRET_KEY = "e2ccdc1b188fc712ed497be825c5cd4a";

		// client logic
		String api = String.format("external-api/v1/orgs/%s/courses/%s/scores", 1, "2014SA01");
		String postfix = null;
		if(COMPARE_MODE)
		{
			postfix = String.format("?aid=%s&ts=%s", APP_ID, TARGET_TIME_STAMP);
		}
		else
		{
			postfix = String.format("?aid=%s&ts=%s", APP_ID, (int)(System.currentTimeMillis()/1000));
		}

		api += postfix;

		String token = "&t=";
		try
		{
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
        	mdInst.update((api + SECRET_KEY).getBytes());
        	// import java.util.Base64; JAVA8 feature, or use custom base64encode, replace '+/' to '-_' for url safe
         	token += Base64.getUrlEncoder().encodeToString(mdInst.digest());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String url = String.format("http://127.0.0.1:5000/%s%s", api, token);
		System.out.println(url);

		if(COMPARE_MODE)
		{
			System.out.println(url.equals(TARGET_URL));
		}
		else
		{
        	BufferedReader in = null;
        	try
        	{
        		URL realUrl = new URL(url);
				URLConnection connection = realUrl.openConnection();
				connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.connect();
	            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            	String line;
            	while ((line = in.readLine()) != null)
            	{
                	System.out.println(line);
         	   	}
        	}
        	catch (Exception e)
        	{
            	e.printStackTrace();
        	}
        	finally
        	{
            	try
            	{
	                if (in != null) 
	                {
	                    in.close();
	                }
            	}
	            catch (Exception e2)
	            {
	                e2.printStackTrace();
            	}
        	}
		}
	}
}