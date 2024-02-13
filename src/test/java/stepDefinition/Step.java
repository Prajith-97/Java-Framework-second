package stepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.*;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;


public class Step {
	WebDriver driver;
	String FromExcel,a;
	@Given("^Open the Chrome and launch the Amazon$")
	public void open_the_Chrome_and_launch_the_application() throws Throwable							
    {		
        System.out.println("This Step open the Chrome and launch the application.");	
        //WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
  	  driver= new ChromeDriver();
  	
  	  driver.manage().window().maximize();
  	driver.get("https://www.amazon.in/");
    }		

    @When("^Add to cart in Amazon$")					
    public void Add_to_cart() throws Throwable 							
    {
    		     WebElement SearchTxt= driver.findElement(By.id("twotabsearchtextbox"));
    			 //Reading data from excel 
    		     getDatafromExcel(1,1);
    		     SearchTxt.sendKeys(FromExcel);
    		     takeSnapShot(driver, System.getProperty("user.dir")+"\\Test.png");
    		     setDataToExcel(1,2);
    		 	 SearchTxt.sendKeys(Keys.ENTER);
    		     driver.findElement(By.xpath("//*[@id=\"a-autoid-0-announce\"]")).click();
    		     driver.findElement(By.xpath("//*[@id=\"s-result-sort-select_1\"]")).click();
    		     Thread.sleep(2000);
    		     //Scroll down
    		     JavascriptExecutor js = (JavascriptExecutor) driver;
    		     js.executeScript("window.scrollBy(0,300)", "");
    		     Thread.sleep(2000);
    		     driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    		     //	     Actions ac = new Actions(driver);
//    	  	     ac.moveToElement(driver.findElement(By.xpath("img=[@alt='Yellow']"))).build().perform();
    	  	     //driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/div/div/div[1]/div/span/a/div/img")).click();
    	driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/span/div/div/div[1]/div/span/a/div/img")).click();
		Thread.sleep(2000);
    	  	     screenhandling();
    	  	     driver.findElement(By.name("submit.add-to-cart")).click();
    	  	     
    	  	
    }		

    @Then("^Quit the Browser$")					
    public void Quit_the_Browser() throws Throwable 							
    {    		
    	driver.quit();
    	//screenhandling();
    	//driver.findElement(By.xpath("//*[@id=\"sw-gtc\"]/span/a")).click();
    }	
    
    @Given("^Open the Chrome and launch the Snapdeal$")
    public void open_the_Chrome_the_application() throws Throwable							
    {		
        System.out.println("This Step open the Chrome and launch the application.");	
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
        //WebDriverManager.chromedriver().setup();
        
  	  driver= new ChromeDriver ();
  	  driver.manage().window().maximize();
		driver.get("https://www.snapdeal.com/");
    }		
   
    @When("^Add to cart in Snapdeal$")	
    public void Enter_the_user_details() throws Exception
    {
    	//driver.get("https://www.snapdeal.com/");
  	  WebElement SearchTxt= driver.findElement(By.xpath("//*[@id=\"inputValEnter\"]"));
  	  SearchTxt.click();
  	  getDatafromExcel(1,1);
  	  SearchTxt.sendKeys(FromExcel);
  	  takeSnapShot(driver, "D:\\SeleniumTraining\\ExtentReport\\Test.png");
  	  setDataToExcel(1,2);
  	  SearchTxt.sendKeys(Keys.ENTER);
  	  //driver.findElement(By.xpath("//*[@id="6917529670976302567"]/div[2]/a/picture/img")).click();
  	Thread.sleep(2000);
  	  driver.findElement(By.id("6917529686007371740")).click();
		//*[@id="6917529686007371740"]/div[2]/a/picture/img
  	  screenhandling();
  	  driver.findElement(By.id("add-cart-button-id")).click();
    }
   
    
	@AfterStep
	public void addScreenshot(Scenario scenario) throws IOException {
		  File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
		  scenario.attach(fileContent, "image/png", "screenshot");
			}
	
    public void getDatafromExcel(int Row,int Cell) throws IOException
    {
  	  File file =    new File("D:\\SeleniumTraining\\ExtentReport\\TestData.xls");
        FileInputStream inputStream = new FileInputStream(file);
        HSSFWorkbook workbook=new HSSFWorkbook(inputStream);
        HSSFSheet sheet=workbook.getSheet("Amazon_Data");
        HSSFRow row=sheet.getRow(Row);
        HSSFCell cell=row.getCell(Cell);
        FromExcel= cell.getStringCellValue();
     }
    
    public void setDataToExcel(int Row,int Cell) throws IOException
    {
  	  FileInputStream fis = new FileInputStream("D:\\SeleniumTraining\\ExtentReport\\TestData.xls");
  	  HSSFWorkbook workbook = new HSSFWorkbook(fis);
  	  HSSFSheet sheet = workbook.getSheet("Amazon_Data");
  	  Row row = sheet.getRow(Row);
  	  Cell cell = row.createCell(Cell);
  	  cell.setCellValue("Pass");
  	  FileOutputStream fos = new FileOutputStream("D:\\SeleniumTraining\\ExtentReport\\TestData.xls");
  	  workbook.write(fos);
  	  fos.close();
     }
    
    
    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception
    {
               TakesScreenshot scrShot =((TakesScreenshot)webdriver);
                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
                File DestFile=new File(fileWithPath);
                FileUtils.copyFile(SrcFile, DestFile);

    }
    
    public void screenhandling()
    {
  	  String parent = driver.getWindowHandle();
  	     Set<String> child = driver.getWindowHandles();
  	     for (String allwindow : child) 
  	     {
  	    	 if (!(parent==allwindow)) 
  	    	 {
  	    		 driver.switchTo().window(allwindow);
  	    	 }
  			
  		 }
    }

}
