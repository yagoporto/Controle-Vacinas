# 📌 Sistema de Gerenciamento de Vacinação

## 🏥 Sobre o Projeto
Este é um sistema de gerenciamento de vacinação desenvolvido em Java utilizando **Spark** como framework para a API REST e **MySQL** como banco de dados. O sistema permite o cadastro, consulta e gerenciamento de pacientes, vacinas e imunizações.

## ⚙️ Tecnologias Utilizadas
- **Java** (versão 21 ou superior)
- **Spark Framework**
- **Maven** (gerenciador de dependências)
- **MySQL** (banco de dados relacional)
- **Jackson** (para manipulação de JSON)
- **MySQL Connector** (driver JDBC para conexão com o banco)
- **Thunder Client/Postman** (para testes dos endpoints REST)

## 🛠️ Configuração do Projeto
### 1️⃣ Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/nome-do-repositorio.git
cd nome-do-repositorio
```

### 2️⃣ Configurar o Banco de Dados
Crie um banco de dados no MySQL:
```sql
CREATE DATABASE vacinacao;
USE vacinacao;
```
Crie as tabelas conforme o modelo do DER.

### 3️⃣ Configurar o Arquivo `application.properties`
Crie um arquivo de configuração ou edite diretamente no código:
```properties
db.url=jdbc:mysql://localhost:3306/vacinacao
db.user=root
db.password=seu-password
```

### 4️⃣ Instalar Dependências e Rodar o Projeto
```bash
mvn clean install
mvn exec:java
```

## 📌 Endpoints Disponíveis
### 📍 Pacientes
- **GET** `/pacientes` → Lista todos os pacientes.
- **GET** `/pacientes/{id}` → Retorna um paciente pelo ID.
- **POST** `/pacientes` → Cadastra um novo paciente.
- **PUT** `/pacientes/{id}` → Atualiza um paciente.
- **DELETE** `/pacientes/{id}` → Remove um paciente.

### 📍 Vacinas
- **GET** `/vacinas` → Lista todas as vacinas.
- **POST** `/vacinas` → Cadastra uma nova vacina.

### 📍 Imunização
- **POST** `/imunizacoes` → Registra uma vacinação para um paciente.
- **GET** `/imunizacoes/paciente/{id}` → Lista as vacinações de um paciente.

## 🚀 Como Testar
Use **Thunder Client**, **Postman** ou **cURL** para testar os endpoints. Exemplo de requisição `POST` para cadastrar um paciente:
```json
{
  "nome": "João Silva",
  "dataNascimento": "1990-05-10",
  "cpf": "12345678900"
}
```

## 📝 Licença
Este projeto está sob a licença MIT. Sinta-se à vontade para contribuir!

---
Desenvolvido por [Seu Nome](https://github.com/seu-usuario) 🚀

