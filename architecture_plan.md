
## Decisão da Interface Gráfica

Decidi utilizar **Swing** para a interface gráfica. Embora o JavaFX ofereça recursos mais modernos, o Swing é mais direto para a implementação de telas CRUD e relatórios para este tipo de sistema, permitindo um desenvolvimento mais ágil e focado nos requisitos funcionais.

## Arquitetura MVC (Model-View-Controller)

O projeto será estruturado seguindo o padrão MVC para garantir a separação de responsabilidades, modularidade e facilidade de manutenção.

### 1. Model (Modelo)
- **Entidades (POJOs)**: Classes que representam os dados do sistema e a lógica de negócios (e.g., `User`, `Project`, `Team`, `Task`).
- **DAO (Data Access Object)**: Classes responsáveis pela comunicação com o banco de dados. Cada entidade terá seu DAO correspondente (e.g., `UserDAO`, `ProjectDAO`), contendo métodos para operações CRUD (Create, Read, Update, Delete).
- **Conexão com o Banco de Dados**: Uma classe utilitária (`DBConnection`) para gerenciar a conexão com o MySQL.

### 2. View (Visão)
- **Telas Swing**: Classes que representam a interface do usuário (e.g., `LoginView`, `UserManagementView`, `ProjectManagementView`, `TaskManagementView`, `ReportView`).
- Serão responsáveis por exibir os dados ao usuário e capturar suas interações.

### 3. Controller (Controlador)
- **Controladores**: Classes que atuam como intermediários entre a View e o Model (e.g., `UserController`, `ProjectController`, `TaskController`).
- Receberão as ações do usuário da View, processarão a lógica de negócios (interagindo com os DAOs para acessar o Model) e atualizarão a View conforme necessário.

## Planejamento das Telas Principais

Serão desenvolvidas as seguintes telas principais:

1.  **Tela de Login**: Autenticação de usuários.
2.  **Tela Principal (Dashboard)**: Após o login, exibirá um dashboard com um resumo geral e opções de navegação para os módulos.
3.  **Gestão de Usuários**: CRUD para usuários (apenas para Administradores).
4.  **Gestão de Projetos**: CRUD para projetos (Administradores e Gerentes).
5.  **Gestão de Equipes**: CRUD para equipes e alocação de membros (Administradores e Gerentes).
6.  **Gestão de Tarefas**: CRUD para tarefas, vinculação a projetos e usuários (Administradores, Gerentes e Colaboradores).
7.  **Relatórios e Dashboards**: Visualização de relatórios de andamento de projetos, desempenho de colaboradores e projetos em risco.

Cada tela terá funcionalidades de cadastro, edição, visualização e exclusão (onde aplicável), com validações e feedback ao usuário. A navegação será intuitiva, com menus e botões claros.

