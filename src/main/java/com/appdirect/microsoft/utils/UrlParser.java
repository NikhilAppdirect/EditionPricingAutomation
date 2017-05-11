package com.appdirect.microsoft.utils;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlParser {
    public String SpreadSheetUrl() throws MalformedURLException {
        URL aURL = new URL("https://docs.google.com/spreadsheets/d/1cXI0jfbaplv4mgTVfH4JcA2mPvVUS0QMXPPPNgIcNLQ/edit#gid=701019997");
        String path = aURL.getPath();
        String Id = path.replace("/spreadsheets/d/", "").replace("/edit","");
        return Id;
    }
}
