#Segunda ganhou anterior

#realiza consulta do arquivo funcoesAuxiliares
sql <- ("select quemTrucoCluster, quemRetrucoCluster, quemValeQuatroCluster, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, id from maos where utilTruco =1 and (ganhadorPrimeiraRodada =1 or (ganhadorPrimeiraRodada = 0 and jogadorMao = 1));")

dados <- executeSelectMysql(sql,"trucocbr")
casos <- dados[1:6]

casos <- scale(casos)
#kmeans
set.seed(1)
k <- retornaK(10, casos)
#CriarElbow(10, casos)

sqlK<- ("select MAX(clusterQuemTrucoSegundaGanhouAnterior) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")


if(kRecebido != k){
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 

#Bd
#excluir resultadosAntigos
excluiLabelCluster("clusterQuemTrucoSegundaGanhouAnterior")



#aqui realiza o insert
realizaInsertClusteringPertencente(resultadokmeans,"clusterQuemTrucoSegundaGanhouAnterior")

##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  con <- criaConexaoNaBaseAtual()
  
  numeroDoKusado <-length(kmeans$centers[,1])
  
  for(i in 1: numeroDoKusado){
    casosQuePertencemAoClusterAtual <- subset(resultadokmeans, resultadokmeans$kmeans.cluster ==i) 

    quemTruco <- mean(casosQuePertencemAoClusterAtual[,1])
    
    quemRetruco <- mean(casosQuePertencemAoClusterAtual[,2])
    quemValeQuatro <- mean(casosQuePertencemAoClusterAtual[,3])
    cartaAltaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,4])
    cartaMediaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,5])
    cartaBaixaRoboClustering <- mean(casosQuePertencemAoClusterAtual[,6])
    
    
sql <-sprintf("INSERT INTO centroidesQuemTrucoSegundaGanhouAnterior(grupo, quemTruco, quemRetruco, quemValeQuatro,numerok, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering) 
                  VALUES(%f, %f, %f, %f, %f, %f, %f, %f);",
                  i, quemTruco, quemRetruco, quemValeQuatro, numeroDoKusado, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesQuemTrucoSegundaGanhouAnterior")
inserirClustersCentroides()
#########################


}





