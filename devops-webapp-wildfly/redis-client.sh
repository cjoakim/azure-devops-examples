#!/bin/sh

# Bash script to open a redis-cli pointing at Azure Redis Cache.
# Chris Joakim, Microsoft, 2019/11/23

echo 'Starting a redis-cli pointing to:'
echo 'AZURE_REDISCACHE_HOST: '$AZURE_REDISCACHE_HOST
echo 'AZURE_REDISCACHE_PORT: '$AZURE_REDISCACHE_PORT
echo '> quit when done'

redis-cli -h $AZURE_REDISCACHE_HOST -p $AZURE_REDISCACHE_PORT -a $AZURE_REDISCACHE_KEY

# Example use:
#
# cjoakimredis.redis.cache.windows.net:6379> get cat
# (nil)
# cjoakimredis.redis.cache.windows.net:6379> set cat miles
# OK
# cjoakimredis.redis.cache.windows.net:6379> get cat
# "miles"
