task2:
Business Requirements
Create a backend for simple News Management application. Pay attention that your application should be without view layer (UI).

The system should expose REST APIs to perform the following operations:

1. CRUD operations for News.
	a)If new tags and(or) Author are passed during creation or modification of a News, then they should be created in the database. (Save news message with author and tags should go as one step in service layer.)
2. CRUD operations for Tag.
3. CRUD operations for Author.
4. Get news. All request parameters are optional and can be used in conjunction.
	a) Sort news by date or tag or author;
	b) Search according SearchCriteria (see details in Tools and Implementation Requirements section);
5. Add tag/tags for news message;
6. Add news author;
7. Each news should have only one author;
8. Each news may have more than 1 tag;
9. Count all news;


Technical requirements
General Requirements:
1. Code should be clean and should not contain any “developer-purpose” constructions.
2. Application should be designed and written with respect to OOD and SOLID principles.
3. Code should contain valuable comments where appropriate.
4. Public APIs should be documented using Javadoc.
5. A clear layered structure should be used: responsibilities of each application layer should be defined.
6. JSON should be used as a format of client-server communication messages.
7. Convenient error and exception handling mechanism should be implemented: all errors should be meaningful and localized on backend side.
8. Abstraction should be used to avoid code duplication.
9. Database schema should be adjusted as described on image. Create 2 separate db scripts: one for db schema generation and one for loading default data (near 20 items for each table);




Tools and Implementation Requirements:
Please note that only GA versions of tools, frameworks, and libraries are allowed.

1. JDK version: 8. Use Streams, java.time.*, etc. where it is appropriate.
2. Application package root: com.epam.lab
3. Database connection pooling: HikariCP.
4. Spring JDBC Template should be used for data access.
5. Java Code Convention is mandatory. Exception: margin size – 120 chars.
6. Build tool: Apache Maven 3.5+. Multi-module project.
7. Web server: Apache Tomcat.
8. Application container: Spring IoC. Spring Framework +.
9. Spring should be configured via Java config (no spring.xml).
10. Database: PostgreSQL 9.+ or 10.+
11. Testing: JUnit 4.+ or 5.+, Mockito.
12. Service layer should be covered with unit tests. Unit tests coverage should be not less than 80%.
13. Repository layer should be tested using integration tests with an in-memory embedded database. All operations with news should be covered with integration tests.
14. APIs should be demonstrated using Postman tool. Postman collections with APIs should be prepared for the demo.
15. For search functionality use SeachCriteria object which may contain author entity and list of tags (will be using for constructing SQL queries in DAO layer);



It is forbidden to use:

1. Spring Boot,
2. Spring Data Repositories,
3. JPA,
4. Lombok,
5. Powermock.

--------------------------------------------------------------------------------------------------------------------------------------------------------------
task4:
Create module for accessing the database.

Technology stack:

Application container:Spring IoC (Spring Framework version 5.x).
Data access: JPA (Hibernate as JPA Provider).
Bean validation: Hibernate Validator.

Tech requirements:

Create new module for data access to use JPA. Use different Criteria Queries.
Enable logging of SQL queries.
Add transactions support using Spring declarative transactions management. Don't forget to test your transactional methods.
There is option switching between JPA+Hibernate or Spring JDBC in configuration file.


-----------------------------------------------------------------------------------------------------------------------------------------------------------------
task5:
Main requirements:

Jenkins have to build your project according to the Maven build script.
Project is deployed at your local Tomcat Server by Jenkins job, can be accessed by a link in EPAM internal LAN (example: EPBYMINW1111.minsk.epam.com:8080/simple-project).
Readme file in your project should contain:
link to: -Sonarqube, -Jenkins (with credential), -deploy link.

Sonarqube: http://epbyminw7595.minsk.epam.com:9000/dashboard?id=com.epam.lab%3Anews

Jenkins (dev / dev): http://epbyminw7595.minsk.epam.com:8090/

Deploy link: http://epbyminw7595.minsk.epam.com:8080/news/

Rest API:

Example: author

GET: /author/id - Gets author by id.

POST: /author/ - Create new author.
body json example:
{
    "name": "Petr",
    "surname": "Petrov"
}

