package genericutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Propertiesutility {
    
    public String getDatafromProperties(String key) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("./src/test/resources/data.properties");
            Properties prop = new Properties();
            prop.load(fis);
            String data = prop.getProperty(key);
            if (data == null) {
                throw new RuntimeException("Property '" + key + "' not found in data.properties file");
            }
            return data;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}