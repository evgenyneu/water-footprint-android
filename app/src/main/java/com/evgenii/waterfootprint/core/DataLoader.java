package com.evgenii.waterfootprint.core;

import android.util.Log;

import com.evgenii.waterfootprint.utils.AssetsFileReader.AssetsFileReaderInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    static List<ProductModel> load(AssetsFileReaderInterface fileReader) {
        String languageCode = CurrentLanguage.currentLanguageCode();
        String text = loadTextForLanguage(languageCode, fileReader);
        return loadFromText(text);
    }

    static List<ProductModel> loadForLanguage(String languageCode, AssetsFileReaderInterface fileReader) {
        String text = loadTextForLanguage(languageCode, fileReader);
        return loadFromText(text);
    }

    static String loadTextForLanguage(String languageCode, AssetsFileReaderInterface fileReader) {
        String filePath = String.format("data/data_%s.tsv", languageCode);

        try {
            return fileReader.ReadFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load product file from assets: " + filePath);
        }
    }

    static List<ProductModel> loadFromText(String text) {
        String[] lines = text.split("\r\n");
        ArrayList<ProductModel> products = new ArrayList<ProductModel>();

        for(String line : lines) {
            ProductModel product = loadSingleProductFromText(line);
            products.add(product);
        }

        return products;
    }

    static ProductModel loadSingleProductFromText(String text) {
        String[] fields = text.split("\t");

        if (fields.length != 3) {
            throw new RuntimeException("Incomplete product data: " + text);
        }

        ProductModel product = new ProductModel();

        product.name = fields[0];
        product.synonyms = fields[1];

        try {
            product.waterLitres = Integer.parseInt(fields[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading water footprint value: " + text);
        }

        return product;
    }
}
