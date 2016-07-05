package org.translation;

import org.commonFunction.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BingTranslatorGUI {
	static WebDriver webdriver;
	//method to capitalize word
	private static String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
//--------------------------------------------------------------------------------
	public static ArrayList<String> translate() throws InterruptedException{
		//opening firefox browser via selenium webdriver	
		GeneralActions actions= new GeneralActions();
		webdriver= actions.getDriver(Utility.getConfigValue("browser"));
		actions.setDriver(webdriver);
		actions.getURL(Utility.getConfigValue("url"));
	    
	    //Reading from csv file
	    File file = new File(Utility.getConfigValue("file_gui"));
	    BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String []lang;
		String []str;
		String xpath,from,to;
		ArrayList<String> translatedList= new ArrayList<>();
		try {

			br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				Thread.sleep(2000);
			    // use comma as separator
				lang = line.split(cvsSplitBy);
				String word= lang[2];
				if(lang[0].equals("")) {
					from= "Auto-Detect";
				}
				else {
					from= lang[0];
				}
				if(lang[1].equals("")) {
					to= "Auto-Detect";
				}
				else {
					to= lang[1];
				}
				webdriver.findElement(By.cssSelector("#srcText")).clear();
				webdriver.findElement(By.xpath("//div[@class='LanguageSelector'and @tabindex='10']")).click();
				webdriver.findElement(By.xpath(".//*[@class='col translationContainer sourceText']/div[1]/div[1]/div[1]/table/tbody/tr[*]/td[text() = '"+BingTranslatorGUI.capitalize(from)+"']")).click();
				webdriver.findElement(By.cssSelector("#srcText")).sendKeys(word);
				Thread.sleep(2000);
				webdriver.findElement(By.xpath("//div[@class='LanguageSelector'and @tabindex='30']")).click();
				webdriver.findElement(By.xpath(".//*[@class='col translationContainer destinationText']/div[1]/div[1]/div[1]/table/tbody/tr[*]/td[text() = '"+BingTranslatorGUI.capitalize(to)+"']")).click();
				Thread.sleep(2000);
				translatedList.add((webdriver.findElement(By.cssSelector("#destText")).getText()));
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("exception: "+ e);
		}
		catch (IOException e) {
			System.out.println("exception: "+ e);
		}
		finally {
			if(br != null) {
				try {
					br.close();
				}
				catch(IOException e) {
					e.printStackTrace();;
				}
			}
		}
		System.out.println(translatedList);
		return translatedList;
	}
}
