package mycat.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mycat.bean.ProductInfo;
import mycat.utils.SingletonSession;

/**
 * ProductInfo 表操作
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月1日]
 * @since  [云服务/模块版本]
 */
public class ProductInfoService
{
    
    /**
     * 插入产品的版本信息
     * <功能详细描述>
     * @param data
     */
    public void insert(ProductInfo data)
    {
        if (null != data)
        {
            SqlSession session = null;
            SqlSessionFactory sf = SingletonSession.getInstance().getSf();
            try
            {
                session = sf.openSession();
                session.insert("ProductInfoMapper.insertSelective", data);
                session.commit();
            }
            finally
            {
                if (null != session)
                {
                    session.close();
                }
            }
        }
    }
    
    /**
     * 获取一个新的分析ID
     * <功能详细描述>
     * @return
     */
    public int getNewId()
    {
        SqlSession session = null;
        ProductInfo data = null;
        int out = -1;
        SqlSessionFactory sf = SingletonSession.getInstance().getSf();
        try
        {
            session = sf.openSession();
            data = session.selectOne("ProductInfoMapper.getMaxId");
            if (null != data)
            {
                out = data.getId() + 1;
            }
            else
            {
                data = session.selectOne("ProductInfoMapper.getCount");
                if(null != data && data.getId() == 0)
                {
                    // 初始化第一条数据
                    out = 1;
                }
            }
        }
        finally
        {
            if (null != session)
            {
                session.close();
            }
        }
        return out;
    }
    
}
