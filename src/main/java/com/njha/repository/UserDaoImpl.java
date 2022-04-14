package com.njha.repository;

import com.njha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "USER";

    // https://github.com/huangjian888/jeeweb-mybatis-springboot/search?p=2&q=RedisTemplate
    //redisTemplate.opsForCluster()
    //redisTemplate.opsForValue()

    // https://github.com/huangjian888/jeeweb-mybatis-springboot/blob/22926faf93f960e02da4a1cad36ed7d8f980b399/x-restful/x-business-service/src/main/java/com/company/shop/sys/service/support/redis/RedisHelper.java
    //redisTemplate.boundHashOps(key).put(field, value);
    //redisTemplate.boundHashOps(key).get(field);

    // https://github.com/huangjian888/jeeweb-mybatis-springboot/blob/22926faf93f960e02da4a1cad36ed7d8f980b399/x-restful/x-business-goods/src/main/java/com/company/business/goods/support/redis/RedisHelper.java
    //redisTemplate.boundValueOps(key).get();
    //redisTemplate.boundValueOps(key).set(value);

    @Override
    public boolean saveUser(User user) {
        try {
            redisTemplate.opsForHash().put(KEY, user.getId().toString(), user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = redisTemplate.opsForHash().values(KEY);
        return users;
    }

    @Override
    public User getUserById(Long id) {
        User user = (User) redisTemplate.opsForHash().get(KEY, id.toString());
        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            redisTemplate.opsForHash().delete(KEY, id.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(Long id, User user) {
        try {
            redisTemplate.opsForHash().put(KEY, id.toString(), user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
