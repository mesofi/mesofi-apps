package mx.com.mesofi.framework.error;

import mx.com.mesofi.framework.orm.OrmAbstractDao;
import mx.com.mesofi.framework.stereotype.DAO;

@SuppressWarnings("unchecked")
@DAO
public class ErrorTracerDaoImpl extends OrmAbstractDao<ErrorTracerVo, Long> implements ErrorTracerDao {

    public ErrorTracerDaoImpl() {
        super(ErrorTracerVo.class);
    }

    @Override
    public void saveLog(ErrorTracerVo errorTracerVO) {
        insert(errorTracerVO);
    }

}
