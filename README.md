# Finanças Java App

API para projeto Java solicitado pelo professor de algoritmos e programação II.

# Dependências

* Editor de texto ou IDE (aka VSCode)
* [JDK 8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven 3+](http://maven.apache.org/)

# Configuração

Clone o arquivo `conf/application.conf.example` para `conf/application.conf`
e atualize com as credenciais do banco de dados a ser utilizado.

# Inicializando

Com o Maven devidamente instalado, rode na raiz do projeto `mvn jooby:run -Dapplication.port=<port>`