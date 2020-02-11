
#conex?o com o banco de dados

library(DBI)
library(RMySQL)





executeSelectMysql <-function(sql, base){
  m <- dbDriver("MySQL", fetch.default.rec=10000)
   conexaoTrucoCbr <- dbConnect(MySQL(),
                               user="root", password="MysqlPass",
                               dbname="dbtrucoimitacao", host="127.0.0.1", port=3306)
  
 
    
  consulta  = dbSendQuery(conexaoTrucoCbr,sql)
    resultado =  fetch(consulta, n= -1) 
    dbClearResult(consulta)
    dbDisconnect(conexaoTrucoCbr)
  
  
  
  return (resultado)
  
}




#CONEX?O server
#con <- dbConnect(MySQL(),
#                user="root", password="desenvolvimento",
#              dbname="dbtrucoimitacao", host="142.93.18.149", port=3306)
#on.exit(dbDisconnect(con))

#conexão local


#aqui deleta todos os clusters contidos nas tabelas para executar de novo
excluiLabelCluster <- function(tabela){
  m <- dbDriver("MySQL", fetch.default.rec=10000)
    conCluster <- dbConnect(MySQL(),
                   user="root", password="MysqlPass",
                   dbname="dbtrucoimitacao", host="127.0.0.1", port=3306)
  
  
  
  sql <-sprintf("update maos set %s =0;",
                tabela)
  dbSendQuery(conCluster,sql)
  dbDisconnect(conCluster)
}

#aqui deleta todos os clusters contidos nas tabelas para executar de novo
excluirConteudoTabela <- function(tabela){
  m <- dbDriver("MySQL", fetch.default.rec=10000)
  conConteudo <- dbConnect(MySQL(),
                   user="root", password="MysqlPass",
                   dbname="dbtrucoimitacao", host="127.0.0.1", port=3306)
  
  sql <-sprintf("delete from %s;",
                tabela)
  dbSendQuery(conConteudo,sql)
  dbDisconnect(conConteudo)
}

#aqui passa a tabela do clustering que pertence, e o dataFrame com o resultado do kmeans faz a inser??o do kmeans
realizaInsertClusteringPertencente <- function(df,tabela){
 
   #aqui pega a quantidade de grupos existentes
  vectorGrupos <- unique(df$kmeans.cluster)
  print(vectorGrupos)
  numeroDeItensVectorGrupos <- seq_along(vectorGrupos)
  m <- dbDriver("MySQL", fetch.default.rec=10000)
  conNew <- dbConnect(MySQL(),
                      user="root", password="MysqlPass",
                      dbname="dbtrucoimitacao", host="127.0.0.1", port=3306)
  
  
  #aqui percorre um vetor com o n?mero de grupos
  for (i in numeroDeItensVectorGrupos) {
    
    #seleciona apenas os casos pertencentes a cada grupo
    vectorCasosPertencentesAoGrupo <- na.omit(subset(df, df$kmeans.cluster== i))
    
    vectorIdCasos <- na.omit(vectorCasosPertencentesAoGrupo$id)
    #print(vectorIdCasos) 
    numeroDeItensVectorCasosPertencentesAoGrupo <- seq_along(vectorIdCasos)
    for (contadorCasosCadaGrupo in numeroDeItensVectorCasosPertencentesAoGrupo) {
      
      
      sql <-sprintf("UPDATE maos SET %s = %f where id = %f;",
                    tabela,i,vectorCasosPertencentesAoGrupo$id[contadorCasosCadaGrupo])  
      #sql <-sprintf("UPDATE maos SET %s = %f where id = %f;",tabela,i,vectorCasosPertencentesAoGrupo$id[contadorCasosCadaGrupo])
      dbSendQuery(conNew,sql)
      
      print(i)
      
      
    }
  }
  dbDisconnect(conNew)
  
}

criaConexaoNaBaseAtual <- function(){
  m <- dbDriver("MySQL", fetch.default.rec=10000)
  con <- dbConnect(MySQL(),
                   user="root", password="MysqlPass",
                   dbname="dbtrucoimitacao", host="127.0.0.1", port=3306)
  return (con)
  dbDisconnect(con)
}


