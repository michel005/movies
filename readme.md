# Michel Douglas Grigoli
mdgrigoli@hotmail.com.br

## Execução

Para executar a aplicação, basta executar o comando:

```mvn spring-boot:run -Dspring-boot.run.arguments="C:\movielist.csv"```

Ou com o jar gerado (mvn clean install), rodar o comando a seguir:

```java -jar movies-1.0.jar "C:\movielist.csv"```

## Testes de integração

* São realizados 3 testes de integração utilizando 3 arquivos CSV diferentes:

  - maxIntervalWith10AndMinIntervalWith1
  - maxIntervalWith22AndMinIntervalWith1
  - emptyBecauseHaveOnlyMoviesNotWinners

Para executar os testes, rodar o comando ```mvn test``` ou ```mvn clean install```.