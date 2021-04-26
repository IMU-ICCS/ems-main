#!/bin/bash
docker exec -it ui-influxdb bash /restartscript.sh
docker exec -it ubuntu_memcache_1 sh /restartscript.sh
