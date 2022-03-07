package com.javasampleapproach.springrest.postgresql.controller;

import com.javasampleapproach.springrest.postgresql.model.Customer;
import com.javasampleapproach.springrest.postgresql.model.Posto;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import com.javasampleapproach.springrest.postgresql.repo.CustomerRepository;
import com.javasampleapproach.springrest.postgresql.repo.PostoRepository;
import com.javasampleapproach.springrest.postgresql.repo.StabilimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class PostoController {

    @Autowired
    PostoRepository repository;




}