CriarElbow <- function(numeroMaximoParaSerTestado, dataFrameEscalonado){
  set.seed(1)
  wcss=vector()
  for (i in 1: numeroMaximoParaSerTestado) {
    kmeans=kmeans(dataFrameEscalonado, i )
    wcss[i] = sum(kmeans$withinss)
  }
  
  plot(1: numeroMaximoParaSerTestado, wcss, type = 'b', xlab = 'Clusters', ylab = 'WCSS')
  
  
}

retornaK <- function(numeroMaximoParaSerTestado, dataFrameEscalonado){
  set.seed(1)
  wcss=vector()
  wcssK=vector()
  maiorWcss = 0
  for (i in 1: numeroMaximoParaSerTestado) {
    kmeans=kmeans(dataFrameEscalonado, i )
    wcss[i] = sum(kmeans$withinss)
    if(maiorWcss == 0){
      maiorWcss = wcss[i]
    }
    wcssK[i] = wcss[i]/maiorWcss
    
  }
  
  dk = vector()
  for(k in 1 : length(wcssK)) {
    proximoK = k + 1
    dk[k] = (wcssK[proximoK] - wcssK[k])/((proximoK) - k)
    
    
  }
  
  result = vector()
  menorValor = 0
  indiceMenorValor = 0
  contador =0
  for (l in 1 : length(dk)) {
    contador = contador +1
    if((l + 1) < numeroMaximoParaSerTestado && l > 1){
      #validar as condições
      #if(abs(dk[l]+ dk[l -1]) >=0.05){
        result[l] = abs(dk[l]/dk[l -1])   
      
        if(menorValor == 0){
          menorValor = result[l]
          indiceMenorValor = l
        }else if(menorValor > result[l]){
          menorValor = result[l]
          indiceMenorValor = l 
        }
      }
      #}
    print(contador)
    print(dk[l])
    print(result[l])
    
  }
  
  kparaRetornar = indiceMenorValor 
  return(kparaRetornar)
}

#setwd("E:/Mestrado/Segundo Semestre/c?digos/clustering/ClusteringEstrategia/resultados/gruposTruco2/")
#setwd("/home/gustavo/Dropbox/Mestrado/Segundo Semestre/códigos/clustering/ClusteringEstrategia/resultados/gruposTruco2/")

#realiza consulta do arquivo funcoesAuxiliares
sql <- ("select quemTrucoCluster, quemRetrucoCluster, quemValeQuatroCluster, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering, id from maos where utilTruco =1 and (ganhadorSegundaRodada = 2 or ((ganhadorSegundaRodada = 0 and ganhadorPrimeiraRodada = 2) or(ganhadorSegundaRodada = 0 and ganhadorPrimeiraRodada = 0 and jogadorMao = 2) ));")

dados <- executeSelectMysql(sql,"trucocbr")
casos <- dados[1:6]

casos <- scale(casos)
#kmeans
set.seed(1)
k <- retornaK(10, casos)

sqlK<- ("select MAX(clusterQuemTrucoTerceiraPerdeuAnterior) from maos;")
kRecebido <- executeSelectMysql(sqlK,"trucocbr")
kRecebido

if(kRecebido != k){
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o id para não dar problema no insert que precisa
resultadokmeans$id = dados$id 

#Bd
#excluir resultadosAntigos
excluiLabelCluster("clusterQuemTrucoTerceiraPerdeuAnterior")



#aqui realiza o insert
realizaInsertClusteringPertencente(resultadokmeans,"clusterQuemTrucoTerceiraPerdeuAnterior")

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
    
    
sql <-sprintf("INSERT INTO centroidesQuemTrucoTerceiraPerdeuAnterior(grupo, quemTruco, quemRetruco, quemValeQuatro,numerok, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering) 
                  VALUES(%f, %f, %f, %f, %f, %f, %f, %f);",
                  i, quemTruco, quemRetruco, quemValeQuatro, numeroDoKusado, cartaAltaRoboClustering, cartaMediaRoboClustering, cartaBaixaRoboClustering)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroidesQuemTrucoTerceiraPerdeuAnterior")
inserirClustersCentroides()
#########################

}






 exit <- function() { q("no") }
 exit()