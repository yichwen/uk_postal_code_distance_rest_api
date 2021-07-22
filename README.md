# UK Postal Code Distance REST API

This project provides the following APIs:

- `PUT /postcode/{postcode}` - to update latitude and longitude by the given UK Postal Code
- `GET /distance` - to get distance by the given UK Postal Codes

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
- This project enables Swagger 2.0. You can open the Swagger UI page `http://localhost:8080/swagger-ui/index.html` after the application is launched
- Or if you want the API docs `http://localhost:8080/v2/api-docs`

## HTTP Basic Authentication
- `Username: user`
- `Password: user`


## Build & Run

Run it with Maven build tool:
```
$ mvn springboot:run
```

Build docker image
```
$ docker build -t uk-postal-code-rest-api .
```

Run in docker
```
$ docker-compose up -d
```