# UK Postal Code Distance REST API

This project provides the following APIs:

- `GET /postcode/{postcode}` - to get latitude and longitude by the given UK Postal Code
- `PUT /postcode/{postcode}` - to update latitude and longitude by the given UK Postal Code
- `GET /distance` - to get distance by the given UK Postal Codes

## Technologies
- MySQL 5.7
- Maven 3.6.3
- JDK 11.0.5

## MySQL Database
- to create table called `uk_postalcode`
```
--
-- Table structure for table `uk_postalcode`
--
CREATE TABLE IF NOT EXISTS `uk_postalcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postcode` varchar(8) NOT NULL,
  `latitude` decimal(12,9) NOT NULL,
  `longitude` decimal(12,9) NOT NULL,
  PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;
```
- click here to download [sample data](https://www.freemaptools.com/download/full-postcodes/ukpostcodesmysql.zip) around 1 million records
- or you can visit the [FreeMapTools download page](https://www.freemaptools.com/download-uk-postcode-lat-lng.htm)

## Swagger 2.0
- This project enables Swagger 2.0. You can open the Swagger UI page `http://localhost:8080/swagger-ui/index.html` after the application is launched so that you can start test the endpoints. Before that you need to login via authorize button with authentication which is provided in HTTP Basic Authentication section.
- Or if you want the API docs `http://localhost:8080/v2/api-docs`

## HTTP Basic Authentication
- `Username: user`
- `Password: user`

## Build & Run

Run it with Maven build tool:
```
$ mvn spring-boot:run
```

Build docker image
```
$ docker build -t uk-postal-code-distance-rest-api .
```
