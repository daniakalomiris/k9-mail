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
    public static String emailLanguage = "de"; //using "de" (German) as default
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

        System.out.println("At START of detectLanguage(), DEVICE is: " + deviceLanguage + " and EMAIL is: " + emailLanguage);

        IdentifyOptions identifyOptions = new IdentifyOptions.Builder().text(stringToDetect).build();

        IdentifiedLanguages languages = service.identify(identifyOptions).execute();
        languages.getLanguages().get(0).getLanguage();

        // update global emailLanguage variable (so it can be used in MessageContainer)
        emailLanguage = languages.getLanguages().get(0).getLanguage();

        System.out.println("At END of detectLanguage(), DEVICE is: " + deviceLanguage + " and EMAIL is: " + emailLanguage);

        return  languages.getLanguages().get(0).getLanguage();
    }

    /**
     * Translates text from base language to spanish (user chosen language)
     * @param textToTranslate sentence formulated in base language.
     * @return sentence in spanish (user chosen language)
     */
    public String translateLanguage(String textToTranslate){

        String applicationLanguage = Locale.getDefault().getLanguage(); // Returns "en", "fr", etc

        try {
            // strip tags to properly detect the language
            String languageSource = detectLanguage(textToTranslate); // Note: Now that e-mail language is detected on e-mail load, could update/replace this line with Watson's static emailLanguage variable to prevent multiple calls to API. (Since emailLanguage already called detectLanguage()


            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(textToTranslate)
                    .source(languageSource)
                    .target(applicationLanguage)
                    .build();

            TranslationResult translationResult = service.translate(translateOptions).execute();

            String result = translationResult.getTranslations().get(0).getTranslationOutput();

            System.out.println("TEST PRINT: " + result);

            return result;

        }catch(Exception e){

            return textToTranslate;
        }
    }

    // Method to check if device language = email language. TODO390: test
    public static boolean doesDeviceLanguageEqualEmailLanguage(){
        if (emailLanguage.equals(deviceLanguage)) {
            System.out.println("check doesDeviceLanguageEqualEmailLanguage() : TRUE  (EMAIL LANGUAGE EQUALS DEVICE, Translate button won't show)");
            return true;
        } else {
            System.out.println("check doesDeviceLanguageEqualEmailLanguage() : FALSE  (EMAIL LANGUAGE NOT EQUAL DEVICE, Translate button will show)");
            return false;
        }
    }
}