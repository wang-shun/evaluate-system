package mycat.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/**
 * 单例获取mybatis session对象
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月1日]
 * @since  [云服务/模块版本]
 */
public class SingletonSession
{
    
    private static SingletonSession bean = new SingletonSession();
    
    private SqlSessionFactory sf = null;
    
    private SingletonSession()
    {
        String conf = "SqlMapConfig.xml";
        InputStream in = null;
        try
        {
            in = Resources.getResourceAsStream(conf);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        // 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
        
        // 通过SessionFactoryBuilder对象获取SqlSessionFactory
        sf = sfb.build(in);
    }
    
    public static SingletonSession getInstance()
    {
        if(null == bean)
        {
            bean = new SingletonSession();
        }
        return bean;
    }

    public SqlSessionFactory getSf()
    {
        return sf;
    }

    public void setSf(SqlSessionFactory sf)
    {
        this.sf = sf;
    }
    
}
