# Shopify

## Android Application for Buy/Sell old items.

## Team Members: &nbsp; member_1,member_2,member_3

## An android application to help peoples in buying cheap items at an affordable price,
## also they can sell those items at their comfort zones by just a click of a button

## Shopify is the perfect online marketplace to buy and sell locally! No need to visit the flea market to find the best deals on pre-owned items! Here you’ll find a wide selection of like new products, from vintage clothes, antique furniture, used books, and retro games, to electronics, pre-owned cars and studios for rent!

## Looking to sell your car or upgrade your mobile? Or just on the lookout for great deals? Then Shopify is the app for you! With Easy OLX you can sell anything with ease and in no time. You can also discover what others around you are selling in your neighborhood.

## To give you an idea, here are some of the awesome things you can do with the Shopify App:
### - Sell your unwanted items quickly, straight from your phone.
### - Find verified sellers in your neighborhood and get to discover great deals.
### - Contact with sellers directly to negotiate your deals from the safety of your home.
### - Easily Manage and edit your posts while on the move.

## Activities: &nbsp; Main Screen consists of the following screens.

###-------------------------------------Main Activity------------------------------------------------------------------
####--> com > example > Shopify > ui > activity > MainActivity    

#### The Activity in which we manage fragments with navigation graph
#### The Nav graph is in res > navigation> my_nav.xml. where you can see full flow of app how it will work.

#### In Main Activity We first show SplashFragment.


###-----------------------------------Splash Fragment--------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > splash > SplashFragment

#### It is the first fragment of activity which show for some seconds and after show the welcome page fragment.
#### it will show the welcome fragment when user first time opens app.
### after it will redirects to the register or login fragment to user every next time.

### if user already login and open app then after spplash screen it will redirects to HomeFragment screen.


###-----------------------------------ViewPagerFragment--------------------------------------------------------------------
#### --> com > example > Shopify > onboarding > ViewPagerFragment

#### this fragment have viewpager with 3 pages 
#### 1. ScreenOne
#### 2. ScreenTwo
#### 3. ScreenThree   -->after click on Get Started Button It Will show the Register Fragment


###----------------------------------RegisterFragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > authentication > RegisterFragment

#### This is The fragment where user can register with input details.
#### if user have already account then they can go to Login Fragment By clicking bottom line there

#### - here we implement the validations of required to register user with us.
#### - when user click on register button after fill all details it will cave the data of user to our firebase database.
#### - also we send the email verification link to user for confimation.
#### - after user can login with the email & password from login screen. 

#### - also there is terms & condition to accept if user wants to Register. it will Redirect to TermsConditionFragment


###----------------------------------TermsConditionFragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > terms > TermsConditionFragment

#### Here are the terms and conditions described for user to accept.


###----------------------------------Login Fragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > authentication > LoginFragment

#### This is The fragment where user can Login with input details Email & Password.
#### user they can go to RegisterFragment if they want to register.
#### user can forgot password if they want from here

#### after correct email & password user is logged in to our app and redirects to out HomeFragment where they can use our app.


###----------------------------------Home Fragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > home > HomeFragment

#### This is the fragment where user can see all posted items here.
#### User can search items from here.

###----------------------------------Settings Fragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > settings > SettingsFragment

#### This is the fragment where user can see own details name,number etc.
#### User can edit their own details here.
#### User can see their old postedd items here


###-------------------------Firebase Database--------------------------------------------------------------
#### We using firebase database as our backend.

#### Here We authenticate user with email and password from firebase authentication.
#### we store users data into firebase database and alos we store other data to manage our app.

###----------------------------------Home Fragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > home > HomeFragment

#### In this fragment we implement the sell button where user can click and post their selling items in SellFragmentOne & Two
#### Also user can see other posted items and search items.
#### if user want select item then it will show the full item details in DetailsFragment

###----------------------------------Sell Fragment One---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > sell > SellFragmentOne

#### when user click on sell button it shows the SellFragmentOne 
#### In this fragment user can choose their items photo , adress ,locaton & category.
#### after clicking next button user shows the SellFragmentTwo
#### also user can add to cart the item which shows into my cart options.

###----------------------------------Sell Fragment Two---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > sell > SellFragmentTwo

#### In this fragment user can enter item name, and other details.
#### also user can select if this item had Bid section or not.
#### after submit succesfully item uploaded to server and shows the other users on thwer home fragment.

###----------------------------------Details Fragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > details > DetailsFragment

#### When select any items from home fragment user can see the detailsFragment.
#### In this fragment user able to see the full details of item & contact the seller from there.


###----------------------------------Favorites Fragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > favorites > FavoritesFragment

#### in this fragment user can see their cart items.


###----------------------------------Web Ad---------------------------------------------------------------------
#### --> http://shopifyreal.000webhostapp.com

### we use free online server to host our website there.
### we put our apps screenshot and description that defines our app to user on our website.
### and also you can download our app from there download button.


###----------------------------------Bid Section---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > details > DetailsFragment

### we implemented bid section into details fragment where user can bid for the item .
### also user can see other users bids so the can compare.

###----------------------------------Filters Fragment---------------------------------------------------------------------
#### --> com > example > Shopify > ui > fragments > filter > FilterFragment

#### we implement some filters functionality into this.
#### user can search the items by filters with
#### - category,price and condition.