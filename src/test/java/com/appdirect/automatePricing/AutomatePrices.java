package com.appdirect.automatePricing;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.appdirect.microsoft.googlesheetread.Editions;
import com.appdirect.microsoft.googlesheetread.GooglesheetAccess;
import com.appdirect.microsoft.utils.DriverUtils;


    public class AutomatePrices {
        private WebDriver driver;
        private HashMap<String, Editions> editions,addons;
        private String baseUrl;

        @BeforeSuite
        public void sheetAccess() {
            GooglesheetAccess googlesheet = new GooglesheetAccess();
            googlesheet.getList();
            addons=googlesheet.getAddonList();
            editions=googlesheet.getEditionsList();
            googlesheet.editionDisplay(addons);
            googlesheet.editionDisplay(editions);
        }

        @BeforeTest
        public void loadBrowser() {
            DriverUtils util = new DriverUtils();
            driver = util.getDriver();
            baseUrl = util.getUrl();
        }

        @Test
        public void testMarketplacePrice() throws IOException, Exception {
            new DriverUtils(driver).openUrl(baseUrl)
                    .gotoLogin()
                    .gotoHome()
                    .gotoProfile(editions);
        }

    }




