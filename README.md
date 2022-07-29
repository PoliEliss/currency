Currency App 

#Technology stack used: 
#1)MVVM
#2)Room
#3)Retrofit+ okHttp
#4)Dagger2
#5) DiffUtil
#6) SheredPreferense in the chain 1907DataStote SheredPreference is replaced with DataStote+Flow (in progress)
#7)Coroutines

The app allows you to get currency exchange rate in rubles

To obtain data on the exchange rate, use https://www.cbr-xml-daily.ru/archive/YYY-MM-DD.js

By default the app uploads information about the currency value of the current day

If the user is interested in another date then it’s necessary to use calendar for that.

Due to the fact that it is not always possible to obtain data from https://www.cbr-xml-daily.ru/archive/YYY-MM-DD.js, the user may encounter an error in obtaining data.

The application will ask you to select another day to receive data.

The data is cached in the Room database, which allows user to receive data even if the connection is lost in the absence of Internet. 
The data will be obtained for the latest date of visiting the app.

There is also a search button on the main page, which will allow you to quickly find the currency of interest to the user using CharCode.

When you click on any currency you are interested in, the BottomSheetBehavior will appear.

When you click on the heart bottom, the currency goes to the favorites. 
The BottomSheetBehavior has the ability to convert currency to rubles and vice versa.

Favorites tab:
The favorites tab contains saved currencies. 
When a currency is clicked on, the user goes to another tab where they can make calculations. 
The currency is deleted if the button is pressed for a long time.

Calculator tab: 
Allows you to quickly convert one currency to another. 
This tab allows the user not to switch between the other tabs, but to immediately perform all the necessary calculations.

Settings tab:
Here the user can change the theme of the application (light and dark mode)
Choose language
Change the way how favorites are displayed.

![goo1cadr](https://user-images.githubusercontent.com/96927298/181693571-780509b2-6524-475d-8012-c9db877fcaff.gif) ![ezgif com-gif-maker (4)](https://user-images.githubusercontent.com/96927298/181699443-171530cc-a81c-4b90-89bf-72a1867e8c42.gif)


