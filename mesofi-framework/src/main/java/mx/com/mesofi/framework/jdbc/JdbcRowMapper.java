package mx.com.mesofi.framework.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * An interface used by {@link JdbcTemplate} for mapping rows of a
 * {@link java.sql.ResultSet} on a per-row basis. Implementations of this
 * interface perform the actual work of mapping each row to a result object, but
 * don't need to worry about exception handling. {@link java.sql.SQLException
 * SQLExceptions} will be caught and handled by the calling JdbcTemplate.
 * 
 * <p>
 * Typically used either for {@link JdbcTemplate}'s query methods or for out
 * parameters of stored procedures. RowMapper objects are typically stateless
 * and thus reusable; they are an ideal choice for implementing row-mapping
 * logic in a single place.
 * 
 * <p>
 * In mesofi framework this is prefered to use instead of {@link RowMapper}
 * 
 * @author Armando Rivas
 * @since 24 Sep 2013
 */
public interface JdbcRowMapper<T> extends RowMapper<T> {

}
