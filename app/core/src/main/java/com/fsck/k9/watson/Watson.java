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

class Watson {

    // IBM credentials
    private final static String TEXT_TRANSLATION_API_KEY = "565Uvd6VZUEGmJxnbBSLRi_9RgouvfYEHMAmYxHpenqM";
    private final static String TEXT_TRANSLATION_API_URL = "https://gateway.watsonplatform.net/language-translator/api";
    private final static String TEXT_TRANSLATION_API_VERSION = "2018-05-01";

    private static final Watson ourInstance = new Watson();

    private LanguageTranslator service;

    static Watson getInstance() {
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

    // TODO: detect original language

    // TODO: translate text but with better result validation
}
