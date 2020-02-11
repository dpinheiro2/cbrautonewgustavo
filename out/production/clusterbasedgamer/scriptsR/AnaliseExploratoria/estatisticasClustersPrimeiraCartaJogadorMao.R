#aqui seleiciona o cluster 
  length(resultadokmeans[,1])
  #tem um total de 7427 casos
  dados <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==  4)
  length(dados[,1])
  #sumario completo dos dados do cluster
summary(dados)
#CARTA ALTA
sumario <- summary(dados[,1])
cartaAltaMinimo <- sumario[1]  
cartaAltaMinimo
cartaAltaMedia <- sumario[3]
cartaAltaMedia
cartaAltaMaximo <- sumario[6]
cartaAltaMaximo


#carta media
sumario <- summary(dados[,2])
cartaMediaMinimo <- sumario[1]  
cartaMediaMinimo
cartaMediaMedia <- sumario[3]
cartaMediaMedia
cartaMediaMaximo <- sumario[6]
cartaMediaMaximo

#carta baixa
sumario <-summary(dados[,3])
cartaBaixaMinimo <- sumario[1]
cartaBaixaMedia <- sumario[3]
cartaBaixaMedia
cartaBaixaMaximo <- sumario[6]

#primeira carta humano
sumario <- summary(dados[,4])
primeiraCartaHumanoMinimo <- sumario[1]
primeiraCartaHumanoMinimo
primeiraCartaHumanoMedia <- sumario[3]
primeiraCartaHumanoMedia
primeiraCartaHumanoMaximo <- sumario[6]
primeiraCartaHumanoMaximo

#primeira carta Robo
sumario <- summary(dados[,5])
primeiraCartaRoboMinimo <- sumario[1]
primeiraCartaRoboMinimo
primeiraCartaRoboMedia <- sumario[3]
primeiraCartaRoboMedia
primeiraCartaRoboMaximo <- sumario[6]
primeiraCartaRoboMaximo


