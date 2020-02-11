#setwd("E:/Mestrado/Segundo Semestre/c?digos/clustering/ClusteringEstrategia/ClusteringDivididos/terceiraCartaRoboPerdeuAsegunda/")

#setwd("/home/gustavo/Dropbox/Mestrado/Segundo Semestre/códigos/clustering/ClusteringEstrategia/resultados/terceiraCartaRoboPerdeuAsegunda/")

sql <-("select terceiraCartaRoboClustering ,terceiraCartaHumanoClustering , ganhadorTerceiraRodada, id, ganhadorSegundaRodada 
from maos where ( (ganhadorSegundaRodada = 2) or (ganhadorSegundaRodada = 0 and ganhadorPrimeiraRodada = 2) or (ganhadorSegundaRodada =0 and ganhadorPrimeiraRodada = 0  and jogadorMao = 2) ) and utilCarta =1;")

dados <- executeSelectMysql(sql,"trucocbr")



casos <-(dados[1:2])
casos <- scale(casos)
k <-retornaK(10, casos)

#CriarElbow(10, casos)

sqlK <- ("select MAX(clusterTerceiraCartaAgentePerdeuAsegunda) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")



if(kRecebido != k){
#Elbow Method for finding the optimal number of clusters

set.seed(1)
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)

#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 

#Bd
#excluir resultadosAntigos
excluiLabelCluster("clusterTerceiraCartaAgentePerdeuAsegunda")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterTerceiraCartaAgentePerdeuAsegunda")

##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- length(kmeans)
  
  con <- criaConexaoNaBaseAtual()
  
  
  numeroDoKusado <-length(kmeans$centers[,1])
  for(i in 1: numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
    terceiraCartaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,1])
    terceiraCartaHumanoClustering <- mean(casosQuePertencemAoClusterAtual[,2])
    
    sql <-sprintf("INSERT INTO centroidesTerceiraCartaRoboPerdeuAsegunda(grupo, terceiraCartaRoboClustering, terceiraCartaHumanoClustering, numerok) 
                  VALUES(%f, %f, %f, %f);",
                  i, terceiraCartaRoboClustering, terceiraCartaHumanoClustering, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesTerceiraCartaRoboPerdeuAsegunda")
inserirClustersCentroides()
#########################


}

#write.csv(resultadokmeans, file = "resultado_kmeansNewTerceiraCartaRoboperdeuASegunda.csv")






