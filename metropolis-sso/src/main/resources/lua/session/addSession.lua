--
-- Created by IntelliJ IDEA.
-- User: Pop
-- Date: 2020/4/14
-- Time: 9:29
-- To change this template use File | Settings | File Templates.
--
redis.call('hset',KEYS[1],'global',ARGV[1]);
reids.call('hset',KEYS[1],ARGV[2],ARGV[3]);
redis.call('EXPIRE',KEYS[1], ARGV[4])
return true
