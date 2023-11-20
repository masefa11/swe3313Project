# Data Storage Plan

Choosing the right data storage format is essential to the smooth operation and user experience of our application that we are creating to sell merch and acessories inspired by video games like Fortnite, Call of Duty, etc. In developing our application, JSON (JavaScript Object Notation) is our solution to implementing a simple database due to its adaptability and simplicity of use. The following steps outline how our Java-based program will leverage JSON.

1. Database Technology
   * We will be using Jackson library, an effective framework that makes it easier to include JSON processing into Java.
2. Data Model for Users, Merch, and Sales
   * Define a Java class representing the merch data model with attributes like name, price, and description.
   * Define a Java class representing the users data model with properties like username, password, and admin status.
3. Data Storage
   * To read and write JSON data to a local file on the user's system, utilize Java's File I/O methods. This file is used to store item information permanently.
4. Serialization and Deserialization
   * Execute methods for deserializing JSON data back into Java objects and serializing Java objects representing merch into JSON format. This ensures seamless communication between the stored data and the internal representation of the application.
