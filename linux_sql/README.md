# Linux Cluster Monitoring Agent
# Introduction
The purpose of this project is to design and implement a Linux Cluster Monitoring Agent which will aid the Jarvis Linux Cluster Administration (LCA) team.
The LCA team manages a Linux cluster with 10 servers, each running CentOS 7, and is interested in recording the hardware specifications and real-time resource usage for each server.
In this project, an MVP is developed using bash scripts, docker, postgres SQL and git.
The overall design involves a set of bash scripts which are to be used on each server to record data and send it to a postgresSQL database which 
will be on one of the servers. The database is provisioned using Docker (which allows it to be easily deployed) and resource usage data will be sent to the database every minute.
In this way, the LCA team can then use the data to better utilize and manage their resources.

# Quick Start
Below are some quick start commands to run the relevant bash scripts and set up the database tables.
- Starting/Stopping the psql instance (provisioned using Docker)
```
# container must first be created
./scripts/psql_docker.sh create db_username db_password

# starting/stopping the container
./scripts/psql_docker.sh start db_username db_password
./scripts/psql_docker.sh stop db_username db_password
```
- Creating tables using ddl.sql
```
# tables are created in the host_agent database
export PGPASSWORD=USER_PASSWORD
psql -h HOST_NAME -p PSQL_PORT -U USER_NAME -d host_agent -f sql/ddl.sql
```
- Inserting hardware specs data into the database using host_info.sh
```
./scripts/host_info.sh HOST_NAME PSQL_PORT host_agent USER_NAME PASSWORD
```
- Inserting hardware usage data into the database using host_usage.sh
```
./scripts/host_usage.sh HOST_NAME PSQL_PORT host_agent USER_NAME PASSWORD
```
- Crontab setup
```
crontab -e

# add to crontab
* * * * * bash [path to host_usage.sh] localhost 5432 host_agent postgres password > /tmp/host_usage.log
```

# Implementation
Each server will have a copy of the `host_info.sh` and `host_usage.sh` scripts which will record the hardware
specifications and the resource usage (e.g. disk available) of each server. The hardware specifications
are collected once while the resource usage values are collected every minute (configured to run using `crontab`).
After collecting the data, these scripts then record the data in a postgreSQL database called `host_agent` which contains
two tables called `host_info` and `host_usage`. This database is provisioned using Docker by creating a container using a psql image and creating a volume (`pgdata`) to preserve
the data from this container. 

## Architecture 
Draw a cluster diagram with three Linux hosts, a DB, and agents (use draw.io website). Image must be saved to the `assets` directory.
![architecture diagram](/assets/LinuxSQL2.drawio.png)

## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling

The schemas for the `host_info` and `host_usage` tables can be seen below.

`host_info` :

| id         | hostname | cpu_number  |  cpu_architecture | cpu_model  | cpu_mhz   | l2_cache  | total_mem | timestamp |
| ----------- | ----------- | --------   |  ------------     | ---------- | --------  | --------- | --------- | --------- |
| serial (primary key)   | varchar       | integer    |  varchar          | varchar    | numeric   | integer   | integer   | timestamp |


`host_usage` :

| timestamp | host_id | memory_free | cpu_idle | cpu_kernel | disk_io | disk_available |
| --------- | --------| ---------   | ---------| --------   | ------- | -------------- |
| timestamp | integer | integer     | integer      | integer | integer | integer       |

# Testing
How did you test your bash scripts and SQL queries? What was the result?

## Bash Scripts

Also tested the PSQL docker script to ensure that it failed properly...

Bash scripts were tested by comparing inserted data to that obtained from `/proc/meminfo` and
the `lscpu` and `vmstat` commands. 

```
$ lscpu

Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                2
On-line CPU(s) list:   0,1
Thread(s) per core:    2
Core(s) per socket:    1
Socket(s):             1
NUMA node(s):          1
Vendor ID:             GenuineIntel
CPU family:            6
Model:                 63
Model name:            Intel(R) Xeon(R) CPU @ 2.30GHz
Stepping:              0
CPU MHz:               2299.998
BogoMIPS:              4599.99
Hypervisor vendor:     KVM
Virtualization type:   full
L1d cache:             32K
L1i cache:             32K
L2 cache:              256K
L3 cache:              46080K
NUMA node0 CPU(s):     0,1
...

$ cat /proc/meminfo
MemTotal:        7489644 kB
MemFree:         1608132 kB
MemAvailable:    4116120 kB

...

$ vmstat -t
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu----- -----timestamp-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st                 UTC
 1  0      0 2200076   6032 2595112    0    0    98     9  359  670  6  1 94  0  0 2021-11-18 05:54:37

$ ./scripts/host_info.sh localhost 5432 host_agent postgres password
$ ./scripts/host_usage.sh localhost 5432 host_agent postgres password
```

```
SELECT * FROM host_info;
```

 | id | hostname | cpu_number | cpu_architecture | cpu_model  | cpu_mhz  | l2_cache | total_mem | timestamp |
| ---- | --------| -----------| -----------------| -----------| ---------| ---------| --------- | ----------| 
 | 1 | jrvs-remote-desktop-centos7.us-east1-c.c.alert-port-331615.internal |  2 | x86_64  | Intel(R) Xeon(R) CPU @ 2.30GHz | 2299.998 |  256 |  7314 | 2021-11-18 05:55:56

```
SELECT * FROM host_usage
```
| timestamp | host_id | memory_free | cpu_idle | cpu_kernel  | disk_io  | disk_available | 
| ---- | --------| -----------| -----------------| -----------| ---------| ---------| 
| 2021-11-18 06:03:10 | 1 |  1717 | 93 | 6 | 0 |  24351 |  

The records created match the expected data with slight variations due to the timing.

Tested SQL queries - inserted test data...

# Deployment
How did you deploy your app? (e.g. Github, crontab, docker)

# Improvements
Three improvements that could be made include:
- allowing an option for updating hardware specifications
- using a bash script to add new crontab job
- more testing using SQL queries to showcase potential business value (e.g. running average of cpu_idle, summary statistics of different cpu models)