DELETE: /author/id - Delete author by id.

PUT: /author/ - Update author.
body json example:
{
    "id": 1,
    "name": "Ivan",
    "surname": "Ivanov"
}

Example: news

GET: /news/id - Gets news by id.

POST: /news/ - Create new news.
body json example:
{
    "title": "title",
    "shortText": "Text about",
    "fullText": "full story of text",
    "author": {
        "name": "Petr",
        "surname": "Fedorov"
    },
    "tags": [
        {
            "name": "car"
        },
        {
            "name": "road"
        },           
        {
            "name": "road"
        }
    ]
}

DELETE: /news/id - Delete news by id.

PUT: /news/ - Update news.
body json example:
{
    "id": 1,
    "title": "titleUpdated",
    "shortText": "newsummery",
    "fullText": "full story",
    "author": {
        "id": 1,
        "name": "Petr",
        "surname": "Fedorov"
    },
    "tags": [
        {
            "id": 2,
            "name": "newTag"
        },
        {
            "name": "newTag456"
        },
        { 
            "name": "newTag4565"
        },
        { 
            "name": "newTag4565"
        }        
    ]
}

GET: /news/count - Count all news.
 
GET: /news/search - Search news by query.
example of params:
?authorName=newAuthor&authorSurname=New Author&tags=newTag&tags=neaasdasdTag&orderByParameter=author_name&orderByParameter=creation_date

Example: tags

GET: /tag/id - Gets tag by id.

POST: /tag/ - Create new tag.
body json example:
{
    "name": "Name"
}

DELETE: /tag/id - Delete tag by id.

PUT: /tag/ - Update tag.
body json example:
{
    "id": 1,
    "name": "newName"
}

Also You can find postman backup in package postman in root of project.


Technology stack:

Build tool: Maven.
Tomcat Server - should be installed as Service and start automatic.
Unit testing framework: JUnit (version 4.x is preferred, but you can use 5.x).
Database: PostgreSQL (version 9.x is preferred, but you can use 10.x).
Continuous Integration server: Jenkins LTS (see CI section for more info).
Continuous Integration: 

Configure Jenkins security (install Role strategy plugin). Remove anonymous access. Create administrator user (all permissions) and developer user (build job, cancel builds). Add Jenkins credentials to Readme file in your git repository.
Configure Jenkins build job (pool, run test, build) to checkout your repository, use pooling interval.
Install SonarQube. Configure Jenkins to use local SonarQube installation. Analyze your source code with SonarQube after Maven builds your project. Use JaCoCo for code coverage.


---------------------------------------------------------------------------------------------------------------------
Task7

Develop News Management application. It should include backend for simple News Management application + introduce UI layer.

UI requirements:

Fixed left-hand sidebar menu. Only the actions applicable to current main view should be displayed on the menu;
Fixed footer. Footer should contain copyright and year.
Fixed header. Login/Logout Button, Top area should contain links to change application language;
News title, short description and creation (or modification) date should present on news list view;
News title, short description and full news text should present on news view page;
Pagination should be present on the news list page;
Username should be seen when user is logged in.
User should be redirected to the login page after clicking on “Logout” button;


Technology stack:

React
Node NPM
HTML5
CSS3
Bundler and dependency management:

Webpack


-----------------------------------------------------------------------------------------------------------------------
Task8

This task is an extension of the RESTful web-service for News Management application .

Application should support user-based authentication. This means a user is stored in a database with some basic information and a password.

User Permissions:
Guest:

-Read operations for News.
-Sign up.
-Log in.


User:

-All read operations.
-All create operations.
-All update operations.


Administrator (can be added only via database call):

-All operations.


Tools and Implementation Requirements:
Please note that only GA versions of tools, frameworks, and libraries are allowed.

Spring Boot.
Server should support only stateless user authentication and verify integrity of JWT token.
Use OAuth2 as an authorization protocol.
OAuth2 scopes should be used to restrict data.
Implicit grant and Resource owner credentials grant should be implemented.
Implement CSRF protection.
APIs should be demonstrated using Postman tool.
For demo, prepare Postman collections with APIs.


Restrictions:
It is forbidden to use:

Spring Data Repositories,,
Lombok,
Powermock.