package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.User;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) { 
		  sb.append(temp);
		}
		br.close();
		String jsonstring = sb.toString();
		Gson gson = new Gson();
		User user = gson.fromJson(jsonstring, User.class);
		System.out.println(user.getPassword()+user.getUsername());
		
		String username = user.getUsername();
		String password = user.getPassword();
//		System.out.println(req);
		
		PrintWriter out = resp.getWriter();
		try {

			if (username.equals("jiajia") && password.equals("123456")) {
				// 准许登陆
				out.println("access");
				System.out.println("access");
			} else {
				// 用户名或密码错误
				out.println("deny");
				System.out.println("deny");
			}
		} finally {
			out.close();
		}
//		super.doPost(req, resp);
	}

}
