package sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.teamchat.client.annotations.OnKeyword;
import com.teamchat.client.annotations.OnMsg;
import com.teamchat.client.sdk.TeamchatAPI;
import com.teamchat.client.sdk.chatlets.TextChatlet;
import com.teamchat.client.sdk.impl.TeamchatAPIImpl;


public class botCode {
	
//	@OnKeyword("hi")
//	public void onkeyword_1(TeamchatAPI api) {	
//		api.performPostInCurrentRoom(new TextChatlet(
//				"PLease send 'Key' to start your BOT ROUTINE "));
//	}
//	
//	
//	@OnKeyword("key")
//	public void onkeyword_2(TeamchatAPI api) {
//	
//		api.performPostInCurrentRoom(new TextChatlet("PLease "
//				+ "write code to start BOT ROUTINE "));
		
//	}

	@OnMsg
	public void onMessage(TeamchatAPI api) {
			// replace Ignore case with suitable Issueless method
		
		if(!api.context().currentSender().getEmail()
				.toString().equalsIgnoreCase(constants_api.botmail) )
		{ 	
			String msg = api.context().currentChatlet().raw().toString();
			String userMail=api.context().currentSender().getEmail().toString();
			System.out.println("Message from user : "+msg);
			System.out.println("User Mail Id Is:  : "+userMail);
			

			try {
				String responseText = sendPostTOPHP(api.context().currentRoom().getId().toString(),msg,userMail);
				api.performPostInCurrentRoom(new TextChatlet(responseText));
				
			} catch (Exception e) {
								e.printStackTrace();
			}
			
		}
		else
		{
			// System.out.println("Khud ka message pakda IGNORE");
		}
		
	}

public String USER_AGENT = "Mozilla/5.0";
	
	private String sendPostTOPHP(String roomid ,String msg, String userMail) throws Exception {
		/*
		 POST: Users/notify

			    Manditory fields:
			              type
			              roomid
			              title
			              message 
		 */
		JSONObject context = new JSONObject();
		context.put("botname", "TeamchatBot_"+userMail);
		context.put("channel", "teamchat");
		context.put("contextid", roomid);
		context.put("type", "msg");
		
					String urlParameters = 
				"?context="+URLEncoder.encode(context.toString())
				+"&message="+URLEncoder.encode(msg)
			    +"&userMail="+URLEncoder.encode(userMail);
					
				String omniHandlerUrl = "http://52.33.177.69:8080/NewRegistartionBot/";	
					
					URL obj = new URL(omniHandlerUrl+urlParameters);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();

					//add reuqest header
					con.setRequestMethod("GET");
					con.setRequestProperty("User-Agent", USER_AGENT);
					con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
					
					BufferedReader in = new BufferedReader(
					        new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
						
					}
					in.close();
					
					//print result
					return response.toString();

				}



}
