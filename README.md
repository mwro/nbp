# nbp

Java application displaying an array of exchange rates for currenecies USD, EUR, CHF, GBP, based on NBP (Polish National Bank) api.  
For more details about nbp API, see http://api.nbp.pl/

Pass the desired date in format "yyyy-MM-dd" as a program argument to show historical echange rates, e.g:  
java pl.dashboard.nbp.MainClass 2017-11-20

To obtain currently effective exchange rate, pass no argument or an empty string.

The result is shown in the following format, printed on console:  
Data: 20.11.2017  
Waluta = kupno; sprzedaż  
USD = 3.5562; 3.6280  
EUR = 4.1943; 4.2791  
CHF = 3.5902; 3.6628  
GBP = 4.6916; 4.7864
