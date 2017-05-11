
package com.appdirect.microsoft.pages;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.appdirect.microsoft.googlesheetread.CsvToPdf;
import com.appdirect.microsoft.googlesheetread.Editions;
import com.appdirect.microsoft.utils.CSVGenerator;
import com.appdirect.microsoft.utils.DriverUtils;
import com.appdirect.microsoft.utils.Reporterlog;


public class HomePage {
    private WebDriver driver;
    private Reporterlog log;
    private Properties props;
    private WebElement loginButton;
    CsvToPdf Pdf = new CsvToPdf();
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.log = new Reporterlog();
        props = new DriverUtils(driver).getProperty("resources/res-pages.properties");
    }
    
    public LoginPage gotoLogin() {
        loginButton = new DriverUtils(this.driver).getElement(props.getProperty("loginButton"));
        this.loginButton.click();
        log.info("Clicked log in button on Home Page");
        return new LoginPage(this.driver);
    }
    public void gotoProfile(HashMap<String, Editions> editions) throws IOException, Exception {
        int editionCount=0,editionAppCount=0;
        try {
            for (Map.Entry<String, Editions> edition : editions.entrySet()){
            	
                editionCount++;
                if(!edition.getValue().getappId().equals("NA")){
                    editionAppCount++;
                    Reporter.log("\n\n"+edition.getValue().getappId(), true);
                    log.info("\n"+editionAppCount+"\tOpening: " + edition.getValue().getEditionName() + "\nOffer id:"
                            + edition.getValue().getOfferId());
                    new ProfilePages(this.driver, edition.getValue())
                            .openLink(edition.getValue().getappId())
                            .clickEdition();
                }
            	
            }
        } catch (Exception e) {
            log.exceptionlog("Skipped: ");
        }

        log.info("\nTotal Editions= "+editionCount+" Edition with App Id="+editionAppCount+" Editions Found= " + ProfilePages.editionFound);
       
        new CSVGenerator().csvgenerator(ProfilePages.differentPricesLists);
        Pdf.csvToPdf()
		   .googleDrive();
    }
}

