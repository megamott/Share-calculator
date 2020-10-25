# Share-calculator <br/>
This is a simple CRUD application, that calculate income of my portfolio in [Tinkoff invest](https://www.tinkoff.ru/invest/).  
![shares](https://user-images.githubusercontent.com/54303323/97119312-62dd6180-1720-11eb-882e-0f8cc257f4b7.png)
## Usage
1. Create *.properties* file in **resources/** folder with following fields:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/
spring.datasource.username=
spring.datasource.password=
```
2. Run *.sql* file to fill your database with the required fields.
3. Build the project with Maven:
```
$ mvn clean package
```
4. Then, run it.
## Technologies 
- Java 1.8
- Spring 2.3.3.RELEASE
- Maven 3.1+
- Jsoup 1.13.1 - for parsing
- Postgersql 42.2.5 - database <br/>
<br/>
Use them to avoid build errors.
## Why?
It seemed to me that the Tinkoff application was counting my money incorrectly.
