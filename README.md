# SeaBook

Se non già installato, installre maven wrapper plugin, posizionandosi nella root del progetto

```bash
mvn -N io.takari:maven:wrapper
```
⚡ Per runnare tutto il progetto, posizionarsi in ogni servizio partendo dal discovery, costruire il package
 
```
./mvnw -DskipTests=true clean package	
```
e costruire e lanciare l'immagine.

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





