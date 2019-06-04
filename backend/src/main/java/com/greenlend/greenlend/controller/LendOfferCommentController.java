package com.greenlend.greenlend.controller;

import com.greenlend.greenlend.model.LendOfferComment;
import com.greenlend.greenlend.repository.LendOfferCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LendOfferCommentController {

    private final LendOfferCommentRepository lendOfferCommentRepository;

    @Autowired
    public LendOfferCommentController(LendOfferCommentRepository lendOfferCommentRepository) {
        this.lendOfferCommentRepository = lendOfferCommentRepository;
    }

    @GetMapping("/savetest")
    public String testSave() {
        LendOfferComment lof = new LendOfferComment();
        lof.setContent("testContent " + (lendOfferCommentRepository.count() + 1));
        lendOfferCommentRepository.save(lof);
        return "Comment saved. :-)";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/loadtest")
    public List<LendOfferComment> testLoad() {
        return lendOfferCommentRepository.findAll();
    }
}
