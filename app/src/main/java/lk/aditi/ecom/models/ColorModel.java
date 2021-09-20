package lk.aditi.ecom.models;

public class ColorModel {
    int code;
    boolean active;
    String name;

    public ColorModel(int code, boolean active, String name) {
        this.code = code;
        this.active = active;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ColorModel{" +
                "code=" + code +
                ", active=" + active +
                ", name='" + name + '\'' +
                '}';
    }
}
