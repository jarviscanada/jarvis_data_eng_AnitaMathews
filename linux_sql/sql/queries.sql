-- groups hosts by hardware info
-- group by cpu number and sort by memory size in descending order

SELECT cpu_number, id AS host_id, total_mem
FROM host_info
ORDER BY 1, 3 DESC;


-- average memory usage in percentage over 5 min intervals for each host
-- used memory = total memory - free memory
create function round5(ts timestamp) RETURNS timestamp
AS
$$
BEGIN
   RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

SELECT t1.host_id, t2.hostname, timestamp5, round((t2.total_mem - t1.avg_free_mem)/t2.total_mem * 100, 2) AS avg_used_mem_percentage
FROM
(SELECT host_id, round5(timestamp) as timestamp5, AVG(memory_free) as avg_free_mem
FROM host_usage
GROUP BY host_id, timestamp5) t1
INNER JOIN host_info t2
ON t1.host_id = t2.id;

SELECT host_id, round5(timestamp) as timestamp5, COUNT(*) AS num_data
FROM host_usage
GROUP BY host_id, timestamp5
HAVING COUNT(*) < 3;