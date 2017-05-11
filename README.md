# EditionPricingAutomation

Project Name: Automation of Edition's price comparision

Introduction: This project is based on Java, Selenium Web Driver, Maven and 
TestNG and will automatically compare the prices of the editions. It has also 
the functionality of choosing different browser .

Initially it will access the google sheet through url id and will retrieve 
the content of the google sheet and then for comparing prices of the edition,
it will go to the appdirect homepage and automatically provide login credentials
and then it starts searching the editions by name and compare the prices and this 
loop will go on till the last edition of the sheet.

At the end CSVs will be generated which contains the editions in which prices are
compared .It contains the edition name, offer code, old price, new price.

Packages::
There are total 5 packages a)Test b)Utils c)sendByDrive d)Pages e)Main

How to Run:: 1)In the com.appdirect.automatePricing Package run the 
AutomatePrices.java through TestNG. 2)Run testing.xml directly
