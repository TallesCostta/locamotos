# Projeto Locamotos

Projeto final locamotos - bootcamp: Arquiteto(a) de Software - Pós graduação XPe.

Este projeto consiste em uma API REST para gerenciamento de uma frota de motocicletas, permitindo operações de CRUD (Create, Read, Update, Delete) e consultas específicas. A aplicação foi desenvolvida com base no ecossistema Spring Boot, seguindo as melhores práticas de arquitetura em camadas.

---

## Tecnologias Utilizadas

- **Java 17**: Versão da linguagem Java utilizada.
- **Spring Boot**: Framework principal para criação da aplicação.
- **Maven**: Gerenciador de dependências e build do projeto.
- **Spring Web**: Para criação de endpoints REST.
- **Spring Data JPA**: Para persistência de dados e abstração de queries SQL.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Spring Security**: Para controle de acesso e segurança da API.
- **Lombok**: Para redução de código boilerplate (getters, setters, construtores).
- **Swagger (OpenAPI 3)**: Para documentação interativa e visual da API.

---

## Como Executar o Projeto

### Pré-requisitos
- JDK 17 ou superior instalado.
- Maven (opcional, pois o projeto utiliza o Maven Wrapper).

### Passos para Execução
1. Clone este repositório.
2. Abra um terminal na raiz do projeto.
3. Execute o seguinte comando para iniciar a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```
A aplicação estará disponível em `http://localhost:8080/locamotos`.

---

## Endpoints Disponíveis

A API pode ser explorada de forma interativa através do Swagger UI.

- **Swagger UI**: [http://localhost:8080/locamotos/swagger-ui/index.html](http://localhost:8080/locamotos/swagger-ui.html)

### Principais Rotas
- `GET /motos`: Lista todas as motos cadastradas.
- `GET /motos/{id}`: Busca uma moto pelo seu ID.
- `POST /motos`: Cadastra uma nova moto.
- `DELETE /motos/{id}`: Deleta uma moto pelo seu ID.
- `GET /motos/modelo/{modelo}`: Busca motos por parte do nome do modelo.
- `GET /motos/inventario`: Retorna a quantidade total de motos.

---

## Arquitetura

O projeto segue uma **Arquitetura em Camadas (MVC)**, com as seguintes responsabilidades:

- **Controller**: Camada de apresentação que recebe as requisições HTTP e as direciona.
- **Service**: Camada de negócio que orquestra as operações e valida as regras.
- **Repository**: Camada de dados que interage com o banco de dados através do Spring Data JPA.
- **Model**: Entidades que representam as tabelas do banco de dados.
- **Exception**: Camada para tratamento global de exceções, retornando erros HTTP padronizados.
- **Config**: Configurações de segurança e comportamento da aplicação.
