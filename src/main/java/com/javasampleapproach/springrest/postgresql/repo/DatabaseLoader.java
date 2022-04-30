package com.javasampleapproach.springrest.postgresql.repo;

import com.javasampleapproach.springrest.postgresql.model.Spot;
import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final StabilimentoRepository stabRepo;
    private final SpotRepository spotRepo;

    @Autowired
    public DatabaseLoader(StabilimentoRepository repository, SpotRepository spotRepository) {
        this.stabRepo = repository;
        this.spotRepo = spotRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.stabRepo.save(new Stabilimento("Bagni Liguria",
                12, 3, 4,
                "Viale Rimembranza, 38, 16039 Sestri Levante GE",
                "0185 482131"));
        this.stabRepo.save(new Stabilimento("Bagni Castelletto",
                10, 2, 5,
                "Via Aurelia, 17024 Finale Ligure SV",
                "019 600106"));
        this.stabRepo.save(new Stabilimento("Capo Torre Beach & Lounge",
                15, 5, 3,
                "Via Aurelia di Ponente, 1, 17015 Celle Ligure SV",
                "019 221 6264"));
        this.stabRepo.save(new Stabilimento("Bagni Vittoria Beach",
                20, 4, 5,
                "Lungomare Augusto Migliorini, 17024 Finale Ligure SV",
                "019 695583"));
        this.stabRepo.save(new Stabilimento("Bagni Al Saraceno",
                14, 2, 7,
                "Via del Capo, 2, 17024 Finale ligure SV",
                "019 698 8187"));


        List<Stabilimento> stabilimentoList = new ArrayList<>();
        stabRepo.findAll().forEach(stabilimentoList::add);
        System.out.println(stabilimentoList);

        // calcoli di prova
        double basePrice = 50.99;
        double rowDiscount = 0;
        double baseDiscount = 0.25;

        for (Stabilimento stab :
                stabilimentoList) {
            System.out.println(stab);
            int rowQty = stab.getRowQty();
            System.out.println(rowQty);
            for (int i = 0; i < rowQty; i++) {
                rowDiscount = i * baseDiscount;
                int columnQty = stab.getColumnQty();
                System.out.println(columnQty + " " + rowDiscount);
                for (int j = 0; j < columnQty; j++) {
                    // e' il repository che crea l'id, quindi se creo l'obj separato da repository.save non ho l'id
                    Spot spot = spotRepo.save(new Spot(stab.getId(), j % 2 == 0, (basePrice - rowDiscount), true, i, j));
                    System.out.println(spot);
                }
                System.out.println();
            }
        }

    }
}
