sql<- ("select id,pontosEnvidoRobo from maos where utilEnvido= 1 or utilFlor =1;")

dados <- executeSelectMysql(sql,"trucocbr") 

casos <- (dados[2])
casos <- scale(casos)

set.seed(1)
k <- retornaK(10, casos)
k
sqlK<- ("select MAX(clusteringIndexacaoPontos) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")

 


#kmeans
set.seed(1)
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 




#excluir resultadosAntigos
excluiLabelCluster("clusteringIndexacaoPontos")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusteringIndexacaoPontos")

##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- seq_along(kmeans)
  centersKmeans <- kmeans$centers 
  
  numeroDoKusado <-length(centersKmeans[,1])
  
  con <- criaConexaoNaBaseAtual()
  
    
  for(i in 1:numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
    
    
    pontosEnvidoRobo <- mean(casosQuePertencemAoClusterAtual[,1])
    
    
    
    
    sql <-sprintf("INSERT INTO centroidesgrupoindexacaopontosEnvidoRobo(grupo, centroidePontosEnvidoRobo, numerok) 
                  VALUES(%f, %f, %f);",
                  i, pontosEnvidoRobo, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
  
}
excluirConteudoTabela("centroidesgrupoindexacaopontosEnvidoRobo")
inserirClustersCentroides()



