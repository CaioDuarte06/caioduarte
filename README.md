# 🛒 Sistema de Vendas (PDV)

👨‍💻 Autor: Caio Duarte Fernandes de Almeida

Sistema desktop desenvolvido em Java utilizando JavaFX e MySQL.

---

## 📖 Sobre o projeto

Este sistema foi desenvolvido com o objetivo de simular um **ponto de venda (PDV)** completo, permitindo:

- Cadastro de clientes
- Cadastro de produtos
- Controle de estoque
- Realização de vendas
- Histórico de compras por cliente

---

## ⚙️ Tecnologias utilizadas

- Java
- JavaFX
- MySQL
- JDBC
- Scene Builder

---

## 🧩 Funcionalidades

### 👤 Clientes
- Cadastro de clientes
- Validação de CPF e CNPJ
- Status (Ativo/Inativo)
- Histórico de compras

### 📦 Produtos
- Cadastro de produtos
- Controle de estoque
- Atualização automática ao vender

### 💰 Vendas (PDV)
- Seleção de cliente
- Adição de produtos ao carrinho
- Cálculo automático de total
- Cálculo de troco
- Métodos de pagamento:
  - Dinheiro
  - Cartão
  - Pix
- Baixa automática no estoque

---

## 🧠 Estrutura do sistema

O sistema segue o padrão **MVC**:

- Model → Regras de negócio
- View → Interface (FXML)
- Controller → Controle das ações

---

# 🗄️ Banco de Dados - Sistema de Vendas 


---

## 📦 Criação do Banco


CREATE DATABASE Sistema;
USE Sistema;

🧩 Estrutura do Banco de Dados

O sistema é composto pelas seguintes tabelas:


📦 produto

Armazena os produtos disponíveis para venda.

Campo	Tipo	Descrição
id	INT	Identificador único
nome	VARCHAR(100)	Nome do produto
descricao	TEXT	Descrição
categoria	VARCHAR(50)	Categoria
preco	DECIMAL(10,2)	Preço de venda
quantidade	INT	Estoque atual
estoque_minimo	INT	Quantidade mínima
codigo_barras	VARCHAR(50)	Código único


🔄 movimentacaoEstoque

Controla entrada e saída de produtos.

Campo	Tipo	Descrição
id	INT	Identificador
idProd	INT	Produto
dataHora	DATETIME	Data/hora
quantidade	INT	Quantidade
tipo	INT	0 = Entrada / 1 = Saída


📝 historico

Registra operações do sistema.

Campo	Tipo	Descrição
id	INT	Identificador
produto_id	INT	Produto afetado
operacao	VARCHAR(20)	Tipo de operação
quantidade	INT	Quantidade
data	DATETIME	Data
usuario	VARCHAR(100)	Usuário responsável


👤 usuario

Armazena os usuários do sistema.

Campo	Tipo	Descrição
id	INT	Identificador
nome	VARCHAR(100)	Nome
cpf	VARCHAR(11)	CPF único
email	VARCHAR(100)	Email
senha	VARCHAR(50)	Senha
cargo	VARCHAR(50)	Função


👥 clientes

Armazena os clientes (Pessoa Física ou Jurídica).

Campo	Tipo	Descrição
id	INT	Identificador
nome	VARCHAR(100)	Nome
cpf	VARCHAR(20)	CPF
cnpj	VARCHAR(18)	CNPJ
email	VARCHAR(100)	Email
status	VARCHAR(10)	Ativo/Inativo


💰 vendas

Registra as vendas realizadas.

Campo	Tipo	Descrição
id	INT	Identificador
cliente_id	INT	Cliente
data	DATETIME	Data da venda
total	DECIMAL(10,2)	Total da venda
desconto	DECIMAL(10,2)	Desconto aplicado
usuario	VARCHAR(100)	Usuário responsável


🧾 item_venda

Itens que compõem cada venda.

Campo	Tipo	Descrição
id	INT	Identificador
venda_id	INT	Venda
produto_id	INT	Produto
quantidade	INT	Quantidade
preco_unitario	DECIMAL(10,2)	Preço unitário
subtotal	DECIMAL(10,2)	Total do item


💳 pagamento

Registra os pagamentos das vendas.

Campo	Tipo	Descrição
id	INT	Identificador
venda_id	INT	Venda
tipo	VARCHAR(50)	Dinheiro, Cartão, Pix
valor	DECIMAL(10,2)	Valor pago


🔗 Relacionamentos
produto → movimentacaoEstoque
produto → item_venda
vendas → item_venda
vendas → pagamento
clientes → vendas


🔁 Regras do Sistema
Ao realizar uma venda:
Um registro é criado em vendas
Os produtos são inseridos em item_venda
O pagamento é registrado em pagamento
O estoque é atualizado em movimentacaoEstoque


📌 Observações
O sistema suporta múltiplos métodos de pagamento
Controle de estoque automático
Histórico completo de operações
Suporte para CPF e CNPJ

## 🛠️ Como executar o projeto

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/seu-repo.git


