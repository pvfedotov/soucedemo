package org.playground.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.playground.models.Product;

import java.io.File;
import java.net.URL;
import java.util.List;

import static java.lang.String.format;

public class DataHelper {
    public static <T> List<T> loadJsonFromFileToListOfObjects(File file, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<T> object = objectMapper.readValue(file,
                    new TypeReference<List<T>>(){});
            return object;
        } catch (Exception e) {
            throw new RuntimeException(format("Cannot load file '%s' to the object '%s'", file.getName(), clazz.getName()), e);
        }
    }

    public static List<Product> loadJsonFromFileToListOfProducts(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Product> list = objectMapper.readValue(file, new TypeReference<List<Product>>(){});
            return list;
        } catch (Exception e) {
            throw new RuntimeException(format("Cannot load file '%s' to the object '%s'", file.getName(), Product.class.getName()), e);
        }
    }

    public static File getFileFromResources(String path) {
        try {
            URL url = DataHelper.class.getClassLoader().getResource(path);
            File file = new File(url.getPath());
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
