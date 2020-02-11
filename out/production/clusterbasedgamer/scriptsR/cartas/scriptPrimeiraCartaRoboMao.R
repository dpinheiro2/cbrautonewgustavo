
sql <-  ("select id,cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, primeiraCartaRoboClustering  from maos where jogadorMao=1 and cartaAltaRoboClustering is not null and cartaMediaRoboClustering is not null and cartaBaixaRoboClustering is not null and utilCarta = 1;")

dados<- executeSelectMysql(sql, "trucocbr")

#casos <- na.omit(dados[2:5])
casos <- (dados[2:5])
casos <- scale(casos)


set.seed(1)
k= retornaK(10, casos)
#CriarElbow(10, casos)

sqlK<- ("select MAX(clusterPrimeiraCartaAgenteMao) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")
k
kRecebido
#if(kRecebido != k){
#kmeans
set.seed(1)
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 


#excluir resultadosAntigos
excluiLabelCluster("clusterPrimeiraCartaAgenteMao")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterPrimeiraCartaAgenteMao")

inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- seq_along(kmeans)
  
  numeroDoKusado <- length(kmeans$centers[,1])
  
  con <- criaConexaoNaBaseAtual()
  
  for(i in 1  : numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 
    
    cartaAltaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,1])
    cartaMediaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,2])
    cartaBaixaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,3]) 
    
    primeiraCartaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,4])
    
    sql <-sprintf("INSERT INTO centroidesPrimeiraCartaRoboEmao(grupo, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, primeiraCartaRoboClustering, numerok) 
                  VALUES(%f, %f, %f, %f, %f, %f);",
                  i, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, primeiraCartaRoboClustering, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesPrimeiraCartaRoboEmao")
inserirClustersCentroides()
#########################

#}

