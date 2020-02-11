
#setwd("/home/gustavo/Dropbox/Mestrado/Segundo Semestre/códigos/clustering/ClusteringEstrategia/resultados/primeiraCartaRoboPe/")
sql <- ("select id,cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering,primeiraCartaHumanoClustering, primeiraCartaRoboClustering  from maos where jogadorMao=2 and cartaAltaRoboClustering is not null and cartaMediaRoboClustering is not null and cartaBaixaRoboClustering is not null and utilCarta = 1;")

dados <- executeSelectMysql(sql, "trucocbr")

casos <- (dados[2:6])

casos <- scale(casos)


set.seed(1)
k = retornaK(10, casos)
k
CriarElbow(10,casos)

sqlK<- ("select MAX(clusterPrimeiraCartaAgentePe) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")
kRecebido
#if(k != kRecebido){

#kmeans
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 


#excluir resultadosAntigos
excluiLabelCluster("clusterPrimeiraCartaAgentePe")

#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterPrimeiraCartaAgentePe")
##########################
#realiza insert do centroide

inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- seq_along(kmeans)
  
  numeroDoKusado <- length(kmeans$centers[,1])
  
  con <- criaConexaoNaBaseAtual()
  
  for(i in 1  : numeroDoKusado){
  casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
  
  cartaAltaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,1])
  cartaMediaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,2])
  cartaBaixaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,3]) 
  primeiraCartaHumanoClustering <- mean(casosQuePertencemAoClusterAtual[,4])
  primeiraCartaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,5])
  
  sql <-sprintf("INSERT INTO centroidesPrimeiraCartaRoboEpe(grupo, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, primeiraCartaHumanoClustering, primeiraCartaRoboClustering, numerok) 
                  VALUES(%f, %f, %f, %f, %f, %f, %f);",
                  i, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, primeiraCartaHumanoClustering, primeiraCartaRoboClustering, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesPrimeiraCartaRoboEpe")
inserirClustersCentroides()


#}



