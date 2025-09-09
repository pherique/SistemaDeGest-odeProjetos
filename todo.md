## Tarefas para o Sistema de Gestão de Projetos e Equipes

### Fase 1: Análise dos requisitos e planejamento da arquitetura
- [ ] Revisar todos os requisitos explícitos e implícitos.
- [ ] Definir a estrutura do banco de dados (tabelas, relacionamentos).
- [ ] Escolher entre Swing e JavaFX (considerando a complexidade e os requisitos).
- [ ] Esboçar a arquitetura MVC para o projeto Java.
- [ ] Planejar as telas principais e seus fluxos de navegação.

### Fase 2: Criação do banco de dados MySQL com scripts SQL
- [ ] Escrever o script SQL para criação do banco de dados.
- [ ] Escrever os scripts SQL para criação das tabelas (usuários, projetos, equipes, tarefas, etc.).
- [ ] Escrever scripts SQL para inserção de dados de teste (perfis de usuário, etc.).

### Fase 3: Estruturação do projeto Java com arquitetura MVC
- [x] Criar a estrutura de pastas do projeto Java (src, lib, etc.).
- [ ] Configurar o ambiente de desenvolvimento (IDE, dependências - **requer ação manual para adicionar o JDBC driver**).
- [x] Criar os pacotes para as camadas Model, View, Controller, DAO, Utils.

### Fase 4: Implementação das classes de modelo (entidades)
- [x] Criar classes para Usuário, Projeto, Equipe, Tarefa.
- [x] Definir atributos e métodos para cada entidade.

### Fase 5: Implementação da camada de acesso a dados (DAO)
- [x] Criar a classe de conexão com o banco de dados (DBConnection).
- [x] Implementar as classes DAO para cada entidade (UserDAO, ProjectDAO, etc.).
- [x] Implementar métodos CRUD (Create, Read, Update, Delete) para cada DAO.

### Fase 6: Implementação da camada de controle (Controllers)
- [x] Criar classes de controle para cada módulo (UserController, ProjectController, etc.).
- [x] Implementar a lógica de negócio e a interação entre View e Model/DAO.

### Fase 7: Criação das interfaces gráficas com Swing
- [x] Desenvolver a tela de login.
- [x] Desenvolver as telas de cadastro (Usuários, Projetos, Equipes, Tarefas).
- [x] Desenvolver as telas de listagem e edição.
- [x] Implementar a navegação entre as telas.

### Fase 8: Implementação da autenticação e controle de acesso
- [x] Implementar a lógica de autenticação na tela de login.
- [x] Implementar o controle de acesso baseado nos perfis de usuário (Administrador, Gerente, Colaborador).

### Fase 9: Implementação dos relatórios e dashboards
- [x] Desenvolver as telas para visualização de relatórios.
- [x] Implementar a lógica para gerar os relatórios (andamento de projetos, desempenho de colaborador, projetos em risco).

### Fase 10: Testes e refinamentos finais do sistema
- [x] Realizar testes unitários e de integração.
- [x] Corrigir bugs e otimizar o código.
- [x] Realizar testes de usabilidade da interface.

### Fase 11: Documentação e entrega do projeto completo
- [ ] Gerar a documentação do código.
- [ ] Preparar um README.md com instruções de como rodar o projeto.
- [ ] Empacotar o projeto para entrega.

