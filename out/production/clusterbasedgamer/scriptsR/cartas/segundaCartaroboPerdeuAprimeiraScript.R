
sql <-  ("select id, segundaCartaRoboClustering, segundaCartaHumanoClustering  from maos where ((ganhadorPrimeiraRodada =2) or (ganhadorPrimeiraRodada =0 and jogadorMao = 2) )and utilCarta  = 1;")
dados <- executeSelectMysql(sql,"trucocbr")

casos <- (dados[2:3])
casos <- scale(casos)
set.seed(1)
k <- retornaK(10, casos)
#CriarElbow(10, casos)

sqlK<- ("select MAX(clusterSegundaCartaAgentePerdeuAprimeira) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")

#if(kRecebido != k){

#kmeans
set.seed(1)
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 

#Bd
#excluir resultadosAntigos
excluiLabelCluster("clusterSegundaCartaAgentePerdeuAprimeira")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterSegundaCartaAgentePerdeuAprimeira")


##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- length(kmeans)
 
  con <- criaConexaoNaBaseAtual()
  
   
  
  numeroDoKusado <-length(kmeans$centers[,1])
  for(i in 1: numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
    
    segundaCartaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,1])
    segundaCartaHumanoClustering <- mean(casosQuePertencemAoClusterAtual[,2])
    
    sql <-sprintf("INSERT INTO centroidesSegundaCartaRoboPerdeuAprimeira(grupo, segundaCartaRoboClustering, segundaCartaHumanoClustering, numerok) 
                  VALUES(%f, %f, %f, %f);",
                  i, segundaCartaRoboClustering, segundaCartaHumanoClustering, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesSegundaCartaRoboPerdeuAprimeira")
inserirClustersCentroides()
#########################

#}


#write.csv(resultadokmeans, file = "resultado_kmeansNewSegundaCartaRoboperdeuAprimeira.csv")





