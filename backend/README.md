# CRUD de Alunos — Spring Boot + React

Projeto simples para demonstrar um CRUD: **listar, cadastrar, editar e excluir alunos**.

- Backend: Java 21, Spring Boot, JDBC e SQLite.
- Frontend: React + Vite.
- Sem login e sem autenticação.

## Pré-requisitos

- Docker Desktop aberto.
- Node.js instalado (para iniciar o React).

Não é necessário instalar Java, Gradle ou um servidor de banco de dados na máquina.

## Como rodar

Abra **dois terminais** dentro da pasta do projeto.

### Terminal 1 — API Java

```powershell
cd backend
docker compose up --build
```

A API estará em `http://localhost:8085`.

O Docker cria e preserva o banco SQLite no volume `alunos_data`.

Para parar, pressione `Ctrl + C` nesse terminal.

### Terminal 2 — frontend React

```powershell
cd frontend
npm.cmd install
npm.cmd run dev
```

Abra no navegador o endereço exibido pelo Vite. Normalmente será:

```text
http://localhost:5173
```

Se essa porta já estiver ocupada, o Vite pode usar `5174`, `5175` ou outra porta. Isso é normal: a API aceita o frontend em qualquer porta local.

> No Windows, use `npm.cmd` caso o PowerShell bloqueie o comando `npm`.

## Endereços importantes

| Serviço | Endereço |
| --- | --- |
| Frontend React | `http://localhost:5173` (ou a porta mostrada no terminal) |
| API | `http://localhost:8085/alunos` |

## Endpoints da API

| Método | URL | Ação |
| --- | --- | --- |
| GET | `/alunos` | Listar alunos |
| POST | `/alunos` | Cadastrar aluno |
| PUT | `/alunos/{id}` | Editar aluno |
| DELETE | `/alunos/{id}` | Excluir aluno |

Exemplo de aluno enviado no POST e PUT:

```json
{
  "nome": "Ana",
  "email": "ana@email.com",
  "idade": 20
}
```

## Alterou algum código? O que fazer?

### Alterou o backend Java

Exemplos: arquivos dentro de `backend/src/main/java`, `application.properties` ou `schema.sql`.

1. No terminal da API, pressione `Ctrl + C` para parar.
2. Rode novamente:

```powershell
docker compose up --build
```

O Spring Boot recompila e inicia o backend novamente.

### Alterou o frontend React

Exemplos: arquivos dentro de `frontend/src`, como `App.jsx` ou `style.css`.

Com o comando `npm.cmd run dev` aberto, basta **salvar o arquivo**. O Vite atualiza o navegador automaticamente. Se não atualizar, pressione `F5`.

### Alterou backend e frontend

Faça os dois procedimentos:

```powershell
# Terminal da API: pare com Ctrl + C e inicie novamente
docker compose up --build
```

```powershell
# Terminal do React: mantenha este comando aberto
cd frontend
npm.cmd run dev
```

### Alterou `frontend/package.json`

Instale as dependências novamente:

```powershell
cd frontend
npm.cmd install
npm.cmd run dev
```

### Alteraram `schema.sql` e querem recriar o banco do zero

> Atenção: o comando abaixo apaga todos os alunos cadastrados no Docker.

```powershell
docker compose down -v
docker compose up --build
```

## Estrutura do projeto

```text
src/main/java/                 # Backend Spring Boot
src/main/resources/schema.sql  # Tabela SQLite
frontend/                      # Frontend React
frontend/src/App.jsx           # Tela de CRUD
compose.yaml                   # Sobe a API e o banco SQLite no Docker
```
