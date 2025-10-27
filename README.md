# 🩺 Prontuário Médico (Código Legado)

Este repositório contém um projeto acadêmico desenvolvido em 2023 utilizando as tecnologias **Java + Swing + MySQL**, esse projeto será utilizado como **código legado** para o trabalho de refatoração e boas práticas de Clean Code.

O objetivo é partir de uma versão funcional, mas com más práticas, e aplicar refatorações para melhorar **legibilidade, manutenibilidade e eficiência** do código, sem alterar sua funcionalidade principal.

---

## 🚀 Tecnologias
- Java 17+
- Maven
- MySQL 8.x
- Swing (Interface Gráfica)

---

## 📂 Estrutura do Projeto Original

ProntuarioMedico/  
│  
├── src/  
│ ├── main/  
│ │ ├── java/  
│ │ │ ├── BancoDeDados/  
│ │ │ ├── componentes_interface/  
│ │ │ ├── entidades/  
│ │ │ ├── interfaces/  
│ │ │ └──   
│ │ └── resources/  
│ │  
│ └── test/
│  
├── pom.xml  

---

## 🛠️ Como rodar o projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/natancardosoo/prontuario-medico-refactored
```
### 2. Após efetuar o download do MySQL, configure o banco de dados

No MySQL, crie o banco e as tabelas
```sql
CREATE DATABASE bd_UBS;
USE bd_UBS;
SOURCE bd_UBS.sql;
```
### 3. Ajustar credenciais

No arquivo `DatabaseConnection.java`, edite usuário e senha do MySQL conforme sua máquina:
```java
private static final String HOST = "localhost";
private static final String PORT = "3306";
private static final String DATABASE = "bd_UBS";
private static final String USER = "root";
private static final String PASSWORD = "1234";
```
### 4. Download das fontes

No diretório do projeto, siga o caminho 'src\main\resources\fonts' e efetue o download de todas as fontes necessárias.

### 5. Executar o projeto via Maven
No terminal do diretório principal do projeto, execute o código abaixo:
```bash
mvn clean compile exec:java
```
Ou abra no IntelliJ e rode a classe `Main`.
---

---

## 📌 Objetivo Acadêmico
Este projeto faz parte do trabalho **“Refatoração de Código e Boas Práticas do Clean Code”**.  
Etapas previstas:
1. Código legado funcional (versão atual).
2. Refatoração (Clean Code, SOLID, DRY, KISS, YAGNI).
3. Implementação de testes unitários.
4. Relatório em PDF e apresentação no YouTube.

---

## 👥 Autores
- Natan Cardoso de Oliveira 
- Breno Vargas Pereira
- Gabriel Antonio Branco Ribeiro Oraggio
