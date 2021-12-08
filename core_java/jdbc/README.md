# Introduction
The purpose of this application is to utilize the JDBC API in Java in order to execute queries on a database. The JDBC API handles connections to RDBMS databases, executes queries written using SQL strings and can manage result sets that are retrieved from the databases. A PostgreSQL database is provisioned using Docker and is populated with data in several tables. Through this Java app (that is built with Maven), queries can be executed on this database which include inserting into tables, updating records and deleting records. As well, the result sets can be iterated over in order to examine them. 

# Implementation
A PSQL database was first provisioned using Docker:
```
> ./psql_docker.sh start postgres password

# creating the tables
> psql -h localhost -U postgres -f database.sql
> psql -h localhost -U postgres -d hplussport -f customer.sql
> psql -h localhost -U postgres -d hplussport -f product.sql
> psql -h localhost -U postgres -d hplussport -f salesperson.sql
> psql -h localhost -U postgres -d hplussport -f orders.sql
```

The tables and their relationships can be seen in the ER diagram.
Java and the JDBC API were then used to access this database.

## ER Diagram
![ER_diagram](./diagrams/hplussport_ERdiagram.png)

## Design Patterns

### DAO Pattern
The DAO design pattern revolves around hiding the implementation for accessing data using an abstract API. It essentially hides the complexity involved with performing CRUD (create, read, update, delete) operations on the database. This abstract API can then be implemented to work with various objects which represent the data. (E.g. A CustomerDAO can be used to perform CRUD operations given Customer objects containing the customer information). This design pattern is at a lower level (closer to the storage) than the Repository pattern.


### Repository Pattern
The Repository design pattern is similar the DAO pattern in that it hides the implementation details when querying the data. However, this design pattern is at a higher level than the DAO (closer to the business logic part of a larger application). This pattern can retrieve and store data from the database using multiple DAOs. In this way, information can be aggregated from various tables or data sources to form a more complete picture which is useful for driving business decisions.

# Test
How you test your app against the database? (e.g. database setup, test data set up, query result)

(testing for each use case - create, read, update and delete.

put the driver code for each case here)
