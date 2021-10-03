# rss_parser
A sample program which consumes RSS feed and stores in a database

# How to run
1. clone the project
2. go to project root directory
3. run mvn clean install
4. run mvn spring-boot:run

# Endpoints
*Default application will run on port 8080, If you want to run it on different port pass -Dserver.port='your_preffered_port'.

1. http://localhost:8080/items

or you can pass parameter for pagination and sorting as below

2. http://localhost:8080/items?page=1&sort=pubDate&direction=desc&size=5

# parameters
page[optional] [default=1]

sort[optional] [default=pubDate]

direction[optional] [default=desc]

size[optional] [default=10]



