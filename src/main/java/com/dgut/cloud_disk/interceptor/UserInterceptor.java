package com.dgut.cloud_disk.interceptor;

import com.dgut.cloud_disk.pojo.CdstorageUser;
import com.dgut.cloud_disk.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisPool jedisPool;
    @Value("${redis.defaultTokenValidTime}")
    private Integer tokenValidTime;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getParameter("token");
        if(token==null) {
            this.renderJson(response, JSONResult.errorMsg("没传token，不允许访问"));
            return false;
        }

        Jedis jedis = jedisPool.getResource();
        String tokenValue = jedis.get(token);
        //判断下有没有token的值，没有值说明已作废
        if(tokenValue==null) {
            this.renderJson(response, JSONResult.errorMsg("用户未登录，请先登录"));
            jedis.close();
            return false;
        }
        //如果有值，还需要看一下有效时间， 》=0 还有具体的有效时间、-1不限时间 、小于-1就是已过期
        Long ttl = jedis.ttl(token);
        if(ttl < -1) {
            this.renderJson(response, JSONResult.errorMsg("登录超时，请重新登录"));
            jedis.close();
            return false;
        }

        //说明里面有东西，还没过期，需要解析有效时间，重新设置token有效期，刷新时间
        ObjectMapper mapper = new ObjectMapper();
        //反序列化读取封装的token对象
        CdstorageUser user = mapper.readValue(tokenValue, CdstorageUser.class);
        if(user.getUserPermission()!=0){
            this.renderJson(response, JSONResult.errorMsg("权限不足"));
            jedis.close();
            return false;
        }
        //从token对象中取得有效时间（当时登录时存的，可能是站点默认值，可能是用户填写的具体时间长，也可能是永远记住-1），重新设置redis中的key的有效时间，完成刷新
        jedis.expire(token,tokenValidTime);
        jedis.close();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 将返回结果对象，转换成json，通过http响应，字符打印到界面
     * @param response
     * @param jsonData
     */
    private void renderJson(HttpServletResponse response,JSONResult jsonData) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            writer.print(mapper.writeValueAsString(jsonData));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
