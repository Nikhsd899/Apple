package com.example.demo.controller;

import com.example.demo.model.AddressForm;
import com.example.demo.model.WeatherResult;
import com.example.demo.service.WeatherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("form", new AddressForm());
        return "form";
    }

    @PostMapping("/search")
    public String search(@Valid @ModelAttribute("form") AddressForm form,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) return "form";

        Optional<String> zipOpt = extractZip(form.getAddress());
        if (zipOpt.isEmpty()) {
            br.rejectValue("address", "zip.missing", "Please include a valid ZIP code.");
            return "form";
        }

        WeatherResult result = service.get(zipOpt.get());
        if (result == null) {
            br.reject("lookup.failed", "Unable to fetch weather data. Try again later.");
            return "form";
        }

        model.addAttribute("result", result);
        return "result";
    }

    private Optional<String> extractZip(String address) {
        Pattern ZIP_PATTERN = Pattern.compile("\\b(\\d{5})(?:-\\d{4})?\\b");
        Matcher m = ZIP_PATTERN.matcher(address);
        return m.find() ? Optional.of(m.group(1)) : Optional.empty();
    }
}
