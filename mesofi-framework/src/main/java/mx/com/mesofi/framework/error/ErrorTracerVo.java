package mx.com.mesofi.framework.error;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import mx.com.mesofi.framework.util.EntityVo;

@Entity(name = "MSF_ERROR_TRACE")
public class ErrorTracerVo extends EntityVo {
    @Column
    private Date errorDt;
    @Column(name = "TYPE")
    @Enumerated(EnumType.ORDINAL)
    private ErrorType errorType;
    private String message;
    private String trace;

    public ErrorTracerVo(ErrorType errorType, String message, String trace) {
        this.errorDt = new Date();
        this.errorType = errorType;
        this.message = message;
        this.trace = trace;
    }

    public ErrorTracerVo() {

    }

    /**
     * @return the errorDt
     */
    public Date getErrorDt() {
        return errorDt;
    }

    /**
     * @return the errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the trace
     */
    public String getTrace() {
        return trace;
    }

}
