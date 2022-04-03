package com.javasampleapproach.springrest.postgresql.repo;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpotDatabaseLoader implements CommandLineRunner {

    private final SpotRepository spotRepo;

    // considerando che i due servizi condividono lo stesso db
    private final StabilimentoRepository stabilimentoRepo;

    @Autowired
    public SpotDatabaseLoader(SpotRepository spotRepo, StabilimentoRepository stabilimentoRepo) {
        this.spotRepo = spotRepo;
        this.stabilimentoRepo = stabilimentoRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // voglio inizializzare la lista di spots per gli stabilimenti
//        List<Stabilimento> stabilimentoList = (List<Stabilimento>) stabilimentoRepo.findAll();
//        List<Stabilimento> stabilimentoList = new ArrayList<>();
//        stabilimentoRepo.findAll().forEach(stabilimentoList::add);
//
//        // calcoli di prova
//        double basePrice = 50.99;
//        double rowDiscount = 0;
//        double baseDiscount = 0.125;
//
//        for (Stabilimento stab :
//             stabilimentoList) {
//            for (int i = 0; i < stab.getRowQty(); i++) {
//                rowDiscount = i * baseDiscount;
//                for (int j = 0; j < stab.getColumnQty(); j++) {
//                    Spot spot = new Spot(stab.getId(), basePrice - rowDiscount, i, j);
//                    spotRepo.save(spot);
//                    System.out.println(spot);
//                }
//            }
//        }
    }
}
