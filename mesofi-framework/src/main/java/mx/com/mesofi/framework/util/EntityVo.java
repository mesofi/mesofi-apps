/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.util;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Contains commons properties among the entities in application.
 * 
 * @author Armando Rivas
 */
@MappedSuperclass
public class EntityVo {
    /**
     * Unique identifier.
     */
    @Id
    private long id;

    public EntityVo() {

    }

    public EntityVo(long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityVo other = (EntityVo) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
