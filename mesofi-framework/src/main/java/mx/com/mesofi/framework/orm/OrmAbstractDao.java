package mx.com.mesofi.framework.orm;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrmAbstractDao<E, I extends Serializable> implements AbstractDao<E, I> {
    private Class<E> entityClass;

    protected OrmAbstractDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E findById(I id) {
        return (E) getCurrentSession().get(entityClass, id);
    }

    @Override
    public void saveOrUpdate(E e) {
        getCurrentSession().saveOrUpdate(e);
    }

    @Override
    public void insert(E e) {
        getCurrentSession().save(e);
    }

    @Override
    public void delete(E e) {
        getCurrentSession().delete(e);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        return criteria.list();
    }
}
