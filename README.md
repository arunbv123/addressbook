

# Addressbook
This repo contains a web application  built using Apache Wicket Framework, Hibernate and mysql. This applicaiton can be deployed on a Wildfly 10 server.

```
Server access :http://107.170.212.195:8080/addressbook
MySql server : http://107.170.212.195:3306, Schema: AddressBook
```

# Project build & deploy

To run the Wicket Address Book, follow these steps:

```sh
    1. git clone https://github.com/arunbv123/addressbook.git to checkout the project
    3. go into the addressbook folder
    4. type "mvn clean install" in the shell to build the project 
    5. Start your Wildfly Server and deploy the addressbook web application
    6. Open the browser at http://localhost:8080/addressbook
```

###Description

The application has following functions:

- User functions:
     1. User sign in: The user can sign in user test account username: test password: test
     2. User sign up: The user can click on sign up link at the bottom of the page and then fill in the details to sign up. Once sign up is successfull user can sign in with the given username and password.
     3. User sign out: The user can signout by clicking the right top button in addressbook page.
 
- Address Book functions:
    1. Add contact: Click on the add button in Addressbook page, fill the details and save the page
    2. View contact: The contacts related to the user will be listed in the left side of page. Click on the contact and the details will be displayed on the right side.
    3. Edit contact: In the contact details page can press on edit button to enabled the edit option then click on the save button to save the contact.
    4. Delete contact: In the contact details page press on the detele button to delete the contact from the user address book.
     


[![N|Solid](http://svn.apache.org/repos/asf/wicket/sandbox/dashorst/animation/logo-top.png)](https://wicket.apache.org/)
[![N|Solid](http://www.javatpoint.com/images/hibernate/hibernate2.png)](http://hibernate.org/)
[![N|Solid](http://www.damnsemicolon.com/wp-content/uploads/2011/04/mysql-database.jpg)](https://www.mysql.com/)
[![N|Solid](https://www-eapps.r.worldssl.net/images/wildfly-bug.jpg)](http://wildfly.org/)
