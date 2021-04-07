Backend (Java)
Implementar un Microservicio que consuma Tweets y basado en unos criterios de
configuración los persista en una base de datos para su gestión a través de una API REST.

Restricciones:
● Solo se deben persistir aquellos tweets cuyos usuarios superen un número N de
seguidores (default 1500). FINALIZADO

● Solo se deben persistir aquellos tweets cuyo idioma esté en una lista de idiomas
permitidos (default español, francés, italiano). FINALIZADO

● De cada tweet deben almacenarse los siguientes datos: usuario, texto, localización,
validación. FINALIZADO

El API REST debe permitir:
● Consultar los tweets. FINALIZADO

● Marcar un tweet como validado. (NO FINALIZADO. Habría que implementar un endpoint del tipo POST "/acciona/api/v1/tweets/{id_tweet} y hacer una llamada al método save del repositorio con el objeto modificado)

● Consultar los tweets validados por usuario. (NO FINALIZADO. Habría que implementar un endpoint del tipo GET y hacer una consulta custom en el repositorio)

● Consultar una clasificación de los N hashtags más usados (default 10). (NO FINALIZADO. Habría que implementar un endpoint del tipo GET,
 y hacer una búsqueda en el texto del Tweet con el patrón de búsqueda "#[a-zA-Z0-9]{1,}" y agrupar los hastags en una lista ordenada de mayor a menor para
 poder recuperar los 10 primeros. Estos serían los métodos aproximadamente:
 
   
   private List<Hashtag> countHastags(List<String> hashtags) {
    return hashtags
        .stream()
        .collect(groupingBy(identity(), counting()))
        .entrySet()
        .stream()
        .map(this::toCount)
        .collect(toList());
  }

  private HashtagCount toCount(Entry<String, Long> e) {
    return HashtagDoc.builder()
       .id(e.getKey()).name(e.getKey())
       .count(e.getValue().intValue())
       .build();
  }
 
 Asimismo, faltaría la documentación generada con OpenAPI y por supuesto los test, pero debido a que tengo una lesión temporal en el ojo derecho y sólo tengo operativo el izquierdo, lo he hecho hasta que mi vista ha dicho basta. Por supuesto, cualquier cosa os la puedo explicar en vivo y en directo, aunque no me haya dado tiempo a codificarla. 