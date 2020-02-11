sql <- ("select quemPediuFlorCluster, quemPediuContraFlorCluster, quemPediuContraFlorEoRestoCluster,idMao from maos where utilFlor =1")
dados <- executeSelectMysql(sql, "trucocbr") 

dados

casos <- dados[1:3]
casos <- scale( casos)




set.seed(1)

CriarElbow(10, casos )
k <- retornaK(10, casos)
k
#kmeans
set.seed(1)
kmeans <- kmeans(casos, k)
casos <- ggfortify::unscale(casos)
resultadokmeans = data.frame(casos, kmeans$cluster)
#cria o idMao para não dar problema no insert que precisa
resultadokmeans$idMao = dados$idMao 

#write.csv(resultadokmeans, file = "resultado_kmeansNewClusteringEnvidoAgentePe.csv")


#excluir resultadosAntigos
excluiLabelCluster("clusterQuemFlorJogadorPe")
#inserir
realizaInsertClusteringPertencente(resultadokmeans,"clusterQuemFlorJogadorPe")
##########################
#realiza insert do centroide
inserirClustersCentroides <- function(){
  quantidadeDeItensKmeans <- length(kmeans)
  
  
  centersKmeans <- kmeans$centers 
  centersKmeans
  numeroDoKusado <-length(centersKmeans[,1])
  m <- dbDriver("MySQL", fetch.default.rec=10000)
  con <- dbConnect(MySQL(),
                   user="root", password="MysqlPass",
                   dbname="dbtrucocbr", host="127.0.0.1", port=3306)
  
  for(i in 1: numeroDoKusado){
    i
    centerLinhas <- centersKmeans[i,]   
     
    quemPediuFlor <- centerLinhas[1]
    quemPediuContraFlor <- centerLinhas[2]
    quemPediuContraFlorEoResto <- centerLinhas[3]
   
    sql <-sprintf("INSERT INTO centroideQuemFlorAgentePe(grupo, quemPediuFlor, quemPediuContraFlor, quemPediuContraFlorEoResto, numerok) 
                  VALUES(%f, %f, %f, %f, %f);",
                  i, quemPediuFlor, quemPediuFlorContraFlor, quemPediuContraFlorEoResto, numeroDoKusado)
    dbSendQuery(con,sql)
  }
  dbDisconnect(con)
}
excluirConteudoTabela("centroideQuemFlorAgentePe")
inserirClustersCentroides()
#########################







