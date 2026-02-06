package com.insurance.extractor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;

public class PdfReader {

    // ✅ For absolute file path
    public static String read(String path) throws Exception {

        PDDocument doc = PDDocument.load(new File(path));
        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(doc);
        doc.close();

        return text;
    }

    // ✅ For resources folder
    public static String readFromResources(String fileName) throws Exception {

        File file = new File(
                PdfReader.class
                        .getClassLoader()
                        .getResource(fileName)
                        .getFile()
        );

        return read(file.getAbsolutePath());
    }
}

