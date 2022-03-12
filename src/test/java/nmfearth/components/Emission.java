package nmfearth.components;

public class Emission {
    // Class attributes
    private String name;
    private String kgCO2Value;

    public Emission(String name, String kgCO2Value) {
        this.name = name;
        this.kgCO2Value = kgCO2Value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKgCO2Value() {
        return kgCO2Value;
    }

    public void setKgCO2Value(String kgCO2Value) {
        this.kgCO2Value = kgCO2Value;
    }
}
