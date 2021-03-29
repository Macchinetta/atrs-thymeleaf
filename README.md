# Airline Ticket Reservation System (ATRS)

This is a reference application for developers to learn how to build applications with Macchinetta Server Framework & Macchinetta Client Libraries.

## How to run the application

### Download source code

Download source code from [here](https://github.com/Macchinetta/atrs/releases "here").
Extract the zip file at any location of choice.

### Install PostgreSQL

Install and start PostgreSQL.
Select 'postgres' as password for postgres user.
If you'd like to use other password, some changes will be required in setting files.

### Insert test data

First of all, create database named 'atrs'.

After that, execute the command below at the directory where the downloaded source code is unzipped.

```console
$ mvn sql:execute -f atrs-initdb/pom.xml
```

It is assumed that maven is already installed.
If not, install it and try again.

### Build and run applicatoin

Execute the command below at the directory where the downloaded source code is unzipped.

```console
$ mvn clean install
$ mvn cargo:run -f atrs-web/pom.xml
```

Access [http://localhost:8080/atrs/].
