#!/bin/bash

#CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Number of args should be 5
if [ $# -ne 5 ]; then
  echo "Error: Number of arguments not equal to 5"
  exit 1
fi

#Obtain timestamp, host_id, mem_free, cpu_idle, cpu_kernel, disk_io, disk_available
#host_id is obtained from finding matching hostname in host_info table
hostname=$(hostname -f)
vmstat_mb=$(vmstat --unit M)
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)

cpu_idle=$(echo "$vmstat_mb" | tail -n1 | awk '{print $15}' | xargs) #id
cpu_kernel=$(echo "$vmstat_mb" | tail -n1 | awk '{print $13}' | xargs) #us
disk_io=$(vmstat -d | tail -n1 | awk '{print $10}' | xargs) #cur
disk_available=$(df -BM / | tail -n1 | awk '{print $4}'| sed 's/.$//') #Mb
timestamp=$(vmstat -t | tail -n1 | awk '{print $18, $19}' | xargs)

host_id="(SELECT id FROM host_info WHERE hostname='$hostname')"
insert_stmt="INSERT INTO host_usage VALUES('$timestamp', $host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available)"

#insert data into host_usage table in database
export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"
exit $?
