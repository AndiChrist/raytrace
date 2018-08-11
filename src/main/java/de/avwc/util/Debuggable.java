package de.avwc.util;

import java.lang.reflect.Field;

/**
 * Created by andichrist on 13.05.17.
 */
public interface Debuggable {
    default String debug() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append(" [ ");
        Field[] fields = getClass().getDeclaredFields();
        int count = 0;
        for(Field f: fields) {
            f.setAccessible(true);
            try {
                sb.append(f.getName() + " = " + f.get(this));
                if (++count < fields.length)
                    sb.append(", ");
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        sb.append(" ]");
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
