import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.Connection;


public class OfferUpdate {

	WebDriver driver;
	//PhantomJSDriver driver;
	DBConnectionUtil dbconnectionutil=new DBConnectionUtil();
	String userId="arinsark";       /*CEC Id */
	String password="xxxx";   /*CEC Password*/
	String url="https://www.desidime.com/";
	String dealName="xxxx";
	String dealStore="xxxx";
	String dealPrice="0";
	String dealImage="xxxx";
	String dealLink="xxxx";
	String newDealLink="xxxx";
	String dealMRP="0";
	String dealViews="0";
	String dealDescp="xxxx";
	String dealCategory="xxxx";
	ArrayList<String> latestDeals = new ArrayList<String>();
	ArrayList<String> finalPriceDeals = new ArrayList<String>();
    Connection conn = null;


	WebElement deal;
	WebElement dealTime;
	int scrollDown=300;

	@BeforeClass
	public void setUp(){ 

		try {


			System.out.println(" Executing on Chrome");

			/*****----------Grid Execution------------ ***/
			//String Node = "http://10.65.150.241:5566/wd/hub";
			//String Node="http://qsghub-nprd-lnx-01:8080/wd/hub";
			//String Node="http://http://QNW-001-04-P:5555/wd/hub";
			//String Node="http://sdaas-gridlab.cisco.com:8080/wd/hub";
			//String Node="http://172.17.0.2:4444/wd/hub";
			//DesiredCapabilities cap = DesiredCapabilities.chrome();
			//cap.setPlatform(Platform.WINDOWS);
			//cap.setBrowserName("chrome");
			//driver = new RemoteWebDriver(new URL(Node), cap);
			
			 /************ Declaring and initialising the Headless Browser **********/
			 //driver = new HtmlUnitDriver();
			 //driver.setJavascriptEnabled(true);			
		     //System.setProperty("phantomjs.binary.path", "phantomjs.exe");		
             driver = new PhantomJSDriver();	
			

			/*****-------------Local Execution------------- ***/
			//System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			//System.setProperty("webdriver.firefox.bin", "C:\\FirefoxPortable45ESR\\FirefoxPortable.exe");
			//driver=new FirefoxDriver();
			
			//System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            //ChromeOptions chromeOptions = new ChromeOptions();
            //chromeOptions.addArguments("--headless");
		    //driver = new ChromeDriver();


			//Puts an Implicit wait, Will wait for 10 seconds before throwing exception
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//driver.manage().deleteAllCookies();
			//driver.manage().window().maximize();
			


		}
		catch(Exception e)
		{
			System.out.println("Exception occured:"+e);
			e.printStackTrace();
		}


	} 

	@AfterClass
	public void tearDown(){
		if (driver != null) {
			driver.quit();
		}
	}


