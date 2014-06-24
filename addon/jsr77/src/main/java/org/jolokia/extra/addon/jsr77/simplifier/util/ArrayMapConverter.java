package org.jolokia.extra.addon.jsr77.simplifier.util;

import java.util.HashMap;
import java.util.Map;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * Convert an array to a map where the map's key is extrcted from each
 * element in the array
 *
 * @author roland
 * @since 24.06.14
 */
public class ArrayMapConverter<T> {

    KeyExtractor<T> keyExtractor;

    /**
     * Constructor with a given extractor
     * @param pKeyExtractor the extractor to use for getting the key
     */
    public ArrayMapConverter(KeyExtractor<T> pKeyExtractor) {
        keyExtractor = pKeyExtractor;
    }

    /**
     * Convert an array to a map
     * @param pValues the values to convert
     * @param pName optional name to use to extract from the objectName
     * @return the converted values as map
     */
    public Map<String,T> convert(T[] pValues,String ... pName) {
        Map<String,T>  ret = new HashMap<String, T>();
        for (T value : pValues) {
            String key = prepareKey(keyExtractor.extractKey(value),pName);
            ret.put(key,value);
        }
        return ret;
    }

    private String prepareKey(String key, String[] pName) {
        if (pName.length > 0) {
            ObjectName oName = createObjectName(key);
            return extractNameProperty(oName,pName[0]);
        } else {
            return key;
        }
    }

    private ObjectName createObjectName(String pName) {
        ObjectName oName;
        try {
            oName = new ObjectName(pName);
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException("Managed Object Name '" + pName +
                                               " is not a ObjectName");
        }
        return oName;
    }

     private String extractNameProperty(ObjectName pOName,String pName) {
         String name = pOName.getKeyProperty(pName);
         if (name == null) {
             throw new IllegalStateException("No 'name' key attribute in " + pOName
                                             + " as required by JSR-77");
         }
         return name;
     }


    /**
     * Extractor interface for getting the key
     * @param <V> value type
     */
    public interface KeyExtractor<V> {
        /**
         * Extract key from given value, which is supposed to be non-null
         *
         * @param value value to get the key from
         * @return key
         */
        String extractKey(V value);
    }
}
