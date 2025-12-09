# DesafioJava

#Objetivo do Projeto
Desenvolver uma API REST completa para gerenciar o cadastro de aeroportos espalhados pelo mundo. A API deverá seguir os padrões e endpoints especificados abaixo e ser capaz de manipular os dados de aeroportos em um banco de dados relacional.

#Tecnologias Utilizadas
Java e Springboot

1. Como configurar o ambiente e instalar as dependências
 Pré-requisitos

Para executar corretamente este projeto, é necessário que o ambiente possua:

Java JDK 17 ou superior

Maven 3.8 ou superior

Uma IDE para desenvolvimento (opcional): IntelliJ IDEA, Eclipse ou VS Code

# 1. Verificação da instalação

No terminal (Prompt de Comando, PowerShell ou terminal Linux), execute:

java -version
mvn -version


Se ambos os comandos retornarem versões corretamente, o ambiente está configurado.

 Instalação das dependências do projeto

Na pasta raiz do projeto (onde está localizado o arquivo pom.xml), execute:

mvn clean install


Esse comando realiza automaticamente:

Download de todas as dependências do projeto

Compilação do código-fonte

Execução dos testes de unidade

Geração do build da aplicação

# 2. Como executar a aplicação

A aplicação pode ser executada de duas formas:

Opção 1 — Execução via Maven (recomendado)

Na raiz do projeto, execute:

mvn spring-boot:run

Opção 2 — Execução via IDE

Abra a classe principal do projeto:

AeroportosApiApplication.java


Clique em Executar / Run

 Endereço da aplicação após execução

Após a inicialização, a aplicação ficará disponível em:

http://localhost:8080

🔗 Endpoints da API

GET /api/v1/aeroportos → Lista todos os aeroportos

GET /api/v1/aeroportos/{iata} → Busca um aeroporto pelo código IATA

POST /api/v1/aeroportos → Cadastra um novo aeroporto

PUT /api/v1/aeroportos/{iata} → Atualiza um aeroporto

DELETE /api/v1/aeroportos/{iata} → Remove um aeroporto

 Acesso ao banco de dados H2

O projeto utiliza o banco de dados H2 em memória para testes e desenvolvimento.

Acesse em:

http://localhost:8080/h2-console


Configurações para login:

JDBC URL: jdbc:h2:mem:aeroportosdb  
User: sa  
Password: (em branco)

# 3. Como executar os testes (unidade e integração)

O projeto está configurado com os seguintes plugins Maven:

Maven Surefire Plugin → Responsável pelos testes de unidade (*Test.java)

Maven Failsafe Plugin → Responsável pelos testes de integração (*IT.java)

 Execução dos testes de unidade

Para executar apenas os testes unitários, utilize:

mvn test


#Esse comando executa os testes das camadas:

Domínio

Serviço

Execução completa dos testes (unidade + integração)

Para executar todos os testes do sistema, utilize:

mvn verify


Esse comando executa automaticamente:

Testes de unidade

Testes de integração

Inicialização do servidor em ambiente de testes

Testes completos dos endpoints REST (POST, GET, PUT, DELETE e 404 após exclusão)

# Resultado esperado

Ao final da execução dos testes, o resultado esperado é:

BUILD SUCCESS


Esse resultado confirma que todos os testes passaram com sucesso e que a API está funcionando corretamente.
