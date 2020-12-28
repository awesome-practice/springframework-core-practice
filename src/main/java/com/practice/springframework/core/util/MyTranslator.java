package com.practice.springframework.core.util;

import com.sun.java.accessibility.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MyTranslator {

   private static MessageSource messageSource;

   @Autowired
   MyTranslator(MessageSource messageSource) {
      MyTranslator.messageSource = messageSource;
   }

   public static String toLocale(String msgCode) {
      Locale locale = LocaleContextHolder.getLocale();
      return messageSource.getMessage(msgCode, null, locale);
   }
}