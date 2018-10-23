package sample;

public class Currency extends CurrencyAbstractModel {
    private String name;
    private Integer multipler;
    private String code;
    private Double rate;

    public Currency(String name, Integer multipler, String code, Double rate) {
        this.name = name;
        this.multipler = multipler;
        this.code = code;
        this.rate = rate;
    }

    public Currency() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMultipler() {
        return multipler;
    }

    public void setMultipler(Integer multipler){
        this.multipler=multipler;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
