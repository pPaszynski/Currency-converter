package sample;

public abstract class CurrencyAbstractModel {
    public abstract String getName();
    public abstract Integer getMultipler();
    public abstract String getCode();
    public abstract Double getRate();

    @Override
    public String toString() {
        return "nazwa_waluty: " + this.getName() + "\nprzelicznik: " + this.getMultipler() +
                "\nkod_waluty: " + this.getCode() + "\nkurs_sredni" + this.getRate();
            }
}
