package sample;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.teamchat.client.annotations.OnMsg;
import com.teamchat.client.sdk.Room;
import com.teamchat.client.sdk.TeamchatAPI;
import com.teamchat.client.sdk.chatlets.TextChatlet;
import com.teamchat.client.sdk.impl.TeamchatAPIImpl;


@WebServlet("/trigger_tc_b2p_bot")
public class API extends HttpServlet {
   
	TeamchatAPI api = TeamchatAPIImpl.fromFile(constants_api.fileSample)
			.setEmail(constants_api.botmail)
			.setPassword(constants_api.pw).startReceivingEvents(new botCode()) ;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//String membermail = request.getParameter("membermail") ;
		String AdditionalMessage = request.getParameter("additional_message");
		
//		String membermail="mallikagogoi7@gmail.com";
//		String AdditionalMessage="22/4/2016 test1";
//		Room room =api.context().create()
//	  			.add(membermail)
//	  			.welcome("Hi"); // This message is necessory
//		String roomid = room.getId();
//		
//		api.perform(
//				api.context().byId(roomid).post
//				(
//						new TextChatlet(constants_api.trigger_message + "\n" + AdditionalMessage))
//				);
//		
		String membermail="56aefe70e4b018a7fa635fa9@566930fee4b0b9734bc9c024.tc.im";
		
		Room r = api.context().create()
				.add(membermail);
				

		api.perform(r.post(new TextChatlet("Hi")));
		
		String roomid = r.getId();
		System.out.println("RoomId Servlet Is:"+roomid);
		
		api.perform(
		api.context().byId(roomid).post
		(
				new TextChatlet(constants_api.trigger_message + "\n" +AdditionalMessage))
		);
      
		
		
		JSONObject j = new JSONObject();
		try {
			j.put("membermail", membermail);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			j.put("roomid",roomid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			j.put("botmail",constants_api.botmail);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out= response.getWriter();
		out.println(j.toString());
		System.out.println("in doget");
		
	}
}