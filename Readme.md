# **Loan API**

Loan API hace parte del proyecto challenge para emitir fácilmente nuevos préstamos a los usuarios y averiguar cuál es el valor y el volumen de la deuda pendiente.

<br/>

# Solucion
![Loan HLD](resources/LoanAPIHLD.png)

Para la solucion se plantea un modelo multi-capa donde separamos las responsabilidades de los componentes asociados a cada una de ellas.

## Controller
En esta capa ubicamos los componentes que exponen las capcidades de nuestra API Restfull al cliente. Para esta implementacion usamos API Restfull.

## Service
En esta capa ubicamos la logicia especializada de negocio, de esta forma mantenemos esta logica oculta del cliente y nos ayuda a generar desacoplamiento con la capa de presentacion(Controller).

## Domain
En esta capa ubicamos los componentes relacionados al dominio de datos y la interaccion con la base de datos para soportar la logica de negocio.

# Tools
* ## Java version 11
* ## Framework Spring Boot
  * ### spring-boot-starter-web
  * ### spring-boot-starter-data-jpa
  * ### lombok
  * ### spring-boot-starter-test
  
El framework principal que usamos para desarrollar la solucion es SpringBoot y nos apoyamos de las librerias listadas
anteriormente.
Cada una de ellas soportando las diferentes capas y requerimientos del challenge, entre ellas tenemos el starter-web,
que uno de sus principales aportes es soportar la capa controller para la exposicion de las API Restfull. Por otro lado
tenemos el starter-data-jpa que nos permite interactuar con mas facilidad con la base de datos y nos brinda notacion y
consultas prediseñadas para reducir la complejidad en la implementacion.
Lombok por su parte es una libreria que nos ayuda con el clean code, reduciendo la cantidad de lineas de codigo 
repetitivas que escribimos en algunos componentes. Por ultimo pero no menos importante, starter-test nos aporta las 
librerias de junit y mockito para la implementacion de las pruebas unitarias realizadas.

# Features
## Solicitud de Prestamo

#### POST /api/loans
###### Request body
```json
{
    "amount": 10000,
    "term": 12,
    "userId": 1
}
```

###### Response body
```json
{
  "loanId": 301,
  "installment": 159.4
}
```

## Listado de Prestamos

#### GET /api/loans?to=2022-09-29&size=2&page=100&from=2021-01-01

###### Response body
```json
{
  "content": [
    {
      "id": 298,
      "amount": 2279991.03,
      "term": 21,
      "user_id": 96,
      "target": "FREQUENT",
      "date": "2022-08-27"
    },
    {
      "id": 299,
      "amount": 2389367.42,
      "term": 19,
      "user_id": 2,
      "target": "NEW",
      "date": "2021-04-17"
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 200,
    "pageNumber": 100,
    "pageSize": 2,
    "unpaged": false,
    "paged": true
  },
  "last": false,
  "totalElements": 203,
  "totalPages": 102,
  "size": 2,
  "number": 100,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": false,
  "numberOfElements": 2,
  "empty": false
}
```