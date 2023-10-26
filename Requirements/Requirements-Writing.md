# Requirements Writing

### Version #1

* Milestone: UI Design

  * Epic: User Self-Registration Page [T6E-1]

    * Create User Registration Page [T6S-1]
      * Priority: Must-Have
      * Estimated Effort: ~ 2 days
      * Functional Requirement
      * Description: The user must self-register with a unique username and a 6-character minimum password. 

  * Epic: User and Admin Login Page [T6E-2]

    * Create a User and Admin Login Page [T6S-2]
      * Priority: Must-Have
      * Estimated Effort: ~ 2 days
      * Functional Requirement
      * Description: The user or admin must login with their own username and password to be sent to main scree.
    * Add “Sign up” Option [T6S-3]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The "sign up" option must direct user to the self-registration page if user is  not already registered.

  * Epic: Main Screen (Inventory) [T6E-3]

    * Add Inventory Search Bar [T6S-4]

      * Priority: Must-Have
      * Estimated Effort: ~1/4 day
      * Functional Requirement
      * Description: The user must be able to type in search bar when searching inventory.

    * Display Inventory Listing [T6S-5]

      * Priority: Must-Have

      * Estimated Effort: ~ 1/2 day

      * Non-functional Requirement

      * Description: The inventory listing must display each item with name, picture, price, and brief discription. All items must be in English and item's prices are in US dollars formatted with "$" sign, commas, and 2 decimal points. 

        ex. 

        ```
        Name: 2016 Suzuki GSXS 1000 Motorcycle
        Picture: At least one photo
        Price: $10,000.00
        Description: Super clean, low mileage liter bike. Great performance, sporty look, and loaded with cool aftermarket toys.
        ```

    * Add “Checkout” Button [T6S-6]

      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The button will start checkout process for user. The user <u>cannot</u> click this button if shopping cart is empty.

    * Create “Add to Cart” Button [T6S-7]

      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Functional Requirement
      * Description: This button will add selected item to user's shopping cart.

    * Create Menu Options for Admin [T6S-8]

      * Priority: Must-Have
      * Estimated Effort: ~ 1 day
      * Functional Requirement
      * Description: The menu options will direct <u>admins only</u> to the terminal to add inventory or the sales report page.

  * Epic: Checkout/Shopping Cart Page [T6E-4]

    * Display Items in Shopping Cart [T6S-9]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Non-functional Requirement
      * Description: The user must be able to view every item in their shopping cart and a subtotal cost in US dollars.
    * Add “Remove Item” Button [T6S-10]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Functional Requirement
      * Description: The button will remove selected items for user <u>with a confirmation pop-up</u>. If user removes all items from shopping cart, the user will be sent to main screen.
    * Add “Pay Now” Button [T6S-11]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: This button will direct user to the payment page to begin the payment process.

  * Epic: Payment Page [T6E-5]

    * Display Payment Process [T6S-12]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Non-functional Requirement
      * Description: The page must list what is information is needed from the user and shipping speed options.
    * Display Subtotal [T6S-13]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Non-functional Requirement
      * Description: The user must be able to view their subtotal while checking out.
    * Create Input Fields for Required Info [T6S-14]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The user must enter the shipping address, credit card information, phone number, and shipping speed in the appropiate input fields.
    * Create “Confirm Order” Button [T6S-15]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The user will be directed to the confirm order page if <u>all required fields</u> are filled out.

  * Epic: Confirm Order Page [T6E-6]

    * Display All Items and Totals [T6S-16]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Non-functional Requirement
      * Description: This page will list all items that user is buying (name and price only), the subtotal, tax (6% of the subtotal), shipping speed cost, and the grand total.
    * Create a “Complete Order” Button [T6S-17]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Functional Requirement
      * Description: The button will send user to the complete order page to complete their transaction. Also, this will take items out of inventory and shopping cart.
    * Create a “Return to Main Page” Button [T6S-18]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The button will send user back to main page while still leaving items in shopping cart.
    * Create a “Return to Shopping Cart” Button [T6S-19]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The button will send user back to shopping cart while still leaving items in cart untouched.

  * Epic: Complete Order Page/Receipt Page [T6E-7]

    * Display User Receipt [T6S-20]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Non-functional Requirement
      * Description: The page will show receipt including the last four digits of the user's credit card number and their shipping address.
    * Add “OK” Button for Receipt Exit [T6S-21]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The button will send user back to main screen after viewing receipt.

  * Epic: Admin Sales Report Page [T6E-8]

    * Display Purchased Items [T6S-22]

      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Functional Requirement
      * Description: This page will show all purchased items along with who made them.

    * Add the “Return to Main Page” Button [T6S-23]

      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Functional Requirement
      * Description: This page will show all purchased items along with who made them.

      

* Milestone: Technical Design

  * Epic: User Registration [T6E-9]
    * Handle User Registration [T6S-24]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The system must verify that the given username and password has not been used already in data table.
  * Epic: User and Admin Login [T6E-10]
    * Verify User and Admin Login [T6S-25]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/4 day
      * Functional Requirement
      * Description: The login credentials must be verified with their previously given username and password.
    * Add New Admins [T6S-26]
      * Priority: Must-Have
      * Estimated Effort: ~ 1/2 day
      * Functional Requirement
      * Description: <u>Only admins</u> must be able to input a registered user's username in login page to transform them to admin. The username input from admin must be used to search data table.
  * Epic: Main Page [T6E-11]
  * Epic: Checking Out [T6E-12]
  * Epic: Payment Process [T6E-13]



### Version #2

* Milestone: Future UI Design
  * Epic: Future Main Screen [T6E-14]
  * Epic: Future Sales Report Page [T6E-15]
  * Epic: Future User and Admin Login Page [T6E-16]
  * Epic: Future Complete Order Page [T6E-17]
  * Epic: Future Payment Page [T6E-18]
* Milestone: Future Technical Design
  * Epic: New Sales Report Page [T6E-19]
  * Epic: New Order Completion [T6E-20]
