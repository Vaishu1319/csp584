import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
			result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				String usertype = session.getAttribute("usertype").toString();
				System.out.print(usertype);
				switch (usertype){
					case "customer": // user
						result = result + "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Wishlist'><span class='glyphicon'>Wishlist</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>"
						+ "</ul></div></div>";
					break;

					case "admin": //store manager
						result = result + "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='ManageProducts'><span class='glyphicon'>ManageProducts</span></a></li>"
						+ "<li><a href='Data'><span class='glyphicon'>DataAnalytics</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li></ul></div></div>";
					break;
				}
			}
			else
				result = result + "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>"
								+ "<li><a href='Registration'><span class='glyphicon'>Register</span></a></li></ul></div></div>";
				pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		try
		{
			hm = MySqlDataStoreUtilities.selectUser();
		}
		catch(Exception e)
		{
		}
		User user = hm.get(username());
		return user;
	}
	
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		int size = 0;
		try
		{
			orderPayments = MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{

		}
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
				 size=entry.getKey();

		}
		return size;
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		System.out.println("type of product:" + type);		
	}
	// store the payment details for orders
	public void storePayment(int orderId, String orderName,double orderPrice,String userAddress,String creditCardNo,String FirstName,String LastName,
						String City, String State, String zip, String PhoneNumber, String CardName, String Month, String Year, String cvv, String purchaseDate, String shipDate){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		// get the payment details file
		try
		{
			orderPayments = MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{

		}
		if(orderPayments==null)
		{
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
		// if there exist order id already add it into same list for order id or create a new record with order id

		if(!orderPayments.containsKey(orderId)){
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,
		FirstName,LastName,City,State,zip,PhoneNumber,CardName,Month,Year,cvv,purchaseDate,shipDate);
		listOrderPayment.add(orderpayment);	
			
			// add order details into file

			try
			{
				MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,FirstName,LastName,City,State,zip,PhoneNumber,CardName,Month,Year,cvv,purchaseDate,shipDate);
			}
			catch(Exception e)
			{
				System.out.println("inside exception file not written properly");
			}	
	}


	public String storeReview(String PName, String PType, String RName, String Rzip, String RCity, String RState, String UAge, String UGender, String UOccupation, String RRating, String RDate, String RText){
		
		String message = MongoDBDataStoreUtilities.insertReview(username(),PName,PType,RName,Rzip,RCity,RState,UAge,UGender,UOccupation,RRating,RDate,RText);
		
		if(!message.equals("Successfull"))
		{ 
			return "UnSuccessfull";
		}
		else
		{	
			HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
			try
			{
				reviews=MongoDBDataStoreUtilities.selectReview();
			}
			catch(Exception e)
			{
				
			}
			if(reviews==null)
			{
				reviews = new HashMap<String, ArrayList<Review>>();
			}
				// if there exist product review already add it into same list for productname or create a new record with product name
				
			if(!reviews.containsKey(PName)){	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(PName, arr);
			}
			ArrayList<Review> listReview = reviews.get(PName);		
			Review review = new Review(username(),PName,PType,RName,Rzip,RCity,RState,UAge,UGender,UOccupation,RRating,RDate,RText);
			listReview.add(review);	
				
				// add Reviews into database
			return "Successfull";	
		}
	}
}
