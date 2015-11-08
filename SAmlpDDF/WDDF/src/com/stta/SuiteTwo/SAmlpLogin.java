//Find More Tutorials On WebDriver at -> http://software-testing-tutorials-automation.blogspot.com
package com.stta.SuiteTwo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;

import org.apache.poi.ss.usermodel.DateUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.stta.utility.Read_XLS;
import com.stta.utility.SuiteUtility;

//SuiteTwoCaseOne Class Inherits From SuiteTwoBase Class.
//So, SuiteTwoCaseOne Class Is Child Class Of SuiteTwoBase Class And SuiteBase Class.
public class SAmlpLogin extends SuiteTwoBase{
	Read_XLS FilePath = null;	
	String SheetName = null;
	String TestCaseName = null;	
	String ToRunColumnNameTestCase = null;
	String ToRunColumnNameTestData = null;
	String TestDataToRun[]=null;
	String docfilepath;
	static boolean TestCasePass=true;
	static int DataSet=-1;	
	static boolean Testskip=false;
	static boolean Testfail=false;
	static boolean aborted=false;
	SoftAssert s_assert =null;
	String parentOrchild;
	Long mlpLoginDOB1;
	String strPorC;
	
	@BeforeTest
	public void checkCaseToRun() throws IOException{
		//Called init() function from SuiteBase class to Initialize .xls Files
		init();	
		
		//To set SuiteTwo.xls file's path In FilePath Variable.
		FilePath = TestCaseListExcelTwo;		
		TestCaseName = this.getClass().getSimpleName();
		//SheetName to check CaseToRun flag against test case.
		SheetName = "TestCasesList";
		//Name of column In TestCasesList Excel sheet.
		ToRunColumnNameTestCase = "CaseToRun";
		//Name of column In Test Case Data sheets.
		ToRunColumnNameTestData = "DataToRun";
				
		  
		
		//To check test case's CaseToRun = Y or N In related excel sheet.
		//If CaseToRun = N or blank, Test case will skip execution. Else It will be executed.
		if(!SuiteUtility.checkToRunUtility(FilePath, SheetName,ToRunColumnNameTestCase,TestCaseName)){			
			//To report result as skip for test cases In TestCasesList sheet.
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "SKIP");
			//To throw skip exception for this test case.
			throw new SkipException(TestCaseName+"'s CaseToRun Flag Is 'N' Or Blank. So Skipping Execution Of "+TestCaseName);
		}
		//To retrieve DataToRun flags of all data set lines from related test data sheet.
		TestDataToRun = SuiteUtility.checkToRunUtilityOfData(FilePath, TestCaseName, ToRunColumnNameTestData);
		System.out.println("Testing888888888888888888 ");
	}
	
	//Accepts 3 column's String data In every Iteration.
	@Test(dataProvider="SAmlpLogin")
	public void SAmlpLogin (String DataCol1, String DataCol2,String DataCol3,String DataCol4, String DataCol5, String ExpectedResult,String ActualResult) throws ParseException, InterruptedException
	{
		
		DataSet++;
		
		//Created object of testng SoftAssert class.
		s_assert = new SoftAssert();
		
		System.out.println("Testing 1: Reading the datas from excel file");
		
		//If found DataToRun = "N" for data set then execution will be skipped for that data set.
		if(!TestDataToRun[DataSet].equalsIgnoreCase("Y")){
			//If DataToRun = "N", Set Testskip=true.
			Testskip=true;
			throw new SkipException("DataToRun for row number "+DataSet+" Is No Or Blank. So Skipping Its Execution.");
		}
				
					
		try{
		//To Initialize Firefox .
		loadWebBrowser();
		//For scrolling down
		 /* driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		*/
				
		System.out.println("Testing 2: Launching Super Awesome's Client produt mlp");
			
		driver.get(Param.getProperty("siteURL"));
		//driver.manage().window().getPosition();
		
		driver.findElement(By.xpath("id('cookie-container')/a")).click();
		
		System.out.println("Testing 3: accepting the cookies by clicking  OK");
		
		
		System.out.println("Testing 4: Clicking the Join in Button");
		
		//To click Login Button
		driver.findElement(By.xpath("id('navigation')/li[9]/a")).click();
		
		System.out.println("Testing 5: Closing another Cookie");
		driver.findElement(By.xpath("id('wrapper')/consent/div/div/div")).click();
	
		// To print Data in User Name Text Box
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[1]/input")).clear();
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[1]/input")).sendKeys(DataCol1);
		System.out.println("Testing 6: Passing the Username, DOB, Password values from Excel file");
				
		                             
		// Receive DOB from Excel file converting it to string format 
		driver.findElement(By.name("birthdate")).clear();
		int foo = Integer.parseInt(DataCol2);
		Date javaDate= DateUtil.getJavaDate((double) foo);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String StrDate = sdf.format(javaDate);
		System.out.println(StrDate);
		System.out.println("Testing 7:Converting the DOB data from date to string");
		
		// printing Date
		System.out.println("Testing 8: Passing the Date to Login page");
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[2]/div/input")).clear();
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[2]/div/input")).sendKeys(StrDate);				
		
				

		// To print Data Password Text Box
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(DataCol3);
		System.out.println("Testing 9:Passing the Password");
		
		//To Click the submit Button in Step 1 of sign up process.
		driver.findElement(By.className("btn-club")).click();
		System.out.println("Testing 10:Clicking the Step 1 button");
		
		// Check if the Parent or Child's email is collected.		 
		System.out.println("Testing 10: Checking if the Application is Collecting PARENT OR CHILD EMAIL ID****");
		strPorC = driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[1]/input")).getAttribute("placeholder");		
		System.out.println("ACTUAL string PorC from WEB|" + strPorC );
		
		//Print the parent or child email address in step 2
		System.out.println("Testing 11:Print the parent or child email address in step 2");
		if (strPorC.equals("Your parent's email address"))
		{
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[1]/input")).sendKeys(DataCol4);
		System.out.println("Testing 11: Parent email is printing...");
		}
		
		if (strPorC.equals("Your email address"))
		{
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[1]/input")).sendKeys(DataCol5);
		System.out.println("Testing 11: Child's email is printing...");
		}
		// Click OK in Step 2
		//driver.findElement(By.className("btn-block")).click();
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[2]/div/button")).click();
		System.out.println("Testing 12: Click OK in Step 2");
		
		
		// Click Accept in Step 3
		driver.findElement(By.xpath("id('main-content')/ui-view/ui-view/div[1]/div[1]/div/div/ui-view/form/div[3]/div/button")).click();
		System.out.println("Testing 12: Click OK in Step 3");
		}catch(Exception e)
		{
		System.out.println("Some Error in Signing UP!!!!!"+ e.getMessage());
		if (e.getMessage()!="")
		{aborted=true;
		Testfail=true;
		strPorC= "Sign up Error";
		}
		}
		
	    
	    
	    if (!strPorC.equals(ExpectedResult))
	    	{
	    	System.out.println("Comparing the result with expected Result and it is not equal..");
	    	Testfail=true;
	    	}
	      	
			if(Testfail)
			{
			//At last, test data assertion failure will be reported In testNG reports and It will mark your test data, test case and test suite as fail.
			s_assert.assertAll();		
			}
		closeWebBrowser();
		
	}
	
	//@AfterMethod method will be executed after execution of @Test method every time.
	@AfterMethod
	public void reporterDataResults(){		
		System.out.println("Inside reporterDataResults");
		if(Testskip){
			//If found Testskip = true, Result will be reported as SKIP against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "SKIP");
		}	
		else if(Testfail){
			//To make object reference null after reporting In report.
			s_assert = null;
			//Set TestCasePass = false to report test case as fail In excel sheet.
			TestCasePass=false;
			//If found Testfail = true, Result will be reported as FAIL against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "FAIL");
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "ActualResult", DataSet+1, strPorC);			
			System.out.println("Testing 13"+ "*****Test FAIL value passed to excel");
		}
		else{
			//If found Testskip = false and Testfail = false, Result will be reported as PASS against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "PASS");
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "ActualResult", DataSet+1, strPorC);
			System.out.println("Inside reporterDataResults...13.1");
		}
		//At last make both flags as false for next data set.
		Testskip=false;
		Testfail=false;
		
		}
	
	//This data provider method will return 3 column's data one by one In every Iteration.
	@DataProvider
	public Object[][] SAmlpLogin(){
		//To retrieve data from Data 1 Column,Data 2 Column and Expected Result column of SuiteTwoCaseOne data Sheet.
		//Last two columns (DataToRun and Pass/Fail/Skip) are Ignored programatically when reading test data.
		return SuiteUtility.GetTestDataUtility(FilePath, TestCaseName);
		
	}

	//To report result as pass or fail for test cases In TestCasesList sheet.
	@AfterTest
	public void closeBrowser(){
		//To Close the web  at the end of test.
		System.out.println("***** Inside closeBrowser");
		closeWebBrowser();
		if(TestCasePass){
			System.out.println("Testing 13.1"+ "*****Pass");			
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "PASS");
		}
		else{
			System.out.println("Testing 13.2"+ "*****Fail");
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "FAIL");
		}		
	}
}