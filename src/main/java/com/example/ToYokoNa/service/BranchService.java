package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.BranchForm;
import com.example.ToYokoNa.repository.BranchRepository;
import com.example.ToYokoNa.repository.entity.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

    public List<BranchForm> findAllBranches() {
        List<Branch> results = branchRepository.findAllByOrderByIdAsc();
        List<BranchForm> branches = setBranchForm(results);
        return branches;
    }

    private List<BranchForm> setBranchForm(List<Branch> results) {
        List<BranchForm> branches = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            BranchForm branch = new BranchForm();
            Branch result = results.get(i);
            branch.setId(result.getId());
            branch.setName(result.getName());
            branch.setCommentCount(result.getCommentCount());
            branch.setMessageCount(result.getMessageCount());
            branches.add(branch);
        }
        return branches;
    }
}
