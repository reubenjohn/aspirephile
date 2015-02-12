package com.aspirephile.shared.debug;

public class NullPointerAsserter {

    Logger l;

    public NullPointerAsserter(String tag) {
        l = new Logger(tag);
    }

    public NullPointerAsserter(Class<?> cls) {
        l = new Logger(cls.getCanonicalName());
    }

    public NullPointerAsserter(Logger l) {
        this.l = l;
    }

    public boolean assertPointer(Object... objects) {
        boolean allGood = true;
        try {
            boolean array = objects.length > 1; // Also checks if objects points
            // to null
            for (int i = 0; i < objects.length; i++) {
                try {
                    objects[i].getClass();
                } catch (NullPointerException e) {
                    allGood = false;
                    l.e("NullPointerException of object"
                            + (array ? ("[" + i + "]") : ""));
                    removeFromStackBottom(e);
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            allGood = false;
            l.e("NullPointerException of object");
            removeFromStackBottom(e);
            e.printStackTrace();
        }

        return allGood;
    }

    public boolean assertPointerQuietly(Object... objects) {
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] == null) {
                    return false;
                }
            }
        } else
            return false;
        return true;
    }

    public void setTag(String tag) {
        l.setTag(tag);
    }
    public void setTag(Class<?> cls) {
        l.setTag(cls);
    }


    private void removeFromStackBottom(Exception e) {
        if (e != null) {
            StackTraceElement[] ea = e.getStackTrace();
            if (ea != null) {
                for (int j = 0; j < ea.length - 1; j++) {
                    ea[j] = ea[j + 1];
                }
                e.setStackTrace(ea);
            }
        }
    }
}
