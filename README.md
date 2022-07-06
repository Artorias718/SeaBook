# SeaBook  :palm_tree:

Services

| Name        | Port          | Description  |
| ------------- |:-------------:| -----:|
| discovery| 8761 | Eureka server per la registrazione dei servizi |
| app| 8080| Microservizio per gestire stabilimenti e posti | 
| bookingService | 7500      |    Microservizio per gestire le prenotazioni |
| rabbitMQ | 5672      |    Message-oriented middleware  |
| frontend | 3000      |    React webapp |
| apiGateway | 9000      |    Zuul poxy per il routing |

Link alla documentazione delle APIs: https://documenter.getpostman.com/view/18547732/UzBmM7GS

Se non già installato, installre maven wrapper plugin, posizionandosi nella root del progetto

```bash
mvn -N io.takari:maven:wrapper
```
⚡ Per runnare tutto il progetto, posizionarsi in ogni servizio partendo dal discovery, costruire il package
 
```
./mvnw -DskipTests=true clean package	
```
e costruire e lanciare il docker-compose.

```bash 
docker-compose build && docker-compose up 
```

Dal momento che verrà lanciato un container con postgres, potrebbe essere necessario killare prima il postgres locale 

```bash 
ps -ef|grep postgres

sudo kill -9 <numero nella seconda colonna>
```






Per pulire i dati nel postgres in docker usare 

```bash
docker-compose down 
```

Per liberare una porta
```bash
sudo kill -9 `sudo lsof -t -i:5672`
```


Per attivare la persistenza dei dati nel postgres locale impostare nell application.properties
```bash
spring.jpa.hibernate.ddl-auto = update 
```
Per comodità dello sviluppo l'autenticazione è temporaneamente disabilitata, per riabilitarla scommentare l'intero file SecurityConfiguration.java e scommentare le dipendenze del pom.xml 

![image](https://user-images.githubusercontent.com/96788765/159331918-65fb3150-8487-45ef-8728-7822e949c282.png)

Per lanciare kubernates
```bash
minikube start 
```
```bash
kubectl apply -f Kubernates
```
```bash
kubectl port-forward svc/app 8080:8080
```





