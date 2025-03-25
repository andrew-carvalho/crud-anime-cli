# crud-anime-cli

Este repositório contém uma aplicação de linha de comando (CLI) para o gerenciamento de animes e produtoras, permitindo
operações de CRUD (criação, leitura, atualização e exclusão) onde cada anime está associado a uma produtora.

## Requisitos

- Docker
- Java
- Maven

## Instalação

1. Clone este repositório com o comando:

```shell
git clone https://github.com/andrew-carvalho/crud-anime-cli.git
```

2. Em seguida navegue para o diretório do projeto:

```shell
cd crud-anime-cli
```

3. Execute o comando do Docker compose para copiar o container com o banco de dados:

```shell
docker compose up
```

4. Após o container ser inicializado, é necessário importar o arquivo `dump.sql` no banco de dados, para a criação
   correta das tabelas necessárias para o programa.
5. Abra o projeto em sua IDE de preferência e rode o programa pelo
   arquivo [Program.java](src/main/java/com/andrew/application/Program.java).

## Como Usar

Após iniciar a aplicação, utilize os comandos disponíveis na interface para realizar operações de CRUD nos animes e
produtoras.