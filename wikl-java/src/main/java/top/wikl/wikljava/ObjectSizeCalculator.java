package top.wikl.wikljava;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSizeCalculator {
    private Object getFirstObjectReference(Object o) {
        String objectType = o.getClass().getTypeName();

        if (objectType.substring(objectType.length()-2).equals("[]")) {
            try {
                if (objectType.equals("java.lang.Object[]"))
                    return ((Object[])o)[0];
                else if (objectType.equals("int[]"))
                    return ((int[])o)[0];
                else
                    throw new RuntimeException("Not Implemented !");
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        return o;
    }

    public int getObjectSizeInBytes(Object o) {
        final String STRING_JAVA_TYPE_NAME ="java.lang.String";

        if (o == null)
            return 0;

        String objectType = o.getClass().getTypeName();
        boolean isArray = objectType.substring(objectType.length()-2).equals("[]");

        Object objRef = getFirstObjectReference(o);
        if (objRef != null && !(objRef instanceof Serializable))
            throw new RuntimeException("Object must be serializable for measuring it's memory footprint using this method !");

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            byte[] bytes = baos.toByteArray();

            for (int i = bytes.length - 1, j = 0; i != 0; i--, j++) {
                if (objectType != STRING_JAVA_TYPE_NAME) {
                    if (bytes[i] == 112)
                        if (isArray)
                            return j - 4;
                        else
                            return j;
                } else {
                    if (bytes[i] == 0)
                        return j - 1;
                }
            }
        } catch (Exception e) {
            return -1;
        }

        return -1;
    }    

}