package executors.customthreadpoolexecutor.feature;

public class Java8_0229_Value< T > {
    public static< T > T defaultValue() {
        return null;
    }
    public T getOrDefault( T value, T defaultValue ) {
        return ( value != null ) ? value : defaultValue;
    }
}

