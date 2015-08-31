package com.hand;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

/**
 * Created by Administrator on 2015/8/31.
 */
public class MyInterceptor extends EmptyInterceptor {
    private int updates;
    private int creates;
    private int loads;

    public boolean onSave(Object entity,
                          Serializable id,
                          Object[] state,
                          String[] propertyNames,
                          Type[] types) {
        if ( entity instanceof CustomerEntity ) {
            System.out.println("Before Save.");
            return true;
        }
        return false;
    }

}
