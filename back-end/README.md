# 🛒 E-Commerce API

Este projeto foi feito buscando boas práticas de arquitetura e segurança de dados, utilizando problemas reais de sistemas de e-commerce:

* **Autenticação e Autorização Stateless (JWT):** Implementação de segurança baseada em tokens JWT. A API não guarda sessão em memória (stateless), validando a autenticidade de cada requisição de forma isolada e segura através do cabeçalho Authorization: Bearer [token].

* **Garantia de Estoque contra Concorrência (Lock Pessimista):** Implementação de `@Lock(LockModeType.PESSIMISTIC_WRITE)` no banco de dados. Isso impede que dois clientes comprem o último item do estoque no mesmo milissegundo, evitando a venda de produtos fantasmas (estoque negativo).
* **Prevenção de Deadlocks:** Ordenação de IDs de produtos antes da execução do Lock na transação, garantindo que requisições paralelas sigam a mesma fila e nunca travem o banco de dados.
* **Precisão Financeira:** Uso de `BigDecimal` para todos os cálculos de preços, subtotais e totais, eliminando completamente os erros de arredondamento de ponto flutuante comuns em tipos como `double` ou `float`.
* **Segurança Isolada no Back-end:** O cálculo de preço total do pedido é feito estritamente no servidor com base nos valores imutáveis do banco de dados, ignorando qualquer cálculo enviado pelo front-end para prevenir fraudes.
* **Histórico Imutável de Pedidos:** Relacionamento Pai/Filho (`Order` e `OrderItem`) mapeado com `CascadeType.ALL`, salvando uma "fotografia" do preço e quantidade do produto no exato momento da compra, garantindo a integridade histórica caso o produto mude de preço ou seja deletado no futuro.

---

## 🛠️ Tecnologias Utilizadas

* **Java 26**
* **Spring Boot 3** (Spring Data JPA, Spring Web)
* **Spring Security (Controle de acesso e filtros de autenticação)**
* **Oauth2**
* **PostgreSQL**
* **RabbitMQ**
* **Springdoc OpenAPI / Swagger**

---

## 📖 Documentação da API (Rotas e Endpoints)

Todas as rotas da aplicação (como criação de usuário, login, criação de pedidos, listagem, confirmação de pagamento e fluxo de cancelamento) estão totalmente documentadas, tipadas e testáveis através do **Swagger UI**.

Com a aplicação rodando localmente, acesse o endereço abaixo para visualizar os contratos, JSONs esperados (DTOs) e testar os endpoints:

> 🔗 **http://localhost:8080/swagger-ui/index.html**

---

## ⚙️ Como Rodar o Projeto

1. Clone o repositório:
   ```bash
    git clone https://github.com/lucas27/e-commerce.git
   ```
2. Rode o docker compose:
    ```terminal
    docker compose up
    ```