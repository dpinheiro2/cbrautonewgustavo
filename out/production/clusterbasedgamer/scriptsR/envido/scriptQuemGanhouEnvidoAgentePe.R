#setwd("E:/Mestrado/Segundo Semestre/c?digos/clustering/ClusteringEstrategia/ClusteringDivididos/primeiraCartaRoboPe/")
#setwd("/home/gustavo/Dropbox/Mestrado/Segundo Semestre/códigos/clustering/ClusteringEstrategia/resultados/quemGanhouEnvidoAgenteEpe/")
sql <- ("select quemPediuEnvidoCluster, quemPediuRealEnvidoCluster, quemPediuFaltaEnvidoCluster, pontosEnvidoRobo, id from maos where jogadorMao=2 and utilEnvido =1")
dados <- executeSelectMysql(sql, "trucocbr") 



casos <- dados[1:4]
casos <- scale(casos)




set.seed(1)
#CriarElbow(10, casos)
k <- retornaK(10, casos)
k
sqlK<- ("select MAX(clusterQuemEnvidoAgentePe) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")
kRecebido


#kmeans
set.seed(1)
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
casos
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 

#write.csv(resultadokmeans, file = "resultado_kmeansNewClusteringEnvidoAgentePe.csv")


#excluir resultadosAntigos
excluiLabelCluster("clusterQuemEnvidoAgentePe")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterQuemEnvidoAgentePe")
##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- length(kmeans)
  
  
  numeroDoKusado <-length(kmeans$centers[,1])
  con <- criaConexaoNaBaseAtual()
  
  for(i in 1: numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster == i) 
    
    quemPediuEnvido <- mean(casosQuePertencemAoClusterAtual[,1])
    quemPediuEnvido
    
    quemPediuRealEnvido <- mean(casosQuePertencemAoClusterAtual[,2])
    quemPediuRealEnvido
    quemPediuFaltaEnvido <- mean(casosQuePertencemAoClusterAtual[,3])
    quemPediuFaltaEnvido
    pontosEnvidoRobo <- mean(casosQuePertencemAoClusterAtual[,4])
    pontosEnvidoRobo
    sql <-sprintf("INSERT INTO centroidesQuemGanhouEnvidoAgentePe(grupo, quemPediuEnvido, quemPediuRealEnvido, quemPediuFaltaEnvido, numerok, pontosEnvidoRobo) 
                  VALUES(%f, %f, %f, %f, %f,%f);",
                  i, quemPediuEnvido, quemPediuRealEnvido, quemPediuFaltaEnvido, numeroDoKusado, pontosEnvidoRobo)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesQuemGanhouEnvidoAgentePe")
inserirClustersCentroides()
#########################






