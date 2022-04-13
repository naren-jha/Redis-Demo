# Redis-Demo
* https://youtu.be/PpkPTviLTLs
* https://spring.io/projects/spring-data-redis

Other videos
* https://youtu.be/cVdQIWpuZk8
* https://youtu.be/p8mK8GBCARE
* https://youtu.be/GEg7s3i6Jak
* https://youtu.be/3WOfXRjYnGA
* https://youtu.be/N8BkmdZzxDg
* https://youtu.be/OqCK95AS-YE
* https://youtu.be/RAPKJFAiBb4
* https://youtu.be/infTV4ifNZY
* https://youtu.be/VLTPqImLapM
* https://youtu.be/jgpVdJB2sKQ
* https://youtu.be/85HzpIk7Mq8
* https://youtu.be/U4WspAKekqM
* https://youtu.be/esbRryo0Ty8
* https://youtu.be/Z8qcpXyMAiA
* https://www.youtube.com/channel/UCD78lHSwYqMlyetR0_P4Vig


## Redis runbook

https://redis.io/docs/getting-started/installation/install-redis-on-mac-os/ 

* **brew install redis** 
* **redis-server** : to start redis in master slave mode

* **brew services start redis** : same as above command
* **brew services stop redis** 
* **brew services info redis** : check status


### Connect to Redis:
Once Redis is running, you can test it by running redis-cli:
* **redis-cli**
* **set mykey somevalue**
* **flushdb**
* **info stats**
* **info memory**

## Doc:
* https://redis.io/docs/getting-started/ 
* https://redis.io/docs/ 


## Jedis vs Lettuce
* https://redis.com/blog/jedis-vs-lettuce-an-exploration/
* Lettuce
  - Thread safe
  - supports cluster
  - capable of synchronous, asynchronous, and reactive interaction with the cluster
  - relatively difficult to use

* Jedis
  - Not thread safe; Need to create connection pool
  - supports cluster
  - capable of only synchronous interaction
  - relatively easy to use


## Clustering
* https://redis.io/docs/reference/cluster-spec/
* https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#cluster
