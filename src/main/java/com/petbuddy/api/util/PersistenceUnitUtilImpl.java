package com.petbuddy.api.util;

import com.petbuddy.api.model.commons.Id;

import javax.persistence.PersistenceUnitUtil;

public abstract class PersistenceUnitUtilImpl implements PersistenceUnitUtil {

    @Override
    public Object getIdentifier(Object o) {

        return ((Id)o).value();
    }
}
