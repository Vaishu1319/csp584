import java.sql.*;
import java.util.*;
                	
public class MySqlDataStoreUtilities
{
static Connection conn = null;

public static void getConnection()
{

	try
	{
	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthhub","root","root");
	}
	catch(Exception e)
	{
	
	}
}


public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			
	}
}

public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo,String FirstName,
		String LastName, String City, String State, String zip, String PhoneNumber, String CardName, String Month, String Year, String cvv, String purchaseDate, String shipDate)
{
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo,FirstName,LastName,City,State,zip,PhoneNumber,CardName,Month,Year,cvv,purchaseDate,shipDate) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setString(3,orderName);
		pst.setDouble(4,orderPrice);
		pst.setString(5,userAddress);
		pst.setString(6,creditCardNo);
		pst.setString(7,FirstName);
		pst.setString(8,LastName);
		pst.setString(9,City);
		pst.setString(10,State);
		pst.setString(11,zip);
		pst.setString(12,PhoneNumber);
		pst.setString(13,CardName);
		pst.setString(14,Month);
		pst.setString(15,Year);
		pst.setString(16,cvv);
		pst.setString(17,purchaseDate);
		pst.setString(18,shipDate);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
		
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("OrderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
			//System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),
				rs.getString("userAddress"),rs.getString("creditCardNo"),rs.getString("FirstName"),rs.getString("LastName"),rs.getString("City"),
				rs.getString("State"),rs.getString("zip"),rs.getString("PhoneNumber"),rs.getString("CardName"),rs.getString("Month"),
				rs.getString("Year"),rs.getString("cvv"),rs.getString("purchaseDate"),rs.getString("shipDate"));
			listOrderPayment.add(order);
					
		}
				
					
	}
	catch(Exception e)
	{
		
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String firstname,String lastname,String email,String usertype)
{
	try
	{
		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,firstname,lastname,email,usertype) "
		+ "VALUES (?,?,?,?,?,?,?);";	
				
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,firstname);
		pst.setString(5,lastname);
		pst.setString(6,email);
		pst.setString(7,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
		{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("email"),rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,StoreDetail> getStoreDetails()
{	
	HashMap<String,StoreDetail> hm=new HashMap<String,StoreDetail>();
	try 
	{
		getConnection();		
		String selectStoreDetail = "select * from storedetails";
		PreparedStatement pst = conn.prepareStatement(selectStoreDetail);
		ResultSet rs = pst.executeQuery();
	
		if (rs.next() == false) { System.out.println("ResultSet in empty in Java"); }
		int rowCount = 0;
		do {
			rowCount++;
			StoreDetail storedetails = new StoreDetail(rs.getInt("id"),rs.getString("storeId"),rs.getString("street"),rs.getString("city"),rs.getString("state"),rs.getString("zipCode"));
			hm.put(rs.getString("id"), storedetails);
		
		}
		while(rs.next());
		System.out.println("rowcount: " + rowCount);
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static void insertProduct(String ProductType,String Id,String productName,double productPrice,String productImage,String productManufacturer,
	String productCondition, Double productDiscount, Double manufacturerRebate, Integer count)
{
	try
	{	

		getConnection();
		String insertIntoProductDetailsQuery = "INSERT INTO Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,rebateAmount,count) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement pst = conn.prepareStatement(insertIntoProductDetailsQuery);
		pst.setString(1,ProductType);
		pst.setString(2,Id);
		pst.setString(3,productName);
		pst.setDouble(4,productPrice);
		pst.setString(5,productImage);
		pst.setString(6,productManufacturer);
		pst.setString(7,productCondition);
		pst.setDouble(8,productDiscount);
		pst.setDouble(9,manufacturerRebate);
		pst.setInt(10,count);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static void insertProvider(String id, String name, String staddress, String city, String state, String zipcode, String speciality, String image, String experience, Double fees, String type, String latitude, String longitude)
{
	try
	{	
		//System.out.println("string name" + name);
		
		getConnection();
		String insertIntoProvidersListQuery = "INSERT INTO providerslist(id,name,staddress,city,state,zipcode,speciality,image,experience,fees, type,latitude,longitude) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
		//System.out.println("inside insert doctor");
		
		PreparedStatement pst = conn.prepareStatement(insertIntoProvidersListQuery);
		pst.setString(1,id);
		pst.setString(2,name);
		pst.setString(3,staddress);
		pst.setString(4,city);
		pst.setString(5,state);
		pst.setString(6,zipcode);
		pst.setString(7,speciality);
		pst.setString(8,image);
		pst.setString(9,experience);
		pst.setDouble(10,fees);
		pst.setString(11,type);
		pst.setString(12,latitude);
		pst.setString(13,longitude);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String,Doctor> getDoctors()
{	
	HashMap<String,Doctor> hm=new HashMap<String,Doctor>();
	try 
	{
		getConnection();
		
		Statement stmt=conn.createStatement();
		String selectDoctor="select * from providerslist";
		ResultSet rs = stmt.executeQuery(selectDoctor);
		while(rs.next())
		{	
			Doctor doctor = new Doctor(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
			hm.put(rs.getString("Id"), doctor);
			doctor.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,HealthInsurance> getHealthInsurances()
{	
	HashMap<String,HealthInsurance> hm=new HashMap<String,HealthInsurance>();
	try 
	{
		getConnection();
		
		Statement stmt=conn.createStatement();
		String selectHealthInsurance="select * from providerslist";
		ResultSet rs = stmt.executeQuery(selectHealthInsurance);
		while(rs.next())
		{	
			HealthInsurance healthinsurance = new HealthInsurance(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
			hm.put(rs.getString("Id"), healthinsurance);
			healthinsurance.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Hospital> getHospitals()
{	
	HashMap<String,Hospital> hm=new HashMap<String,Hospital>();
	try 
	{
		getConnection();
		
		Statement stmt=conn.createStatement();
		String selectDoctor="select * from providerslist";
		ResultSet rs = stmt.executeQuery(selectDoctor);
		while(rs.next())
		{	
			Hospital hospital = new Hospital(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
			hm.put(rs.getString("Id"), hospital);
			hospital.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,HealthClub> getHealthClubs()
{	
	HashMap<String,HealthClub> hm=new HashMap<String,HealthClub>();
	try 
	{
		getConnection();
		
		Statement stmt=conn.createStatement();
		String selectHealthClub="select * from providerslist";
		ResultSet rs = stmt.executeQuery(selectHealthClub);
		while(rs.next())
		{	
			HealthClub healthclub = new HealthClub(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
			hm.put(rs.getString("Id"), healthclub);
			healthclub.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Pharmacy> getPharmacys()
{	
	HashMap<String,Pharmacy> hm=new HashMap<String,Pharmacy>();
	try 
	{
		getConnection();
		
		Statement stmt=conn.createStatement();
		String selectPharmacy="select * from providerslist";
		ResultSet rs = stmt.executeQuery(selectPharmacy);
		while(rs.next())
		{	
			Pharmacy pharmacy = new Pharmacy(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
			hm.put(rs.getString("Id"), pharmacy);
			pharmacy.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static String addproducts(String ProductType,String Id,String productName,double productPrice,String productImage,String productManufacturer,
	String productCondition,double productDiscount,double rebateAmount,Integer count,String prod)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,rebateAmount,count)" +
		"VALUES (?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement pst = conn.prepareStatement(addProductQurey);
		pst.setString(1,ProductType);
		pst.setString(2,Id);
		pst.setString(3,productName);
		pst.setDouble(4,productPrice);
		pst.setString(5,productImage);
		pst.setString(6,productManufacturer);
		pst.setString(7,productCondition);
		pst.setDouble(8,productDiscount);
		pst.setDouble(9,rebateAmount);
		pst.setInt(10,count);
		pst.executeUpdate();

		try{
			if (!prod.isEmpty())
			{
				String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
				"VALUES (?,?);";
				PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
				pst1.setString(1,prod);
				pst1.setString(2,Id);
				pst1.executeUpdate();
			}
		}
		catch(Exception e)
		{
			msg = "Error while adding the product";
			e.printStackTrace();
	
		}	
	}
	catch(Exception e)
	{
		msg = "Error while adding the product";
		e.printStackTrace();	
	}
	return msg;
}


public static String updateproducts(String ProductType,String Id,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,double rebateAmount,Integer count)
{
    String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=?,rebateAmount=?,count=? where Id =?;" ;
		   
		PreparedStatement pst = conn.prepareStatement(updateProductQurey);
		
		pst.setString(1,productName);
		pst.setDouble(2,productPrice);
		pst.setString(3,productImage);
		pst.setString(4,productManufacturer);
		pst.setString(5,productCondition);
		pst.setDouble(6,productDiscount);
		pst.setDouble(7,rebateAmount);
		pst.setInt(8,count);
		pst.setString(9,Id);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();	
	}
	return msg;	
}


public static String deleteproducts(String Id)
{   String msg = "Product is deleted successfully";
	try
	{
		
		getConnection();
		String deleteproductsQuery ="Delete from Productdetails where Id=?";
		PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
		pst.setString(1,Id);
		
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			msg = "Proudct cannot be deleted";
	}
	return msg;
}

public static String[] getLatLong(String zipCode)
{   
	//System.out.println("Inside Mysql zipCode: " + zipCode);
	String latlon[] = new String[2];
	try
	{	
		getConnection();
		String getLatLongQuery = "select * from latlonlist where zipCode=?";
		PreparedStatement pst = conn.prepareStatement(getLatLongQuery);
		pst.setString(1, zipCode);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
		//	System.out.println("Inside while loop");
			latlon[0] = rs.getString("latitude");
			latlon[1] = rs.getString("longitude");
		}
	}
	catch(Exception e)
	{
	}
	//System.out.println("after while loop: " + latlon);
	return latlon;
}

public static HashMap<String,Provider> getProviders(String pType, String zipCode)
{	
	//System.out.println("inside mysql getProviders");
	HashMap<String,Provider> hm=new HashMap<String,Provider>();
	try 
	{
		//System.out.println("pType in mysql:" + pType);
		getConnection();
		String selectProvider = "select * from providerslist where type=? and zipCode=?";
		PreparedStatement pst = conn.prepareStatement(selectProvider);
		pst.setString(1, pType);
		pst.setString(2, zipCode);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{	
			Provider provider = new Provider(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
			hm.put(rs.getString("Id"), provider);
			provider.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Provider> getDetails(String id)
{	
	HashMap<String,Provider> hm=new HashMap<String,Provider>();
	try 
	{
		getConnection();
		String selectProvider = "select * from providerslist where id=?";
		PreparedStatement pst = conn.prepareStatement(selectProvider);
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{	
			Provider provider = new Provider(rs.getString("name"),rs.getString("staddress"),rs.getString("city"),rs.getString("state"),rs.getString("zipcode"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"),rs.getString("latitude"),rs.getString("longitude"));
			hm.put(rs.getString("Id"), provider);
			provider.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,String[]> selectBooking(String cid)
{   
	HashMap<String,String[]> hm = new HashMap<String,String[]>();
	int count=0;
	try
	{	
		getConnection();
		String getBookingQuery = "select * from bookinglist where cid=?";
		PreparedStatement pst = conn.prepareStatement(getBookingQuery);
		pst.setString(1, cid);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			String bookings[] = new String[6];
			bookings[0] = rs.getString("pname");
			bookings[1] = rs.getString("date");
			bookings[2] = rs.getString("time");
			bookings[3] = rs.getString("crn");
			bookings[4] = rs.getString("fullname");
			bookings[5] = rs.getString("ptype");
			hm.put(rs.getString("crn"), bookings);
			count++;
		}
	}
	catch(Exception e)
	{
	}
	//System.out.println("mysql count: " + count);
	return hm;
}

public static String getBooking(String FullName, String Date, String Time, String PId)
{	
	String res = "No";
	try 
	{
		getConnection();
		String selectBooking = "select * from bookinglist where fullname=? and date=? and time=? and pid=?";
		PreparedStatement pst = conn.prepareStatement(selectBooking);
		pst.setString(1, FullName);
		pst.setString(2, Date);
		pst.setString(3, Time);
		pst.setString(4, PId);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{	
			res = "Yes";
		}
	}
	catch(Exception e)
	{
	}
	return res;			
}

public static String insertBooking(String FullName, String Date, String Time, String PName, String PId, String Crn, String userid, String PType)
{	
	String msg = "Successfully added appointment";
	try
	{	
		getConnection();
		String insertIntoBookingListQuery = "INSERT INTO bookinglist(fullname,date,time,pname,pid,crn,cid,ptype) "
		+ "VALUES (?,?,?,?,?,?,?,?);";
		
		PreparedStatement pst = conn.prepareStatement(insertIntoBookingListQuery);
		pst.setString(1,FullName);
		pst.setString(2,Date);
		pst.setString(3,Time);
		pst.setString(4,PName);
		pst.setString(5,PId);
		pst.setString(6,Crn);
		pst.setString(7,userid);
		pst.setString(8,PType);
		pst.execute();
	}
	catch(Exception e)
	{
		msg = "Error while adding appointment";
		e.printStackTrace();
	}				
	return msg;
}

public static String deleteBooking(String CId, String bookingName)
{
	String msg = "Successfully deleted appointment";
	try
	{
		getConnection();
		String deleteBookingQuery ="Delete from bookinglist where crn=? and pname=?";
		PreparedStatement pst = conn.prepareStatement(deleteBookingQuery);
		pst.setString(1,CId);
		pst.setString(2,bookingName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
		msg = "Error while deleting appointment";
		e.printStackTrace();	
	}
	return msg;
}

public static String getWishlist(String username, String PName, String PId, String PType)
{	
	String res = "No";
	try 
	{
		getConnection();
		String selectWishlist = "select * from wishlist where username=? and pname=? and pid=? and ptype=?";
		PreparedStatement pst = conn.prepareStatement(selectWishlist);
		pst.setString(1, username);
		pst.setString(2, PName);
		pst.setString(3, PId);
		pst.setString(4, PType);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{	
			res = "Yes";
		}
	}
	catch(Exception e)
	{
	}
	return res;			
}

public static String insertWishlist(String username, String PName, String PId, String PType, String PAddress)
{	
	String msg = "Successfully added to wishlist";
	try
	{	
		getConnection();
		String insertIntoWishlistQuery = "INSERT INTO wishlist(username,pname,pid,ptype,paddress) "
		+ "VALUES (?,?,?,?,?);";
		
		PreparedStatement pst = conn.prepareStatement(insertIntoWishlistQuery);
		pst.setString(1,username);
		pst.setString(2,PName);
		pst.setString(3,PId);
		pst.setString(4,PType);
		pst.setString(5,PAddress);
		pst.execute();
	}
	catch(Exception e)
	{
		msg = "Error while adding to wishlist";
		e.printStackTrace();
	}				
	return msg;
}

public static HashMap<String,String[]> selectWishlist(String cid)
{   
	HashMap<String,String[]> hm = new HashMap<String,String[]>();
	int count=0;
	try
	{	
		getConnection();
		String getWishlistQuery = "select * from wishlist where username=?";
		PreparedStatement pst = conn.prepareStatement(getWishlistQuery);
		pst.setString(1, cid);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			String wishlists[] = new String[5];
			wishlists[0] = rs.getString("username");
			wishlists[1] = rs.getString("pname");
			wishlists[2] = rs.getString("pid");
			wishlists[3] = rs.getString("ptype");
			wishlists[4] = rs.getString("paddress");
			hm.put(rs.getString("pid"), wishlists);
			count++;
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}

public static HashMap<String, Product> getInventoryProducts()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select * from Productdetails;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setId(rs.getString("Id"));
			product.setProductType(rs.getString("ProductType"));
			product.setproductName(rs.getString("productName"));
			product.setproductPrice(rs.getDouble("productPrice"));
			product.setproductImage(rs.getString("productImage"));
			product.setproductManufacturer(rs.getString("productManufacturer"));
			product.setproductCondition(rs.getString("productDiscount"));
			product.setproductDiscount(rs.getDouble("productDiscount"));
			product.setrebateAmount(rs.getDouble("rebateAmount"));
			product.setcount(rs.getInt("count"));

			products.put(product.getId(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static String UpdateInventory(String orderName)
{
    String msg = "Product is updated successfully";
	try{
		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET count=count-1 where productName =?;" ;
		PreparedStatement pst = conn.prepareStatement(updateProductQurey);
		pst.setString(1,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
	}
	return msg;
}


public static HashMap<String, Product> getProductsOnSale()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select * from Productdetails where count>0;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setId(rs.getString("Id"));
			product.setProductType(rs.getString("ProductType"));
			product.setproductName(rs.getString("productName"));
			product.setproductPrice(rs.getDouble("productPrice"));
			product.setproductImage(rs.getString("productImage"));
			product.setproductManufacturer(rs.getString("productManufacturer"));
			product.setproductCondition(rs.getString("productDiscount"));
			product.setproductDiscount(rs.getDouble("productDiscount"));
			product.setrebateAmount(rs.getDouble("rebateAmount"));
			product.setcount(rs.getInt("count"));

			products.put(product.getId(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static HashMap<String, Product> getProductsRebate()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select * from Productdetails where rebateAmount>0;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setId(rs.getString("Id"));
			product.setProductType(rs.getString("ProductType"));
			product.setproductName(rs.getString("productName"));
			product.setproductPrice(rs.getDouble("productPrice"));
			product.setproductImage(rs.getString("productImage"));
			product.setproductManufacturer(rs.getString("productManufacturer"));
			product.setproductCondition(rs.getString("productDiscount"));
			product.setproductDiscount(rs.getDouble("productDiscount"));
			product.setrebateAmount(rs.getDouble("rebateAmount"));
			product.setcount(rs.getInt("count"));

			products.put(product.getId(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static HashMap<String, Product> getSalesReport()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select orderName, orderPrice, count(orderName) as cnt, (orderPrice*count(orderName)) as total from customerorders group by orderName;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setproductName(rs.getString("orderName"));
			product.setproductPrice(rs.getDouble("orderPrice"));
			product.setnum_items(rs.getInt("cnt"));
			product.settotal_sales(rs.getDouble("total"));

			products.put(product.getproductName(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static HashMap<String, Product> getDailySales()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select purchaseDate, sum(orderPrice) as total from customerorders group by purchaseDate;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setdates(rs.getString("purchaseDate"));
			product.settotal_sales(rs.getDouble("total"));

			products.put(product.getdates(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}



}
