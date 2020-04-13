
-- 获取此hash下的所有值
local groups = redis.call('hvals',KEYS[1]);
local key_perfix='shiro:session';
-- 处理
for i,v in ipairs(groups) do
    local key = key_perfix..tostring(v);
    redis.call('del',key);
end
return true
--删除完毕