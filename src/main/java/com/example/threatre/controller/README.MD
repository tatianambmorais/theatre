# Threatre

Projeto de exemplo utilizando **Spring Boot 3** para gestão de filmes com banco de dados PostgreSQL. O projeto permite o gerenciamento de filmes, incluindo operações como buscar, salvar, deletar e exibir detalhes de filmes.

## Tecnologias utilizadas

- **Spring Boot 3.x** (framework principal)
- **Java 17**
- **Spring Data JPA** (para persistência de dados)
- **PostgreSQL** (banco de dados)
- **Spring Web** (API REST)
- **Spring WebFlux** (para reatividade)
- **JUnit 5 e Mockito** (para testes)
- **Lombok** (para simplificação do código)

## Funcionalidades

### Endpoints da API:

1. **POST /filmes/buscar**
    - Busca e salva um filme com base no título.
    - Caso o título esteja vazio ou nulo, uma exceção de `IllegalArgumentException` será lançada.

2. **GET /filmes/{id}**
    - Obtém um filme pelo ID.
    - Caso o filme não seja encontrado, uma exceção `ResourceNotFoundException` será lançada.

3. **GET /filmes/todos**
    - Obtém todos os filmes cadastrados.

4. **DELETE /filmes/deletar/{id}**
    - Deleta um filme pelo ID.

### Tratamento de Exceções

- **IllegalArgumentException**: Retorna `400 Bad Request` com mensagem informando o erro no parâmetro.
- **ResourceNotFoundException**: Retorna `404 Not Found` quando o recurso solicitado não é encontrado.
- **Exception**: Exceções genéricas retornam `500 Internal Server Error` com mensagem genérica.

## Como rodar o projeto

### Pré-requisitos

- **Java 17**
- **Maven** (para gerenciamento de dependências)
- **PostgreSQL** (banco de dados)

### Passos para rodar

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/threatre.git
   ```

2. Entre na pasta do projeto:

   ```bash
   cd threatre
   ```

3. Configure seu banco de dados PostgreSQL. No arquivo `src/main/resources/application.properties`, defina as configurações de conexão com o banco de dados. Exemplo de configuração:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/threatre_db
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

    - **localhost**: O aplicativo rodará localmente na URL `http://localhost:8081`.
    - **Porta padrão**: O Spring Boot usa a porta 8081 por padrão, a menos que configurado de outra forma.

4. Execute o projeto com Maven:

   ```bash
   mvn spring-boot:run
   ```

   O servidor estará disponível em `http://localhost:8081`.

### Testes

O projeto já inclui testes automatizados utilizando JUnit e Mockito. Para rodá-los, use o seguinte comando:

```bash
mvn test
```

Caso queira rodar apenas os testes de integração:

```bash
mvn verify
```

