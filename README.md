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

## 🛠️ Como executar o projeto

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/seu-repo.git

---

# 🗄️ Banco de Dados - Sistema de Vendas 


---

## 📦 Criação do Banco

```sql
CREATE DATABASE Sistema;
USE Sistema;

