#setwd("E:/Mestrado/Segundo Semestre/c?digos/clustering/ClusteringEstrategia/ClusteringDivididos/primeiraCartaRoboPe/")
#setwd("/home/gustavo/Dropbox/Mestrado/Segundo Semestre/códigos/clustering/ClusteringEstrategia/resultados/quemGanhouEnvidoAgenteEpe/")
sql <- ("select quemPediuEnvidoCluster, quemPediuRealEnvidoCluster, quemPediuFaltaEnvidoCluster, pontosEnvidoRobo, id from maos where jogadorMao=1 and utilEnvido = 1")
dados <- executeSelectMysql(sql, "trucocbr") 



casos <- dados[1:4]
casos <- scale(casos)





set.seed(1)
k = retornaK(10,casos)
#CriarElbow(10, casos)

sqlK<- ("select MAX(clusterQuemEnvidoAgenteMao) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")



if(kRecebido != k){
#kmeans
set.seed(1)
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 

#write.csv(resultadokmeans, file = "resultado_kmeansNewClusteringEnvidoAgentePe.csv")


#excluir resultadosAntigos
excluiLabelCluster("clusterQuemEnvidoAgenteMao")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterQuemEnvidoAgenteMao")
##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- length(kmeans)
  con <- criaConexaoNaBaseAtual()
  
  numeroDoKusado <-length(kmeans$centers[,1])
 
  
  for(i in 1: numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 

    quemPediuEnvido <- mean(casosQuePertencemAoClusterAtual[,1])
    quemPediuRealEnvido <- mean(casosQuePertencemAoClusterAtual[,2])
    quemPediuFaltaEnvido <- mean(casosQuePertencemAoClusterAtual[,3])
    pontosEnvidoRobo <- mean(casosQuePertencemAoClusterAtual[,4])
        
    sql <-sprintf("INSERT INTO centroidesQuemGanhouEnvidoAgenteEmao(grupo, quemPediuEnvido, quemPediuRealEnvido, quemPediuFaltaEnvido, numerok, pontosEnvidoRobo) 
                  VALUES(%f, %f, %f, %f, %f, %f);",
                  i, quemPediuEnvido, quemPediuRealEnvido, quemPediuFaltaEnvido,  numeroDoKusado, pontosEnvidoRobo )
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesQuemGanhouEnvidoAgenteEmao")
inserirClustersCentroides()
#########################
}






