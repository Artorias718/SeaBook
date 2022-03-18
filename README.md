# SeaBook

Se non già installato, installre maven wrapper plugin, posizionandosi nella root del progetto

```bash
mvn -N io.takari:maven:wrapper
```
costruire il package 
```bash
./mvnw -DskipTests=true clean package	
```
costruire e lanciare l'immagine

```bash 
docker-compose build && docker-compose up 
```

Dal momento che verrà lanciato un conteiner con postgres, potrebbe essere necessario killare prima il postgres locale 

```bash 
ps -ef|grep postgres

sudo kill -9 <numero nella seconda colonna>
```
Per pulire i dati nel postgres in docker usare 

```bash
docker-compose down 
```

Per attivare la persistenza dei dati nel postgres locale impostare nell application.properties
```bash
spring.jpa.hibernate.ddl-auto = update 
```



