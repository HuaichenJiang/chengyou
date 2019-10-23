# chengyou
Path planning system based on train

This system is a mixed traffic path planning system.
why we create that project?
In nowdays, many people have frequent cross city travel, but most of the time, there is no direct access between the two places.
Our system is aim to solve this problem.

First step:
The city.txt and the station.txt are the basic data.
You should create a database based on mysql, and create the table from mapping file.

Second step:
Start up the system, and request the url : http://{hostname}:{port}/initData. 
It may be take some time, and it will Initialization the data into the database.

Third step:
