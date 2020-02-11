sql<- ("select id,cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering  from maos where utilCarta =1 or utilTruco =1;")

dados <- executeSelectMysql(sql,"trucocbr") 
  
casos <- (dados[2:4])
casos <- scale(casos)

set.seed(1)
k <- retornaK(20, casos)
k
CriarElbow(20, casos)
sqlK<- ("select MAX(clusteringindexacao) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")

kRecebido
#kmeans
set.seed(1)
kmeans <- kmeans(casos, k)


casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)

#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 




#excluir resultadosAntigos
excluiLabelCluster("clusteringindexacao")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusteringindexacao")

##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- seq_along(kmeans)
  
  numeroDoKusado <-length(kmeans$centers[,1])
  con <- criaConexaoNaBaseAtual()
  
  for(i in 1:numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
      
    cartaAltaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,1])
    cartaMediaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,2])
    cartaBaixaRoboClustering <- mean(mean(casosQuePertencemAoClusterAtual[,3]))
  
    
    
    sql <-sprintf("INSERT INTO centroidesgrupoindexacao(grupo, centroidecartaaltarobomao, centroidecartamediarobomao, centroidecartabaixarobomao,  numerok) 
                  VALUES(%f, %f, %f, %f, %f);",
                  i, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesgrupoindexacao")
inserirClustersCentroides()
#########################





