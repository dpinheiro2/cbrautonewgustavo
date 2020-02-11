#conex?o com o banco de dados

library(DBI)
library(RMySQL)





executeSelectMysql <-function(sql, base){
  m <- dbDriver("MySQL", fetch.default.rec=10000)
   conexaoTrucoCbr <- dbConnect(MySQL(),
                               user="root", password="MysqlPass",
                               dbname="dbaprendizadoativo", host="127.0.0.1", port=3306)
  
 
    
  consulta  = dbSendQuery(conexaoTrucoCbr,sql)
    resultado =  fetch(consulta, n= -1) 
    dbClearResult(consulta)
    dbDisconnect(conexaoTrucoCbr)
  
  
  
  return (resultado)
  
}




#CONEX?O server
#con <- dbConnect(MySQL(),
#                user="root", password="desenvolvimento",
#              dbname="dbaprendizadoativo", host="142.93.18.149", port=3306)
#on.exit(dbDisconnect(con))

#conexão local


#aqui deleta todos os clusters contidos nas tabelas para executar de novo
excluiLabelCluster <- function(tabela){
  m <- dbDriver("MySQL", fetch.default.rec=10000)
    conCluster <- dbConnect(MySQL(),
                   user="root", password="MysqlPass",
                   dbname="dbaprendizadoativo", host="127.0.0.1", port=3306)
  
  
  
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
                   dbname="dbaprendizadoativo", host="127.0.0.1", port=3306)
  
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
                      dbname="dbaprendizadoativo", host="127.0.0.1", port=3306)
  
  
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
                   dbname="dbaprendizadoativo", host="127.0.0.1", port=3306)
  return (con)
  dbDisconnect(con)
}


