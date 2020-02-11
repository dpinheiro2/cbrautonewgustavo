#aqui seleiciona o cluster
dados <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==  1)
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
cartaBaixaMaximo <- sumario[6]

#primeira carta humano
sumario <- summary(dados[,4])
primeiraCartaHumanoMinimo <- sumario[1]
primeiraCartaHumanoMedia <- sumario[3]
primeiraCartaHumanoMaximo <- sumario[6]

#primeira carta Robo
sumario <- summary(dados[,5])
primeiraCartaRoboMinimo <- sumario[1]
primeiraCartaRoboMedia <- sumario[3]
primeiraCartaRoboMaximo <- sumario[6]