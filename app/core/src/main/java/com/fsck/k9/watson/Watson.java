package com.fsck.k9.watson;

import android.util.Log;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;
import com.ibm.watson.developer_cloud.language_translator.v3.model.IdentifiableLanguages;
import com.ibm.watson.developer_cloud.language_translator.v3.model.IdentifiedLanguages;
import com.ibm.watson.developer_cloud.language_translator.v3.model.IdentifyOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.ListIdentifiableLanguagesOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;

import com.ibm.watson.developer_cloud.service.security.IamOptions;

import com.ibm.watson.developer_cloud.util.GsonSingleton;
import com.ibm.watson.developer_cloud.util.RequestUtils;
import com.ibm.watson.developer_cloud.util.ResponseConverterUtils;
import com.ibm.watson.developer_cloud.util.Validator;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.Locale;


public class Watson {

    // Global variables for device language and e-mail language
    public static String deviceLanguage = Locale.getDefault().getLanguage();; // returns "en"/"fr"/etc
    public static String emailLanguage = "de"; //using "de" (German) as default

    // IBM credentials
    private final static String TEXT_TRANSLATION_API_KEY = "565Uvd6VZUEGmJxnbBSLRi_9RgouvfYEHMAmYxHpenqM";
    private final static String TEXT_TRANSLATION_API_URL = "https://gateway.watsonplatform.net/language-translator/api";
    private final static String TEXT_TRANSLATION_API_VERSION = "2018-05-01";

    private static final Watson ourInstance = new Watson();

    private LanguageTranslator service;

    public static Watson getInstance() {
        ourInstance.authenticate();
        return ourInstance;
    }

    private Watson() {}

    private void authenticate(){

        try {

            IamOptions iamOptions = new IamOptions.Builder()
                    .apiKey(TEXT_TRANSLATION_API_KEY)
                    .build();

            this.service = new LanguageTranslator(TEXT_TRANSLATION_API_VERSION, iamOptions);

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public String detectLanguage(String stringToDetect){
        IdentifyOptions identifyOptions = new IdentifyOptions.Builder().text(stringToDetect).build();

        IdentifiedLanguages languages = service.identify(identifyOptions).execute();
        languages.getLanguages().get(0).getLanguage();

        System.out.println("Language Detected:"+ languages.getLanguages().get(0).getLanguage());

        // update global emailLanguage variable (so it can be used in MessageContainer)
        emailLanguage = languages.getLanguages().get(0).getLanguage();

        return  languages.getLanguages().get(0).getLanguage();
    }
    /**
     * test function to show proof of concept and validate authentication, keep until release
     * @param english sentence formulated in english.
     * @return sentence in spanish
     */
    public String test(String english){
        try {
            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(english)
                    .source(Language.ENGLISH)
                    .target(Language.SPANISH)
                    .build();

            TranslationResult translationResult = service.translate(translateOptions).execute();

            String result = translationResult.getTranslations().get(0).getTranslationOutput();

            System.out.println(result);

            return result;

        }catch(Exception e){
            return english;
        }
    }

    /**
     * TODO390 real test with html tags like emails have, different languages too
     * Translates text from base language to spanish (user chosen language)
     * @param textToTranslate sentence formulated in base language.
     * @return sentence in spanish (user chosen language)
     */
    public String translateLanguage(String textToTranslate){
        String applicationLanguage = Locale.getDefault().getLanguage();

        try {
            // strip tags to properly detect the language
            String languageSource = detectLanguage(stripHtmlTags(textToTranslate));

            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(textToTranslate)
                    .source(languageSource)
                    .target(applicationLanguage)
                    .build();

            TranslationResult translationResult = service.translate(translateOptions).execute();

            String result = translationResult.getTranslations().get(0).getTranslationOutput();

            System.out.println(result);

            return result;

        }catch(Exception e){
            return textToTranslate;
        }
    }

    // TODO390 test/ improve regex
    private String stripHtmlTags(String htmlText){

        String result = htmlText.replaceAll("\\{(.*?)\\}|<(.*?)>","");

        return result;
    }

    // Method to check if device language = email language. TODO390: test
    public static boolean doesDeviceLanguageEqualEmailLanguage(){
        if (emailLanguage.equals(deviceLanguage)) {
            return true;
        } else {
            return false;
        }
    }
}