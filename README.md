![Logo of the project](https://github.com/LSNovais/CurrencyConverter/blob/0380db0c78faf66d8e560a8c4142fda9716be7d9/public/readme_images/Logo.png)

# CurrencyConverter


## Purpose
Currency Converter is an application that was developed with the purpose of allowing users to convert values ​​between currencies of different nationalities, based on constantly updated conversion rates and to consult their conversion history.


## Technology 

Here are the technologies used in this project.

* Java 11.0
* Spring boot 2.4.9
* H2 Database

These technologies were chosen for the development of this application, in order to optimize development time and increase productivity. As the application was developed from scratch, I already get most of the resources I need.


## Services Used

* Github


## Separation of Layers
### The layers are separated as following way:

- service: Class that performs action to the database or to requests from other external APIs
- repository: Make requests for the Model classes
- model: Classes that assemble the database tables
- dto: Classes that will receive JSON values from the URL
- controller: Controls and handles requests received per URL
![Layers](https://github.com/LSNovais/CurrencyConverter/blob/94c15264336019d1ee67e9d6d1e960fd8207eafb/public/readme_images/Layers.png)





## How to use
### 1 - Go to class CurrencyconverterApplication.java and run the application.
![Run Application](https://github.com/LSNovais/CurrencyConverter/blob/cb500fbbb8b5141e81971a5d188f30f2b4b5db42/public/readme_images/Start.png)



### 2 - Go to archive currencyHTTP.http and fill in the URL as the example below:
- http://localhost:8080/currencyConverter/convert/{idUser}/{currencyOrigin}/{valueOrigin}/{currencyDestiny}



### 3 - Click on "Send Request" and the value will be converted:
![Convert_Value](https://github.com/LSNovais/CurrencyConverter/blob/cb500fbbb8b5141e81971a5d188f30f2b4b5db42/public/readme_images/conversao1.png)
![Convert_Value](https://github.com/LSNovais/CurrencyConverter/blob/cb500fbbb8b5141e81971a5d188f30f2b4b5db42/public/readme_images/conversao2.png)
![Convert_Value](https://github.com/LSNovais/CurrencyConverter/blob/cb500fbbb8b5141e81971a5d188f30f2b4b5db42/public/readme_images/conversao3.png)



### 4 - Go to archive histCurrencyHTTP.http and fill in the URL as the example below:
- http://localhost:8080/currencyConverter/consult/transactions/{idUser}



### 5 - Click on "Send Request" and the history will be consulted by idUser:
![Convert_Value](https://github.com/LSNovais/CurrencyConverter/blob/cb500fbbb8b5141e81971a5d188f30f2b4b5db42/public/readme_images/consulta1.png)
![Convert_Value](https://github.com/LSNovais/CurrencyConverter/blob/cb500fbbb8b5141e81971a5d188f30f2b4b5db42/public/readme_images/consulta2.png)



## Features

  - Currency Type Conversion in real time
  - Consult conversion History


## Links

  - Repository: https://github.com/LSNovais/CurrencyConverter
    - In case of errors or asks, please contact:
      lucas.nds@hotmail.com 



## Versioning

1.0.0.0


## Authors

* **Lucas Novais dos Santos**: @LSNovais (https://github.com/LSNovais)


Please follow github and join us!
Thanks to visiting me!
