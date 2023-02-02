import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import englishJSON from './assets/locales/en/en.json';
import spanishJSON from './assets/locales/es/es.json';

i18n
    // detect user language
    // learn more: https://github.com/i18next/i18next-browser-languageDetector
    .use(LanguageDetector)
    // pass the i18n instance to react-i18next.
    .use(initReactI18next)
    // init i18next
    // for all options read: https://www.i18next.com/overview/configuration-options
    .init({
        keySeparator: false, // we do not use keys in form messages.welcome
        debug: true,

        interpolation: {
            escapeValue: false, // not needed for react as it escapes by default
        },
        fallbackLng: ['en', 'es'],
        resources: {
            en: {
                translation: englishJSON
            },
            es:{
                translation: spanishJSON
            }
        }
    });

export default i18n;