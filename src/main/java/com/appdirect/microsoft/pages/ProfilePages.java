
package com.appdirect.microsoft.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.appdirect.microsoft.googlesheetread.EditionPriceDomain;
import com.appdirect.microsoft.googlesheetread.Editions;
import com.appdirect.microsoft.utils.DriverUtils;
import com.appdirect.microsoft.utils.Reporterlog;



public class ProfilePages {
    private WebDriver driver;
    private Reporterlog log;
    private Editions edition;
    private Properties props;
    public static int editionFound=1;
    private List<WebElement> dropDownEditions;
    static List<EditionPriceDomain> differentPricesLists = new ArrayList<EditionPriceDomain>();


    public ProfilePages(WebDriver driver, Editions edition) {
        this.driver = driver;
        this.log = new Reporterlog();
        this.edition = edition;
        props = new DriverUtils(driver).getProperty("resources/res-pages.properties");
    }

    public void clickEdition() {
        int count=0;
        try{
            new DriverUtils(this.driver).getElement(props.getProperty("clickEditions")).click();
            dropDownEditions = new DriverUtils(this.driver).getElements(props.getProperty("dropDownEditions"));
            for (WebElement dropDownEdition : dropDownEditions) {
                if (dropDownEdition.getText().equals(edition.getEditionName())) {
                    Reporter.log(dropDownEdition.getText(),true);
                    dropDownEdition.click();
                    String id = new DriverUtils(this.driver).getElement(props.getProperty("editionCode"))
                            .getAttribute("value");
                    if (id.equals(edition.getOfferId())) {
                        Reporter.log("Id matching", true);
                        editionFound++;
                        count++;
                        String usdPrice = new DriverUtils(this.driver).getElement(props.getProperty("price"),"USD").getAttribute("value");
                        String audPrice = new DriverUtils(this.driver).getElement(props.getProperty("price"),"AUD").getAttribute("value");
                        String eurPrice = new DriverUtils(this.driver).getElement(props.getProperty("price"),"EUR").getAttribute("value");
                        String gbpPrice = new DriverUtils(this.driver).getElement(props.getProperty("price"),"GBP").getAttribute("value");
                        new ProfilePages(driver, edition).showPrice(usdPrice, eurPrice, gbpPrice, audPrice);
                       
                        EditionPriceDomain pricingForFoundEditions = new EditionPriceDomain();
                        pricingForFoundEditions.setEditionName(edition.getEditionName());
                        pricingForFoundEditions.setOfferid(edition.getOfferId());
                        this.setPrice(pricingForFoundEditions, usdPrice, eurPrice, gbpPrice, audPrice);
                        differentPricesLists.add(pricingForFoundEditions);
                    }
                    else {
                        Reporter.log("Id not matching", true);
                    }
                    break;
                }
            }
        }
        catch(Exception e){
            log.exceptionlog("Skipped: Page Load Failure"+e.getMessage());
        }
        if(count==0)
        {
            Reporter.log("Edition Not Found",true);
        }
    }

    public void showPrice(String... price) {
        String report="Prices are changed for ";
        Reporter.log("USD\t[OLD VALUE] : " + price[0] + "  [NEW VALUE] :" + edition.getusdPrice(), true);
        Reporter.log("EUR\t[OLD VALUE] : " + price[1] + "  [NEW VALUE] :" + edition.geteurPrice(), true);
        Reporter.log("GBP\t[OLD VALUE] : " + price[2] + "  [NEW VALUE] :" + edition.getgbpPrice(), true);
        Reporter.log("AUD\t[OLD VALUE] : " + price[3] + "  [NEW VALUE] :" + edition.getaudPrice(), true);
        
        
        if((Double.parseDouble(price[0]) - Double.parseDouble(edition.getusdPrice()) == 0) &&
                (Double.parseDouble(price[1]) - Double.parseDouble(edition.geteurPrice()) == 0) &&
                (Double.parseDouble(price[2]) - Double.parseDouble(edition.getgbpPrice()) == 0) &&
                (Double.parseDouble(price[3]) - Double.parseDouble(edition.getaudPrice()) == 0))
        {
            Reporter.log("Prices are not changed",true);
        }

        else {
            if(Double.parseDouble(price[0]) - Double.parseDouble(edition.getusdPrice()) != 0){
                report=report+"USD,";
            }
            if(Double.parseDouble(price[1]) - Double.parseDouble(edition.geteurPrice()) != 0){
                report=report+"EUR,";
            }
            if(Double.parseDouble(price[2]) - Double.parseDouble(edition.getgbpPrice()) != 0){
                report=report+"GBP,";
            }
            if(Double.parseDouble(price[3]) - Double.parseDouble(edition.getaudPrice()) != 0){
                report=report+"AUD";
            }
            Reporter.log(report,true);
        }
    }
    public ProfilePages setPrice(EditionPriceDomain priceForFoundEditions, String... price) {
        priceForFoundEditions.setOldaudPrice(price[3]);
        priceForFoundEditions.setOldeurPrice(price[1]);
        priceForFoundEditions.setOldusdPrice(price[0]);
        priceForFoundEditions.setOldgbpPrice(price[2]);
        priceForFoundEditions.setNewaudPrice(edition.getaudPrice());
        priceForFoundEditions.setNeweurPrice(edition.geteurPrice());
        priceForFoundEditions.setNewusdPrice(edition.getusdPrice());
        priceForFoundEditions.setNewgbpPrice(edition.getgbpPrice());
        return new ProfilePages(driver, edition);
    }

    public ProfilePages openLink(String getappId) {
        driver.navigate().to("https://marketplace.appdirect.com/channel/editApp/"+getappId+"?");
        System.out.println();
        
        return new ProfilePages(this.driver,edition);
    }
}

