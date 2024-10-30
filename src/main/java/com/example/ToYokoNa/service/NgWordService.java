package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.NgWordForm;
import com.example.ToYokoNa.repository.NgWordRepository;
import com.example.ToYokoNa.repository.entity.NgWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NgWordService {
    @Autowired
    NgWordRepository ngWordRepository;
    public void saveNgWord(NgWordForm ngWordForm) {
        NgWord ngWord = setNgWord(ngWordForm);
        ngWordRepository.save(ngWord);
    }

    public List<NgWordForm> findAllNgWords() {
        List<NgWord> results = ngWordRepository.findAll();
        List<NgWordForm> ngWordForms = setNgWordFoms(results);
        return ngWordForms;
    }

    private List<NgWordForm> setNgWordFoms(List<NgWord> results) {
        List<NgWordForm> ngWordForms = new ArrayList<>();
        for (NgWord result : results) {
            NgWordForm ngWordForm = new NgWordForm();
            ngWordForm.setId(result.getId());
            ngWordForm.setNgWord(result.getNgWord());
            ngWordForms.add(ngWordForm);
        }
        return ngWordForms;
    }

    private NgWord setNgWord(NgWordForm ngWordForm) {
        NgWord ngWord = new NgWord();
        ngWord.setId(ngWordForm.getId());
        ngWord.setNgWord(ngWordForm.getNgWord());
        return ngWord;
    }

    public void deleteNgWord(int id) {
        ngWordRepository.deleteById(id);
    }
}
