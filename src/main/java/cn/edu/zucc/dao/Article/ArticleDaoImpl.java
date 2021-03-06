package cn.edu.zucc.dao.Article;

import cn.edu.zucc.common.CommonDaoImpl;
import cn.edu.zucc.dao.User.UserDao;
import cn.edu.zucc.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by shentao on 2016/5/31.
 */
public class ArticleDaoImpl  extends CommonDaoImpl<TbArticleEntity> implements ArticleDao {


    @Override
    public void updateArticleInfo(TbArticleEntity tbArticleEntity) throws Exception {

        System.out.println("articleId="+tbArticleEntity.getArticleId());
        System.out.println("articleLikes="+tbArticleEntity.getArticleLikes());
        System.out.println("articleCdate="+tbArticleEntity.getArticleCdate());
        System.out.println("articleLooks="+tbArticleEntity.getArticleLooks());
        System.out.println("articleStaticUrl="+tbArticleEntity.getArticleStaticUrl());


        String hql ="update TbArticleEntity set articleLikes=? , articleCdate=?,articleLooks=?,articleStaticUrl=? where articleId=?";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setInteger(0,tbArticleEntity.getArticleLikes());
        query.setTimestamp(1,tbArticleEntity.getArticleCdate());
        query.setInteger(2,tbArticleEntity.getArticleLooks());
        query.setString(3,tbArticleEntity.getArticleStaticUrl());
        query.setInteger(4,tbArticleEntity.getArticleId());

    }

    @Override
    public long getCount() throws Exception {
        String hql = "select count(*)  from TbArticleEntity ";
        long count = (long) getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult();
        return  count;
    }

    @Override
    public TbArticleEntity findByIdinfo(Integer id) throws Exception {
        String hql ="select articleId,articleLooks,articleLikes,articleMdate,articleStaticUrl from TbArticleEntity where articleId=?";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setInteger(0,id);
        return (TbArticleEntity) query.uniqueResult();

    }

    @Override
    public List<TbArticleEntity> findByqQuery(String hql,int i) {
        //Query query = getSessionFactory().getCurrentSession().createQuery("from TbArticleEntity");
        List<TbArticleEntity> articles =getSessionFactory().getCurrentSession().createQuery("select articleTitle,articleId from TbArticleEntity").list();
        return articles;

    }

    @Override
    public List<LastarticleEntity> findAllLastarticle() {
        List<LastarticleEntity> list=null;
        try {
            list=getSessionFactory().getCurrentSession().createQuery("from LastarticleEntity order by articleCdate desc").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ToparticlesEntity> findAllToparticle() {
        List<ToparticlesEntity> list=null;
        try {
            list=getSessionFactory().getCurrentSession().createQuery("from ToparticlesEntity order by articleCdate desc").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ArticlesEntity> findArticles(int articleId) {
        List<ArticlesEntity> list=null;
        try {
            Query query = getSessionFactory().getCurrentSession().createQuery("from ArticlesEntity where articleId=?");
            query.setInteger(0,articleId);
            return query.list();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ArticlesEntity> findNextArticle(Timestamp Cdate) {
        List<ArticlesEntity> list=null;
        try {
            Query query = getSessionFactory().getCurrentSession().createQuery("from ArticlesEntity where articleCdate>? order by articleCdate");
            query.setTimestamp(0,Cdate);
            query.setMaxResults(3);  //查询出来的记录数
            return query.list();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ArticlesEntity> findLastArticle(Timestamp Cdate) {
        List<ArticlesEntity> list=null;
        try {
            Query query = getSessionFactory().getCurrentSession().createQuery("from ArticlesEntity where articleCdate<? order by articleCdate");
            query.setTimestamp(0,Cdate);
            query.setMaxResults(3);  //查询出来的记录数
            return query.list();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }


}
