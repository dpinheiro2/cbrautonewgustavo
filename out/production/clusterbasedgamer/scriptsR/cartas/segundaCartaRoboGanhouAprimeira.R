

sql <- ("select id, segundaCartaRoboClustering  from maos where ((ganhadorPrimeiraRodada =1) or (ganhadorPrimeiraRodada = 0 and jogadorMao = 1) )and utilCarta = 1;")
dados <- executeSelectMysql(sql,"trucocbr")


casos <- (dados[2])
casos <- scale(casos)
set.seed(1)
 
k <- retornaK(7, casos)

#CriarElbow(7, casos)

sqlK<- ("select MAX(clusterSegundaCartaAgenteGanhouAprimeira) from maos;")
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
excluiLabelCluster("clusterSegundaCartaAgenteGanhouAprimeira")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterSegundaCartaAgenteGanhouAprimeira")

##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- length(kmeans)
  
  con <- criaConexaoNaBaseAtual()
  
  numeroDoKusado <-length(kmeans$centers[,1])
  for(i in 1: numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
    
    segundaCartaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,1])
    
    
sql <-sprintf("INSERT INTO centroidesSegundaCartaRoboGanhouAprimeira(grupo, segundaCartaRoboClustering, numerok) 
                  VALUES(%f, %f, %f);",
                  i, segundaCartaRoboClustering, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesSegundaCartaRoboGanhouAprimeira")
inserirClustersCentroides()
#########################


#write.csv(resultadokmeans, file = "resultado_kmeansNewSegundaCartaRoboGanhouAprimeira.csv")

#}



