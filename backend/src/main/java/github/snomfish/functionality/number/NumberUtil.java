package github.snomfish.functionality.number;

public class NumberUtil {
    

    private NumberUtil() {}

    
    public static <T extends Number> T convert(double value, Class<T> clazz) {
        if (clazz == Double.class) return clazz.cast(value);
        if (clazz == Integer.class) return clazz.cast((int) value);
        
        if (clazz == Float.class) return clazz.cast((float) value);
        if (clazz == Long.class) return clazz.cast((long) value);
        if (clazz == Short.class) return clazz.cast((short) value);
        if (clazz == Byte.class) return clazz.cast((byte) value);

        throw new IllegalArgumentException("Unsupported Number type: " + clazz.getName());
    }
}
