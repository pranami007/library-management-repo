Spring boot : 3.3.5

Java : 17

step 1 : create database in mysql with name "library-management"

step 2 : run command "mvn clean install" in execute maven goal for mapstruct interface implementation

step 3 : run project

step 4 : there is 2 in memory user 

        1.) username : user
            password : user
            Role : USER

        2.) username : admin
            password : admin
            Role : ADMIN

step 5 : use this 2 user credentials to perform crud operation on book

step 6 : swagger url : http://localhost:8080/swagger-ui/index.html

Information : 

      user with role USER can only perform get operation to fetch books

      user with role ADMIN can perform all operation on book controller
 
