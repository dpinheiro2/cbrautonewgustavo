#setwd("E:/Mestrado/Segundo Semestre/c?digos/clustering/ClusteringEstrategia/ClusteringDivididos/terceiraCartaRoboGanhouAsegunda/")
#setwd("/home/gustavo/Dropbox/Mestrado/Segundo Semestre/códigos/clustering/ClusteringEstrategia/resultados/terceiraCartaRoboGanhouAsegunda/")
sql <- ("select terceiraCartaRoboClustering, id from maos where ( (ganhadorSegundaRodada = 1) or (ganhadorSegundaRodada = 0 and ganhadorPrimeiraRodada = 1) or (ganhadorSegundaRodada = 0 and ganhadorPrimeiraRodada = 0 and jogadorMao = 1) ) and utilCarta = 1;")
dados <- executeSelectMysql(sql,"trucocbr")

casos <- (dados[1])
casos <- scale(casos)
set.seed(1)
k <- retornaK(6, casos)


sqlK<- ("select MAX(clusterTerceiraCartaAgenteGanhouAsegunda) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")



if(kRecebido != k){
#kmeans

kmeans <- kmeans(casos,k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 

#Bd
#excluir resultadosAntigos
excluiLabelCluster("clusterTerceiraCartaAgenteGanhouAsegunda")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterTerceiraCartaAgenteGanhouAsegunda")

##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- length(kmeans)
  con <- criaConexaoNaBaseAtual()
  
  numeroDoKusado <-length(kmeans$centers[,1])
  for(i in 1: numeroDoKusado){
    
    
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
    terceiraCartaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,1])
    
    
    
    sql <-sprintf("INSERT INTO centroidesTerceiraCartaRoboGanhouAsegunda(grupo, terceiraCartaRoboClustering, numerok) 
                  VALUES(%f, %f, %f);",
                  i, terceiraCartaRoboClustering, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesTerceiraCartaRoboGanhouAsegunda")
inserirClustersCentroides()
#########################




}

#write.csv(resultadokmeans, file = "resultado_kmeansNewSegundaCartaRoboperdeuAprimeira.csv")






