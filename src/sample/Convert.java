package sample;

public class Convert {

    // static variable single_instance of type Singleton
    private static Convert single_instance = null;

    // static method to create instance of Singleton class
    public static Convert getInstance()
    {
        if (single_instance == null)
            single_instance = new Convert();

        return single_instance;
    }

    public Double calculate(Currency from, Currency to, Double value){
        return value/to.getMultipler()*to.getRate()*from.getMultipler()/(from.getRate());
    }
}
