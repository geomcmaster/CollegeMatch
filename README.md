# CollegeMatch
A Java EE web application for finding colleges based on a number of search criteria. Users can save personal data that can be used in search criteria and can also save colleges they are interested in and track them through the application process.

Building and running the application:
* Download [Tomcat](https://tomcat.apache.org/download-80.cgi)
    * Download [MySql Connector](https://dev.mysql.com/downloads/connector/j/8.0.html) to Tomcat's lib folder
         * if this isn't set up right, you'll receive a "no suitable driver found" error
    * Set Tomcat as the targeted Runtime for the project in eclipse
* Create a db.properties file in src/main/resources

      user=[my_db_user]
      password=[my_db_pw]
* To run in Eclipse

    * right-click project
    * Run As
    * Run on Server
    * Select your server
    * Open in browser of choice
# DBBuilder
Java program for creating the database using the College Scorecard dataset.

Coordinates table data must be imported separately from csv (the table itself is created, however).
The three stored procedures used by the application are not at this time created by DBBuilder code and so they must be added manually.

Requires command line arguments:
1. API key
2. DB username
3. DB password
4. Run-type option [1-4]
    * 1: Drop database if it exists. Create database and populate.
    * 2: Drop tables if they exist. Create tables and populate.
    * 3: Clear tables and repopulate.
    * 4: Do nothing before populating tables.
