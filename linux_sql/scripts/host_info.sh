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

#obtain hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem and timestamp
hostname=$(hostname -f)
lscpu_out=$(lscpu)
meminfovals=$(cat /proc/meminfo)

hostname=$(echo "$hostname" | xargs)
cpu_number=$(echo "$lscpu_out"  | grep -E "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | grep -E "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | grep "Model name:" | awk '{print substr($0, index($0, $3)) }' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | grep "CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | grep "L2 cache:" | sed 's/.$//'| awk '{print $3}')
total_mem=$(echo "$meminfovals" | grep "MemTotal:" | awk '{print $2 / 1024}' | xargs)
timestamp=$(vmstat -t | tail -n1 | awk '{print $18, $19}' | xargs)

insert_stmt="INSERT INTO host_info VALUES (DEFAULT, '$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, $total_mem, '$timestamp')"

#insert data into host_info table in database
export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"
exit $?