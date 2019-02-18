import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DBConnectionUtil {

	/******************Prod DB Details **********************/

	static String serverName = "us-cdbr-iron-east-03.cleardb.net:3306";
	//static String serverName = "104.197.227.143";
	
	static String mydatabase = "heroku_813d276cc465e94";
	/*---  Database credentials---- */
	static final String USER = "b1162df0501833";
	static final String PASS = "6423989b";
	
	//static String mydatabase = "dealslooterData";
	/*---  Database credentials---- */
	//static final String USER = " dealslooterdb";
	//static final String PASS = "Newboys!25";

	/*************Dev DB Details *********************/
	//static String serverName = "localhost";
	//static String mydatabase = "hotinddeals_db";
	/*---  Database credentials---- */
	//static final String USER = "root";
	//static final String PASS = "admin";

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL  = "jdbc:mysql://" + serverName + "/" + mydatabase; 
	
	//static final String JDBC_DRIVER="com.mysql.jdbc.GoogleDriver";
	//String DB_URL = String.format( "jdbc:mysql://google/%s?cloudSqlInstance=%s" + "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",mydatabase, serverName);

	private static final DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

	String cmbCategory="22";
	//String txtName="xxx";
	String txtDesc="xxx";
	String txtSize="NA";

	String frdStatus="N";
	String LatestStatus="Y";
	String PrmStatus="N";
	String AvlStatus="Y";
	String ItemCondition="New";
	//String QntAvl="200";
	String PrdfullDesc="NA";
	String Brand="NA";
	String Modelno="NA";
	String Dimension="NA";

	String DispSize="NA";
	String PrdFeatures="NA";
	String PrdReviews="NA";

	String PostedBy="arwin10";
	String ReleaseDate="2017-08-03";
	String ReleaseTime="20:06:10";
	//String DealDescp="xxx";
	//String DealLink="http://www.amazon.com";
	String StoreId="1";
	//String StoreName="@Amazon";

	//String txtPrice="200";
	String txtDiscount="100";
	//String txtFinal="100";
	Float dis;
	int flag=0;

	String path2= "unnamed.png";
	//String path1= "unnamed.png";

	String CatDesc="xxxx";
	String CatPath="xxxx";
	String CatMainName="xxxx";
	int catFound=0;
	int offerFound=0;
	String offerId="0";
    Connection conn = null;
	Statement stmt = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;

	public  Connection dbconnectSetup()
	{
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			if(conn!=null)
			{
				System.out.println("Connected database successfully...");
				return conn;
			}

		}

		catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		return conn;

	}

	public void dbClose(Connection conn1)
	{
		try{
			if(stmt!=null)
				conn1.close();
			   System.out.println("-----------DB Connection closed successfully.----------------");

		}catch(SQLException se){
			se.printStackTrace();
		}


		try{
			if(conn1!=null)
				conn1.close();
			 System.out.println("----------------DB Connection closed successfully.------------");
		}
		catch(SQLException se){
			se.printStackTrace();
		}

	}



	public void dbUpdate(Connection conn1,String txtName,String StoreName,String txtFinal,String txtPrice,String path1,String DealLink,String QntAvl,String DealDescp,String cmbCategoryName) throws Exception
	{

		try{

			//STEP 4: Execute a query
			System.out.println("Inserting records into the table...");
			stmt = conn1.createStatement();

			String catselect="select CategoryId from Category_Master where CategoryName='"+cmbCategoryName+"'";
			ResultSet rs= stmt.executeQuery(catselect);

			while(rs.next())
			{   
				catFound=1;
				System.out.println("Category Already Exist."); 
				cmbCategory= rs.getString("CategoryId");

			}


			if(catFound==0)
			{   
				stmt1 = conn1.createStatement();
				System.out.println("Category Not found.");
				String catinsert= "insert into Category_Master(CategoryName,Description,Image,MainCategoryName,ActiveStatus) values('"+cmbCategoryName+"','"+CatDesc+"','"+CatPath+"','"+CatMainName+"','Y')";
				flag=stmt1.executeUpdate(catinsert);
				if(flag>0)
				{ System.out.println("Inserted Category Successfully!");
				flag=0;
				}
				else
					System.out.println("Category insertion Failed!"); 

				stmt2 = conn1.createStatement();
				String catreselect="select CategoryId from Category_Master where CategoryName='"+cmbCategoryName+"'";
				ResultSet rs1= stmt2.executeQuery(catreselect);

				while(rs1.next())
				{	  
					cmbCategory= rs1.getString("CategoryId");
				}


				rs1.close();
			}


			String offerselect="select ItemId from Item_Master where ItemName='"+txtName+"'";
			ResultSet rs1= stmt.executeQuery(offerselect);



			while(rs1.next())
			{   
				offerFound=1;
				offerId= rs1.getString("ItemId");
				System.out.println("/************------------------Offer Already Exist with Offer Id="+offerId+"------------------------------***************/"); 

			}

			if(offerFound==0)
			{   
				Calendar cal = Calendar.getInstance();
				ReleaseTime=timeformat.format(cal.getTime()).toString();
				Date date = new Date();
				ReleaseDate=dateformat.format(date).toString();

				if(Integer.parseInt(QntAvl)>=300)
				{
					frdStatus="Y";
				}
				else
				{
					frdStatus="N";
				}
				String storeselect="select StoreId from Popular_Stores where StoreName='"+StoreName+"'";
				ResultSet rs2= stmt.executeQuery(storeselect);

				while(rs2.next())
				{   
					StoreId= rs2.getString("StoreId");
					System.out.println("Store Id="+StoreId); 

				}


				stmt3 = conn1.createStatement();

				dis = (Float.parseFloat(txtPrice)-Float.parseFloat(txtFinal));
				txtDiscount=Float.toString(dis);
				String sql = "insert into Item_Master(CategoryId,ItemName,Description,Size,Image,PrdDescFile,Price,Discount,Total,FeaturedPrd,LatestPrd,PromotedPrd,ItemCondtion,AvalibiltyStatus,QuantityAvailable,ItemsFullDescription,Brand,ModelNo,ReleaseDate,DisplaySize,PrdFeatures,PrdReviews,PostedBy,ReleaseTime,DealDescription,DealLink,StoreId,PrdDimension,DealWebsite) values("+cmbCategory+",'"+txtName+"','"+txtDesc+"','"+txtSize+"','"+path1+"','"+path2+"',"+txtPrice+","+txtDiscount+","+txtFinal+",'"+frdStatus+"','"+LatestStatus+"','"+PrmStatus+"','"+ItemCondition+"','"+AvlStatus+"',"+QntAvl+",'"+PrdfullDesc+"','"+Brand+"','"+Modelno+"','"+ReleaseDate+"','"+DispSize+"','"+PrdFeatures+"','"+PrdReviews+"','"+PostedBy+"','"+ReleaseTime+"','"+DealDescp+"','"+DealLink+"',"+StoreId+",'"+Dimension+"','"+StoreName+"')";
				flag=stmt3.executeUpdate(sql);
				if(flag>0)
					System.out.println("/********-------------------Inserted Offer Successfully!---------------------------************/");
				else
					System.out.println("Offer insertion Failed!"); 

				rs2.close();
			}  

		  rs.close();


		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources

			try{

				catFound=0;
				offerFound=0;
				flag=0;
			}catch(Exception e){
				e.printStackTrace();
			}//end finally try
		}//end try


	}

	public void scrollDown(WebDriver driver,int crDwn) throws Exception
	{
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("scroll(0,"+crDwn+");");
	}

	public void implicitWait(int time,WebDriver driver) throws Exception
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public WebElement getElementWithIndex(WebDriver driver, int index,String xpath)
	{
		List<WebElement> elements = driver.findElements(By.xpath(xpath)); 
		return elements.get(index);
	}


}
