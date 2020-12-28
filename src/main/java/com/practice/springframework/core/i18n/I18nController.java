package com.practice.springframework.core.i18n;

import com.practice.springframework.core.util.MyTranslator;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class I18nController {

    final MessageSource messageSource;

    public I18nController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/i18n")
    public String index(Locale locale) {
        return messageSource.getMessage("error.notfound", null, locale);
    }

    @GetMapping("/i18n/2")
    public String threadLocal() {
        return MyTranslator.toLocale("error.notfound");
    }


}