	@Test
	public void offerUpdate() throws Exception{
		try
		{
			System.out.println("Login to Desidime..."); 
			driver.get(url);
			WebDriverWait wait = new WebDriverWait(driver,60);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[contains(@class,'deal-off-percent')]/div[@class='getdeal ftl']/a")));

			/********Scrolling HomePage **********************/
			for(int i=1,j=300;i<=1;i++,j+=300)
			{
				dbconnectionutil.scrollDown(driver,j); 
				System.out.println("Scrolling Down="+i);
			}

			
			List<WebElement> dealBoxShadow = driver.findElements(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']"));
			List<WebElement> dealDescriptions = driver.findElements(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[@class='deal-dsp']/a"));
			List<WebElement> dealTimes = driver.findElements(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[@class='promoblock']/div[@class='tgrid']/div[@class='tgrid-cell']/div[@class='promotime']/span"));
			List<WebElement> dealStores = driver.findElements(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[@class='cf']/div[@class='deal-store ftl']/a"));
			List<WebElement> dealPrices = driver.findElements(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[contains(@class,'deal-off-percent')]/div[@class='ftl dealpriceoff']"));
			List<WebElement> dealImages = driver.findElements(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[contains(@class,'deal-box-image fpd_prod_img')]/a/img"));
			List<WebElement> dealLinks = driver.findElements(By.xpath(".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[contains(@class,'deal-off-percent')]/div[@class='getdeal ftl']/a"));

			System.out.println("No. of DealsBox="+dealBoxShadow.size());
			System.out.println("No. of Dealsdescription="+dealDescriptions.size());
			System.out.println("No. of dealStores="+dealStores.size());
			System.out.println("No. of dealTimes="+dealTimes.size());
			System.out.println("No. of dealPrices="+dealPrices.size());
			System.out.println("No. of dealImages="+dealImages.size());
			System.out.println("No. of dealLinks="+dealLinks.size());


			for(int i=0;i<dealDescriptions.size();i++)
			{   
				/*  if(i>16)
                {   
                	dbconnectionutil.implicitWait(2, driver);
                	scrollDown+=600;
                	dbconnectionutil.scrollDown(driver,scrollDown); 
                	System.out.println("Scrolling Down to get more offers....");

                }*/

				//deal=driver.findElement(By.xpath(".//*[@id='deals-grid']/ul[@class='gridfix cf deals_grid']/li["+i+"]/div[@class='deal-box shadow']/div[@class='deal-dsp']/a"));
				deal=dbconnectionutil.getElementWithIndex(driver,i, ".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[@class='deal-dsp']/a");
				//dealTime=driver.findElement(By.xpath(".//*[@id='deals-grid']/ul[@class='gridfix cf deals_grid']/li["+i+"]/div[@class='deal-box shadow']/div[@class='promoblock']/div[@class='tgrid']/div[@class='tgrid-cell']/div[@class='promotime']/span"));
				dealTime=dbconnectionutil.getElementWithIndex(driver,i, ".//*[@id='deals-grid']/div[@id='infinite-scrolling-list']/ul[@class='cf']/li/div[@class='deal-box shadow']/div[@class='promoblock']/div[@class='tgrid']/div[@class='tgrid-cell']/div[@class='promotime']/span");
				if(dealTime.getText().contains("hours")||dealTime.getText().contains("minutes")||dealTime.getText().contains("seconds"))
				{   


					System.out.println("Getting Offer No.="+i);
					driver.findElement(By.xpath("//li[@class='subNav-home']/a[text()='Hot']")).click();
					deal.click();

					/********************Getting Offer Details *******************/

					if(driver.findElements(By.xpath(".//*[@id='deal-detail-like-dislike-container']/div/div[1]/div[1]/h1")).size()>0)
					{ 
						dealName=driver.findElement(By.xpath(".//*[@id='deal-detail-like-dislike-container']/div/div[1]/div[1]/h1")).getText().replaceAll("[^a-zA-Z0-9&,_%\\s]", " ");

					}
					else
					{ 
						dealName="Deal Name not available";

					}
					System.out.println("Latest Deals="+dealName);
					if(driver.findElements(By.xpath(".//*[@id='deal-detail-like-dislike-container']/div/div[3]/div[2]/div/div/a/span[2]")).size()>0)
					{ 
						dealStore=driver.findElement(By.xpath(".//*[@id='deal-detail-like-dislike-container']/div/div[3]/div[2]/div/div/a/span[2]")).getText();
						if(dealStore.contains("Amazon"))
							dealStore="@Amazon";
						else if(dealStore.contains("Flipkart"))
							dealStore="@Flipkart";
						else if(dealStore.contains("Paytm"))
							dealStore="@Paytm";
						else
							dealStore="@Others";
					}
					else
					{
						dealStore="@Others";
					}
					System.out.println("Deal Store="+dealStore);
					if(driver.findElements(By.xpath("//div[@class='dealprice']/span")).size()>0)
						dealPrice=driver.findElement(By.xpath("//div[@class='dealprice']/span")).getText();
					else
						dealPrice="0";	  
					System.out.println("Deal Price="+dealPrice);

					if(driver.findElements(By.xpath("//div[@class='dealpercent']/span[@class='line-through']")).size()>0)
						dealMRP=driver.findElement(By.xpath("//div[@class='dealpercent']/span[@class='line-through']")).getText();
					else
						dealMRP="0";	  
					System.out.println("Deal MRP="+dealMRP);

					if(driver.findElements(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/a[@class='dealpromoimage']/img[contains(@class,'media-image')]")).size()>0)
						dealImage=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/a[@class='dealpromoimage']/img[contains(@class,'media-image')]")).getAttribute("src");
					else
						dealImage="http://www.kalahandi.info/wp-content/uploads/2016/05/sorry-image-not-available.png";	  
					System.out.println("Deal Image="+dealImage);

					if(driver.findElements(By.xpath("//ul[@class='dalist']/li[1]/span")).size()>0)
						dealViews=driver.findElement(By.xpath("//ul[@class='dalist']/li[1]/span")).getText().replaceAll("[^0-9]", "");
					else
						dealViews="101";	  
					System.out.println("Deal Views="+dealViews);

					if(driver.findElements(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).size()>0)
					{

						if(dealStore.contains("Amazon"))
						{ 
							dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href").concat("?ref_=as_li_ss_tl&tag=arwin10-21&camp=3638&creative=24630");
							//dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href");
							dealLink=URLDecoder.decode(dealLink, "UTF-8");
							newDealLink=dealLink.replace("https://links.desidime.com/?ref=deals&url=","");

						}
						else if(dealStore.contains("Flipkart"))
						{
							//dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href").concat("?affid=arsoftech");
							dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href");
							if(dealLink.contains("sort"))
							{
								dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href").concat("&affid=arsoftech");
							}
							else if(dealLink.contains("&pid"))
							{
								dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href").concat("&affid=arsoftech");
							}
							else
							{
								dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href").concat("?affid=arsoftech");
							}
							dealLink=URLDecoder.decode(dealLink, "UTF-8");
							newDealLink=dealLink.replace("https://links.desidime.com/?ref=deals&url=","");
						}
						else
						{
							dealLink=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='image-wrap pos-relative']/div[1]/a")).getAttribute("href");
							dealLink=URLDecoder.decode(dealLink, "UTF-8");
							newDealLink=dealLink.replace("https://links.desidime.com/?ref=deals&url=","");
						}
					}
					else
					{
						newDealLink="NA";
					}
					System.out.println("Deal Link="+newDealLink);

					if(driver.findElements(By.xpath("//ul[@class='list-details']/li/a")).size()>0)
						dealCategory=driver.findElement(By.xpath("//ul[@class='list-details']/li/a")).getText();
					else
						dealCategory="NA";	  
					System.out.println("Deal Category="+dealCategory);


					if(driver.findElements(By.xpath("//div[@class='postblock']/div[@class='mainpost postcontent']")).size()>0)
					{   
						if(driver.findElements(By.xpath("//div[@class='postblock']/div[@class='mainpost postcontent']/p[1]")).size()>0)
							dealDescp=driver.findElement(By.xpath("//div[@class='postblock']/div[@class='mainpost postcontent']/p[1]")).getText()+"\n";
						else
							dealDescp= "Deal Description not available";	
						if(driver.findElements(By.xpath("//div[@class='postblock']/div[@class='mainpost postcontent']/p[2]")).size()>0)
							dealDescp=dealDescp.concat(driver.findElement(By.xpath("//div[@class='postblock']/div[@class='mainpost postcontent']/p[2]")).getText())+"\n";
						else
							dealDescp= "Deal Description not availablen";	
						if(driver.findElements(By.xpath("//div[@class='postblock']/div[@class='mainpost postcontent']/p[3]")).size()>0)
							dealDescp=dealDescp.concat(driver.findElement(By.xpath("//div[@class='postblock']/div[@class='mainpost postcontent']/p[3]")).getText())+"\n";
						else
							dealDescp= "Deal Description not available";
					}
					else
					{
						dealDescp= "Deal Description not available";
					}
					System.out.println("Deal Description="+dealDescp);
                    
					conn=dbconnectionutil.dbconnectSetup();
					if(conn!=null)
					{
					 dbconnectionutil.dbUpdate(conn,dealName,dealStore,dealPrice,dealMRP,dealImage,newDealLink,dealViews,dealDescp,dealCategory);
					 dbconnectionutil.dbClose(conn);
					}
					driver.navigate().to(url); 

				} 			

			}

			System.out.println("Inserted All offers successfully.");

		}
		catch(Exception e)
		{  
			Assert.assertTrue(false);
			System.out.println("Exception occured:"+e);
			System.out.println("Offers Insertion Failed.");
			e.printStackTrace();
		}
	
	}


}
