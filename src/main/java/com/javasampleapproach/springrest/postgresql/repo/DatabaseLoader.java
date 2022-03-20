package com.javasampleapproach.springrest.postgresql.repo;

import com.javasampleapproach.springrest.postgresql.model.Stabilimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final StabilimentoRepository repository;

    @Autowired
    public DatabaseLoader(StabilimentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.repository.save(new Stabilimento("Bagni Liguria", 500, "Viale Rimembranza, 38, 16039 Sestri Levante GE", "0185 482131"));
        this.repository.save(new Stabilimento("Bagni Castelletto", 700, "Via Aurelia, 17024 Finale Ligure SV", "019 600106"));
        this.repository.save(new Stabilimento("Capo Torre Beach & Lounge", 750, "Via Aurelia di Ponente, 1, 17015 Celle Ligure SV", "019 221 6264"));
        this.repository.save(new Stabilimento("Bagni Vittoria Beach", 480, "Lungomare Augusto Migliorini, 17024 Finale Ligure SV", "019 695583"));
        this.repository.save(new Stabilimento("Bagni Al Saraceno", 730, "Via del Capo, 2, 17024 Finale ligure SV", "019 698 8187"));
    }
}
