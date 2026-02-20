package bankingsystem;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
public class DBconfig {
    private static Properties props = new Properties();
    static {
        try{
            var inputstream = DBconfig.class.getClassLoader().getResourceAsStream("bankingsystem/db.properties");
            props.load(inputstream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String get(String key){
        return props.getProperty(key);
    }
}
