package com.invento.ecom.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    public boolean isNotNull(String value){
        boolean notNull =false;
        if(value!=null && value.length()>0){
            notNull = true;
        }
        return notNull;
    }
}
