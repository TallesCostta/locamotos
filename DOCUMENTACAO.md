# Documentação Técnica: Estrutura do Projeto Locamotos

A aplicação **Locamotos** foi construída utilizando o ecossistema Spring Boot com Java 17. A estrutura do projeto segue o padrão **MVC (Model-View-Controller) / Arquitetura em Camadas (N-Tier)**, garantindo separação de responsabilidades, facilidade de manutenção e escalabilidade.

Abaixo está o detalhamento técnico da organização dos pacotes e arquivos do projeto.

---

## ? Estrutura de Pastas e Arquivos

### 1. Raiz do Projeto
A raiz do projeto abriga os arquivos de configuração de ambiente e as ferramentas de automação de build (Maven).

* **`pom.xml`**: Coração do gerenciamento de dependências. É aqui que declaramos os pacotes necessários como Spring Web, Spring Data JPA, banco de dados H2, Lombok, Swagger/OpenAPI e Spring Security.
* **`mvnw` / `mvnw.cmd`**: Scripts executáveis do Maven Wrapper. Permitem rodar o build do projeto sem a necessidade de ter o Maven previamente instalado no computador (respectivamente para sistemas Unix-like e Windows).
* **`.mvn/`**: Diretório de suporte ao Maven Wrapper, contém o arquivo `.properties` que define a versão exata do Maven a ser baixada.
* **`.gitignore` / `.gitattributes`**: Arquivos de controle de versionamento. Definem arquivos ignorados e como o Git deve lidar com quebras de linha em sistemas operacionais distintos.
* **`HELP.md`**: Guia automático gerado pelo Spring contendo referências para as dependências adicionadas.

### 2. Código da Aplicação (`src/main/`)
Diretório principal onde toda a lógica do negócio, configurações da API e recursos estáticos residem.

#### Pacote Base: `br.com.donatti.locamotos`
* **`LocamotosApplication.java`**: A classe `main()` e o ponto de partida do Spring Boot. Usando a anotação `@SpringBootApplication`, ela inicializa o contexto e o servidor embutido (Tomcat).
* **`ServletInitializer.java`**: Utilizado caso a aplicação mude o empacotamento de JAR para WAR, servindo como ponte de comunicação caso o projeto seja rodado em um container externo como um Tomcat standalone.

#### Pacote: `config`
Camada onde centralizamos as configurações de infraestrutura e comportamento técnico geral da aplicação.
* **`SecurityConfig.java`**: Classe que intercepta a auto-configuração do Spring Security. Desativa o bloqueio padrão de login em memória do Spring e expõe/libera os endpoints (como `/motos`) definindo que todas as requisições estão permitidas via método `.permitAll()`.

#### Pacote de Domínio: `moto`
Módulo focado unicamente no gerenciamento e regras de negócio para a entidade "Motos".

* **`controller/MotoController.java`**: A camada de Apresentação. Anotada com `@RestController`, é a responsável por escutar as requisições HTTP (Rotas `/motos` - GET, POST, DELETE), receber JSONs e enviá-los ao Service. As funções dentro dela estão fortemente anotadas com o OpenAPI 3 (`@Operation`, `@ApiResponse`) para geração automática do Swagger UI.
* **`service/MotoService.java`**: A camada de Serviço. Funciona como o orquestrador do domínio. É onde aplicamos as regras de negócio, validamos regras antes de salvar (ex: impedir placas vazias ou repetidas) e delegamos o acesso de dados ao Repository.
* **`repository/MotoRepository.java`**: A camada de Acesso a Dados. É uma interface que herda propriedades do Spring Data JPA (`JpaRepository`). Com poucas linhas de código, abstrai comandos SQL inteiros, permitindo buscar, listar, deletar ou salvar motos dentro da base dados gerada pela aplicação.
* **`model/Moto.java`**: A camada de Domínio / Entidade. Uma classe POJO anotada com as tags do JPA (`@Entity`, `@Id`) e Lombok (`@Data`). Representa fisicamente o esquema da tabela no banco de dados e as propriedades de uma moto. Também abriga as descrições em Swagger pelo `@Schema`.

#### Pacote Auxiliar: `exception`
Onde gerimos os possíveis problemas e direcionamentos de erros.
* **`MotoException.java`**: Classe customizada que herda de `RuntimeException`. Utilizada pelo `Service` sempre que uma regra de negócio é violada.
* **`GlobalExceptionHandler.java`**: Usando o conceito de *Controller Advice*, esse interceptador global captura as exceções (`MotoException` e as genéricas `Exception`) que não foram propriamente tratadas nos controllers, transformando o "Trace de erro do Java" em um formato JSON amigável e legível com códigos HTTP apropriados (como HTTP 400 ou 500).

#### Diretório: `src/main/resources/`
Guarda configurações não transpiladas e propriedades de sistema.
* **`application.properties`**: Onde definimos variáveis vitais do ecossistema da aplicação:
  - Caminho raiz de roteamento da API (`server.servlet.context-path=/locamotos`).
  - Configurações do Banco em memória (URL do H2).
  - Configuração do dialeto do Hibernate JPA.
* **`static/` e `templates/`**: Pastas (atualmente vazias) reservadas para servir conteúdo visual caso houvessem arquivos estáticos (CSS, Imagens) ou HTML server-side (Thymeleaf).

### 3. Código de Testes (`src/test/`)
* **`br/com/donatti/locamotos/LocamotosApplicationTests.java`**: Uma classe gerada automaticamente que contém um teste de *context load*. Sua função é rodar o Spring limpo; se as injeções de dependência não falharem, o teste passa, garantindo que o projeto não foi "quebrado" estruturalmente.