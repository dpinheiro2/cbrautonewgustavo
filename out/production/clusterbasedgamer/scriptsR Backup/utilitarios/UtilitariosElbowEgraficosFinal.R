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

