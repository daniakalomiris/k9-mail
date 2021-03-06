package com.fsck.k9.watson;

import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;
import com.ibm.watson.developer_cloud.language_translator.v3.model.IdentifiedLanguages;
import com.ibm.watson.developer_cloud.language_translator.v3.model.IdentifyOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

import java.util.Locale;


public class Watson {

    // Global variables for device language and e-mail language
    public static String deviceLanguage = Locale.getDefault().getLanguage(); // returns "en"/"fr"/etc
    public static String emailLanguage = "es"; //using "es" (Spanish) as default because the translation tests use Spanish
    public static boolean isEmailLanguageDetectedYet = false;  // used in ASyncWatson.java to prevent unnecessary double-checking during translation. Gets reset every time new e-mail loads (in MessageTopView.onInflate() method).
    
    // IBM credentials
    private final static String TEXT_TRANSLATION_API_KEY = "565Uvd6VZUEGmJxnbBSLRi_9RgouvfYEHMAmYxHpenqM";
    private final static String TEXT_TRANSLATION_API_VERSION = "2018-05-01";

    private static final Watson ourInstance = new Watson();

    private LanguageTranslator service;

    /**
     * @return Watson
     */
    public static Watson getInstance() {
        ourInstance.authenticate();
        return ourInstance;
    }

    private Watson() {}

    /**
     * Validate our IAM credentials to access Language Translation API
     */
    private void authenticate(){
        try {

            IamOptions iamOptions = new IamOptions.Builder()
                    .apiKey(TEXT_TRANSLATION_API_KEY)
                    .build();

            this.service = new LanguageTranslator(TEXT_TRANSLATION_API_VERSION, iamOptions);

        }catch(Exception e){
            throw e;
        }
    }

    /**
     *
     * @param stringToDetect
     * @return @example 'en' 'es' 'fr'
     */
    public String detectLanguage(String stringToDetect){
        IdentifyOptions identifyOptions = new IdentifyOptions.Builder().text(stringToDetect).build();

        IdentifiedLanguages languages = service.identify(identifyOptions).execute();
        languages.getLanguages().get(0).getLanguage();

        // update global emailLanguage variable (so it can be used in MessageContainer)
        emailLanguage = languages.getLanguages().get(0).getLanguage();

        return  languages.getLanguages().get(0).getLanguage();
    }

    /**
     * Translates text from base language to spanish (user chosen language)
     * @param textToTranslate sentence formulated in base language.
     * @return sentence in spanish (user chosen language)
     */
    public String translateLanguage(String textToTranslate){
        String applicationLanguage = deviceLanguage;

        try {
            String languageSource = emailLanguage;  // Note: emailLanguage was detected by showTranslateButtonIfNeeded() method in MessageTopView.java, when e-mail was first opened

            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(textToTranslate)
                    .source(languageSource)
                    .target(applicationLanguage)
                    .build();

            TranslationResult translationResult = service.translate(translateOptions).execute();

            String result = translationResult.getTranslations().get(0).getTranslationOutput();

            return result;

        }catch(Exception e){
            return textToTranslate;
        }
    }

    /**
     *
     * @return boolean whether device language is equal to email language
     */
    public static boolean doesDeviceLanguageEqualEmailLanguage(){
        return emailLanguage.equals(deviceLanguage);
    }
}