#aqui seleiciona o cluster 
length(resultadokmeans[,1])
#tem um total de 7427 casos
dados <- subset(resultadokmeans, resultadokmeans$kmeans.cluster == 4)
length(dados[,1])
#sumario completo dos dados do cluster
summary(dados)

library(cluster)
library(fpc)

plotcluster(resultadokmeans, resultadokmeans$kmeans.cluster, main = "indexação da base de acordo com a pontuação") 

plot(resultadokmeans, resultadokmeans$kmeans.cluster)


