package example.android.volleydemo;

/**
 * Created by Quhaofeng on 2016-4-11-0011.
 */
public class Data {

    /**
     * id : 5
     * version : 5.5
     * name : Angry Birds
     */

    private int id;
    private double version;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                '}';
    }
}
